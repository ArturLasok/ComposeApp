package com.arturlasok.composeArturApp.network.responses


import com.google.gson.annotations.SerializedName
import com.arturlasok.composeArturApp.network.model.WiadomoscDto

// Dotyczy pelnej odpowiedzi i tworzy liste wiadomosciDto
data class WiadomoscSearchResponse(

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var wszystkiewiadomosci: List<WiadomoscDto>
)

