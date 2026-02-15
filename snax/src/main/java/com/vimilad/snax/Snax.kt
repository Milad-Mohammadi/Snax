package com.vimilad.snax

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Remembers the state for managing a Snax Snackbar.
 * @return A new or existing [SnaxState].
 */
@Composable
fun rememberSnaxState(): SnaxState {
    return remember { SnaxState() }
}

/**
 * Composable function to display a customizable Snackbar using Snax.
 *
 * @param state The [SnaxState] managing the Snackbar's data.
 * @param modifier Modifier for styling.
 * @param animationEnter The enter animation for the Snackbar.
 * @param animationExit The exit animation for the Snackbar.
 * @param shape The shape of the Snackbar.
 * @param progressStyle The style of the progress indicator.
 * @param dismissBehavior The behavior for dismissing the Snackbar.
 * @param titleStyle The text style for the title.
 * @param messageStyle The text style for the message.
 * @param buttonTextStyle The text style for the button text.
 * @param shadow The shadow size of the Snackbar.
 * @param shadowColor The shadow color of the Snackbar.
 */
@Composable
fun Snax(
    state: SnaxState,
    modifier: Modifier = Modifier,
    animationEnter: EnterTransition = slideInVertically(initialOffsetY = { it }) + scaleIn(initialScale = 0.8f),
    animationExit: ExitTransition = slideOutVertically(targetOffsetY = { it }) + scaleOut(targetScale = 0.8f),
    shape: Shape = RoundedCornerShape(8.dp),
    progressStyle: ProgressStyle = ProgressStyle.LINEAR,
    dismissBehavior: DismissBehavior = DismissBehavior.NOT_DISMISSABLE,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    messageStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    buttonTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    shadow: Dp = 8.dp,
    shadowColor: Color = Color.Black.copy(0.8f),
) {
    val layoutDirection = LocalLayoutDirection.current

    var showSnax by remember { mutableStateOf(false) }
    var dismissedByUser by remember { mutableStateOf(false) }
    val data by rememberUpdatedState(newValue = state.data.value)
    val type = data?.type
    val duration = data?.duration ?: 3000L
    val backgroundColor = if (type is SnaxType.CUSTOM) type.backgroundColor else ColorBackground
    val contentColor = if (type is SnaxType.CUSTOM) type.contentColor else ColorWhite
    val progress = remember { Animatable(1f) }
    val animateProgress = progressStyle == ProgressStyle.LINEAR || progressStyle == ProgressStyle.SYMMETRIC
    val scope = rememberCoroutineScope()
    val colors = listOf(
        when (type) {
            SnaxType.ERROR -> ColorRed.copy(0.3f)
            SnaxType.INFO -> ColorPrimary.copy(0.3f)
            SnaxType.SUCCESS -> ColorGreen.copy(0.3f)
            SnaxType.WARNING -> ColorYellow.copy(0.3f)
            SnaxType.LOADING -> ColorBlue.copy(0.3f)
            is SnaxType.CUSTOM -> type.overlayColor
            null -> ColorYellow.copy(0.3f)
        },
        Color.Transparent,
        Color.Transparent
    )
    val finalColors = if (layoutDirection == LayoutDirection.Rtl) colors.reversed() else colors

    val canDismissBySwipe = dismissBehavior == DismissBehavior.SWIPE_HORIZONTAL ||
            dismissBehavior == DismissBehavior.SWIPE_AND_CLICK
    val canDismissByClick = dismissBehavior == DismissBehavior.CLICK_OUTSIDE ||
            dismissBehavior == DismissBehavior.SWIPE_AND_CLICK

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.StartToEnd || newValue == SwipeToDismissBoxValue.EndToStart) {
                dismissedByUser = true
                showSnax = false
            }
            true
        },
        positionalThreshold = { it * 0.25f }
    )

    LaunchedEffect(state.updateState) {
        progress.stop()
        progress.snapTo(1f)
        showSnax = false
        dismissedByUser = false
        if (data != null) {
            showSnax = true
            progress.animateTo(
                targetValue = if (animateProgress && data?.action == null && type != SnaxType.LOADING) 0f else 1f,
                animationSpec = tween(durationMillis = duration.toInt(), easing = LinearEasing)
            )
            if (data?.action == null && type != SnaxType.LOADING) {
                showSnax = false
            }
        }
    }

    LaunchedEffect(showSnax) {
        if (!showSnax) {
            scope.launch {
                delay(500) // Wait for exit animation
                dismissState.reset()
                data?.onDismiss?.invoke(dismissedByUser)
                state.clearData()
            }
        }
    }

    // When click-outside is enabled, handle the full-screen overlay
    if (canDismissByClick) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Clickable background overlay
            if (showSnax) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            dismissedByUser = true
                            showSnax = false
                        }
                )
            }

            // Snackbar content positioned at bottom center
            SwipeToDismissBox(
                state = dismissState,
                modifier = modifier,
                gesturesEnabled = canDismissBySwipe,
                backgroundContent = {},
            ) {
                SnackbarContent(
                    showSnax = showSnax,
                    animationEnter = animationEnter,
                    animationExit = animationExit,
                    shadow = shadow,
                    shape = shape,
                    shadowColor = shadowColor,
                    backgroundColor = backgroundColor,
                    finalColors = finalColors,
                    type = type,
                    contentColor = contentColor,
                    data = data,
                    titleStyle = titleStyle,
                    messageStyle = messageStyle,
                    action = data?.action,
                    dismissedByUser = {
                        dismissedByUser = true
                        showSnax = false
                    },
                    buttonTextStyle = buttonTextStyle,
                    progressStyle = progressStyle,
                    progress = progress.value,
                    progressColor = when (type) {
                        SnaxType.ERROR -> ColorRed
                        SnaxType.INFO -> ColorPrimary
                        SnaxType.SUCCESS -> ColorGreen
                        SnaxType.WARNING -> ColorYellow
                        SnaxType.LOADING -> ColorBlue
                        is SnaxType.CUSTOM -> type.progressColor ?: type.overlayColor
                        null -> ColorPrimary
                    }
                )
            }
        }
    } else {
        // Normal behavior without click-outside
        SwipeToDismissBox(
            state = dismissState,
            modifier = modifier,
            gesturesEnabled = canDismissBySwipe,
            backgroundContent = {},
        ) {
            SnackbarContent(
                showSnax = showSnax,
                animationEnter = animationEnter,
                animationExit = animationExit,
                shadow = shadow,
                shape = shape,
                shadowColor = shadowColor,
                backgroundColor = backgroundColor,
                finalColors = finalColors,
                type = type,
                contentColor = contentColor,
                data = data,
                titleStyle = titleStyle,
                messageStyle = messageStyle,
                action = data?.action,
                dismissedByUser = {
                    dismissedByUser = true
                    showSnax = false
                },
                buttonTextStyle = buttonTextStyle,
                progressStyle = progressStyle,
                progress = progress.value,
                progressColor = when (type) {
                    SnaxType.ERROR -> ColorRed
                    SnaxType.INFO -> ColorPrimary
                    SnaxType.SUCCESS -> ColorGreen
                    SnaxType.WARNING -> ColorYellow
                    SnaxType.LOADING -> ColorBlue
                    is SnaxType.CUSTOM -> type.progressColor ?: type.overlayColor
                    null -> ColorPrimary
                }
            )
        }
    }
}

