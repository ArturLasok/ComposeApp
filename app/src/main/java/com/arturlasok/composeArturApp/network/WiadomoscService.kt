package com.arturlasok.composeArturApp.network


import com.arturlasok.composeArturApp.network.model.UserDto
import com.arturlasok.composeArturApp.network.responses.WiadomoscSearchResponse
import com.arturlasok.composeArturApp.network.model.WiadomoscDto
import retrofit2.http.*

interface WiadomoscService {
    @FormUrlEncoded
    @POST("profil.php")
    suspend fun update_user_to_mysql(
        @Header("Authorization") token:String,
        @Field("puid") puid:String,
        @Field("pimie") pimie:String,
        @Field("pnazwisko") pnazwisko:String,
        @Field("saveProfile") saveProfile:String,

    ): String

    // Zapis uzytkownika do bazy jezeli jeszcze nie istnieje
    @FormUrlEncoded
    @POST("profil.php")
    suspend fun put_user_to_mysql(
        @Header("Authorization") token:String,
        @Field("puid") puid:String,
        ): String

    // Wyszukiawnie uzytkownika w bazie mysql
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