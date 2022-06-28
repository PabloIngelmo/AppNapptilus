package com.ingelmogarcia.appnapptilus.data.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaDetailModel(
    @SerializedName("last_name") val last_name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("quota") val quota: String,
    @SerializedName("height") val height: Int,
    @SerializedName("first_name") val first_name: String,
    @SerializedName("country") val country: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("email") val email: String,
    @SerializedName("favorite") val favorite: OompaLoompaDetailFavoriteModel
)

data class OompaLoompaDetailFavoriteModel(
    @SerializedName("color") val color: String,
    @SerializedName("food") val food: String,
    @SerializedName("random_string") val random_string: String,
    @SerializedName("song") val song: String
)