package com.arturlasok.composeArturApp.presentation.components.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserAdminProfile(){
   val userId = FirebaseAuth.getInstance().currentUser?.uid
   Text("Profil $userId")
}