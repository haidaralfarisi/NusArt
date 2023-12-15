package com.farhanadi.horryapp.user_data.local_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemotePaginationKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemotePaginationKeys>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: Int): RemotePaginationKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()
}