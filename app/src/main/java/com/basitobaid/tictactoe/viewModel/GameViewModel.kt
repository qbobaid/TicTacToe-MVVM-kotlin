package com.basitobaid.tictactoe.viewModel

import android.util.Log
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.basitobaid.tictactoe.model.Cell
import com.basitobaid.tictactoe.model.Game
import com.basitobaid.tictactoe.model.Player

class GameViewModel: ViewModel() {
    lateinit var cells: ObservableArrayMap<String, String>
    lateinit var backgrounds: ObservableArrayMap<String, Int>
    private lateinit var game: Game

    fun init(player1: String, player2: String) {
        game = Game(player1, player2)
        cells = ObservableArrayMap()
        initBackground()
    }

    private fun initBackground() {
        backgrounds = ObservableArrayMap()
        backgrounds["one"] = android.R.color.holo_green_light
        backgrounds["two"] = android.R.color.transparent
    }

    fun onCellClickAt(row: Int, col: Int) {
        if (game.cells[row][col] == null) {
            game.cells[row][col] = Cell(game.currentPlayer)
            cells[stringFromNumber(row, col)] = game.currentPlayer?.value
            if (game.isGameOver()) {
                game.reset()
            } else {
                game.switchPlayer()
                if (game.currentPlayer?.value.equals("X")) {
                    backgrounds["one"] = android.R.color.holo_green_light
                    backgrounds["two"] = android.R.color.transparent
                } else {
                    backgrounds["one"] = android.R.color.transparent
                    backgrounds["two"] = android.R.color.holo_green_light
                }
            }
        }
    }

    fun getWinner(): LiveData<Player?> {
        return game.winner
    }

    private fun stringFromNumber(row:Int, col: Int): String {
        Log.d("GameViewModel", "$row$col")
        return "$row$col"
    }
}