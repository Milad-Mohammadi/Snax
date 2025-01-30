package com.vimilad.snax.sample

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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.vimilad.snax.DismissBehavior
import com.vimilad.snax.ProgressStyle
import com.vimilad.snax.R
import com.vimilad.snax.Snax
import com.vimilad.snax.SnaxType
import com.vimilad.snax.rememberSnaxState

val vazirmatnFontFamily = FontFamily(
    Font(R.font.vazirmatn_bold, FontWeight.Bold),
    Font(R.font.vazirmatn_regular, FontWeight.Normal),
    Font(R.font.vazirmatn_medium, FontWeight.Medium),
)

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val shapes = listOf(
                    Pair("default", "Default"),
                    Pair("circle", "Circle"),
                    Pair("rectangle", "Rectangle"),
                    Pair("rounded", "Rounded"),
                )
                val durations = listOf(
                    Pair("3", "3s"),
                    Pair("5", "5s"),
                    Pair("10", "10s"),
                    Pair("15", "15s"),
                    Pair("20", "20s"),
                )

                val snaxState = rememberSnaxState()
                var showTitle by remember { mutableStateOf(false) }
                var actionRequired by remember { mutableStateOf(false) }
                var isDismissable by remember { mutableStateOf(false) }
                var progressStyle by remember { mutableStateOf(ProgressStyle.LINEAR) }
                var animation by remember { mutableStateOf(AnimationType.DEFAULT) }
                var shape by remember { mutableStateOf(shapes.first()) }
                var duration  by remember { mutableStateOf(durations.first()) }
                val action = if (actionRequired) ({}) else null
                var rtl by remember { mutableStateOf(false) }

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

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Duration")
                                ItemSelector(
                                    items = durations,
                                    currentItem = duration,
                                    onSelected = { duration = it }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            maxItemsInEachRow = 2
                        ) {
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    rtl = false
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
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    rtl = false
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
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    rtl = false
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
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    rtl = false
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
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    rtl = false
                                    snaxState.setData(
                                        type = SnaxType.CUSTOM(
                                            icon = R.drawable.vimilad_logo,
                                            backgroundColor =  Color(0XFFB28D8D),
                                            overlayColor = Color(0XFF613DC1),
                                            contentColor = Color(0xFFE1E1E1)
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

                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    rtl = true
                                    snaxState.setData(
                                        type = SnaxType.CUSTOM(
                                            icon = R.drawable.ic_backup,
                                            backgroundColor =  Color(0xFFE1E1E1),
                                            overlayColor = Color(0xFFE1E1E1),
                                            contentColor = Color(0xFF0E0E0E),
                                            progressColor = Color(0xFF0E0E0E)
                                        ),
                                        title = if (showTitle) "پشتیبان\u200Cگیری" else null,
                                        message = "برای جلوگیری از از\u200Cدست\u200Cرفتن داده\u200Cها، پشتیبان\u200Cگیری را به\u200Cصورت منظم انجام دهید.",
                                        actionTitle = "پشتیبان\u200Cگیری",
                                        action = action
                                    )
                                },
                                content = {
                                    Text(text = "Custom 2")
                                }
                            )
                        }
                        }

                    CompositionLocalProvider(
                        LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr
                    ) {
                        Snax(
                            state = snaxState,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            progressStyle = progressStyle,
                            dismissBehavior = if (isDismissable) DismissBehavior.SWIPE_HORIZONTAL else DismissBehavior.NOT_DISMISSABLE,
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
                            },
                            duration = duration.first.toInt().times(1000).toLong(),
                            titleStyle = MaterialTheme
                                .typography
                                .titleLarge
                                .copy(fontFamily = vazirmatnFontFamily, fontWeight = FontWeight.Bold),
                            messageStyle = MaterialTheme
                                .typography
                                .bodyMedium
                                .copy(fontFamily = vazirmatnFontFamily, fontWeight = FontWeight.Normal),
                            buttonTextStyle = MaterialTheme
                                .typography
                                .labelLarge
                                .copy(fontFamily = vazirmatnFontFamily, fontWeight = FontWeight.Medium)
                        )
                    }
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