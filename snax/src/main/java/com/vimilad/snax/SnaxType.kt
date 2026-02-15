package com.vimilad.snax

import androidx.compose.ui.graphics.Color

/**
 * Represents the type of the Snackbar and its associated properties.
 *
 * Includes predefined types (e.g., ERROR, INFO) and a customizable type.
 *
 * @author Milad Mohammadi
 */
sealed class SnaxType {

    /**
     * Represents an error type Snackbar.
     */
    data object ERROR : SnaxType()

    /**
     * Represents an informational type Snackbar.
     */
    data object INFO : SnaxType()

    /**
     * Represents a success type Snackbar.
     */
    data object SUCCESS : SnaxType()

    /**
     * Represents a warning type Snackbar.
     */
    data object WARNING : SnaxType()

    /**
     * Represents a loading type Snackbar.
     */
    data object LOADING : SnaxType()

    /**
     * Represents a custom type Snackbar with user-defined properties.
     *
     * @param icon The resource ID of the icon to display.
     * @param backgroundColor The background color of the Snackbar.
     * @param overlayColor The overlay color applied to the Snackbar.
     * @param contentColor The color of the text and other content.
     * @param progressColor The optional color for the progress indicator.
     */
    data class CUSTOM(
        val icon: Int,
        val backgroundColor: Color,
        val overlayColor: Color,
        val contentColor: Color,
        val progressColor: Color? = null,
    ) : SnaxType()
}
