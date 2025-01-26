package com.vimilad.snax

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val shapes = listOf(
                    Pair("default", "default"),
                    Pair("circle", "circle"),
                    Pair("rectangle", "rectangle"),
                    Pair("rounded", "rounded"),
                )

                val snaxState = rememberSnaxState()
                var showTitle by remember { mutableStateOf(false) }
                var actionRequired by remember { mutableStateOf(false) }
                var isDismissable  by remember { mutableStateOf(false) }
                var progressStyle  by remember { mutableStateOf(ProgressStyle.LINEAR) }
                var animation  by remember { mutableStateOf(AnimationType.DEFAULT) }
                var shape  by remember { mutableStateOf(shapes.first()) }
                val action = if (actionRequired) ({}) else null

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                            .fillMaxWidth(0.85f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Action Required")
                                Checkbox(
                                    checked = actionRequired,
                                    onCheckedChange = { actionRequired = actionRequired.not() }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Show Title")
                                Checkbox(
                                    checked = showTitle,
                                    onCheckedChange = { showTitle = showTitle.not() }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Can Dismiss")
                                Checkbox(
                                    checked = isDismissable,
                                    onCheckedChange = { isDismissable = isDismissable.not() }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Progress Style")
                                ItemSelector(
                                    items = ProgressStyle.entries.map { Pair(it.name, it.name) },
                                    currentItem = Pair(progressStyle.name, progressStyle.name),
                                    onSelected = { progressStyle = ProgressStyle.valueOf(it.first) }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Animation Type")
                                ItemSelector(
                                    items = AnimationType.entries.map { Pair(it.name, it.title) },
                                    currentItem = Pair(animation.name, animation.title),
                                    onSelected = { animation = AnimationType.valueOf(it.first) }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Shape")
                                ItemSelector(
                                    items = shapes,
                                    currentItem = shape,
                                    onSelected = { shape = it }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                snaxState.setData(
                                    type = SnaxType.SUCCESS,
                                    title = if (showTitle) "Order Confirmed" else null,
                                    message = "Your order was placed successfully. A confirmation email is on its way.",
                                    actionTitle = "Ok",
                                    action = action
                                )
                            },
                            content = {
                                Text(text = "Success")
                            }
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                snaxState.setData(
                                    type = SnaxType.ERROR,
                                    title = if (showTitle) "Payment Failed" else null,
                                    message = "Payment failed. Please try again or contact support.",
                                    actionTitle = "Support",
                                    action = action
                                )
                            },
                            content = {
                                Text(text = "Error")
                            }
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                snaxState.setData(
                                    type = SnaxType.WARNING,
                                    title = if (showTitle) "Low Storage" else null,
                                    message = "Your device is low on storage. Free up space for better performance.",
                                    actionTitle = "Settings",
                                    action = action
                                )
                            },
                            content = {
                                Text(text = "Warning")
                            }
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                snaxState.setData(
                                    type = SnaxType.INFO,
                                    title = if (showTitle) "New Update Available" else null,
                                    message = "A new app update is available. Please update to enjoy new features.",
                                    actionTitle = "Update",
                                    action = action
                                )
                            },
                            content = {
                                Text(text = "Info")
                            }
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                snaxState.setData(
                                    type = SnaxType.CUSTOM(
                                        icon = R.drawable.vimilad_logo,
                                        backgroundColor =  Color(0XFFB28D8D),
                                        overlayColor = Color(0XFF613DC1),
                                        contentColor = Color(0XFF222222)
                                    ),
                                    title = if (showTitle) "Vimilad.com" else null,
                                    message = "Check my website for more projects.",
                                    actionTitle = "Visit",
                                    action = action
                                )
                            },
                            content = {
                                Text(text = "Custom")
                            }
                        )
                    }

                    Snax(
                        state = snaxState,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        progressStyle = progressStyle,
                        shape = when(shape) {
                            shapes[0] -> RoundedCornerShape(8.dp)
                            shapes[1] -> CircleShape
                            shapes[2] -> RectangleShape
                            else -> RoundedCornerShape(20.dp)
                        },
                        animationEnter = when (animation) {
                            AnimationType.DEFAULT -> fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
                            AnimationType.SLIDE_SCALE -> slideInVertically(initialOffsetY = { it }) + scaleIn(initialScale = 0.8f)
                            AnimationType.EXPAND_CONTRACT -> expandVertically(
                                expandFrom = Alignment.Top,
                                animationSpec = spring(stiffness = Spring.StiffnessMedium)
                            ) + fadeIn()
                        },
                        animationExit = when (animation) {
                            AnimationType.DEFAULT -> fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                            AnimationType.SLIDE_SCALE -> slideOutVertically(targetOffsetY = { it }) + scaleOut(targetScale = 0.8f)
                            AnimationType.EXPAND_CONTRACT -> shrinkVertically(
                                shrinkTowards = Alignment.Top,
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            ) + fadeOut()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemSelector(
    items: List<Pair<String, String>>,
    currentItem: Pair<String, String>,
    onSelected: (Pair<String, String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        TextButton(onClick = { expanded = true }) {
            Text(text = currentItem.second, style = MaterialTheme.typography.bodyMedium)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.second, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        onSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}