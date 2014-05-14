package chess.controller;

import chess.model.board.ChessBoard;
import chess.view.ConsoleUI;
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
            // [0] = Piece type: 'k', 'q', 'b', 'n', 'r', 'p'
            // [1] = Piece color: 'l' or 'd'
            // [2]+[3] = Board location [letter][number]
            // or
            // [0] = board index for a piece
            // [1] = board index for the movement destination
            if (partsForPieceGen.length == 2) {

            } else if (partsForPieceGen.length == 3){
                System.out.println(partsForPieceGen[0] + " " + partsForPieceGen[1] + " " + partsForPieceGen[2]);
                containerForTheGame.placePiece(
                        ChessHelp.getNewPiece(
                                partsForPieceGen[0], partsForPieceGen[1].charAt(0)),
                        partsForPieceGen[2]
                );
            }
        }
    }

}
