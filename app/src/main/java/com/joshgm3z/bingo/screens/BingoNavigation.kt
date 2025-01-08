package com.joshgm3z.bingo.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joshgm3z.bingo.game.GameContainer
import com.joshgm3z.bingo.menu.MenuScreenContainer
import kotlinx.serialization.Serializable

class Route {
    @Serializable
    data object Menu

    @Serializable
    data class Game(val player: Int)
}

@Composable
fun BingoNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Route.Menu,
    ) {
        composable<Route.Menu> {
            MenuScreenContainer {
                navController.navigate(Route.Game(it))
            }
        }
        composable<Route.Game> {
            GameContainer {
                navController.popBackStack()
            }
        }
    }
}