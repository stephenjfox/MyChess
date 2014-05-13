package chess;

import chess.view.ConsoleUI;
import chess.viewmodel.FileInputHandler;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Starter {

    public static void main(String[] args) {
        if(args.length == 1) {
            FileInputHandler fIH = new FileInputHandler();
            ConsoleUI chessView = new ConsoleUI();
//            fIH.executeFromFile(new File(args[0]));
            chessView.drawBoard();
        }
    }
}
