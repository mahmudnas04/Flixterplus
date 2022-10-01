package com.example.flixter
import com.google.gson.annotations.SerializedName

class FlixterPlus {


    @JvmField
    @SerializedName("title")
    var title: String? = null


    //TODO bookImageUrl
    @SerializedName("film_image")
    var filmImageUrl : String? = null

    //TODO description
    @SerializedName("description")
    var description: String? = null
}