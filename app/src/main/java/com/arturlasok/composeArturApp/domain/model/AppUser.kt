package com.arturlasok.composeArturApp.domain.model

import android.util.Log
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.firebase.auth.FirebaseAuth

class AppUser() {

private var pid:Long? = 0
private var pimie: String? = "anonymous"
private var pnazwisko: String? = "anonymous"


init {
    Log.d(TAG, "MAKE NEW INIT ${hashCode()}")
}
    fun get_puid() : String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }
    fun get_pimie() : String? {
        return pimie
    }
    fun get_mail(): String?
    { return FirebaseAuth.getInstance().currentUser?.email }
    fun get_pnazwisko() : String? {
        return pnazwisko
    }
    fun get_hash() : Int  { return hashCode() }

    fun set_pid(pid: Long?) { this.pid = pid }
    fun set_pimie(pimie: String?) { this.pimie = pimie }
    fun set_pnazwisko(pnazwisko: String?) { this.pnazwisko = pnazwisko }
    fun getUserData(): Map<String,String?> {
        val pimie = get_pimie()
        val pnazwisko = get_pnazwisko()
        val pmail = get_mail()


        return mapOf(Pair("imie",pimie),Pair("nazwisko",pnazwisko),Pair("mail",pmail))
    }
}