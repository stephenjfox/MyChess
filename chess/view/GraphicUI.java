package chess.view;

import chess.model.board.ChessBoard;

/**
 * Created by Stephen on 5/27/2014.
 */
public class GraphicUI implements UserInterface {

    private ChessBoard focusBoard;

    public GraphicUI(ChessBoard board) {
        this.focusBoard = board;
    }

    @Override
    public void drawBoard() {

    }

    @Override
    public void informTheUser(String input) {

    }
}
