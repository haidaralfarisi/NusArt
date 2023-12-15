package com.farhanadi.horryapp.user_data.local_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemotePaginationKeys(

    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
