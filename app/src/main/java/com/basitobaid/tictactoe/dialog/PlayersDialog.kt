package com.basitobaid.tictactoe.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.basitobaid.tictactoe.R

class PlayersDialog: DialogFragment() {


    companion object {
        fun newInstance(): PlayersDialog {
            return PlayersDialog()
        }
    }


    private lateinit var onButtonClickListener: OnButtonClickListener

    fun setOnButtonClickListener(onClickListener: OnButtonClickListener) {
        this.onButtonClickListener = onClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_players, container, false)
        val playerOne = view.findViewById<EditText>(R.id.player_one)
        val playerTwo = view.findViewById<EditText>(R.id.player_two)
        val ok = view.findViewById<Button>(R.id.ok)
        ok.setOnClickListener {
            if (playerOne.text.toString() == "" || playerTwo.text.toString() == "") {
                Toast.makeText(context, "Provide both players", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            onButtonClickListener.onSubmit(playerOne.text.toString(), playerTwo.text.toString())
        }
        return view
    }

    interface OnButtonClickListener {
        fun onSubmit(playerOne: String, playerTwo:String)
    }
}