package com.arturlasok.composeArturApp.presentation.components.user

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.util.Rational
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.camera.core.*
import androidx.camera.core.ViewPort.FIT
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.arturlasok.composeArturApp.MainActivity
import com.arturlasok.composeArturApp.presentation.util.TAG
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(executor: Executor, userAdminViewModel: UserAdminViewModel){
    val imageCapture = userAdminViewModel.imageCapture
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        modifier = Modifier.size(150.dp,150.dp)

        ,
        update = {
            //it.scaleX = 1F
            //it.clipChildren = true
            //  it.setScaleType(PreviewView.ScaleType.FIT_CENTER)
            // val mat = it.meteringPointFactory
            // val viewport = ViewPort.Builder()
            Log.d(TAG, "xcamera update scaetype:${it.scaleType}")
            Log.d(TAG, "xcamera update viewport:${it.viewPort.toString()}")
            Log.d(TAG, "xcamera update hashcode:${it.hashCode()}")


        },
        factory = { ctx ->
            val previewView = PreviewView(ctx)




            cameraProviderFuture.addListener({

                val cameraProvider = cameraProviderFuture.get()
                val viewPort =  ViewPort.Builder(Rational(2,4), Surface.ROTATION_0).build()


                val preview = Preview.Builder()

                    .build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)

                    }



                val useCaseGroup = UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(imageCapture)
                    .setViewPort(viewPort)
                    .build()





                Log.d(TAG, "xcamera start:${previewView.viewPort?.aspectRatio}")

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                   useCaseGroup
                )
            }
                , executor
            )





            previewView
        },
    )


}