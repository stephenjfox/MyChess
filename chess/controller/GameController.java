package chess.controller;

import chess.view.ConsoleUI;
import chess.viewmodel.FileInputHandler;

import java.io.File;

/**
 * Created by Stephen on 5/13/2014.
 */
public class GameController {
    public static void start(String[] starterArgs){
        FileInputHandler fIH = new FileInputHandler();
        ConsoleUI chessView = new ConsoleUI();
        fIH.executeFromFile(new File(starterArgs[0]));
        chessView.drawBoard();
    }
}
