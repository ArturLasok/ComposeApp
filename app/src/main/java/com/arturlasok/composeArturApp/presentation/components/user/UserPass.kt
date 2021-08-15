package com.arturlasok.composeArturApp.presentation.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import ruchradzionkow.ruchappartur.R


@Composable
fun UserPass(
    navController: NavController,
    scaffoldState : ScaffoldState,
    scope :CoroutineScope,
    userViewViewModel: UserViewViewModel
    ) {


    val email = rememberSaveable { mutableStateOf("") }
    //val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())

    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            //Icon(Icons.Outlined.AccountCircle, contentDescription = "Login screen")
            Text(
                modifier = Modifier.padding(top = 40.dp, bottom = 40.dp),
                text = "ODZYSKIWANIE HASŁA",
                color = Color.DarkGray,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Medium
            )
        }
        var ikon_email = rememberSaveable { mutableStateOf(false) }
        TextField(


            trailingIcon = {

                if (ikon_email.value) {
                    Icon(

                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_ver_ok),
                        contentDescription = "Email true",
                        tint = Color.Green
                    )


                } else {
                    Icon(

                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_ver_not),
                        contentDescription = "Email false",
                        tint = Color.Red

                    )

                }


            },

            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            value = email.value,

            onValueChange = {
               userViewViewModel.passRecButtonEnable.value = true
                email.value = it
                ikon_email.value = email.value.isEmailValid()
            },
            label = {
                Text(
                    color = Color.White,
                    text = "Email:"
                )
            }

        )




        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.DarkGray),
            enabled = userViewViewModel.passRecButtonEnable.value,
            onClick = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                //keyboardController?.hide()
                userViewViewModel.checkPassRecoveryData(email,scaffoldState,navController)

            },
        ) {
            Text("RESETUJ HASŁO")
        }
    }
}



