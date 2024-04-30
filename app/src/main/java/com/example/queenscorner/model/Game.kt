package com.example.queenscorner.model


data class Player(
    val id: Int,
    var pieces: MutableList<Piece>
)

class QueensCorner {
    // Create an instance of the Board class, with an 8x8 default size
    private val board = Board(7)

    // List of players
    private val players = mutableListOf<Player>()
    private var currentPlayerIndex: Int = 0

    private var defaultPieces: MutableList<Piece> =

    // Initialize the game with players and board setup
    init {
        setupPlayers()
        setupInitialBoard()
    }

    // Set up the players for the game
    private fun setupPlayers() {
        players.add(Player(0, ))
        players.add(Player(1,))
        players.add(Player(2,))
        players.add(Player(3,))
    }

    // Set up the initial pieces on the board
    // Provide the default list of pieces for each player
    private fun defaultPieces(id: Int): MutableList<Piece> {
        val pieces = mutableListOf<Piece>()

        // Example: Set initial positions and create pieces
        when (id) {
            0 -> {
                pieces.add(Rook(id, Position(0, 0)))
                pieces.add(Knight(id, Position(1, 0)))
                pieces.add(Bishop(id, Position(2, 0)))
                pieces.add(Queen(id, Position(3, 0)))
                pieces.add(King(id, Position(4, 0)))
                pieces.add(Pawn(id, Position(0, 1))) // Pawns' positions can vary
                pieces.add(Pawn(id, Position(1, 1)))
                pieces.add(Pawn(id, Position(2, 1)))
                pieces.add(Pawn(id, Position(3, 1)))
            }
            1 -> {
                // Initialize pieces for other colors similarly
            }
            2 -> {
                // Initialize pieces for other colors similarly
            }
            3 -> {
                // Initialize pieces for other colors similarly
            }
        }

        private fun setupInitialBoard() {
        // Define starting positions for the initial pieces for each player

    }

    // Get the current player for the current turn
    fun getCurrentPlayer(): Player {
        return players[currentPlayerIndex]
    }

    // Move to the next player's turn
    fun nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }

    // Move a piece from one position to another
    fun movePiece(from: Position, to: Position): Boolean {
        // Use the board's movePiece method
        val moved = board.movePiece(from, to)

        if (moved) {
            nextTurn() // If the move was successful, proceed to the next turn
            return true
        }

        return false
    }
}
