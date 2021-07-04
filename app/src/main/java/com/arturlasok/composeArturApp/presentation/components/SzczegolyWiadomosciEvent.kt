package com.arturlasok.composeArturApp.presentation.components

sealed class SzczegolyWiadomosciEvent {

   data class GetWiadomoscEvent(val id: Int)  :SzczegolyWiadomosciEvent()



}
