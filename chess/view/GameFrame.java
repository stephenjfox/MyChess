package chess.view;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class GameFrame extends JFrame {

    // TODO: Return the frame with the pieces on it

    ChessBoard modelBoard;
    BoardLocation[][] functBoard;

    public GameFrame(ChessBoard chessBoard) {

        this.modelBoard = chessBoard;
        this.functBoard = modelBoard.getFunctionalBoard();

        this.setTitle("VirtuaChess");
        this.setBackground(Color.black);

    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }


    private final void drawModelSquare(BoardLocation boardLocation) {

        // TODO: retrieve image panel

    }
}
