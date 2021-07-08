package com.arturlasok.composeArturApp.presentation.components.user

sealed class UserAdminEvent {

    object UserAdminProfileUpdate : UserAdminEvent()
    object UserAdminProfileAdd:UserAdminEvent()


}
