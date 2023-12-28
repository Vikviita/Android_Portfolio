package com.vikvita.tictactoe

import java.io.Serializable

enum class ticTac(val image: Int) : Serializable {
    TIC(R.drawable.tics), TAC(R.drawable.tacs), EMPTY(0);


}