@Composable
private fun SnackbarContent(
    showSnax: Boolean,
    animationEnter: EnterTransition,
    animationExit: ExitTransition,
    shadow: Dp,
    shape: Shape,
    shadowColor: Color,
    backgroundColor: Color,
    finalColors: List<Color>,
    type: SnaxType?,
    contentColor: Color,
    data: SnaxData?,
    titleStyle: TextStyle,
    messageStyle: TextStyle,
    action: (() -> Unit)?,
    dismissedByUser: () -> Unit,
    buttonTextStyle: TextStyle,
    progressStyle: ProgressStyle,
    progress: Float,
    progressColor: Color
) {
    AnimatedVisibility(
        visible = showSnax,
        enter = animationEnter,
        exit = animationExit,
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = shadow,
                    shape = shape,
                    spotColor = shadowColor
                )
                .clip(shape = shape)
                .background(color = backgroundColor)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { /* Prevent clicks from passing through */ }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(shape = shape)
                    .background(brush = Brush.horizontalGradient(finalColors))
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                if (type == SnaxType.LOADING) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(34.dp)
                            .padding(4.dp),
                        color = contentColor,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        painter = painterResource(
                            id = when (type) {
                                SnaxType.ERROR -> R.drawable.ic_cross_circle_fill
                                SnaxType.INFO -> R.drawable.ic_info_circle_fill
                                SnaxType.SUCCESS -> R.drawable.ic_tick_circle_fill
                                SnaxType.WARNING -> R.drawable.ic_warning_fill
                                is SnaxType.CUSTOM -> type.icon
                                else -> R.drawable.ic_info_circle_fill
                            }
                        ),
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(contentColor.copy(0.1f))
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    data?.title?.let { title ->
                        Text(
                            text = title,
                            color = contentColor,
                            style = titleStyle,
                        )
                    }

                    Text(
                        text = data?.message.orEmpty(),
                        color = contentColor,
                        style = messageStyle,
                    )
                }

                action?.let {
                    TextButton(
                        onClick = {
                            dismissedByUser()
                            it()
                        }
                    ) {
                        Text(
                            text = data?.actionTitle.orEmpty(),
                            color = contentColor,
                            style = buttonTextStyle
                        )
                    }
                }
            }

            if (progressStyle != ProgressStyle.HIDDEN && type != SnaxType.LOADING) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = when (progressStyle) {
                        ProgressStyle.LINEAR -> Arrangement.Start
                        else -> Arrangement.SpaceAround
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(4.dp)
                            .background(progressColor)
                    )
                }
            }
        }
    }
}

// Predefined color constants for the Snax Snackbar.
private val ColorBackground = Color(0xFF242C32)
private val ColorPrimary = Color(0xFF1C54D4)
private val ColorRed = Color(0xFFF04349)
private val ColorGreen = Color(0xFF00DF80)
private val ColorYellow = Color(0xFFFFD21E)
private val ColorBlue = Color(0xFF2196F3)
private val ColorWhite = Color(0xFFFDFDFD)