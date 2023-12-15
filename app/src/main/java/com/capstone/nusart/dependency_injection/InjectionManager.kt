package com.capstone.nusart.dependency_injection

import android.content.Context
import android.provider.ContactsContract.Data
import com.capstone.nusart.data.Datarepository
import com.capstone.nusart.data.api.ApiClient
import com.capstone.nusart.data.local_database.ArtDataStorage


object InjectionManager {
    fun provideRepository(context: Context): Datarepository {
        val database = ArtDataStorage.getDatabase(context)
        val apiService = ApiClient.getApiService()
        return Datarepository(database, apiService)
    }
}