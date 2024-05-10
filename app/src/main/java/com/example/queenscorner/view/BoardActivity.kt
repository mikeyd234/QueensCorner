package com.example.queenscorner.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.queenscorner.R
import com.example.queenscorner.model.Bishop
import com.example.queenscorner.model.King
import com.example.queenscorner.model.Knight
import com.example.queenscorner.model.PawnHor
import com.example.queenscorner.model.PawnVer
import com.example.queenscorner.model.Position
import com.example.queenscorner.model.QueensCorner
import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.Queen
import com.example.queenscorner.model.Rook
import java.lang.IllegalArgumentException

class BoardActivity : ComponentActivity() {
    private var selectedFrom: Position? = null
    private var game: QueensCorner = QueensCorner()
    private val defaultColor = 0x00FFFFFF // Transparent
    private val selectedColor = 0x6000FF00.toInt() // semi transparent green
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        setupBoard()
    }

    private fun setupBoard() {
        // Place pieces on the board
        for (player in game.players) {
            for (piece in player.pieces) {
                placePiece(piece)
            }
        }

        for (x in 0 until 8) {
            for (y in 0 until 8) {
                // Generate button ID based on the square position
                val buttonId = resources.getIdentifier("x${x}y${y}", "id", packageName)
                if (buttonId == 0) {
                    throw IllegalArgumentException("Button ID not found for x$x, y$y")
                }
                val button = findViewById<Button>(buttonId)

                if (button == null) {
                    Log.e("BoardSetup", "Button not found for ID: $buttonId")
                } else {
                    Log.d("BoardSetup", "Button found for ID: $buttonId")
                    button.setOnClickListener {
                        handleSquareClick(Position(x, y))
                        Log.d("Button", "initialized x$x, y$y")
                    }
                }
            }
        }

    }

    private fun placePiece(piece: Piece) {
        val pos = piece.position
        val squareId = resources.getIdentifier("square_${pos.x}${pos.y}", "id", packageName)
        if (squareId == 0) {
            throw IllegalArgumentException("Square ID not found for x$pos.x, y$pos.y")
        }
        val squareView = findViewById<ImageView>(squareId)

        squareView.setImageResource(getPieceDrawable(piece))
    }

    private fun getPieceDrawable(piece: Piece): Int {
        return when (piece.owner) {
            0 -> when (piece) {
                is PawnHor -> R.drawable.whitepawn
                is PawnVer -> R.drawable.whitepawn
                is Bishop -> R.drawable.whitebishop
                is King -> R.drawable.whiteking
                is Knight -> R.drawable.whiteknight
                is Rook -> R.drawable.whiterook
                is Queen -> R.drawable.whitequeen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }

            1 -> when (piece) {
                is PawnHor -> R.drawable.bluepawn
                is PawnVer -> R.drawable.bluepawn
                is Bishop -> R.drawable.bluebishop
                is King -> R.drawable.blueking
                is Knight -> R.drawable.blueknight
                is Rook -> R.drawable.bluerook
                is Queen -> R.drawable.bluequeen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }

            2 -> when (piece) {
                is PawnHor -> R.drawable.redpawn
                is PawnVer -> R.drawable.redpawn
                is Bishop -> R.drawable.redbishop
                is King -> R.drawable.redking
                is Knight -> R.drawable.redknight
                is Rook -> R.drawable.redrook
                is Queen -> R.drawable.redqueen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }

            3 -> when (piece) {
                is PawnHor -> R.drawable.blackpawn
                is PawnVer -> R.drawable.blackpawn
                is Bishop -> R.drawable.blackbishop
                is King -> R.drawable.blackking
                is Knight -> R.drawable.blackknight
                is Rook -> R.drawable.blackrook
                is Queen -> R.drawable.blackqueen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }

            else -> throw IllegalArgumentException("Unknown Piece owner")
        }
    }

    private fun handleSquareClick(position: Position) {
        val buttonId = resources.getIdentifier("x${position.x}y${position.y}", "id", packageName)
        val button = findViewById<Button>(buttonId)
        if (selectedFrom == null) { // No square is selected yet
            if (game.pieceCheck(game.getCurrentPlayerIndex(), position)) {
                selectedFrom = position // Set the "from" square
                // Change the background color to indicate selection
                button?.setBackgroundColor(selectedColor)
            }
        } else {
            // "From" square is already selected, this is the "to" square
            val from = selectedFrom!!

            val moveSuccessful = game.movePiece(from, position) // Call your move function
            val fromButtonId = resources.getIdentifier("x${from.x}y${from.y}", "id", packageName)
            val fromButton = findViewById<Button>(fromButtonId)
            if (moveSuccessful.first) {
                // Reset the color of the "from" button
                fromButton?.setBackgroundColor(defaultColor)
                selectedFrom = null // Clear the selection
                updateBoard(from, position) // Refresh the board UI to reflect the move
            } else {
                // Move failed, reset the selection and possibly give feedback
                fromButton?.setBackgroundColor(defaultColor) // Reset the background color
                selectedFrom = null
                Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show()
            }
            if (moveSuccessful.second != null){
                getPieceDrawable(moveSuccessful.second!!)
            }
        }


    }

    private fun updateBoard(from: Position, to: Position) {
        val fromView = getSquareView(from)
        val toView = getSquareView(to)

        if (fromView == null || toView == null) {
            Log.e("UpdateBoard", "Invalid square: from=$from, to=$to")
            return // Early exit if either view is not found
        }

        val piece = game.getCurrentPlayer().pieces.firstOrNull { it.position == to }

        // After piece is moved, proceed to next turn
        game.nextTurn()

        if (piece != null) {
            toView.setImageResource(getPieceDrawable(piece))
            fromView.setImageResource(android.R.color.transparent)
        } else {
            Log.e("UpdateBoard", "No piece found at position $to")
        }
    }

    private fun getSquareView(position: Position): ImageView? {
        val squareId =
            resources.getIdentifier("square_${position.x}${position.y}", "id", packageName)
        val squareView = findViewById<View>(squareId)
        if (squareView is ImageView) { // Ensure it's an ImageView before casting
            return squareView
        } else {
            throw IllegalArgumentException("Expected ImageView, found different type at position: $position")
        }
    }
}