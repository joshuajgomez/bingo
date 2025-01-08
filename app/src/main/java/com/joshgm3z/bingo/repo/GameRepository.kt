package com.joshgm3z.bingo.repo

import javax.inject.Inject

class GameRepository
@Inject constructor(
    private val firebase: FirebaseManager,
) {
    fun notifyBoard(player: Int, list: List<Int>) {
        firebase.notifyBoard(player, list)
    }

    fun callNumber(number: Int, nextTurn: Int) {
        firebase.callNumber(number, nextTurn)
    }

    fun observeRoomUpdates(
        onRoomUpdate: (BingoRoom) -> Unit
    ) {
        firebase.observeGameState(onRoomUpdate)
    }

    fun callBingo(player: Int) {
        firebase.callBingo(player)
    }

    fun resetGame() {
        firebase.resetGame()
    }
}
