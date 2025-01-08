package com.joshgm3z.bingo.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshgm3z.bingo.ui.ThemePreviews
import com.joshgm3z.bingo.ui.theme.BingoTheme

@ThemePreviews
@Composable
fun BingoTextPreview() {
    BingoTheme {
        BingoText(3)
    }
}

@Composable
fun BingoText(count: Int, gameBoxSize: Int = 300) {
    LazyRow(
        Modifier.width(gameBoxSize.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed("BINGO".toList()) { index, item ->
            Text(
                item.toString(),
                fontSize = (gameBoxSize / 10).sp,
                modifier = Modifier
                    .width((gameBoxSize / 5).dp)
                    .background(
                        color = when {
                            index < count -> colorScheme.primary
                            else -> colorScheme.background
                        },
                        shape = CircleShape
                    ),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = when {
                    index < count -> colorScheme.onPrimary
                    else -> colorScheme.primary
                },
            )
        }
    }
}