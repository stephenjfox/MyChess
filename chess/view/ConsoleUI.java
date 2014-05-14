package chess.view;

import chess.model.board.ChessBoard;

/**
 * Created by Stephen on 5/9/2014.
 */
public class ConsoleUI implements UserInterface {

    private ChessBoard viewFocusBoard;

    public ConsoleUI(ChessBoard boardForTheGame) {
        viewFocusBoard = boardForTheGame;
    }

    @Override
    public void drawBoard() {
        viewFocusBoard.printBoard();
    }

    @Override
    public void informTheUser(String input) {
        System.out.println(input);
    }
}