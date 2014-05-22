package chess.controller;

import chess.controller.instruction.Instruction;
import chess.model.board.ChessBoard;
import chess.view.ConsoleUI;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Stephen on 5/13/2014.
 */
public class GameController {

    public static ChessBoard containerForTheGame = new ChessBoard();

    public static void start(String[] starterArgs){
        FileInputHandler fIH = new FileInputHandler();
//        containerForTheGame.init();
        ConsoleUI chessView = new ConsoleUI(containerForTheGame);
        runFileGame(starterArgs[0], fIH);
        chessView.drawBoard();
    }

    private static void runFileGame(String starterArg, FileInputHandler fIH) {
        ArrayList<Instruction> executionInstructions =
                fIH.executeFromFile(new File(starterArg));

        for (Instruction instruction : executionInstructions) {
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
            instruction.execute();
        }
    }


}
