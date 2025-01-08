package com.joshgm3z.bingo.repo

import javax.inject.Inject

class PlayerRepository
@Inject constructor(
    private val firebaseManager: FirebaseManager
) {

}
