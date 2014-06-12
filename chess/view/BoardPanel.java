package chess.view;

import chess.controller.MoveProjector;
import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Graphics graphics; // Assigned when the BoardPanel is drawn
    private int rows = 8, cols = rows;
    final int SQUARE_SIZE = 80;
    private ChessBoard gameBoard;
    private MoveProjector mp;
    private BoardLocation focusSquare;
    private ArrayList<BoardLocation> validMoves;
    private int last_mouse_x = 0, last_mouse_y = 0;
    private boolean highlighted;


    public BoardPanel(ChessBoard chessBoard) {
        this.gameBoard = chessBoard;
        this.mp = new MoveProjector(chessBoard);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;

        // Checkerboard
        for (int i = 0; i < rows; i++) {

            for (int k = 0; k < cols; k++) {

                g.setColor(k % 2 == 0 ^ i % 2 == 1 ? Color.black : Color.white);

                g.fillRect(k * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }

        }

        doTheHighlights(g);


    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Point mouseCurrent = e.getPoint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        BoardLocation[][] functionalBoard = gameBoard.getFunctionalBoard();

        Point theMouseCurrent = e.getPoint();

        setMouseIndices(theMouseCurrent);

        // check the x,y against the chessboard
        focusSquare =
                functionalBoard[((last_mouse_y - 39) / SQUARE_SIZE)][((last_mouse_x) / SQUARE_SIZE)];

        System.out.println(focusSquare + " x: " + last_mouse_x + " y: " + last_mouse_y);

        if (focusSquare != null) {
            highlightValidMoves(focusSquare);
        }
        else {
            highlighted = false;
            repaint();
        }
    }

    private void highlightValidMoves(BoardLocation square) {

        if (square == null) {
            return; // don't bother
        }

        validMoves = mp.getValidMoves(square);
//        graphics.setColor(Color.green);
//
//        graphics.drawRect(square.getX() * SQUARE_SIZE, square.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
//
//        for (BoardLocation validMove : validMoves) {
//            System.out.println("Attempting to fillRect");
//            graphics.fillRect(validMove.getX() * SQUARE_SIZE, validMove.getY() * SQUARE_SIZE,
//                    SQUARE_SIZE, SQUARE_SIZE);
//        }

        highlighted = true;
        repaint();

    }

    private void doTheHighlights(Graphics g) {
        if (highlighted) {
            // The Highlights
            graphics.setColor(Color.green);
            for (BoardLocation validMove : validMoves) {
                System.out.println("Attempting to fillRect");
                graphics.fillRect((validMove.getX() - 1) * SQUARE_SIZE, (validMove.getY() - 1) * SQUARE_SIZE,
                        SQUARE_SIZE, SQUARE_SIZE);
            }
            graphics.setColor(Color.orange);
            graphics.fillRect((focusSquare.getX() - 1) * SQUARE_SIZE, (focusSquare.getY() - 1) * SQUARE_SIZE,
                    SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    private void setMouseIndices(Point theMouseCurrent) {

        this.last_mouse_x = (int) theMouseCurrent.getX();
        this.last_mouse_y = (int) theMouseCurrent.getY();
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
