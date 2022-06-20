package com.ingelmogarcia.appnapptilus.data.model

import com.google.gson.annotations.SerializedName

data class DataPageModel(
    @SerializedName("current") val current: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("results") val results: List<DataPageResultsModel>,
)

data class DataPageResultsModel(
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val urlImage: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("age") val age: Int,
    @SerializedName("country") val country: String,
    @SerializedName("height") val height: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: DataPageResultFavoriteModel
)

data class DataPageResultFavoriteModel(
    @SerializedName("color") val color: String,
    @SerializedName("food") val food: String,
    @SerializedName("random_string") val random_string: String,
    @SerializedName("song") val song: String
)