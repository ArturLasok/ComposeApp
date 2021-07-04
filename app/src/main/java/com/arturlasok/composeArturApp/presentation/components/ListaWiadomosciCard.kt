package com.arturlasok.composeArturApp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ListaWiadomosciCard
    (
    wiadomosc_vm: Wiadomosc,
    onClick: () -> Unit,
    ) {

        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(bottom = 6.dp, top = 6.dp, end = 6.dp, start = 6.dp)
                .fillMaxWidth()
                .clickable(onClick = onClick),
            elevation = 8.dp,
        )
        {

            Column() {


                wiadomosc_vm.domimg?.let { url ->
                    Image(
                        painter = rememberCoilPainter(wiadomosc_vm.domimg),
                        contentDescription = wiadomosc_vm.domtytul,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(225.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
                wiadomosc_vm.domtytul?.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = title,
                            //text = wiadomosc_vm.domtytul as String,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = wiadomosc_vm.domid.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h6
                        )
                    }
                }


            }
        }
    }



