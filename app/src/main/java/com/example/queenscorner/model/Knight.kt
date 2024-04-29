package model

class Knight(owner: Int, position: Position) : Piece(owner, position, PieceType.KNIGHT) {
    override fun getValidMoves(board: Array<Array<Piece?>>): List<Position> {
        val moves = mutableListOf<Position>()
        val potentialMoves = listOf(
            Position(position.x + 2, position.y + 1),
            Position(position.x + 2, position.y - 1),
            Position(position.x - 2, position.y + 1),
            Position(position.x - 2, position.y - 1),
            Position(position.x + 1, position.y + 2),
            Position(position.x + 1, position.y - 2),
            Position(position.x - 1, position.y + 2),
            Position(position.x - 1, position.y - 2),
        )

        for (pos in potentialMoves) {
            if (isWithinBoard(pos) && (board[pos.y][pos.x] == null || board[pos.y][pos.x]!!.owner != owner)) {
                moves.add(pos)
            }
        }

        return moves
    }
}
