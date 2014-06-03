package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;
import chess.model.pieces.ChessPiece;

import java.util.ArrayList;

/**
 * Created by Stephen on 5/31/2014.
 * In project: NewChess
 */
public class MoveProjector {
    ChessBoard testBoard;

    public MoveProjector(ChessBoard testBoard) {
        this.testBoard = testBoard;
    }

    /**
     * Simulate movement of the <code>piece</code> range squares
     * Ex: king on d2 with range 4, will test movement in all 9 direction until
     * the movement fails.
     * @param pieceLocation : The BoardLocation for the piece to be simu-Moved
     * @param range :
     *              Logically, iteration count.
     *              Practically, the distance for the piece to move, ON the
     *              simulatedBoard. To be tested in every direction.
     */
    private void projectMove(BoardLocation pieceLocation, int range) {
        // TODO: Simulate movement of <code> piece </code> range squares in every valid direction

        ChessPiece mover = pieceLocation.getPresentPiece();

        ArrayList<BoardLocation> testMoveLocations = new ArrayList<>();

        testMoveLocations.add( pieceLocation.add(1, 1) );
        testMoveLocations.add( pieceLocation.add(0, 1) );
        testMoveLocations.add( pieceLocation.add(-1, 1) );
        testMoveLocations.add( pieceLocation.add(-1, 0) );
        testMoveLocations.add( pieceLocation.add(-1, -1) );
        testMoveLocations.add( pieceLocation.add(0, -1) );
        testMoveLocations.add( pieceLocation.add(1, -1) );
        testMoveLocations.add( pieceLocation.add(1, 0) );
    }

    // TODO: In the AI, use T-tree to determine the best moves (T-Tree is Ternary Tree)
}