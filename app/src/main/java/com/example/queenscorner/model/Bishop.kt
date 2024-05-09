package com.example.queenscorner.model

import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.PieceType
import com.example.queenscorner.model.Position

class Bishop(owner: Int, position: Position) : Piece(owner, position, PieceType.BISHOP) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()

        // Directions for diagonal movement
        val directions = listOf(
            Position(1, 1),
            Position(-1, 1),
            Position(1, -1),
            Position(-1, -1)
        )

        // Add valid moves in each direction
        for (direction in directions) {
            var i = 1
            while(i < 8){
                val pos = Position(position.x + (i*direction.x), position.y + (i*direction.y))
                if (isWithinBoard(pos)) {
                    if (board[pos.y][pos.x] == null) {
                        moves.add(pos)
                    } else {
                        if (board[pos.y][pos.x]!!.owner != owner) {
                            moves.add(pos)
                        }
                        break
                    }
                }
                i++
            }
        }

        return moves
    }
}
