package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;
import chess.model.board.NullBoardLocation;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    public ArrayList<BoardLocation> projectValidMoves(BoardLocation pieceLocation, int range) {
        // TODO: Simulate movement of <code> piece </code> range squares in every valid direction

        ArrayList<BoardLocation> testMoveLocations = new ArrayList<>();

        for(int i = 1; i == range; i++ )
        {
            testMoveLocations.add( pieceLocation.add(i, i)  );
            testMoveLocations.add( pieceLocation.add(0, i)  );
            testMoveLocations.add( pieceLocation.add(-i, i) );
            testMoveLocations.add( pieceLocation.add(-i, 0) );
            testMoveLocations.add( pieceLocation.add(-i, -i));
            testMoveLocations.add( pieceLocation.add(0, -i) );
            testMoveLocations.add( pieceLocation.add(i, -i) );
            testMoveLocations.add( pieceLocation.add(i, 0)  );
        }

        // Remove squares off the board
        testMoveLocations.removeIf(problem -> problem instanceof NullBoardLocation);

        // Filter the list down to valid boardLocations
        testMoveLocations.stream()
                .filter(boardLocation -> boardLocation.getY() <= 8)
                .collect(Collectors.toList());




        testMoveLocations.removeIf(problemLocation ->
                (testBoard.getFunctionalBoard()[problemLocation.getY() - 1][problemLocation.getX() - 1] != null &&
                        testBoard.getFunctionalBoard()[problemLocation.getY() - 1][problemLocation.getX() - 1].getPresentPiece() != null)
                && testBoard.getFunctionalBoard()[problemLocation.getY() - 1][problemLocation.getX() - 1]
                        .getPresentPiece().colorMatches(pieceLocation.getPresentPiece()) );

//        testMoveLocations.forEach(System.out::println);

        ArrayList<BoardLocation> possibleMoveLocations = new ArrayList<>();

        // Add only the valid moves
        testMoveLocations.forEach(
                location -> {
                    if (ChessHelp.testMoveForCheck(pieceLocation, location)) {
                        possibleMoveLocations.add(location);
                    }
                }
        );


        return possibleMoveLocations;
    }


}
