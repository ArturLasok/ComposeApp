package com.arturlasok.composeArturApp.network


import com.arturlasok.composeArturApp.network.model.UserDto
import com.arturlasok.composeArturApp.network.responses.WiadomoscSearchResponse
import com.arturlasok.composeArturApp.network.model.WiadomoscDto
import retrofit2.http.*

interface WiadomoscService {
    // Odpowiedz w postaci pelnej do przetworzenia w search response
    @FormUrlEncoded
    @POST("profil.php")
    suspend fun put_user_to_mysql(
        @Header("Authorization") token:String,
        @Field("puid") puid:String,


        ): String

    // Odpowiedz w postaci pelnej do przetworzenia w search response
    @GET("profil.php")
    suspend fun search_user(
        @Header("Authorization") token:String,
        @Query("puid") puid:String,


    ): UserDto

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