package com.vimilad.snax

data class SnaxData(
    val type: SnaxType,
    val text: String,
    val title: String? = null,
    val actionTitle: String? = null,
    val action: (() -> Unit)? = null,
)
