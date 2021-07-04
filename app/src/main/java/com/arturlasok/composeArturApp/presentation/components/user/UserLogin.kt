package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ruchradzionkow.ruchappartur.R
import java.util.regex.Pattern

@ExperimentalComposeUiApi
@Composable
fun UserLogin(
       navController: NavController,
       scaffoldState: ScaffoldState,
       scope:CoroutineScope,
       userViewViewModel: UserViewViewModel,
)
{



    val email =  rememberSaveable { mutableStateOf("") }
    val password =  rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
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
                text = "LOGOWANIE",
                color = Color.DarkGray,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Medium
            )
        }
        val ikon_email = rememberSaveable { mutableStateOf(false) }
        TextField(

            //isError = true,
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
                userViewViewModel.loginButtonEnable.value = true
                email.value = it
                ikon_email.value = email.value.isEmailValid()
            },
            label = { Text(
                color = Color.White,
                text = "Email:"
            ) }

        )

        val ikon = remember { mutableStateOf(false) }
        val trans : MutableState<VisualTransformation> = remember { mutableStateOf(
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

dfd
            },
            visualTransformation = trans.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            value = password.value,
            onValueChange = { userViewViewModel.loginButtonEnable.value = true; if(it.length<20) { password.value = it}; },
            label = { Text(
                color = Color.White,
                text = "Hasło:"
            ) }

        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.DarkGray)
            ,
            enabled = userViewViewModel.loginButtonEnable.value,
            onClick = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                keyboardController?.hide()

                userViewViewModel.checkLoginData(email,password,scaffoldState,navController,scope)

            },
        ){


            Text("ZALOGUJ")
        }

        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end=10.dp),horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Jeżeli nie posiadasz konta to ",)
            Text(modifier = Modifier
                .clickable(enabled = true,onClick = {navController.navigate(Screen.UserView.route+"/2")}),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                text = "ZAREJESTRUJ SIĘ.",

            )


        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end=10.dp),horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Aby odzyskać hasło ",)
            Text(modifier = Modifier
                .clickable(enabled = true,onClick = {navController.navigate(Screen.UserView.route+"/3")}),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                text = "KLIKNIJ TUTAJ",)
        }

    }




}
//Weryfikacja maila
fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}


