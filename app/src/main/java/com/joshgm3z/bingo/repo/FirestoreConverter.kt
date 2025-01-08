package com.joshgm3z.bingo.repo

import com.google.firebase.firestore.DocumentSnapshot

@Suppress("UNCHECKED_CAST")
class FirestoreConverter {
    companion object {
        fun toBingoRoom(document: DocumentSnapshot): BingoRoom {
            return BingoRoom(
                nextTurn = document.getLong("nextTurn")?.toInt() ?: 0,
                calledNumbers = document.getListSafe("calledNumbers"),
                player1Board = document.getListSafe("player1Board"),
                player2Board = document.getListSafe("player2Board"),
                bingoPlayer = document.getLong("bingoPlayer")?.toInt() ?: 0
            )
        }

        fun toDocument(bingoRoom: BingoRoom): Map<String, Any> {
            return mapOf(
                "nextTurn" to bingoRoom.nextTurn,
                "calledNumbers" to bingoRoom.calledNumbers,
                "player1Board" to bingoRoom.player1Board,
                "player2Board" to bingoRoom.player2Board,
                "bingoPlayer" to bingoRoom.bingoPlayer,
            )
        }
    }
}

private fun DocumentSnapshot.getListSafe(key: String): List<Int> = when {
    contains(key) -> get(key) as List<Int>
    else -> emptyList()
}

private fun DocumentSnapshot.getStringSafe(key: String): String = when {
    contains(key) -> get(key) as String
    else -> ""
}

private fun DocumentSnapshot.getLongSafe(key: String): List<Int> = when {
    contains(key) -> get(key) as List<Int>
    else -> emptyList()
}
