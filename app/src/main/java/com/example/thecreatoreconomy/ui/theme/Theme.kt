package com.example.thecreatoreconomy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF0E6FFF),
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val selectedButtonColor = lightColors(
    primary = Color(0xFF0E6FFF),
    primaryVariant = Color(0xFFFFFFFF),

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
private val unselectedButtonColor = lightColors(
    primary = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFF999CA0),

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TheCreatorEconomyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}

@Composable
fun TabTheme( content: @Composable () -> Unit) {

    MaterialTheme(
        colors = lightColors(
            primary = Color.Transparent
        ),
        typography = Typography,
        content = content
    )
}



@Composable
fun tabButtonTheme(isTabSelected: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (isTabSelected) {
        selectedButtonColor
    } else {
        unselectedButtonColor
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}


