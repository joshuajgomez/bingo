package com.joshgm3z.bingo.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun GameButtonsPreview() {
    GameButtons(GameUiState())
}

@Composable
fun GameButtons(
    gameState: GameUiState,
    onBingoClick: () -> Unit = {},
    onStartClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.8f)
    ) {
        val buttonMod = Modifier
            .width(140.dp)
            .height(40.dp)
        Button(
            onClick = onBingoClick,
            modifier = buttonMod,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = colorScheme.primary
            ),
            enabled = gameState.isBingoButtonEnabled
        ) {
            Icon(Icons.Default.Star, contentDescription = null)
            Spacer(Modifier.size(5.dp))
            Text("Call Bingo")
        }
        Button(
            onClick = onStartClick,
            modifier = buttonMod,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = colorScheme.secondary
            ),
            enabled = gameState.isStartButtonEnabled
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(Modifier.size(5.dp))
            Text("Start Game")
        }
    }
}