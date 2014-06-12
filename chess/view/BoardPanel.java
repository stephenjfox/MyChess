package chess.view;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Graphics graphics; // Assigned when the BoardPanel is drawn
    private int rows = 8, cols = rows;
    final int SQUARE_SIZE = 80;
    private ChessBoard gameBoard;
    private int last_mouse_x, last_mouse_y;


    public BoardPanel(ChessBoard chessBoard) {
        this.gameBoard = chessBoard;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;

        for (int i = 0; i < rows; i++) {

            for (int k = 0; k < cols; k++) {

                if(k % 2 == 0 ^ i % 2 == 1) g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect(k * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO: check the position, confirm its the piece, leave the highlight trail
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        BoardLocation[][] functionalBoard = gameBoard.getFunctionalBoard();

        // TODO: contains the idea of a square, show highlight and the repaints
        Point theMouseCurrent = e.getPoint();

        this.last_mouse_x = (int) theMouseCurrent.getX();
        this.last_mouse_y = (int) theMouseCurrent.getY();

        // check the x,y against the chessboard
        BoardLocation focusSquare = functionalBoard[(last_mouse_y - 39) / 80][(last_mouse_x/* - 39*/) / 80];
        System.out.println(focusSquare);

        highlightValidMoves(focusSquare);
    }

    private void highlightValidMoves(BoardLocation square) {

        // TODO: get the list of moves. highlight the squares

    }


    // Not concerned
    @Override
    public void mouseEntered(MouseEvent e) {
    }


    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
