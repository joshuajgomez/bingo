package com.joshgm3z.bingo.repo

data class BingoRoom(
    val nextTurn: Int = 0,
    val calledNumbers: List<Int> = listOf(),
    var player1Board: List<Int> = listOf(),
    var player2Board: List<Int> = listOf(),
    val bingoPlayer: Int = 0,
)