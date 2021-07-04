package com.arturlasok.composeArturApp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wiadomosci")
data class WiadomoscEntity(

    // Value from API
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var cacheid: Int?,

    // Value from API
    @ColumnInfo(name = "tytul")
    var cachetytul: String?,

    // Value from API
    @ColumnInfo(name = "tresc")
    var cachetresc: String?,

    // Value from API
    @ColumnInfo(name = "url")
    var cacheurl: String?,

    // Value from API
    @ColumnInfo(name = "img")
    var cacheimg: String?,

)