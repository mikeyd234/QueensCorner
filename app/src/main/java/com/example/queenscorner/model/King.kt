package model

import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.PieceType
import com.example.queenscorner.model.Position

class King(owner: Int, position: Position) : Piece(owner, position, PieceType.KING) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()

        val potentialMoves = listOf(
                Position(position.x + 1, position.y),
        Position(position.x - 1, position.y),
        Position(position.x, position.y + 1),
        Position(position.x, position.y - 1),
        Position(position.x + 1, position.y + 1),
        Position(position.x - 1, position.y - 1),
        Position(position.x + 1, position.y - 1),
        Position(position.x - 1, position.y + 1)
        )

        for (pos in potentialMoves) {
            if (isWithinBoard(pos) && (board[pos.y][pos.x] == null || board[pos.y][pos.x]!!.owner != owner)) {
                moves.add(pos)
            }
        }

        return moves
    }
}
