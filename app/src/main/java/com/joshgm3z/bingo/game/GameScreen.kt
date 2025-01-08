package com.joshgm3z.bingo.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joshgm3z.bingo.R
import com.joshgm3z.bingo.ui.ThemePreviews
import com.joshgm3z.bingo.ui.theme.BingoTheme
import com.joshgm3z.bingo.utils.Calculator.Companion.calculateStrikes
import com.joshgm3z.bingo.utils.Logger

val gameBoxSize = 300

@ThemePreviews
@Composable
fun GamePreview() {
    val list = arrayListOf<Int>()
    for (i in 1..25) {
        list.add(i)
    }
    val calledNumbers = listOf(11, 12, 13, 14, 15, 1, 7, 19, 25)
    BingoTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            val list2 = list.shuffled()
            GameScreen(
                GameUiState(
                    myBoard = list.shuffled(),
                    opponentBoard = list2,
                    player2Strikes = calculateStrikes(list2, calledNumbers),
                    calledNumbers = calledNumbers,
                    strikes = calculateStrikes(list, calledNumbers)
                )
            )
        }
    }
}

@Composable
fun GameContainer(
    gameViewModel: GameViewModel = hiltViewModel(),
    onCloseClick: () -> Unit,
) {
    val gameState = gameViewModel.uiState.collectAsState()
    GameScreen(
        gameState.value,
        onNumberClicked = { gameViewModel.onNumberClicked(it) },
        onBingoClick = { gameViewModel.callBingo() },
        onStartClick = { gameViewModel.resetGameClick() },
        onCloseClick = {},
    )
}

@Composable
fun GameScreen(
    gameState: GameUiState,
    onNumberClicked: (Int) -> Unit = {},
    onBingoClick: () -> Unit = {},
    onStartClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    Logger.debug("gameState = [$gameState]")
    Scaffold(
        topBar = { GameActionBar(gameState, onCloseClick) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//        Player2Board(gameState)
//        Spacer(Modifier.size(20.dp))
            BingoLogo()
            Spacer(Modifier.size(20.dp))
            BingoText(gameState.strikes.size)
            Spacer(Modifier.size(10.dp))
            Board(gameState, onNumberClicked, gameBoxSize)
            Spacer(Modifier.size(10.dp))
            GameButtons(gameState, onBingoClick, onStartClick)
            Spacer(Modifier.size(30.dp))
        }
    }
}

@Composable
fun BingoLogo() {
    Box(
        Modifier
            .padding(40.dp)
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = Color.Gray.copy(alpha = 0.2f),
                shape = RoundedCornerShape(30.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.bingo_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameActionBar(
    gameState: GameUiState,
    onCloseClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(gameState.status)
        },
        navigationIcon = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun Player2Board(gameState: GameUiState) {
    if (gameState.opponentBoard.isEmpty()) return
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = colorScheme.secondary,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp)
    ) {
        Text(
            "Opponent board",
            modifier = Modifier.padding(10.dp),
            fontSize = 20.sp,
            color = colorScheme.onSecondary
        )
        Board(
            gameState = gameState.copy(
                myBoard = gameState.opponentBoard,
                strikes = gameState.player2Strikes
            ),
            onNumberClicked = {},
            gameBoxSize = 200
        )
    }
}

@Composable
fun Board(
    gameState: GameUiState,
    onNumberClicked: (Int) -> Unit = {},
    gameBoxSize: Int,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .width(gameBoxSize.dp)
            .background(color = colorScheme.primary)
    ) {
        itemsIndexed(gameState.myBoard) { index, item ->
            Box(
                Modifier
                    .padding(vertical = 1.dp, horizontal = 1.dp)
                    .size((gameBoxSize / 5).dp)
                    .background(color = getBgColor(index, gameState.strikes))
                    .clickable { onNumberClicked(item) },
                contentAlignment = Alignment.Center
            ) {
                val calledNumbers = gameState.calledNumbers.toIntArray()
                Text(
                    item.toString(),
                    color = getFgColor(index, gameState.strikes),
                    fontSize = (gameBoxSize / 12).sp,
                    textDecoration = when {
                        calledNumbers.contains(item) -> TextDecoration.LineThrough
                        else -> TextDecoration.None
                    },
                )
            }
        }
    }
}

@Composable
fun getBgColor(index: Int, strikes: List<Strike>): Color {
    strikes.forEach {
        when {
            it == Strike.None || index !in it.indexes -> {} // Ignore
            else -> return colorScheme.primary
        }
    }
    return colorScheme.surface
}

@Composable
fun getFgColor(index: Int, strikes: List<Strike>): Color {
    strikes.forEach {
        when {
            it == Strike.None || index !in it.indexes -> {} // Ignore
            else -> return colorScheme.onPrimary
        }
    }
    return colorScheme.onSurface
}
