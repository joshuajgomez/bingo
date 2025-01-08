package com.joshgm3z.bingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import com.joshgm3z.bingo.screens.BingoNavHost
import com.joshgm3z.bingo.ui.theme.BingoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BingoTheme {
                Surface(color = colorScheme.background) {
                    BingoNavHost()
                }
            }
        }
    }
}
