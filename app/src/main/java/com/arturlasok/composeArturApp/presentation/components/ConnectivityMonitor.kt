package com.arturlasok.composeArturApp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ConnectivityMonitor(
    isNetworkAvailable: Boolean,
    isConMonVis:Boolean
){

        if (!isNetworkAvailable) {
            if (isConMonVis) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "<!> Brak połączenia z siecią. Informacje mogą być nieaktualne. Funkcjonalność ograniczona",
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )

            }
        }
    }
}
