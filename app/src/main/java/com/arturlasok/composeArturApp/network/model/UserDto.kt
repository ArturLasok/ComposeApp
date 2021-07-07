package com.arturlasok.composeArturApp.network.model

import com.google.gson.annotations.SerializedName

data class UserDto(

    //pid in mysql database
    @SerializedName("pid")
    var netpid: Long? = null,

    //puid from firebase auth
    @SerializedName("puid")
    var netpuid: String? = null,

    @SerializedName("pimie")
    var netpimie: String? = null,

    @SerializedName("pnazwisko")
    var npnazwisko: String? = null,

    @SerializedName("pimg")
    var pimg: String? = null



)