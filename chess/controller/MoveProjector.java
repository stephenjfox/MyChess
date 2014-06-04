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
    public ArrayList<BoardLocation> projectMoves(BoardLocation pieceLocation, int range) {
        // TODO: Simulate movement of <code> piece </code> range squares in every valid direction

        ChessPiece mover = pieceLocation.getPresentPiece();

        ArrayList<BoardLocation> testMoveLocations = new ArrayList<>();

        for(int i = 1; i == range; i++ )
        {
            testMoveLocations.add( pieceLocation.add(i, i) );
            testMoveLocations.add( pieceLocation.add(0, i) );
            testMoveLocations.add( pieceLocation.add(-i, i) );
            testMoveLocations.add( pieceLocation.add(-i, 0) );
            testMoveLocations.add( pieceLocation.add(-i, -i) );
            testMoveLocations.add( pieceLocation.add(0, -i) );
            testMoveLocations.add( pieceLocation.add(i, -i) );
            testMoveLocations.add( pieceLocation.add(i, 0) );
        }

        ArrayList<BoardLocation> validMoveLocations = new ArrayList<>();
        testMoveLocations.forEach(
                location -> {
                    if (ChessHelp.putsKingInCheck(pieceLocation, location)) {
                        validMoveLocations.add(location);
                    }
                }
        );

        return validMoveLocations;
    }


}
