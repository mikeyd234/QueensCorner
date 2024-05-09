package com.example.queenscorner.model

// Represents the chessboard
class Board(private val size: Int) {
    // 2D array representing the board with optional pieces
    val board: Array<Array<Piece?>> = Array(size) { Array(size) { null } }

    // Check if a given position is within the board's bounds
    private fun isWithinBounds(position: Position): Boolean {
        return position.x in 0 until size && position.y in 0 until size
    }

    // Get the piece at a given position
    fun getPiece(position: Position): Piece? {
        if (isWithinBounds(position)) {
            return board[position.y][position.x]
        }
        return null
    }

    // Check if a given move is valid based on the piece's movement restrictions
    private fun isValidMove(piece: Piece?, targetPosition: Position): Boolean {
        return piece != null && isWithinBounds(targetPosition) && piece.getValidMoves(board).contains(targetPosition)
    }

    // Place a piece at a given position
    fun placePiece(piece: Piece, position: Position) {
        if (isWithinBounds(position)) {
            board[position.y][position.x] = piece
            piece.position = position // Update the piece's position
        }
    }

    // Move result function returning move success and piece elimination status
    fun movePiece(from: Position, to: Position): Pair<Boolean, Boolean> {
        val piece = getPiece(from)
        val dest = getPiece(to)

        // Check if within bounds and if the move is valid
        if (isValidMove(piece, to)) {
            if (piece != null) {
                board[to.y][to.x] = piece // Place the piece on the new position
                board[from.y][from.x] = null // Remove the piece from the original position
                piece.position = to // Update the piece's position

                val pieceEliminated = (dest != null) // Check if there's a piece at the destination
                return Pair(true, pieceEliminated) // Return move success and elimination status
            }
        }

        // Return false for unsuccessful move and no piece eliminated
        return Pair(false, false)
    }

}
