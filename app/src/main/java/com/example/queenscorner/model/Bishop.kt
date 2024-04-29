package model

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
