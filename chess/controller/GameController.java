package chess.controller;

import chess.model.board.ChessBoard;
import chess.view.ConsoleUI;
import chess.viewmodel.ErrorLogger;
import chess.viewmodel.FileInputHandler;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Stephen on 5/13/2014.
 */
public class GameController {

    private static ChessBoard containerForTheGame = new ChessBoard();

    public static void start(String[] starterArgs){
        FileInputHandler fIH = new FileInputHandler();
        ConsoleUI chessView = new ConsoleUI(containerForTheGame);
        runFileGame(starterArgs[0], fIH);
        chessView.drawBoard();
    }

    private static void runFileGame(String starterArg, FileInputHandler fIH) {
        ArrayList<String[]> executionInstructions =
                fIH.executeFromFile(new File(starterArg));

        for (String[] partsForPieceGen : executionInstructions) {
            /* Place piece  */
            // [0] = Piece type: "King", "Queen", etc.
            // [1] = Piece color: 'l' or 'd'
            // [2] = Board location [letter][number]
            // or
            /* Move a piece */
            // [0] = board index for a piece
            // [1] = board index for the movement destination
            // or
            /* Move two pieces */
            // [0] = board index for a piece1
            // [1] = board index for the movement destination1
            // [2] = board index for a piece2
            // [3] = board index for the movement destination2
            if (partsForPieceGen.length == 2) {
                System.out.println(partsForPieceGen[0] + " " + partsForPieceGen[1]);
                try {
                    containerForTheGame.movePiece(
                            partsForPieceGen[0], partsForPieceGen[1]
                    );
                } catch (NullPointerException e) {
                    ErrorLogger.logError("Something in the movePiece() threw an error.");
                }
            }
            else if (partsForPieceGen.length == 3){
                // Pull the predetermined parts from the array and place piece on the board
//                System.out.println(partsForPieceGen[0] + " " + partsForPieceGen[1] + " " + partsForPieceGen[2]);
                // That's a debug line ^^^^^^^^^^^^^^^^
                containerForTheGame.placePiece(
                        ChessHelp.getNewPiece(
                                partsForPieceGen[0], partsForPieceGen[1].charAt(0)),
                        partsForPieceGen[2]
                );
            }
            else { // length of 4 is the only other possibility
                System.out.println("Move two pieces simultaneously");
            }
        }
    }

}
