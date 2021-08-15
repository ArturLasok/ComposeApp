package com.arturlasok.composeArturApp.presentation.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ruchradzionkow.ruchappartur.R


@Composable
fun UserRegister(navController: NavController, scaffoldState: ScaffoldState, scope: CoroutineScope,userViewViewModel: UserViewViewModel) {




        val email =  rememberSaveable { mutableStateOf("") }
        val password =  rememberSaveable { mutableStateOf("") }
        val password2 =  rememberSaveable { mutableStateOf("") }

    //val keyboardController = LocalSoftwareKeyboardController.current
        Column(modifier = Modifier
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

                Text(
                    modifier = Modifier.padding(top = 40.dp, bottom = 40.dp),
                    text = "REJESTRACJA",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Medium
                )
            }
       // Email input ------------------------------------------------------------------------
            var ikon_email = rememberSaveable { mutableStateOf(false) }
            TextField(


                trailingIcon = {

                    if(ikon_email.value)
                    {
                        Icon(

                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_ver_ok),
                            contentDescription = "Email true",
                            tint = Color.Green
                        )


                    } else
                    {
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
                    userViewViewModel.registerButtonEnable.value = true
                    email.value = it
                    ikon_email.value = email.value.isEmailValid()
                },
                label = { Text(
                    color = Color.White,
                    text = "Email:"
                ) }

            )
        // password input -------------------------------------------------------------------------------------
            var ikon = remember { mutableStateOf(false) }
            var trans : MutableState<VisualTransformation> = remember { mutableStateOf(
                VisualTransformation.None) }

            TextField(
                trailingIcon = {

                    if(ikon.value)
                    {
                        Icon(
                            modifier = Modifier.clickable { ikon.value = false  },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_visibility_on),
                            contentDescription = "Password visibility is ON")
                        trans.value = VisualTransformation.None
                    } else
                    {
                        Icon(
                            modifier = Modifier.clickable {  ikon.value = true },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic__visibility_off),
                            contentDescription = "Password visibility is OFF")
                        trans.value = PasswordVisualTransformation()
                    }


                },
                visualTransformation = trans.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                singleLine = true,
                value = password.value,
                onValueChange = { userViewViewModel.registerButtonEnable.value = true
                    if(it.length<20) { password.value = it}; },
                label = { Text(
                    color = Color.White,
                    text = "Hasło:"
                ) }

            )
            TextField(
                trailingIcon = {

                    if(ikon.value)
                    {
                        Icon(
                            modifier = Modifier.clickable { ikon.value = false  },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_visibility_on),
                            contentDescription = "Password visibility is ON")
                        trans.value = VisualTransformation.None
                    } else
                    {
                        Icon(
                            modifier = Modifier.clickable {  ikon.value = true },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic__visibility_off),
                            contentDescription = "Password visibility is OFF")
                        trans.value = PasswordVisualTransformation()
                    }


                },
                visualTransformation = trans.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                singleLine = true,
                value = password2.value,
                onValueChange = { userViewViewModel.registerButtonEnable.value = true
                    if(it.length<20) { password2.value = it}; },
                label = { Text(
                    color = Color.White,
                    text = "Powtórz hasło:"
                ) }

            )

            // klawisz zatwierdzajacy formularz -------------------------------------------------------------------
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.DarkGray)
                ,
                enabled = userViewViewModel.registerButtonEnable.value,
                onClick = {

                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    //keyboardController?.hide()
                    userViewViewModel.checkRegisterData(email,password,password2,scaffoldState,navController)

                },
            ){
                Text("ZAREJESTRUJ SIĘ")
            }


    }






}
