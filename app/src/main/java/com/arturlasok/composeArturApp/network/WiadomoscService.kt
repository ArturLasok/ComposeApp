package com.arturlasok.composeArturApp.network


import com.arturlasok.composeArturApp.network.responses.WiadomoscSearchResponse
import com.arturlasok.composeArturApp.network.model.WiadomoscDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WiadomoscService {


    // Odpowiedz w postaci pelnej do przetworzenia w search response
    @GET("testjson.php")
    suspend fun search(
        @Header("Authorization") token:String,
        @Query("page") page: Int,
        @Query("kategoriaid") query: String

    ): WiadomoscSearchResponse

    // Odpowiedz w postaci jednej wiadomosci WiadomoscDto
    @GET("testjson.php")
    suspend fun get(
        @Header("Authorization") token:String,
        @Query("newsid") id: Int
    ): WiadomoscDto
}