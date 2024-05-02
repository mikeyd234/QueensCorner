package com.example.queenscorner.view

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.queenscorner.R
import com.example.queenscorner.model.Position
import com.example.queenscorner.model.QueensCorner
import com.example.queenscorner.ui.theme.QueensCornerTheme

class BoardActivity : ComponentActivity() {
    private var selectedFrom: Position? = null
    private var game: QueensCorner = QueensCorner()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        setupBoard()
    }

    private fun setupBoard() {
        val boardLayout = findViewById<ConstraintLayout>(R.id.board_layout)

        for (x in 0 until 7) {
            for (y in 0 until 7) {
                // Generate button ID based on the square position
                val buttonId = resources.getIdentifier("x${x}y${y}", "id", packageName)
                val button = findViewById<Button>(buttonId)

                button.setOnClickListener {
                    handleSquareClick(Position(x, y))
                }
            }
        }
    }

    private fun handleSquareClick(position: Position) {
        if (selectedFrom == null) { // No square is selected yet
            selectedFrom = position // Set the "from" square
            // Optional: Provide visual feedback for selection (e.g., changing color)
        } else {
            // "From" square is already selected, this is the "to" square
            val from = selectedFrom!!
            val to = position

            val moveSuccessful = game.movePiece(from, to) // Call your move function

            if (moveSuccessful) {
                selectedFrom = null // Clear the selection
                updateBoard() // Refresh the board UI to reflect the move
            } else {
                // Move failed, reset the selection and possibly give feedback
                selectedFrom = null
                // Optional: show a message about the failed move
            }
        }


    }
    private fun updateBoard(){
        TODO()
    }
}