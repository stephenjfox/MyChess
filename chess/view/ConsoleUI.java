package chess.view;

import chess.model.board.ChessBoard;
import chess.model.board.TextChessBoard;

/**
 * Created by Stephen on 5/9/2014.
 */
public class ConsoleUI implements UserInterface {

    @Override
    public void drawBoard() {
    	new ChessBoard().printSelf();
    }

    @Override
    public void informTheUser(String input) {
        System.out.println(input);
    }
}