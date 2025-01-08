package com.joshgm3z.bingo.game

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joshgm3z.bingo.R
import com.joshgm3z.bingo.ui.ThemePreviews
import com.joshgm3z.bingo.ui.theme.BingoTheme

@ThemePreviews
@Composable
fun BingoLogoPreview() {
    BingoTheme {
        BingoLogo()
    }
}

@Composable
fun BingoLogo(
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Gray.copy(alpha = 0.2f),
        targetValue = Color.Gray.copy(alpha = 0.1f),
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
        label = "color"
    )
    Box(
        Modifier
            .padding(start = 40.dp, end = 40.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(
                color = when {
                    enabled -> animatedColor
                    else -> Color.Gray.copy(alpha = 0.2f)
                },
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.bingo_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}