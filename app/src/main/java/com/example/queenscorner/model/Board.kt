package com.example.queenscorner.model

// Represents the chessboard
class Board(private val size: Int = 7) {
    // 2D array representing the board with optional pieces
    private val board: Array<Array<Piece?>> = Array(size) { Array(size) { null } }

    // Check if a given position is within the board's bounds
    private fun isWithinBounds(position: Position): Boolean {
        return position.x in 0 until size && position.y in 0 until size
    }

    // Get the piece at a given position
    private fun getPiece(position: Position): Piece? {
        if (isWithinBounds(position)) {
            return board[position.y][position.x]
        }
        return null
    }

    // Place a piece at a given position
    fun placePiece(piece: Piece, position: Position) {
        if (isWithinBounds(position)) {
            board[position.y][position.x] = piece
            piece.position = position // Update the piece's position
        }
    }

    // Move a piece from one position to another
    fun movePiece(from: Position, to: Position): Boolean {
        if (isWithinBounds(from) && isWithinBounds(to)) {
            val piece = getPiece(from)
            if (piece != null) {
                board[to.y][to.x] = piece
                board[from.y][from.x] = null
                piece.position = to
                return true
            }
        }
        return false
    }
}
