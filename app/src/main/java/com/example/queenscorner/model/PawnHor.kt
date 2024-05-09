package com.example.queenscorner.model

class PawnHor(owner: Int, position: Position) : Piece(owner, position, PieceType.PAWN) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()

        val direction = if (owner == 0 || owner == 1) 1 else -1
        val forward = Position(position.x + direction, position.y)

        // Move forward
        if (isWithinBoard(forward) && board[forward.y][forward.x] == null) {
            moves.add(forward)
            // Double move if first move
            if ((owner == 0 || owner == 1) && position.x == 2 || (owner == 2 || owner == 3) && position.x == 5) {
                val doubleMove = Position(position.x + 2 * direction, position.y)
                if (board[doubleMove.y][doubleMove.x] == null) {
                    moves.add(doubleMove)
                }
            }
        }

        // Diagonal attacks
        val diagLeft = Position(position.x + direction, position.y - 1)
        val diagRight = Position(position.x + direction, position.y + 1)

        if (isWithinBoard(diagLeft) && board[diagLeft.y][diagLeft.x] != null && board[diagLeft.y][diagLeft.x]!!.owner != owner) {
            moves.add(diagLeft)
        }

        if (isWithinBoard(diagRight) && board[diagRight.y][diagRight.x] != null && board[diagRight.y][diagRight.x]!!.owner != owner) {
            moves.add(diagRight)
        }

        return moves
    }
}