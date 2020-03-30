package com.basitobaid.tictactoe.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.basitobaid.tictactoe.R

class GameEndDialog: DialogFragment() {

    private var winner: String? = null
    private lateinit var onButtonClickListener: OnButtonClickListener


    companion object {
        fun newInstance(winner: String): GameEndDialog {
            val dialog = GameEndDialog()
            val args = Bundle()
            args.putString("winner", winner)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        winner = arguments?.getString("winner")
    }

    fun setOnClickListener(onClickListener: OnButtonClickListener) {
        this.onButtonClickListener = onClickListener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_game_end, container, false)
        val winnerTv = view.findViewById<TextView>(R.id.winnerName)
        val cancel = view.findViewById<Button>(R.id.close_btn)
        val playAgain = view.findViewById<Button>(R.id.play_btn)
        cancel.setOnClickListener { onButtonClickListener.onCancel() }
        playAgain.setOnClickListener { onButtonClickListener.onPlayAgain() }
        winnerTv.text = winner
        return view
    }

    interface OnButtonClickListener {
        fun onCancel()
        fun onPlayAgain()
    }
}