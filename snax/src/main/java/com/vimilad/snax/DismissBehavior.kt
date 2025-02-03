package com.vimilad.snax

/**
 * Represents the behavior for dismissing a Snackbar in the Snax library.
 *
 * @author Milad Mohammadi
 */
enum class DismissBehavior {
    /**
     * The Snackbar can not be dismissed by the user.
     */
    NOT_DISMISSABLE,

    /**
     * The Snackbar can be dismissed by swiping horizontally.
     */
    SWIPE_HORIZONTAL
}
