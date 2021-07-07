package com.arturlasok.composeArturApp.presentation.components.user

sealed class UserAdminEvent {

    object UserAdminProfileEdit : UserAdminEvent()
    object UserAdminProfileAdd:UserAdminEvent()


}
