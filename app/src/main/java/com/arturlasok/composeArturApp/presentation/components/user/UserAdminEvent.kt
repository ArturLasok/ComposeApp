package com.arturlasok.composeArturApp.presentation.components.user

sealed class UserAdminEvent {

    object UserAdminProfileUpdate : UserAdminEvent()
    object UserAdminProfileAdd:UserAdminEvent()
    object UserAdminProfileSave:UserAdminEvent()
    object UpdateAppUserClass:UserAdminEvent()
    object UserAdminFieldsEditUpdate:UserAdminEvent()
    object FotoActivityIntent:UserAdminEvent()


}
