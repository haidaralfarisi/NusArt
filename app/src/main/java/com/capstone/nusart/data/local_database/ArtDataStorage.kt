package com.capstone.nusart.data.local_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.nusart.data.api.response.ListArt
import com.farhanadi.horryapp.user_data.local_database.RemotePaginationKeys
import com.farhanadi.horryapp.user_data.local_database.RemotePaginationKeysDao

@Database(
    entities = [ListArt::class, RemotePaginationKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ArtDataStorage : RoomDatabase() {

    abstract fun ArtDao(): ArtDataAccess
    abstract fun remoteKeysDao(): RemotePaginationKeysDao

    companion object {
        @Volatile
        private var INSTANCE: ArtDataStorage? = null

        fun getDatabase(context: Context): ArtDataStorage {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): ArtDataStorage {
            return Room.databaseBuilder(
                context.applicationContext,
                ArtDataStorage::class.java, "art_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
