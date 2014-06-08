package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;
import chess.model.board.NullBoardLocation;
import chess.model.board.NumberCruncher;
import chess.model.pieces.ChessPiece;

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


    /**
     * Makes changes to fake board and determines check status
     * @param kingLocation square with the king to be captured against
     * @param enemyLocations every enemy square, to check if they can hit the king
     * @param allyLocations The King's team to attempt all moves
     * @return where the king is still in check afterward
     */
    public boolean projectCheckScenario(BoardLocation kingLocation, ArrayList<BoardLocation> enemyLocations,
                                        ArrayList<BoardLocation> allyLocations) {

        boolean whichKing = kingLocation.getPresentPiece().isWhite();
        boolean kingStillInCheck = false;

        BoardLocation[][] boardArray = testBoard.getFunctionalBoard(); // retrieve a copy of the board-array

        // Get the King's valid moves
        ArrayList<BoardLocation> kingValidMoves = projectValidMoves(kingLocation, 1);

        ChessBoard focusChessBoard = new ChessBoard(boardArray);

        // For every possible king future Location
        for (BoardLocation kingValidMove : kingValidMoves) {

            // If path is clear to the new state
            if (ChessHelp.pathIsClear(kingLocation, kingValidMove)) {

                // move to new state
                focusChessBoard.movePieceWithoutTurnCheck(kingLocation.getName(), kingValidMove.getName());

                // Check if "the King can still be hit"
                kingStillInCheck = whichKing ?
                        focusChessBoard.getCheckFinder().whiteIsInCheck() :
                        focusChessBoard.getCheckFinder().blackIsInCheck();

                // undo back to the old state
                focusChessBoard.movePieceWithoutTurnCheck(kingValidMove.getName(), kingLocation.getName());
            }

        }

        // TODO: This conditional needs to run
        // If it looks like Checkmate, check against every move the allies can make
        if(kingStillInCheck) {

            // Every piece against every move validation. See if the king is check after

            // For-Each allied location
            for (BoardLocation allyLocation : allyLocations) {

                ChessPiece currentAlly = allyLocation.getPresentPiece();
                // For-each allied move
                for (BoardLocation potentialAlliedMove : projectValidMoves(allyLocation, NumberCruncher.pieceMaxRange(currentAlly))) {

                    // That is valid
                    if(ChessHelp.pathIsClear(allyLocation, potentialAlliedMove, focusChessBoard)) {

                        // Move the piece
                        focusChessBoard.movePieceWithoutTurnCheck(allyLocation.getName(), potentialAlliedMove.getName());

                        // Check for Check-condition
                        for (BoardLocation enemyLocation : enemyLocations) {


                                kingStillInCheck = (ChessHelp.pathIsClear(enemyLocation, kingLocation, focusChessBoard));

                        }

                        // Undo the move, rinse, repeat
                        focusChessBoard.movePieceWithoutTurnCheck(potentialAlliedMove.getName(), allyLocation.getName());

                    }

                }
            }

        }

        return kingStillInCheck;
    }

}
