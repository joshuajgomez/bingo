package com.joshgm3z.bingo.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joshgm3z.bingo.repo.BingoRoom
import com.joshgm3z.bingo.repo.GameRepository
import com.joshgm3z.bingo.screens.Route
import com.joshgm3z.bingo.utils.Calculator.Companion.calculateStrikes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameUiState(
    val myBoard: List<Int> = listOf(),
    val calledNumbers: List<Int> = listOf(),
    val strikes: List<Strike> = listOf(),
    val isStartButtonEnabled: Boolean = true,
    val isBingoButtonEnabled: Boolean = false,
    val opponentBoard: List<Int> = listOf(),
    val player2Strikes: List<Strike> = listOf(),
    val status: String = "New game",
    val gameOver: Boolean = false,
    val nextTurn: Int = 0,
) {
}

enum class Strike(vararg val indexes: Int) {
    None,
    Diag1(0, 6, 12, 18, 24),
    Diag2(4, 8, 12, 16, 20),
    Row1(0, 1, 2, 3, 4),
    Row2(5, 6, 7, 8, 9),
    Row3(10, 11, 12, 13, 14),
    Row4(15, 16, 17, 18, 19),
    Row5(20, 21, 22, 23, 24),
    Col1(0, 5, 10, 15, 20),
    Col2(1, 6, 11, 16, 21),
    Col3(2, 7, 12, 17, 22),
    Col4(3, 8, 13, 18, 23),
    Col5(4, 9, 14, 19, 24),
}

@HiltViewModel
class GameViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    private val player: Int = savedStateHandle.toRoute<Route.Game>().player

    init {
        viewModelScope.launch {
            gameRepository.observeRoomUpdates { bingoRoom ->
                _uiState.update {
                    val strikes = bingoRoom.strikes()
                    it.copy(
                        myBoard = bingoRoom.board(),
                        opponentBoard = bingoRoom.opponentBoard(),
                        calledNumbers = bingoRoom.calledNumbers,
                        strikes = strikes,
                        isBingoButtonEnabled = strikes.size >= 5 && bingoRoom.bingoPlayer == 0,
                        isStartButtonEnabled = (bingoRoom.board().isEmpty() &&
                                bingoRoom.opponentBoard().isEmpty()) || bingoRoom.bingoPlayer != 0,
                        status = getStatus(bingoRoom),
                        nextTurn = bingoRoom.nextTurn
                    )
                }
            }

        }
    }

    private fun getStatus(bingoRoom: BingoRoom): String = when {
        bingoRoom.bingoPlayer == player -> "$player You called bingo"
        bingoRoom.bingoPlayer > 0 -> "$player Opponent called bingo"
        bingoRoom.strikes().size >= 5 -> "$player Call bingo!"
        bingoRoom.nextTurn == player -> "$player Your turn"
        bingoRoom.nextTurn != player -> "$player Opponent turn"
        else -> "$player Start game"
    }


    fun onNumberClicked(number: Int) {
        if (_uiState.value.nextTurn == player) {
            gameRepository.callNumber(
                number,
                when (player) {
                    1 -> 2; else -> 1
                }
            )
        }
    }

    private fun BingoRoom.strikes() = when (player) {
        1 -> calculateStrikes(player1Board, calledNumbers)
        2 -> calculateStrikes(player2Board, calledNumbers)
        else -> listOf()
    }

    private fun BingoRoom.board() = when (player) {
        1 -> player1Board
        2 -> player2Board
        else -> listOf()
    }

    private fun BingoRoom.opponentBoard() = when (player) {
        1 -> player2Board
        2 -> player1Board
        else -> listOf()
    }

    fun callBingo() {
        gameRepository.callBingo(player)
    }

    fun resetGameClick() {
        gameRepository.resetGame()
    }
}

