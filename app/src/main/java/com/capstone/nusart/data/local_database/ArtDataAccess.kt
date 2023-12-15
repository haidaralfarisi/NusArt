package com.capstone.nusart.data.local_database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.nusart.data.api.response.ListArt

@Dao
interface ArtDataAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArt(stories: List<ListArt>)

    @Query("SELECT * FROM art")
    fun getArt(): PagingSource<Int, ListArt>

    @Query("DELETE FROM art")
    suspend fun deleteAll()
}