package com.arturlasok.composeArturApp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arturlasok.composeArturApp.presentation.components.ConnectivityMonitor
import com.arturlasok.composeArturApp.presentation.components.ProgressBar

private val DarkColorPalette = darkColors(
    primary = primaryDark,
    primaryVariant = primaryVariantDark,
    secondary = secondaryDark,
    background = backgroundDark,
    surface =  surfaceDark,
    onSurface = onSurfaceDark,
    onPrimary = onPrimaryDark,
    onSecondary = onSecondaryDark,
    onBackground = onBackgroundDark,

)

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    background = background,
    surface =  surface,
    //onSurface = onSurface,
    onPrimary = onPrimary,
    //onSecondary = onSecondary,
    onBackground = onBackground,

)

@Composable
fun RuchAppArturTheme(
    displayProgressBar:Boolean,
    darkTheme: Boolean = isSystemInDarkTheme(),
    isNetworkAvailable:Boolean,
    isConMonVis:Boolean,
    content: @Composable() () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (!darkTheme) MaterialTheme.colors.background else MaterialTheme.colors.background)
        ) {
            Column {
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable, isConMonVis = isConMonVis)

                content()
            }
            ProgressBar(isDisplayed = displayProgressBar)

        }
    }
}