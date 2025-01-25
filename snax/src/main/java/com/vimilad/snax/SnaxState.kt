package com.vimilad.snax

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SnaxState {

    private val _data = mutableStateOf<SnaxData?>(null)
    val data: State<SnaxData?> = _data

    var updateState by mutableStateOf(false)
        private set

    fun setData(
        type: SnaxType,
        text: String,
        title: String? = null,
        actionTitle: String? = null,
        action: (() -> Unit)? = null,
    ) {
        _data.value = SnaxData(
            type = type,
            message = text,
            title = title,
            actionTitle = actionTitle,
            action = action
        )
        updateState = !updateState
    }
}