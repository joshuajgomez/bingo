package com.joshgm3z.bingo.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joshgm3z.bingo.repo.FirebaseManager
import com.joshgm3z.bingo.repo.PlayerRepository
import com.joshgm3z.bingo.ui.theme.BingoTheme

@Preview
@Composable
fun MenuScreenPreview() {
    BingoTheme {
        MenuScreen()
    }
}

@Composable
fun MenuScreenContainer(navigate: (Int) -> Unit) {
    MenuScreen {
        navigate(it)
    }
}

@Composable
fun MenuScreen(onPlayerChosen: (Int) -> Unit = {}) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("I play as ")
        Button(onClick = { onPlayerChosen(1) }) {
            Text("Player 1")
        }
        Button(onClick = { onPlayerChosen(2) }) {
            Text("Player 2")
        }
    }
}