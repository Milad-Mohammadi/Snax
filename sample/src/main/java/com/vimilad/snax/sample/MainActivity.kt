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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
        supportActionBar?.hide()

        setContent {
            MaterialTheme {
                val booleanPair = listOf("No", "Yes")
                val shapes = listOf("default", "circle", "rectangle", "rounded")
                val durations = listOf("3", "5", "10", "15", "20")
                val types = listOf("Success", "Error", "Warning", "Info", "Custom", "Custom (RTL)")
                val shadows = listOf("2", "4", "8", "16", "32", "64")
                val shadowColors = listOf("Default", "Black", "Red", "Green", "Blue", "Yellow")

                val snaxState = rememberSnaxState()
                var showTitle by remember { mutableStateOf(false) }
                var actionRequired by remember { mutableStateOf(false) }
                var isDismissable by remember { mutableStateOf(false) }
                var progressStyle by remember { mutableStateOf(ProgressStyle.LINEAR) }
                var animation by remember { mutableStateOf(AnimationType.DEFAULT) }
                var shape by remember { mutableStateOf(shapes.first()) }
                var duration  by remember { mutableStateOf(durations.first()) }
                val action = if (actionRequired) ({}) else null
                var type by remember { mutableStateOf(types.first()) }
                var shadow by remember { mutableStateOf(shadows.first()) }
                var shadowColor by remember { mutableStateOf(shadowColors.first()) }
                var rtl by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                            .fillMaxWidth(0.85f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_logo),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Text(
                            text = "Github.com/Milad-Mohammadi/Snax\nVIMILAD.COM",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(0.2f))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            maxItemsInEachRow = 2
                        ) {
                            ItemSelector(
                                title = "Action",
                                items = booleanPair,
                                currentItem = if (actionRequired) booleanPair.last() else booleanPair.first(),
                                onSelected = { actionRequired = it == booleanPair.last() },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Show Title",
                                items = booleanPair,
                                currentItem = if (showTitle) booleanPair.last() else booleanPair.first(),
                                onSelected = { showTitle = it == booleanPair.last() },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Can Dismiss",
                                items = booleanPair,
                                currentItem = if (isDismissable) booleanPair.last() else booleanPair.first(),
                                onSelected = { isDismissable = it == booleanPair.last() },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Progress",
                                items = ProgressStyle.entries.map { it.name },
                                currentItem = progressStyle.name,
                                onSelected = { progressStyle = ProgressStyle.valueOf(it) },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Animation",
                                items = AnimationType.entries.map { it.name },
                                currentItem = animation.name,
                                onSelected = { animation = AnimationType.valueOf(it) },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Shape",
                                items = shapes,
                                currentItem = shape,
                                onSelected = { shape = it },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Duration (s)",
                                items = durations,
                                currentItem = duration,
                                onSelected = { duration = it },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Type",
                                items = types,
                                currentItem = type,
                                onSelected = { type = it },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Shadow",
                                items = shadows,
                                currentItem = shadow,
                                onSelected = { shadow = it },
                                modifier = Modifier.weight(1f)
                            )

                            ItemSelector(
                                title = "Shadow Color",
                                items = shadowColors,
                                currentItem = shadowColor,
                                onSelected = { shadowColor = it },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                rtl = type == types.last()
                                snaxState.setData(
                                    type = when (type) {
                                        types[0] -> SnaxType.SUCCESS
                                        types[1] -> SnaxType.ERROR
                                        types[2] -> SnaxType.WARNING
                                        types[3] -> SnaxType.INFO
                                        types[4] -> SnaxType.CUSTOM(
                                            icon = R.drawable.vimilad_logo,
                                            backgroundColor =  Color(0XFFB28D8D),
                                            overlayColor = Color(0XFF613DC1),
                                            contentColor = Color(0xFFE1E1E1)
                                        )
                                        else -> SnaxType.CUSTOM(
                                            icon = R.drawable.ic_backup,
                                            backgroundColor =  Color(0xFFE1E1E1),
                                            overlayColor = Color(0xFFE1E1E1),
                                            contentColor = Color(0xFF0E0E0E),
                                            progressColor = Color(0xFF0E0E0E)
                                        )
                                    },
                                    title = if (showTitle)
                                        when (type) {
                                            types[0] -> "Order Confirmed"
                                            types[1] -> "Payment Failed"
                                            types[2] -> "Low Storage"
                                            types[3] -> "New Update Available"
                                            types[4] -> "Vimilad.com"
                                            else -> "پشتیبان\u200Cگیری"
                                        } else null,
                                    message = when (type) {
                                        types[0] -> "Your order was placed successfully. A confirmation email is on its way."
                                        types[1] -> "Payment failed. Please try again or contact support."
                                        types[2] -> "Your device is low on storage. Free up space for better performance."
                                        types[3] -> "A new app update is available. Please update to enjoy new features."
                                        types[4] -> "Check my website for more projects."
                                        else -> "برای جلوگیری از از\u200Cدست\u200Cرفتن داده\u200Cها، پشتیبان\u200Cگیری را به\u200Cصورت منظم انجام دهید."
                                    },
                                    actionTitle = when (type) {
                                        types[0] -> "Ok"
                                        types[1] -> "Support"
                                        types[2] -> "Settings"
                                        types[3] -> "Update"
                                        types[4] -> "Visit"
                                        else -> "پشتیبان\u200Cگیری"
                                    },
                                    action = action
                                )
                            },
                            content = {
                                Text(text = "SNAX IT")
                            }
                        )
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
                                AnimationType.DEFAULT -> slideInVertically(initialOffsetY = { it }) + scaleIn(initialScale = 0.8f)
                                AnimationType.SLIDE_AND_FADE -> fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
                                AnimationType.EXPAND_AND_CONTRACT -> expandVertically(
                                    expandFrom = Alignment.Top,
                                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                                ) + fadeIn()
                            },
                            animationExit = when (animation) {
                                AnimationType.DEFAULT -> slideOutVertically(targetOffsetY = { it }) + scaleOut(targetScale = 0.8f)
                                AnimationType.SLIDE_AND_FADE -> fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                                AnimationType.EXPAND_AND_CONTRACT -> shrinkVertically(
                                    shrinkTowards = Alignment.Top,
                                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                                ) + fadeOut()
                            },
                            shadow = Dp(shadow.toFloat()),
                            shadowColor = when (shadowColor) {
                                shadowColors[0] -> Color.Black.copy(0.8f)
                                shadowColors[1] -> Color.Black
                                shadowColors[2] -> Color.Red
                                shadowColors[3] -> Color.Green
                                shadowColors[4] -> Color.Blue
                                else -> Color.Yellow
                            },
                            duration = duration.toInt().times(1000).toLong(),
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
    title: String,
    items: List<String>,
    currentItem: String,
    onSelected: (String) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                )

                Text(
                    text = currentItem,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        onSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}