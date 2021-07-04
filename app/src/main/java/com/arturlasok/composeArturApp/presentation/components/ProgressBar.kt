package com.arturlasok.composeArturApp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(isDisplayed:Boolean) {
    if(isDisplayed) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8F)
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            //horizontalArrangement = Arrangement.Center,
            //verticalAlignment = Alignment.CenterVertically

        ) {
            Row() { Text("To potrwa chwilę...",modifier = Modifier
                .padding(20.dp),color = MaterialTheme.colors.primary) }
            Row() {  LinearProgressIndicator(backgroundColor = Color.Transparent)}


        }
    }
}