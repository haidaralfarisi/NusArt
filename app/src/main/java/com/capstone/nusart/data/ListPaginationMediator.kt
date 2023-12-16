package com.capstone.nusart.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.capstone.nusart.data.api.ApiService
import com.capstone.nusart.data.api.response.ListArt
import com.capstone.nusart.data.local_database.ArtDataStorage
import com.capstone.nusart.ui_page.utils.wrapEspressoIdlingResource
import com.farhanadi.horryapp.user_data.local_database.RemotePaginationKeys

@OptIn(ExperimentalPagingApi::class)
class ListPaginationMediator(
    private val Artdatabase: ArtDataStorage,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, ListArt>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
         state: PagingState<Int, ListArt>
    ): MediatorResult {


        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        wrapEspressoIdlingResource {
            return try {
                val responseData =
                    apiService.getArt(page, state.config.pageSize, token).listStory
                val endOfPaginationReached = responseData.isEmpty()

                Artdatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        Artdatabase.remoteKeysDao().deleteRemoteKeys()
                        Artdatabase.ArtDao().deleteAll()
                    }
                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = responseData.map {
                        RemotePaginationKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    Artdatabase.remoteKeysDao().insertAll(keys)
                    Artdatabase.ArtDao().addArt(responseData)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (exception: Exception) {
                Log.d("Remote Mediator", exception.message.toString())
                MediatorResult.Error(exception)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ListArt>
    ): RemotePaginationKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            Artdatabase.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ListArt>
    ): RemotePaginationKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            Artdatabase.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ListArt>
    ): RemotePaginationKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                Artdatabase.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
