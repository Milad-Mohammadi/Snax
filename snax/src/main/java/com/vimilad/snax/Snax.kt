package com.vimilad.snax

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun rememberSnaxState(): SnaxState {
    return remember { SnaxState() }
}

@Composable
fun Snax(
    state: SnaxState,
    modifier: Modifier = Modifier,
    animationEnter: EnterTransition = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
    animationExit: ExitTransition = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }),
    cornerRadius: Dp = 8.dp,
    backgroundColor: Color = Color(0xFF242C32),
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    messageStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    duration: Long = 3000L
) {
    var showSnax by remember { mutableStateOf(false) }
    val data by rememberUpdatedState(newValue = state.data.value)

    DisposableEffect(
        key1 = state.updateState
    ) {
        if (data != null) showSnax = true
        val timer = Timer("Animation Timer", true)
        if (data?.action == null) {
            timer.schedule(duration) {
                showSnax = false
            }
        }
        onDispose {
            timer.cancel()
            timer.purge()
        }
    }

    Column(modifier) {
        AnimatedVisibility(
            visible = showSnax,
            enter = animationEnter,
            exit = animationExit,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = backgroundColor)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(cornerRadius))
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    when (data?.type) {
                                        SnaxType.ERROR -> ColorRed.copy(0.3f)
                                        SnaxType.INFO -> ColorPrimary.copy(0.3f)
                                        SnaxType.SUCCESS -> ColorGreen.copy(0.3f)
                                        SnaxType.WARNING -> ColorYellow.copy(0.3f)
                                        null -> ColorYellow.copy(0.3f)
                                    },
                                    Color.Transparent,
                                    Color.Transparent,
                                )
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = when (data?.type) {
                                SnaxType.ERROR -> R.drawable.ic_cross_circle_fill
                                SnaxType.INFO -> R.drawable.ic_info_circle_fill
                                SnaxType.SUCCESS -> R.drawable.ic_tick_circle_fill
                                SnaxType.WARNING -> R.drawable.ic_warning_fill
                                null -> R.drawable.ic_warning_fill
                            }
                        ),
                        contentDescription = null,
                        tint = ColorWhite,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ColorWhite.copy(0.1f))
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        data?.title?.let { title ->
                            Text(
                                text = title,
                                color = ColorWhite,
                                style = titleStyle,
                            )
                        }

                        Text(
                            text = data?.message.orEmpty(),
                            color = ColorWhite,
                            style = messageStyle,
                        )
                    }

                    data?.action?.let { action ->
                        TextButton(
                            onClick = {
                                showSnax = false
                                action()
                            }
                        ) {
                            Text(
                                text = data?.actionTitle.orEmpty(),
                                color = ColorWhite
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .animateContentSize(tween(durationMillis = duration.toInt(), easing = LinearEasing))
                        .align(Alignment.BottomCenter)
                        .clip(CircleShape)
                        .background(
                            when (data?.type) {
                                SnaxType.ERROR -> ColorRed
                                SnaxType.INFO -> ColorPrimary
                                SnaxType.SUCCESS -> ColorGreen
                                SnaxType.WARNING -> ColorYellow
                                null -> ColorPrimary
                            }
                        )
                )
            }
        }
    }
}


private val ColorPrimary = Color(0xFF1C54D4)
private val ColorRed = Color(0xFFF04349)
private val ColorGreen = Color(0xFF00DF80)
private val ColorYellow = Color(0xFFFFD21E)
private val ColorWhite = Color(0xFFFDFDFD)