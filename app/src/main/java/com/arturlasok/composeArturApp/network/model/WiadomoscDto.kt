package com.arturlasok.composeArturApp.network.model

import com.google.gson.annotations.SerializedName

data class WiadomoscDto(

    @SerializedName("id")
    var netid: Int? = null,

    @SerializedName("tytul")
    var nettytul: String? = null,

    @SerializedName("tresc")
    var nettresc: String? = null,

    @SerializedName("url")
    var neturl: String? = null,

    @SerializedName("img")
    var netimg: String? = null


) {
}