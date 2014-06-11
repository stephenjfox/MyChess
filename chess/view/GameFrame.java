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

        this.setPreferredSize(new Dimension(1000, 800));
        this.setLayout(new GridLayout(8, 8));

        initializeSelf();

        this.setTitle("VirtuaChess");
        this.setBackground(Color.black);
        this.setVisible(true);
        this.pack();

    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }

    private void drawModelSquare(BoardLocation boardLocation, Color c) {

        ImagePanel panel = new ImagePanel(boardLocation.getPresentPiece());
        panel.setVisible(true);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(c);

        this.add(panel);

    }

    private void initializeSelf() {

        // TODO: get all the ImagePanels are draw them in the grid layout

        // We print backwards in this town
        for (int i = functBoard.length - 1; i >= 0; i--) {
            BoardLocation[] row = functBoard[i];

            for (int i1 = 0; i1 < row.length; i1++) {
                BoardLocation square = row[i1];

                if(square == null) square = new BoardLocation(i1 + 1, i);

                drawModelSquare(square, (i * i1 % 2 == 0) ? Color.black : Color.white);
            }

        }

    }
}
