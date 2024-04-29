package com.example.queenscorner.model

// Enum representing the type of piece
enum class PieceType {
    PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
}

// Class representing a position on the chess board
data class Position(val x: Int, val y: Int)

// Abstract base class for all pieces
abstract class Piece(val owner: Int, var position: Position, val pieceType: PieceType) {
    // Abstract method to get valid moves for a piece
    abstract fun getValidMoves(board: Array<Array<Piece?>>): List<Position>

    // Check if a position is within the bounds of the board
    protected fun isWithinBoard(pos: Position): Boolean {
        return pos.x in 0..7 && pos.y in 0..7
    }
}
