package com.vimilad.snax

import androidx.compose.ui.graphics.Color

sealed class SnaxType {
    data object ERROR : SnaxType()
    data object INFO : SnaxType()
    data object SUCCESS : SnaxType()
    data object WARNING : SnaxType()

    data class CUSTOM(
        val icon: Int,
        val backgroundColor: Color,
        val overlayColor: Color,
        val contentColor: Color
    ) : SnaxType()
}