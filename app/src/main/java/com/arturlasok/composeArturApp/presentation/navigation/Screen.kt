package com.arturlasok.composeArturApp.presentation.navigation


sealed class Screen(
    val route: String,
){
    object ListaWiadomosci: Screen("listaWiadomosci")

    object SzczegolyWiadomosci: Screen("szczegolyWiadomosci")

    object SettingsView: Screen("SettingsView")

    object UserView: Screen("UserView")

    object UserAdmin: Screen("UserAdmin")
}
