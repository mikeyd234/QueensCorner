package com.example.queenscorner.model

import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.PieceType
import com.example.queenscorner.model.Position

class Queen(owner: Int, position: Position) : Piece(owner, position, PieceType.QUEEN) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()

        // Queen can move like a rook and a bishop
        // Rook-like moves
        val rookDirections = listOf(
            Position(0, 1),
            Position(0, -1),
            Position(1, 0),
            Position(-1, 0)
        )

        for (direction in rookDirections) {
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

        // Bishop-like moves
        val bishopDirections = listOf(
                Position(1, 1),
        Position(-1, 1),
        Position(1, -1),
        Position(-1, -1)
        )

        for (direction in bishopDirections) {
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
