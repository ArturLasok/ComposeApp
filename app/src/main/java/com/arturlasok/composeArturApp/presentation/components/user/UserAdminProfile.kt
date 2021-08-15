package com.arturlasok.composeArturApp.presentation.components.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.util.Log
import androidx.camera.core.internal.utils.ImageUtil
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toAdaptiveIcon
import androidx.core.graphics.drawable.toIcon
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.firebase.auth.FirebaseAuth
import ruchradzionkow.ruchappartur.R
import java.util.concurrent.Executor

@Composable
fun UserAdminProfile(
    userAdminViewModel: UserAdminViewModel,

) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ){



        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 14.dp, end = 6.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5,
            text = "Twój profil w ComposeApp")
        Row() {

            Log.i(TAG, "OBRAZEK: ${userAdminViewModel.getAppUser().get_pimage().value}")



                if(userAdminViewModel.getAppUser().get_pimage().value!=null) {
                   val image= userAdminViewModel.getAppUser().get_pimage().value


                    if (image != null) {
                        Log.i(TAG,"Image x,y ${image.height} ${image.width}")
                        Log.i(TAG,"Image byte count ${image.byteCount}")

                        Log.i(TAG,"as Imagebit x,y ${image.asImageBitmap().height} ${image.asImageBitmap().width}")
                        Log.i(TAG,"as Imagebit colorspace ${image.asImageBitmap().colorSpace}")

Image(bitmap = (image.asImageBitmap()), contentDescription = "no",modifier = Modifier.size(300.dp,300.dp))

                        Icon(

                            image.asImageBitmap(),


                            contentDescription = "Twoje zdjęcie",
                            modifier = Modifier
                                .size(180.dp)
                                .clickable(
                                    true,
                                    onClick = { }),

                        )




                    }


                } else {
                    Icon(

                    Icons.Filled.AccountBox,

                    contentDescription = "Twoje zdjęcie",
                    modifier = Modifier
                        .size(180.dp)
                        .clickable(
                            true,
                            onClick = { }),
                    tint = MaterialTheme.colors.onBackground
                    )
                       }





            Column(
                modifier = Modifier.height(150.dp),
                verticalArrangement = Arrangement.Bottom
            )

            {   Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = {

                    userAdminViewModel.setEvent(UserAdminEvent.FotoActivityIntent)

                })
                    ,
                ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Dodaj zdjęcie",
                    modifier = Modifier
                        .size(32.dp),

                    tint = MaterialTheme.colors.onBackground
                )
                Text(
                    style = MaterialTheme.typography.h6,
                    text="USTAW ZDJĘCIE")
            }
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable { }
                    ,
                ){

                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Usuń zdjęcie",
                        modifier = Modifier
                            .size(32.dp),
                        tint = Color.Red
                    )
                    Text(
                        style = MaterialTheme.typography.h6,
                        text="USUŃ ZDJĘCIE")
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 14.dp, end = 6.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5,
            text = "Uzupełnij proszę swój profil")
        //Zmiana imienia uzytkownika
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            value = userAdminViewModel.imie_nowe.value,
            onValueChange = {
                userAdminViewModel.saveButtonEnable.value = true
                if(it.length<100) { userAdminViewModel.imie_nowe.value = it}; },
            label = { Text(
                color = Color.White,
                text = "Twoje Imię:"
            ) }

        )
        //Zmiana nazwiska uzytkownika
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            value = userAdminViewModel.nazwisko_nowe.value,
            onValueChange = {
                userAdminViewModel.saveButtonEnable.value = true
                if(it.length<100) { userAdminViewModel.nazwisko_nowe.value = it}; },
            label = { Text(
                color = Color.White,
                text = "Twoje Nazwisko:"
            ) }

        )
        Button(
            enabled = userAdminViewModel.saveButtonEnable.value,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground),
            modifier = Modifier

                .padding(start = 16.dp, top = 16.dp,end = 10.dp, bottom = 20.dp),
            onClick = {
                userAdminViewModel.saveButtonEnable.value = false
                userAdminViewModel.setEvent(UserAdminEvent.UpdateAppUserClass)
                userAdminViewModel.setEvent(UserAdminEvent.UserAdminProfileSave)

            },
            content = { Text(color = Color.White, text ="ZAPISZ") }
        )




    }

}




