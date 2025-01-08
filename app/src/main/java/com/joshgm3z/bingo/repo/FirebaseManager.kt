package com.joshgm3z.bingo.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.joshgm3z.bingo.utils.Logger
import javax.inject.Inject

const val COLLECTION_BINGO_ROOM = "bingo_rooms"
const val DEFAULT_ROOM_ID = "B7zcQzCdPsXTmYSXHTGa"

class FirebaseManager
@Inject constructor() {

    private val firestore = Firebase.firestore

    private val bingoRoomCollection: CollectionReference
        get() = firestore.collection(COLLECTION_BINGO_ROOM)

    fun notifyBoard(player: Int, list: List<Int>) {
        val bingoRoom = BingoRoom(
            nextTurn = 1,
        )
        when (player) {
            1 -> bingoRoom.player1Board = list
            2 -> bingoRoom.player2Board = list
        }
        bingoRoomCollection.document(DEFAULT_ROOM_ID)
            .update(FirestoreConverter.toDocument(bingoRoom))
            .addOnSuccessListener {
                Logger.entry()
            }
            .addOnFailureListener {
                Logger.entry()
            }
    }

    fun callNumber(number: Int, nextTurn: Int) {
        bingoRoomCollection.document(DEFAULT_ROOM_ID)
            .update(
                "calledNumbers", FieldValue.arrayUnion(number),
                "nextTurn", nextTurn
            )
            .addOnSuccessListener {
                Logger.entry()
            }
            .addOnFailureListener {
                Logger.entry()
            }
    }

    fun callBingo(player: Int) {
        bingoRoomCollection.document(DEFAULT_ROOM_ID)
            .update("bingoPlayer", player)
            .addOnSuccessListener {
                Logger.entry()
            }
            .addOnFailureListener {
                Logger.entry()
            }
    }

    fun observeGameState(
        onRoomUpdate: (BingoRoom) -> Unit
    ) {
        bingoRoomCollection.document(DEFAULT_ROOM_ID)
            .addSnapshotListener { value, error ->
                val bingoRoom = value?.let { FirestoreConverter.toBingoRoom(it) }
                when {
                    bingoRoom != null -> onRoomUpdate(bingoRoom)
                    else -> Logger.error(error?.message ?: "Error fetching room")
                }
            }
    }

    fun resetGame() {
        bingoRoomCollection.document(DEFAULT_ROOM_ID)
            .update(
                "calledNumbers", listOf<Int>(),
                "bingoPlayer", 0,
                "nextTurn", 1,
                "player1Board", getBoard(),
                "player2Board", getBoard(),
            )
    }

    private fun getBoard(): List<Int> {
        val list = arrayListOf<Int>()
        for (i in 1..25) {
            list.add(i)
        }
        return list.shuffled()
    }

}
