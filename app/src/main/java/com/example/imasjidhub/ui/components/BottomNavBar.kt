package com.example.imasjidhub.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.theme.AppText

data class NavItem(val title: String, val iconRes: Int)

@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        NavItem("Beranda", R.drawable.ic_home),
        NavItem("Jadwal", R.drawable.ic_time),
        NavItem("Tambah", R.drawable.ic_add),
        NavItem("Berita", R.drawable.ic_news),
        NavItem("Profil", R.drawable.ic_profile)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        NavigationBar(
            containerColor = AppText.Fourth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        BottomNavItem(
                            title = item.title,
                            icon = item.iconRes,
                            selected = selectedIndex == index,
                            onClick = { onItemSelected(index) }
                        )
                    }
                }
            }
        }
        Canvas(
            modifier = Modifier
                .size(width = 120.dp, height = 45.dp)
                .align(Alignment.TopCenter)
        ) {
            val width = size.width
            val height = size.height

            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, 0f)
                // Cekung masuk ke dalam dari kiri → tengah
                cubicTo(
                    width * 0.15f, height * 0.0f,
                    width * 0.25f, height * 1f,
                    width * 0.5f, height * 1f
                )

                // Dari tengah → kanan atas, cekung naik keluar
                cubicTo(
                    width * 0.75f, height * 1f,
                    width * 0.85f, height * 0.0f,
                    width, 0f
                )
                lineTo(width, 0f)
                lineTo(0f, 0f)
                close()
            }

            drawPath(
                path = path,
                color = AppText.Fitfth
            )
        }
        FloatingNavItem(
            title = items[2].title,
            icon = items[2].iconRes,
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-36).dp)
        )
    }
}


@Composable
fun BottomNavItem(title: String, icon: Int, selected: Boolean, onClick: () -> Unit) {
    val iconSize by animateFloatAsState(
        targetValue = if (selected) 34f else 30f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val labelColor by animateColorAsState(
        targetValue = if (selected) AppText.Main else Color.Gray
    )
    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(iconSize.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = fontWeight),
            color = labelColor
        )
    }
}


@Composable
fun FloatingNavItem(
    title: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val labelColor by animateColorAsState(if (selected) AppText.Main else Color.Gray)
    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .size(72.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(AppText.Third, CircleShape)
            )

            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier
                    .size(42.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = fontWeight),
            color = labelColor
        )
    }
}