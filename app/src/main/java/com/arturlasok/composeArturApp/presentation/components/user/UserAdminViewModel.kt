package com.arturlasok.composeArturApp.presentation.components.user

import android.Manifest
import android.app.Application
import android.net.Uri
import android.util.*
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.interactors.GetUser
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.presentation.util.isOnline
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ruchradzionkow.ruchappartur.R
import java.nio.ByteBuffer
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserAdminViewModel @Inject constructor(
    private val appUser: AppUser,
    private @Named("auth_token") val token: String,
    private val connectivityManager: isOnline,
    private val getUser: GetUser,
    private val aplikacja : Application
    ): ViewModel() {

    //Foto cap
    private lateinit var outputDirectory: File

    val imie_edycja = mutableStateOf("")
    val imie_nowe = mutableStateOf("")
    val nazwisko_edycja = mutableStateOf("")
    val nazwisko_nowe = mutableStateOf("")

     //Foto cap
     val imageCapture = ImageCapture.Builder().build()
     private lateinit var toImageExecutor : Executor

    val loading = mutableStateOf(false)

    init {

        imie_edycja.value = appUser.get_pimie().value.toString()
        nazwisko_edycja.value = appUser.get_pnazwisko().value.toString()

        //Dir for foto
        outputDirectory = getOutputDirectory()


        setEvent(UserAdminEvent.UserAdminProfileAdd)
        setEvent(UserAdminEvent.UserAdminProfileUpdate)

    }
    companion object {

        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
    private fun getOutputDirectory(): File {
        val mediaDir = aplikacja.externalMediaDirs.firstOrNull()?.let {
            File(it, aplikacja.resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else aplikacja.filesDir
    }
        //ustawienie jezyka firebase
        fun setFirebaseLang() {
            FirebaseAuth.getInstance().setLanguageCode("pl-PL")
        }
    fun setExec(e:Executor) {
        toImageExecutor = e;
    }
        //events in Admin
        fun setEvent(event: UserAdminEvent) {
            try {

                when (event) {
                   is UserAdminEvent.UserAdminProfileUpdate ->  { UserAdminProfileUpdate() }
                   is UserAdminEvent.UserAdminProfileAdd -> { UserAdminProfileAdd() }
                   is UserAdminEvent.UserAdminProfileTakePic -> { UserAdminProfileTakePic() }
                }
            } catch (e: Exception) {
                Log.d(TAG, "UserAdmin Exception: ${e.cause}")
            }
        }
// EVENT Robienie zdjecia do profilu --------------------------------------------------------------
    fun UserAdminProfileTakePic(){
        Log.i(TAG,"TAKE PIC EVENT")
       // val imageCapture = imageCapture ?:return
    // Create time-stamped output file to hold the image
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(FILENAME_FORMAT, java.util.Locale.UK).format(System.currentTimeMillis()) + ".jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions, toImageExecutor, object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val msg = "Photo capture succeeded: $savedUri"

                Log.d(TAG, msg)
            }
        })



}
//EVENT Dodanie uzytkownika do mysql jezeli nie jest zapisany -------------------------------------
    fun UserAdminProfileAdd() {
        loading.value = true

        appUser.get_puid()?.let {
            getUser.putUserToMysqlinAdmin(
                puid = it,
                token = token,
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value
            ).onEach { data ->
                Log.i(TAG, "UserAdminProfileAdd: $data")
                loading.value = false
            }.launchIn(viewModelScope)
        }
    }
//EVENT Zmiana danych w profilu uzytkownika -------------------------------------------------------
        fun UserAdminProfileUpdate() {
            appUser.get_puid()?.let {
                getUser.getUserFlow(
                    token = token,
                    puid = it,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                ).onEach { data ->


                        Log.i(TAG, "UserAdminProfileUpdate: ${data}")

                    loading.value = false
                }.launchIn(viewModelScope)
            }




        }
}