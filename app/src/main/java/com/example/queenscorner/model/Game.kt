package com.example.queenscorner.model


data class Player(
    val id: Int,
    var pieces: MutableList<Piece>,
    var hasQueen: Boolean
)

data class Settings(
    val zombie: Boolean
)

class QueensCorner {
    // Create an instance of the Board class, with an 8x8 default size
    private val board = Board(8)

    // List of players
    val players = mutableListOf<Player>()
    private var currentPlayerIndex: Int = 0
    // Game settings
    private val settings: Settings = Settings(zombie = true)

    // Initialize the game with players and board setup
    init {
        setupPlayers()
        populateBoardFromPlayers(board.board, players)
    }


    // Set up the players for the game
    private fun setupPlayers() {
        players.add(Player(0, defaultPieces(0), true))
        players.add(Player(1,defaultPieces(1), true))
        players.add(Player(2,defaultPieces(2), true))
        players.add(Player(3,defaultPieces(3), true))
    }

    // Set up the initial pieces on the board
    // Provide the default list of pieces for each player
    private fun defaultPieces(id: Int): MutableList<Piece> {
        val pieces = mutableListOf<Piece>()

        // Set initial positions and create pieces
        when (id) {
            0 -> {
                pieces.add(Rook(id, Position(1, 1)))
                pieces.add(Knight(id, Position(0, 1)))
                pieces.add(Bishop(id, Position(1, 0)))
                pieces.add(Queen(id, Position(0, 0)))
                pieces.add(King(id, Position(2, 2)))
                pieces.add(Pawn(id, Position(0, 2)))
                pieces.add(Pawn(id, Position(1, 2)))
                pieces.add(Pawn(id, Position(2, 1)))
                pieces.add(Pawn(id, Position(2, 0)))
            }
            1 -> {
                pieces.add(Rook(id, Position(6, 1)))
                pieces.add(Knight(id, Position(7, 1)))
                pieces.add(Bishop(id, Position(6, 0)))
                pieces.add(Queen(id, Position(7, 0)))
                pieces.add(King(id, Position(5, 2)))
                pieces.add(Pawn(id, Position(5, 1)))
                pieces.add(Pawn(id, Position(5, 0)))
                pieces.add(Pawn(id, Position(6, 2)))
                pieces.add(Pawn(id, Position(7, 2)))
            }
            2 -> {
                pieces.add(Rook(id, Position(1, 6)))
                pieces.add(Knight(id, Position(0, 6)))
                pieces.add(Bishop(id, Position(1, 7)))
                pieces.add(Queen(id, Position(0, 7)))
                pieces.add(King(id, Position(2, 5)))
                pieces.add(Pawn(id, Position(0, 5)))
                pieces.add(Pawn(id, Position(1, 5)))
                pieces.add(Pawn(id, Position(2, 7)))
                pieces.add(Pawn(id, Position(2, 6)))
            }
            3 -> {
                pieces.add(Rook(id, Position(6, 6)))
                pieces.add(Knight(id, Position(7, 6)))
                pieces.add(Bishop(id, Position(6, 7)))
                pieces.add(Queen(id, Position(7, 7)))
                pieces.add(King(id, Position(5, 5)))
                pieces.add(Pawn(id, Position(7, 5)))
                pieces.add(Pawn(id, Position(6, 5)))
                pieces.add(Pawn(id, Position(5, 6)))
                pieces.add(Pawn(id, Position(5, 7)))
            }
        }
        return pieces
    }
    private fun populateBoardFromPlayers(board: Array<Array<Piece?>>, players: List<Player>) {
        // Clear the board by setting all positions to null
        for (x in board.indices) {
            for (y in 0 until board[x].size) {
                board[x][y] = null // Clear the board
            }
        }

        // Now iterate through each player and place their pieces on the board
        for (player in players) {
            for (piece in player.pieces) {
                val position = piece.position // Get the piece's position

                // Ensure the position is within bounds
                if (position.x in board.indices && position.y in 0 until board[0].size) {
                    board[position.x][position.y] = piece // Place the piece on the board
                } else {
                    throw IllegalArgumentException("Piece position out of bounds: $position")
                }
            }
        }
    }



    // Get the current player for the current turn
    fun getCurrentPlayer(): Player {
        return players[currentPlayerIndex]
    }
    fun getCurrentPlayerIndex(): Int {
        return currentPlayerIndex
    }

    // Move to the next player's turn
    fun nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }

    // Move a piece from one position to another
    fun movePiece(from: Position, to: Position): Boolean {
        val piece = board.getPiece(to)
        // Use the board's movePiece method
        val moved = board.movePiece(from, to)
        // If piece is moved to already occupied position, remove it from the corresponding player's pieces
        if (moved.first && moved.second){
            if (piece != null) {
                removePiece(piece, piece.owner)
                if(!queenCheck(piece.owner)){
                    if(!settings.zombie){
                        removeAllPieces(piece.owner)
                    }
                }
            }

        }
        // If the move was successful, returns true
        return moved.first
    }

    // Function to check if a player with a given ID has a queen piece
    private fun queenCheck(playerId: Int): Boolean {
        // Ensure the player ID is within valid bounds
        if (playerId < 0 || playerId >= players.size) {
            return false // Invalid player ID
        }

        // Get the player by ID
        val player = players[playerId]

        // Check if any piece in the player's list is a Queen
        return player.pieces.any { it is Queen }
    }

    private fun removePiece(piece: Piece, playerId: Int) {
        // Ensure the player ID is within valid bounds
        if (playerId < 0 || playerId >= players.size) {
            throw IllegalArgumentException("Invalid player ID")
        }

        // Get the player's list of pieces
        val player = players[playerId]

        // Attempt to remove the specified piece
        val removed = player.pieces.remove(piece)

        if (!removed) {
            throw IllegalArgumentException("Piece not found in player's list")
        }
    }

    private fun removeAllPieces(playerId: Int){
        // Ensure the player ID is within valid bounds
        if (playerId < 0 || playerId >= players.size) {
            throw IllegalArgumentException("Invalid player ID")
        }

        val player = players[playerId]
        for(piece in player.pieces){
            val removed = player.pieces.remove(piece)
            if (!removed) {
                throw IllegalArgumentException("Piece not found in player's list")
            }
        }

    }

    fun pieceCheck(playerId: Int, position: Position): Boolean{
        if (playerId < 0 || playerId >= players.size) {
            throw IllegalArgumentException("Invalid player ID")
        }
        // if there exists a piece at position with player ID.
        val player = players[playerId]

        // Check if any piece in the player's list is at the specified position
        return player.pieces.any { it.position == position }
    }

}
