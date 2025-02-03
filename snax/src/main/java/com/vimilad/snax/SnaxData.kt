package com.vimilad.snax

/**
 * Holds the data required to display a custom Snackbar in the Snax library.
 *
 * @param type The type of the Snackbar (e.g., success, error, etc.).
 * @param message The main message to display in the Snackbar.
 * @param title An optional title for the Snackbar.
 * @param actionTitle An optional title for the action button.
 * @param action An optional action to execute when the action button is clicked.
 *
 * @author Milad Mohammadi
 */
data class SnaxData(
    val type: SnaxType,
    val message: String,
    val title: String? = null,
    val actionTitle: String? = null,
    val action: (() -> Unit)? = null,
)
