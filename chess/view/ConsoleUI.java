package chess.view;

import chess.model.board.ChessBoard;

/**
 * Created by Stephen on 5/9/2014.
 */
public class ConsoleUI implements UserInterface {

    @Override
    public void drawBoard() {
    	ChessBoard focusBoard = new ChessBoard();
        focusBoard.init();
        focusBoard.printBoard();
    }

    @Override
    public void informTheUser(String input) {
        System.out.println(input);
    }
}