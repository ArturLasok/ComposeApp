package com.arturlasok.composeArturApp.presentation.components

sealed class ListaWiadomosciEvent {

    object NewSearchEvent : ListaWiadomosciEvent()

    object NextPageEvent:ListaWiadomosciEvent()

}
