package com.capstone.nusart.data.api.response

import com.google.gson.annotations.SerializedName

data class RegisterResult(
    @SerializedName("error") val error: Boolean?,
    @SerializedName("message") val message: String?
)
