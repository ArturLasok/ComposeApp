package com.arturlasok.composeArturApp.presentation.components.foto

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.camera.core.*
import androidx.camera.core.internal.utils.ImageUtil
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.arturlasok.composeArturApp.ui.theme.RuchAppArturTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_foto.*
import kotlinx.coroutines.MainScope
import ruchradzionkow.ruchappartur.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class FotoActivity : ComponentActivity() {
    // VAL TO IMAGE CAPTURE
    private var imageCapture: ImageCapture? = null
    //THEME FOR COMPOSE
    var ThisDark = mutableStateOf(false)
    //CAMERA EXECUTOR
    private lateinit var cameraExecutor: ExecutorService
    // VIEWMODEL
    private val FotoActivityViewModel : FotoActivityViewModel by viewModels()
    //ON CREATE

    override fun onCreate(savedInstanceState: Bundle?) {

        // Pobranie dancyh o uzytkowniku z MainActivity i aktualny Theme
        val message = intent.getStringExtra(EXTRA_MESSAGE).orEmpty()
        Log.i(TAG, "Extra message $message")
        try {
            val firebase_id = message?.substringBefore(">><<")
            val isDark = message?.substringAfter(">><<")

            if(isDark.toBoolean()) {
                setTheme(R.style.Theme_RuchAppArturnight); ThisDark.value = true }
            else { setTheme(R.style.Theme_RuchAppArtur); ThisDark.value = false }

        } catch (e:Exception) {   Log.i(TAG, "Extra message ERROR from intent MainActivity")}
        //SUPER ON CREATE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)
        val liveback = this

        //DODANIE WIDOKU COMPOSE
        val topAppBar = findViewById<ComposeView>(R.id.composeView)
        topAppBar.setContent {
            RuchAppArturTheme(false, ThisDark.value, true, true) { // or AppCompatTheme
            TopBar(FotoActivityViewModel,liveback)
            }
        }
        // Aparat
        cameraExecutor = Executors.newSingleThreadExecutor()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                // Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))


        camera_capture_button.setOnClickListener {
            Log.d(TAG, "but clicked")
        takePicture()

        }
    }

    fun takePicture(){
        try {
            val imageCapture = imageCapture ?: return
            Log.d(TAG, "Image cap mode ${imageCapture.captureMode}")
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                @SuppressLint("UnsafeOptInUsageError")
                override fun onCaptureSuccess(image: ImageProxy) {
                    Log.d(TAG, "Capture success")

                    FotoActivityViewModel.imageFromTakePic = image
                    FotoActivityViewModel.onTriggerFotoEvent(FotoActivityEvents.AddImageToUser)
                    super.onCaptureSuccess(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(TAG, "Capture error $exception")
                    super.onError(exception)
                }
            })
        } catch (e:Exception) {  Log.d(TAG, "Capture problem ${e.cause}") }
    }

}

@Composable
private fun TopBar(
    FotoActivityViewModel: FotoActivityViewModel,
    liveback: Activity
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = 8.dp,

        ) {
        Row(horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)) {


            Row(
                modifier = Modifier

                    .clickable(

                        onClick = {
                            liveback.finish()
                        }
                    )
            ) {

                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Przejdź do poprzedniej aktywności",
                )



                Text(
                    text = "Powrót", color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.h5, modifier = Modifier.padding(start = 10.dp)
                )
            }






        }

    }

}