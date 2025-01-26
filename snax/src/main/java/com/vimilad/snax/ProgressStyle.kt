package com.vimilad.snax


/**
 * Defines the styles of progress indicator that can be displayed in the snack bar.
 *
 * @author Milad Mohammadi
 */
enum class ProgressStyle {
    /**
     * A linear progress indicator that decreases from end to start.
     */
    LINEAR,

    /**
     * A symmetric circular progress indicator that decreases from both sides.
     */
    SYMMETRIC,

    /**
     * A static progress indicator with no animation.
     */
    STATIC,

    /**
     * Hidden progress indicator.
     */
    HIDDEN
}
