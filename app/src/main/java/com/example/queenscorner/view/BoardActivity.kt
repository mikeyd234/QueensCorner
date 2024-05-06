package com.example.queenscorner.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.queenscorner.R
import com.example.queenscorner.model.Bishop
import com.example.queenscorner.model.King
import com.example.queenscorner.model.Knight
import com.example.queenscorner.model.Pawn
import com.example.queenscorner.model.Position
import com.example.queenscorner.model.QueensCorner
import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.Queen
import com.example.queenscorner.model.Rook
import java.lang.IllegalArgumentException

class BoardActivity : ComponentActivity() {
    private var selectedFrom: Position? = null
    private var game: QueensCorner = QueensCorner()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        setupBoard()
    }

    private fun setupBoard() {
        // Place pieces on the board
        for(player in game.players){
            for(piece in player.pieces){
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

                button.setOnClickListener {
                    handleSquareClick(Position(x, y))
                }
            }
        }

    }

    private fun placePiece(piece: Piece){
        val pos = piece.position
        val squareId = resources.getIdentifier("square_${pos.x}${pos.y}", "id",  packageName)
        if (squareId == 0) {
            throw IllegalArgumentException("Square ID not found for x$pos.x, y$pos.y")
        }
        val squareView= findViewById<ImageView>(squareId)

        squareView.setImageResource(getPieceDrawable(piece))
    }

    private fun getPieceDrawable(piece: Piece): Int{
        return when(piece.owner){
            0 -> when(piece){
                is Pawn -> R.drawable.whitepawn
                is Bishop -> R.drawable.whitebishop
                is King -> R.drawable.whiteking
                is Knight -> R.drawable.whiteknight
                is Rook -> R.drawable.whiterook
                is Queen -> R.drawable.whitequeen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }
            1 -> when(piece){
                is Pawn -> R.drawable.bluepawn
                is Bishop -> R.drawable.bluebishop
                is King -> R.drawable.blueking
                is Knight -> R.drawable.blueknight
                is Rook -> R.drawable.bluerook
                is Queen -> R.drawable.bluequeen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }
            2 -> when(piece){
                is Pawn -> R.drawable.redpawn
                is Bishop -> R.drawable.redbishop
                is King -> R.drawable.redking
                is Knight -> R.drawable.redknight
                is Rook -> R.drawable.redrook
                is Queen -> R.drawable.redqueen
                else -> throw IllegalArgumentException("Unknown Piece type")
            }
            3 -> when(piece){
                is Pawn -> R.drawable.blackpawn
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
        if (selectedFrom == null) { // No square is selected yet
            selectedFrom = position // Set the "from" square
            // Optional: Provide visual feedback for selection (e.g., changing color)
        } else {
            // "From" square is already selected, this is the "to" square
            val from = selectedFrom!!

            val moveSuccessful = game.movePiece(from, position) // Call your move function

            if (moveSuccessful) {
                selectedFrom = null // Clear the selection
                updateBoard(from, position) // Refresh the board UI to reflect the move
            } else {
                // Move failed, reset the selection and possibly give feedback
                selectedFrom = null
                // Optional: show a message about the failed move
            }
        }


    }
    private fun updateBoard(from: Position, to: Position){
        // Get image views
        val fromView = getSquareView(from)
        val toView = getSquareView(to)
        // Ensure valid ImageViews
        if(fromView != null && toView != null){
            animatePieceMovement(fromView, toView)

            //After the animation, update the resource of "to" with the new piece image
            val piece = game.getCurrentPlayer().pieces.first {it.position == to}
            toView.setImageResource(getPieceDrawable(piece))

            // Clear the "from" square after the move
            fromView.setImageResource(android.R.color.transparent)
        }
    }
    private fun getSquareView(position: Position): ImageView?{
        val squareId = resources.getIdentifier("x${position.x}y${position.y}", "id", packageName)
        return findViewById(squareId)
    }

    private fun animatePieceMovement(fromView: ImageView, toView: ImageView){
        val targetX = toView.x
        val targetY = toView.y

        // Animate piece movement with ObjectAnimator
        val animatorX = ObjectAnimator.ofFloat(fromView, "x", targetX)
        val animatorY = ObjectAnimator.ofFloat(fromView, "y", targetY)

        // Animation duration
        animatorX.duration = 500
        animatorY.duration = 500

        // Start both animations to move in a straight line
        animatorX.start()
        animatorY.start()
    }
}