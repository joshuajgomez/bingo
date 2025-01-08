package com.joshgm3z.bingo.utils

import com.joshgm3z.bingo.game.Strike

class Calculator {
    companion object {
        fun calculateStrikes(
            allNumbers: List<Int>,
            calledNumbers: List<Int>
        ): List<Strike> {
            val indexes = mutableListOf<Int>()
            allNumbers.forEachIndexed { index, i ->
                if (i in calledNumbers.toIntArray()) {
                    indexes.add(index)
                }
            }
            return Strike.entries.filter {
                it != Strike.None && indexes.containsAll(it.indexes.toList())
            }
        }
    }
}