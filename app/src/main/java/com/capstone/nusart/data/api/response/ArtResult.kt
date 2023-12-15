package com.capstone.nusart.data.api.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArtResponse(
    @SerializedName("listStory") val listStory: List<ListArt>,
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)

@Parcelize
@Entity(tableName = "art")
data class ListArt(
    @SerializedName("image_url") val imageurl: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist : String,
    @SerializedName("genre") val genre : String,
    @SerializedName("era") val era : String,
    @SerializedName("description") val description: String,
    @PrimaryKey @SerializedName("id") val id: Int,
) : Parcelable