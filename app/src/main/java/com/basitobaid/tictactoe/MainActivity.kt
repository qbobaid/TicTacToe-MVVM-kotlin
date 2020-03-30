package com.basitobaid.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.basitobaid.tictactoe.databinding.ActivityMainBinding
import com.basitobaid.tictactoe.dialog.GameEndDialog
import com.basitobaid.tictactoe.dialog.GameEndDialog.OnButtonClickListener
import com.basitobaid.tictactoe.dialog.PlayersDialog
import com.basitobaid.tictactoe.model.Player
import com.basitobaid.tictactoe.viewModel.GameViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel
    lateinit var playerOneName: String
    lateinit var playerTwoName: String
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        showPlayersDialog()
    }

    private fun initGame() {
        gameViewModel.init(playerOneName, playerTwoName)
        binding.gameViewModel = gameViewModel
        binding.playerOne.text = playerOneName
        binding.playerTwo.text = playerTwoName
        setGameEndListener()
    }

    private fun setGameEndListener() {
        gameViewModel.getWinner().observe(this, Observer { winner: Player? ->
            onWinnerChanged(winner)
        })
    }

    private fun onWinnerChanged(winner: Player?) {
        if (winner == null) {
            showGameEndDialog("No One")
        } else {
            showGameEndDialog(winner.name)
        }
    }

    private fun showPlayersDialog() {
        val playersDialog = PlayersDialog.newInstance()
        val listener = object : PlayersDialog.OnButtonClickListener {
            override fun onSubmit(playerOne: String, playerTwo: String) {
                playerOneName = playerOne
                playerTwoName = playerTwo
                initGame()
                playersDialog.dismiss()
            }
        }
        playersDialog.setOnButtonClickListener(listener)
        playersDialog.show(supportFragmentManager, "players_dialog")
        playersDialog.isCancelable = false
    }

    private fun showGameEndDialog(winnerName: String) {
        val gameEndDialog = GameEndDialog.newInstance(winnerName)
        val listener = object : OnButtonClickListener {
            override fun onCancel() {
                showPlayersDialog()
                gameEndDialog.dismiss()
            }

            override fun onPlayAgain() {
                initGame()
                gameEndDialog.dismiss()
            }
        }
        gameEndDialog.setOnClickListener(listener)
        gameEndDialog.show(supportFragmentManager, "end_dialog")
        gameEndDialog.isCancelable = false
    }
}
