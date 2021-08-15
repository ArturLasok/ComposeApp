package com.arturlasok.composeArturApp.presentation.components.foto

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.camera.core.internal.utils.ImageUtil
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.arturlasok.composeArturApp.BaseApplication
import com.arturlasok.composeArturApp.MainActivity
import com.arturlasok.composeArturApp.domain.model.AppUser
import com.arturlasok.composeArturApp.presentation.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FotoActivityViewModel @Inject constructor(
    private val app: BaseApplication,
    private val appUser: AppUser

    ): ViewModel() {
    var imageFromTakePic : ImageProxy? =  null

    //Events

    fun onTriggerFotoEvent(event:FotoActivityEvents) {
        try {
            when (event) {

                is FotoActivityEvents.PowrotEvent -> { PowrotDoMain() }
                is FotoActivityEvents.AddImageToUser -> {  AddImageToUser() }

            }
        }
        catch (e: Exception) {
            Log.d(TAG, "FotoActivityEvent Exception: ${e.cause}")
        }


    }
    @SuppressLint("RestrictedApi")
    private fun AddImageToUser()
    {
        imageFromTakePic?.let {
            val imageArray= ImageUtil.imageToJpegByteArray(it)

            val imageBitmap =
                imageArray?.let { it1 -> BitmapFactory.decodeByteArray(imageArray,0, it1.size) }
            if (imageBitmap != null) {
                appUser.set_pimage(imageBitmap)
            };   Log.d(TAG, "Image added to USER ${appUser.get_mail()}") }
    }

    private fun PowrotDoMain()
    {


        try {
            val intent_message = "true"
            val intent_source = Intent(app.applicationContext, MainActivity::class.java).apply {
                putExtra("EXTRA_MSG", intent_message)
            }
            intent_source.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent_source.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            app.startActivity(intent_source)
        } catch (e: Exception) {  Log.d(TAG, "Intent Exception: ${e.cause} ${e.message} ${e.toString()}")}

    }



}