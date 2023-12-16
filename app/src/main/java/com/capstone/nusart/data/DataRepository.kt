package com.capstone.nusart.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.nusart.data.api.ApiService
import com.capstone.nusart.data.api.response.ListArt
import com.capstone.nusart.data.api.response.LoginResponse
import com.capstone.nusart.data.api.response.RegisterResult
import com.capstone.nusart.data.api.response.UploadResult
import com.capstone.nusart.data.local_database.ArtDataStorage
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DataRepository(
    private val artDatabase: ArtDataStorage,
    private val apiService: ApiService
) {
    fun register(
        name: String,
        email: String,
        pass: String
    ): LiveData<ResultResource<RegisterResult>> = liveData {
        emit(ResultResource.Loading)
        try {
            val response = apiService.register(name, email, pass)
            emit(ResultResource.Success(response))
        } catch (e: Exception) {
            Log.d("register", e.message.toString())
            emit(ResultResource.Error(e.message.toString()))
        }
    }

    fun login(email: String, pass: String): LiveData<ResultResource<LoginResponse>> = liveData {
        emit(ResultResource.Loading)
        try {
            val response = apiService.login(email, pass)
            emit(ResultResource.Success(response))
        } catch (e: Exception) {
            Log.d("login", e.message.toString())
            emit(ResultResource.Error(e.message.toString()))
        }
    }

    fun getArt(token: String): LiveData<PagingData<ListArt>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = ListPaginationMediator(artDatabase, apiService, token),
            pagingSourceFactory = {
                artDatabase.ArtDao().getArt()
            }
        ).liveData
    }

    fun postImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
        token: String,
        multiPort: String,
    ): LiveData<ResultResource<UploadResult>> = liveData {
        emit(ResultResource.Loading)
        try {
            val response = apiService.postImage(file, description, lat, lon, token, multiPort)
            emit(ResultResource.Success(response))
        } catch (e: Exception) {
            Log.d("post_image", e.message.toString())
            emit(ResultResource.Error(e.message.toString()))
        }
    }


}
