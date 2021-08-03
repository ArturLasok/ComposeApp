package com.arturlasok.composeArturApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.arturlasok.composeArturApp.presentation.util.TAG
import kotlinx.android.synthetic.main.activity_foto.*
import ruchradzionkow.ruchappartur.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FotoActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService

    val fotoActivityViewModel : fotoActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        // Pobranie dancyh o uzytkowniku z MainActivity i aktualny Theme
        val message = intent.getStringExtra(EXTRA_MESSAGE).orEmpty()
        Log.i(TAG, "Extra message $message")
        try {
            val firebase_id = message?.substringBefore(">><<")
            val isDark = message?.substringAfter(">><<")
            Log.i(TAG, "Extra message dark $isDark and viewmodel is ${fotoActivityViewModel.noth}")
            if(isDark.toBoolean()) {
                setTheme(R.style.Theme_RuchAppArturnight) } else { setTheme(R.style.Theme_RuchAppArtur) }

        } catch (e:Exception) {   Log.i(TAG, "Extra message ERROR from intent MainActivity")}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)

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

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview)

            } catch(exc: Exception) {
                // Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))


    }
}