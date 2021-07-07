package com.arturlasok.composeArturApp.presentation.components.user

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.arturlasok.composeArturApp.presentation.navigation.Screen
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewViewModel @Inject constructor(



    ): ViewModel() {
    //
    val passRecButtonEnable = mutableStateOf(true)
    //login register enable
    val registerButtonEnable = mutableStateOf(true)
    //login button enable
    val loginButtonEnable = mutableStateOf(true)
    //error on firebase operation login, register, pass recovery
    val login_res = mutableStateOf( "")
    //loading on screen
    val loading = mutableStateOf(false)
    //check login password
    fun checkLoginPassword(password: MutableState<String>) : Boolean {
       return (password.value.length>7)
    }
    //show firebase auth error
    fun showAuthError(scaffoldState: ScaffoldState){
       loading.value=false
       viewModelScope.launch {

            scaffoldState.snackbarHostState.showSnackbar(
                login_res.value,
                "ROZUMIEM",
                SnackbarDuration.Short

            )
           passRecButtonEnable.value = true
           loginButtonEnable.value= true
           registerButtonEnable.value=true
        }


    }

    // Odzyskiwanie hasła
    fun checkPassRecoveryData(email: MutableState<String>,scaffoldState: ScaffoldState,navController: NavController){
        loading.value = true
        passRecButtonEnable.value =  false
        if (email.value.isEmailValid()) {
            login_res.value = ""
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.value)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        loading.value=false
                        //Sprawdzenie danych i nawigacja do ustawien uzytkownika UserAdmin
                        val route = Screen.UserView.route + "/4"
                        navController.navigate(route)
                    } else {
                        login_res.value = task.exception?.message.toString()

                        if ("There is no user record" in login_res.value) login_res.value =
                            "Użytkownik o takim adresie nie jest zarejestrowany"

                        if ("A network error" in login_res.value) login_res.value =
                            "Problem z połączeniem. Spróbuj później"
                        showAuthError(scaffoldState)
                    }

                }


        } else {

            login_res.value = "Nie prawidłowy adres email"
            showAuthError(scaffoldState)
        }


    }
    //Weryfikacja hasel
    fun isPassRegOk(password : MutableState<String>, password2: MutableState<String>): Boolean {
        return password.value.length>7 && password2.value.length>7 && password2.value==password.value
    }
    //fun to chcek register data
    fun checkRegisterData(email: MutableState<String>, password: MutableState<String>, password2: MutableState<String>,
                          scaffoldState:ScaffoldState,navController: NavController) {
        loading.value = true
        registerButtonEnable.value =  false

        if(email.value.isEmailValid() && isPassRegOk(password,password2)) {
            login_res.value = ""
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.value,password.value).addOnCompleteListener {
                    task->
                if(task.isSuccessful) {
                    //Sprawdzenie i nawigacja do UserAdmina
                    FirebaseAuth.getInstance().signOut()
                    loading.value = false
                    val route = Screen.UserView.route+"/5"
                    navController.navigate(route)
                } else {  login_res.value = task.exception?.message.toString()

                    if(login_res.value.equals("The email address is already in use by another account.")) login_res.value="Użytkownik o takim adresie email jest już zarejestrowany"
                    if("A network error" in login_res.value) login_res.value="Problem z połączeniem. Spróbuj później"
                    showAuthError(scaffoldState)

                }

            }


        } else {
            //sprawdzanie bledu logowania
            if(email.value.isEmailValid()) {
                //jezeli dobry email to czy podane jest wlasciwe haslo
                if(password.value.length<8 || password2.value.length<8)
                { login_res.value = "Hasło powinno składać się z minimum 8 znaków" }
                else if(password.value!=password2.value)
                { login_res.value = "Hasła powinny być takie same" }
            } else {
                //jezeli bledny mail to blad
                login_res.value = "Nie prawidłowy adres email" }
            showAuthError(scaffoldState)


        }

    }
    //fun to check login data
    fun checkLoginData(email: MutableState<String>, password: MutableState<String>, scaffoldState:ScaffoldState,
    navController: NavController,scope: CoroutineScope) {
        loading.value = true
        loginButtonEnable.value =  false
        if(email.value.isEmailValid() && checkLoginPassword(password)) {
            //Firebaseauth test
            login_res.value = ""
            Log.d(TAG, "firebase login test")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.value,password.value).addOnCompleteListener {
                    task->
                if(task.isSuccessful) {
                    Log.d(TAG, "firebase test sukces")
                    //Dodanie uid użytkownika przez api do bazy mysql poza Firebase

                    //Sprawdzenie danych i nawigacja do ustawien uzytkownika UserAdmin
                    val route = Screen.UserAdmin.route+"/9"
                    navController.navigate(route)
                } else {
                    login_res.value = task.exception?.message.toString()
                    if("We have blocked" in login_res.value) login_res.value="Konto zablokowane. Zrestartuj hasło"
                    if("The password is invalid" in login_res.value) login_res.value="Nie prawidłowe hasło"
                    if("A network error" in login_res.value) login_res.value="Problem z połączeniem. Spróbuj później"
                    showAuthError(scaffoldState)

                }

            }

        } else {
            //sprawdzanie bledu logowania
            if(!email.value.isEmailValid()) {
               login_res.value = "Nie prawidłowy adres email" }

             else {
                login_res.value = "Hasło powinno się składać z min. 8 znaków" }
            showAuthError(scaffoldState)




        }


    }
}