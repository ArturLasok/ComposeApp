package com.arturlasok.composeArturApp.domain.model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.firebase.auth.FirebaseAuth

class AppUser() {

private var pid:Long? = 0
private var pimie:MutableState<String?> = mutableStateOf("anonymous")
private var pnazwisko:MutableState<String?> = mutableStateOf("anonymous")


init {
    Log.d(TAG, "MAKE NEW INIT ${hashCode()}")



}
    fun get_puid() : String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }
    fun get_pimie() : MutableState<String?> {
        return pimie
    }
    fun get_mail(): String?
    { return FirebaseAuth.getInstance().currentUser?.email }
    fun get_pnazwisko() :  MutableState<String?> {
        return pnazwisko
    }
    fun get_hash() : Int  { return hashCode() }

    fun set_pid(pid: Long?) { this.pid = pid }
    fun set_pimie(pimie: String?) { this.pimie.value = pimie }
    fun set_pnazwisko(pnazwisko: String?) { this.pnazwisko.value = pnazwisko }
    fun getUserData(): Map<String,MutableState<String?>> {
        val pimie = get_pimie()
        val pnazwisko = get_pnazwisko()
        val pmail:MutableState<String?> = mutableStateOf(""); pmail.value = get_mail()


        return mapOf(Pair("imie",pimie),Pair("nazwisko",pnazwisko),Pair("mail",pmail))
    }
}