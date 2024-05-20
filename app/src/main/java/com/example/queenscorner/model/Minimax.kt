package com.example.queenscorner.model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Move(val from: Position, val to: Position)
class Minimax {
    private fun evaluateBoard(board: Array<Array<Piece?>>): Int {
        val pieceValues = mapOf(
            PawnHor::class to 1, PawnVer::class to 1,
            Bishop::class to 3, Knight::class to 3,
            Rook::class to 5, Queen::class to 9,
            King::class to 0 // King is invaluable
        )

        var score = 0

        for (row in board) {
            for (piece in row) {
                if (piece != null) {
                    val value = pieceValues[piece::class] ?: 0
                    if (piece.owner == 0) { // Assuming 0 is AI player
                        score += value
                    } else {
                        score -= value
                    }
                }
            }
        }

        return score
    }

    private suspend fun minimax(board: Array<Array<Piece?>>, depth: Int, isMaximizingPlayer: Boolean, game: QueensCorner): Int {
        return withContext(Dispatchers.Default) {
            if (depth == 0 || game.winCheck()) {
                return@withContext evaluateBoard(board)
            }

            if (isMaximizingPlayer) {
                var maxEval = Int.MIN_VALUE
                val possibleMoves = getAllPossibleMoves(board, 0) // Assuming 0 is AI player
                for (move in possibleMoves) {
                    val newBoard = makeMove(board, move)
                    val eval = minimax(newBoard, depth - 1, false, game)
                    maxEval = maxOf(maxEval, eval)
                }
                return@withContext maxEval
            } else {
                var minEval = Int.MAX_VALUE
                val possibleMoves = getAllPossibleMoves(board, 1) // Assuming 1 is the opponent
                for (move in possibleMoves) {
                    val newBoard = makeMove(board, move)
                    val eval = minimax(newBoard, depth - 1, true, game)
                    minEval = minOf(minEval, eval)
                }
                return@withContext minEval
            }
        }
    }

    private fun makeMove(board: Array<Array<Piece?>>, move: Move): Array<Array<Piece?>> {
        val newBoard = board.map { it.copyOf() }.toTypedArray()
        val piece = newBoard[move.from.x][move.from.y]
        newBoard[move.to.x][move.to.y] = piece
        newBoard[move.from.x][move.from.y] = null
        return newBoard
    }


    private fun getAllPossibleMoves(board: Array<Array<Piece?>>, player: Int): List<Move> {
        val moves = mutableListOf<Move>()
        for (x in 0 until 8) {
            for (y in 0 until 8) {
                val piece = board[x][y]
                if (piece != null && piece.owner == player) {
                    val validMoves = piece.getValidMoves(board)
                    for (targetPosition in validMoves) {
                        moves.add(Move(Position(x, y), targetPosition))
                    }
                }
            }
        }
        return moves
    }


    suspend fun getBestMove(board: Array<Array<Piece?>>, game: QueensCorner): Move {
        return withContext(Dispatchers.Default) {
            var bestMove: Move? = null
            var bestValue = Int.MIN_VALUE

            val possibleMoves = getAllPossibleMoves(board, 0)
            for (move in possibleMoves) {
                val newBoard = makeMove(board, move)
                val boardValue = minimax(newBoard, depth = 3, isMaximizingPlayer = false, game)
                if (boardValue > bestValue) {
                    bestValue = boardValue
                    bestMove = move
                }
            }

            bestMove!!
        }
    }

}