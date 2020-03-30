package com.basitobaid.tictactoe.model

import androidx.lifecycle.MutableLiveData

class Game(player1: String, player2: String) {

    private val _boardSize = 3

    var cells: Array<Array<Cell?>>
    var winner: MutableLiveData<Player?> = MutableLiveData()

    private var player1: Player?
    private var player2: Player?
    var currentPlayer: Player?
    private var cell:Cell? = null

    init {
        this.player1 = Player(player1, "X")
        this.player2 = Player(player2, "O")
        this.currentPlayer = this.player1
        cells = initCells()
    }

    private fun initCells(): Array<Array<Cell?>> {
        return Array(3) {
            Array(3) {
                cell
            }
        }
    }

    fun switchPlayer() {
        currentPlayer = if(currentPlayer == player1) player2 else player1
//        Log.d("Game", currentPlayer?.name)
    }

    fun isGameOver(): Boolean {
        if (hasThreeSameDiagonalCells() || hasThreeSameHorizontalCells() || hasThreeSameVerticalCells()) {
            winner.value = currentPlayer
            return true
        }
        if (isBoardFull()) {
            winner.value = null
            return true
        }
        return false

    }

    private fun isBoardFull(): Boolean {
        for (i in 0 until _boardSize) {
            for (j in 0 until _boardSize) {
                if (cells[i][j] == null) {
                    return false
                }
                if (!cells[i][j]?.player?.value.equals("X") &&
                    !cells[i][j]?.player?.value.equals("O")) {
                    return false
                }
            }
        }
        return true
    }

    private fun hasThreeSameHorizontalCells(): Boolean {
        for ( i in 0 until _boardSize) {
            if (areEqual(cells[i][0], cells[i][1], cells[i][2])) {
                return true
            }
        }
        return false
    }

    private fun hasThreeSameVerticalCells(): Boolean {
        for ( i in 0 until _boardSize) {
            if (areEqual(cells[0][i], cells[1][i], cells[2][i])) {
                return true
            }
        }
        return false
    }

    private fun hasThreeSameDiagonalCells(): Boolean {
        if (areEqual(cells[0][0], cells[1][1], cells[2][2]) ||
            areEqual(cells[0][2], cells[1][1], cells[2][0])) {
            return true
        }
        return false
    }

    private fun areEqual(vararg cell: Cell?): Boolean {
        if (cell[0] == null || cell[1] == null || cell[2] == null)
            return false

        if (cell[0]?.player?.value == cell[1]?.player?.value &&
            cell[0]?.player?.value == cell[2]?.player?.value)
            return true
        return false


    }

    fun reset() {
        player1 = null
        player2 = null
        currentPlayer = null
        cells = initCells()
    }

}