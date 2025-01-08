package com.joshgm3z.bingo.menu

import androidx.lifecycle.ViewModel
import com.joshgm3z.bingo.repo.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
@Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {
    fun onPlayerChosen(player: Int) {
    }

}