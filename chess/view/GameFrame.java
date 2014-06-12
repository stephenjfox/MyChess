package chess.view;

import chess.model.board.ChessBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class GameFrame extends JFrame {

    // TODO: Return the frame with the pieces on it

    private ChessBoard modelBoard;
    private BoardPanel thePanel;

    public GameFrame(ChessBoard chessBoard) {

        this.modelBoard = chessBoard;
        this.thePanel = new BoardPanel(chessBoard);

        this.setPreferredSize(new Dimension(1000, 800));

//        initializeSelf();
        this.add(thePanel);
        this.addMouseListener(thePanel);
        this.addMouseMotionListener(thePanel);

        this.setContentPane(thePanel);
        this.setTitle("VirtuaChess");
        this.setVisible(true);
        this.pack();

    }

}
