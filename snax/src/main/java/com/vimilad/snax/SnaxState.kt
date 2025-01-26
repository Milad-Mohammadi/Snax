package com.vimilad.snax

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Manages the state of the Snax snack bar.
 *
 * @property data The current data being displayed by the snack bar.
 * @property updateState Tracks changes in the state to trigger recomposition.
 *
 * @author Milad Mohammadi
 */
class SnaxState {

    private val _data = mutableStateOf<SnaxData?>(null)
    val data: State<SnaxData?> = _data

    /**
     * Tracks whether the state has been updated to trigger UI recomposition.
     */
    var updateState by mutableStateOf(false)
        private set

    /**
     * Updates the snack bar data and toggles the update state.
     *
     * @param type The type of the snack bar.
     * @param message The main message to display.
     * @param title An optional title for the snack bar.
     * @param actionTitle An optional title for the action button.
     * @param action An optional action to execute when the action button is clicked.
     */
    fun setData(
        type: SnaxType,
        message: String,
        title: String? = null,
        actionTitle: String? = null,
        action: (() -> Unit)? = null,
    ) {
        _data.value = SnaxData(
            type = type,
            message = message,
            title = title,
            actionTitle = actionTitle,
            action = action
        )
        updateState = !updateState
    }
}
