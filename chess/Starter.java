package chess;

import chess.controller.GameController;

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
