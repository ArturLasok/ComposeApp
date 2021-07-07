package com.arturlasok.composeArturApp.presentation.components.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserAdminProfile(userAdminViewModel: UserAdminViewModel) {
   val userId = FirebaseAuth.getInstance().currentUser?.uid
   Text("Profil from firebase $userId, AppUser imie:${userAdminViewModel.user_name.value}")

}