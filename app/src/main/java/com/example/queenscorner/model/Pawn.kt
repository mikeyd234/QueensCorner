package model

import com.example.queenscorner.model.Piece
import com.example.queenscorner.model.PieceType
import com.example.queenscorner.model.Position

class Pawn(owner: Int, position: Position) : Piece(owner, position, PieceType.PAWN) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()

        val direction = if (owner == 0 || owner == 2) 1 else -1
        val forward = Position(position.x, position.y + direction)

        // Move forward
        if (isWithinBoard(forward) && board[forward.y][forward.x] == null) {
            moves.add(forward)
            // Double move if first move
            if ((owner == 0 || owner == 2) && position.y == 1 || (owner == 1 || owner == 3) && position.y == 6) {
                val doubleMove = Position(position.x, position.y + 2 * direction)
                if (board[doubleMove.y][doubleMove.x] == null) {
                    moves.add(doubleMove)
                }
            }
        }

        // Diagonal attacks
        val diagLeft = Position(position.x - 1, position.y + direction)
        val diagRight = Position(position.x + 1, position.y + direction)

        if (isWithinBoard(diagLeft) && board[diagLeft.y][diagLeft.x] != null && board[diagLeft.y][diagLeft.x]!!.owner != owner) {
            moves.add(diagLeft)
        }

        if (isWithinBoard(diagRight) && board[diagRight.y][diagRight.x] != null && board[diagRight.y][diagRight.x]!!.owner != owner) {
            moves.add(diagRight)
        }

        return moves
    }
}