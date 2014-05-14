package chess;

import chess.controller.GameController;
import chess.view.ConsoleUI;
import chess.viewmodel.FileInputHandler;

import java.io.File;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Starter {

    public static void main(String[] args) {
        if(args.length == 1) {
            GameController.start(args);
        }
    }
}
