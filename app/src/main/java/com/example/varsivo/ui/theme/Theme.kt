package com.example.varsivo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

private val VarsivoLightColorScheme = lightColorScheme(

    primary = VarsivoNavy,
    onPrimary = VarsivoWhite,

    primaryContainer = Color(0xFFDCEAF7),
    onPrimaryContainer = VarsivoNavy,

    secondary = VarsivoGold,
    onSecondary = VarsivoWhite,

    secondaryContainer = Color(0xFFFFE8A8),
    onSecondaryContainer = Color(0xFF4A3900),

    background = VarsivoBackground,
    onBackground = VarsivoText,

    surface = VarsivoSurface,
    onSurface = VarsivoText,

    surfaceVariant = Color(0xFFE9EEF4),
    onSurfaceVariant = VarsivoTextSecondary,

    outline = Color(0xFF9AA8B5),

    error = VarsivoError,
    onError = VarsivoWhite
)

private val VarsivoTypography = Typography(

    headlineLarge = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),

    headlineMedium = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),

    titleLarge = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),

    titleMedium = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),

    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp
    ),

    bodyMedium = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp
    ),

    labelLarge = androidx.compose.ui.text.TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
)

@Composable
fun VarsivoTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = VarsivoLightColorScheme,
        typography = VarsivoTypography,
        content = content
    )
}