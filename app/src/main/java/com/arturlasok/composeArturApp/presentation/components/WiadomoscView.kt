package com.arturlasok.composeArturApp.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.presentation.util.TAG
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun WiadomoscView(
    wiadomosc_vm: Wiadomosc,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        item {
            wiadomosc_vm.domimg?.let { url ->
                Log.d(TAG, "img ${wiadomosc_vm.domimg}")
               Image(
                    painter = rememberCoilPainter(wiadomosc_vm.domimg),
                    contentDescription = wiadomosc_vm.domtytul,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop,
                )

            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)

            ) {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    wiadomosc_vm.domtytul?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.primary
                        )

                    }
                }
                Row() {
                    wiadomosc_vm.domtresc?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }


            }
        }

    }
}
