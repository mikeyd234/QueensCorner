package com.example.queenscorner.model

import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.PieceType
import com.example.queenscorner.model.Position

class Rook(owner: Int, position: Position) : Piece(owner, position, PieceType.ROOK) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()

        // Directions for straight-line movement
        val directions = listOf(
            Position(0, 1),
            Position(0, -1),
            Position(1, 0),
            Position(-1, 0)
        )

        // Add valid moves in each direction
        for (direction in directions) {
            var pos = Position(position.x + direction.x, position.y + direction.y)
            while (isWithinBoard(pos)) {
                if (board[pos.y][pos.x] == null) {
                    moves.add(pos)
                } else {
                    if (board[pos.y][pos.x]!!.owner != owner) {
                        moves.add(pos)
                    }
                    break
                }
                pos = Position(pos.x + direction.x, pos.y + direction.y)
            }
        }

        return moves
    }
}
