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

    private void drawModelSquare(BoardLocation boardLocation) {

        // TODO: retrieve image panel
        this.add(new ImagePanel(boardLocation.getPresentPiece()));

    }


    private void initializeSelf() {

        // TODO: get all the ImagePanels are draw them in the grid layout

        // We print backwards in this town
        for (int i = functBoard.length - 1; i >= 0; i--) {
            BoardLocation[] row = functBoard[i];

            for (int i1 = 0; i1 < row.length; i1++) {
                BoardLocation square = row[i1];

                if (square == null) {
                    System.out.println("We hit a null square.");
                }
                else {
                    drawModelSquare(square);
                }
            }

        }

    }
}
