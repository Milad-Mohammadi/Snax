package com.vimilad.snax

/**
 * Represents the behavior for dismissing a snack bar in the Snax library.
 *
 * @author Milad Mohammadi
 */
enum class DismissBehavior {
    /**
     * The snack bar can not be dismissed by the user.
     */
    NOT_DISMISSABLE,

    /**
     * The snack bar can be dismissed by swiping horizontally.
     */
    SWIPE_HORIZONTAL
}
