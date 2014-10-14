package chess.view;

import chess.controller.MoveProjector;
import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;
import chess.model.pieces.ChessPiece;

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
    private BoardLocation focusSquare, source, destination;
    private ArrayList<BoardLocation> validMoves;
    private BoardLocation[][] functionalBoard;
    private int last_mouse_x = 0, last_mouse_y = 0;
    private boolean highlighted, sourceSet, destSet;
    private boolean frozen;


    public BoardPanel(ChessBoard chessBoard) {
        this.gameBoard = chessBoard;

        this.functionalBoard = gameBoard.getFunctionalBoard();

        this.mp = new MoveProjector(chessBoard);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;

        // Checkerboard
        for (int rowIndex = rows - 1; rowIndex >= 0; rowIndex--) {

            for (int colIndex = 0; colIndex < cols; colIndex++) {
                int currentX = colIndex * SQUARE_SIZE;
                int currentY = rowIndex/*(rows - 1 - rowIndex)*/ * SQUARE_SIZE;

                g.setColor(colIndex % 2 == 0 ^ rowIndex % 2 == 1 ? Color.gray : Color.white);

                g.fillRect(currentX, currentY, SQUARE_SIZE, SQUARE_SIZE);

                ChessPiece piece = functionalBoard[rowIndex][colIndex].getPresentPiece();
                if (piece != null) {
                    g.drawImage(new ImageFactory(piece).getImage(), currentX, currentY, SQUARE_SIZE, SQUARE_SIZE, null);
                }

            }

        }

        // This body was cluttered, so the hacking was moved elsewhere
        doTheHighlights(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Point mouseCurrent = e.getPoint();
        setMouseIndices(mouseCurrent);
        int curRow = ((last_mouse_x) / SQUARE_SIZE);
        int curCol = ((last_mouse_y - 39) / SQUARE_SIZE);//(7 - ((last_mouse_y - 39) / SQUARE_SIZE));
        // check the x,y against the chessboard

        focusSquare =
                functionalBoard[((last_mouse_y - 39) / SQUARE_SIZE)][((last_mouse_x) / SQUARE_SIZE)];

//        System.out.println("Mouse clicked on: " + focusSquare + " x: " + last_mouse_x + " y: " + last_mouse_y);

        if(focusSquare.getPresentPiece() != null) {
            // TODO: fill me in
        }

        if(!sourceSet) {
            source = focusSquare.add(0, 0); // Clone the square
            System.out.println("Source set " + source.getName());

            sourceSet = true; frozen = true;
        }
        else {
            if(!destSet) {
                destination = focusSquare.add(0, 0); // Clone the clicked square
                System.out.println("Destination set " + destination.getName());

                destSet = true; frozen = false;
            }
        }

        if (sourceSet && destSet) {

            String sourceName = source.getName(), destName = destination.getName();

            // Move the piece and execute
            gameBoard.movePiece(sourceName, destName);
            // The source and destination are no longer set
            sourceSet = false; destSet = false;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println("x: " + e.getX() + ", y: " + e.getY());

        try {

            if (!frozen) {

                Point theMouseCurrent = e.getPoint();

                setMouseIndices(theMouseCurrent);

                int curRow = ((last_mouse_x) / SQUARE_SIZE);
                int curCol = ((last_mouse_y - 39) / SQUARE_SIZE);//(7 - ((last_mouse_y - 39) / SQUARE_SIZE));
//                int curCol = (7 - ((last_mouse_y - 39) / SQUARE_SIZE));
//                System.out.println("curCol: " + curCol + ", colRow: " + curRow);
                // check the x,y against the chessboard
                focusSquare = functionalBoard[curCol][curRow];

//                System.out.println(focusSquare + " x: " + last_mouse_x + " y: " + last_mouse_y);

                if (focusSquare != null) {
                    highlightValidMoves(focusSquare);
                } else {
                    highlighted = false;
                    repaint();
                }
            }
        }
        catch (NullPointerException | IndexOutOfBoundsException np1) {
//            System.err.println(np1.getLocalizedMessage());
            // Because this is thrown so often, I'd like to just see the dialogues that I care about
        }
    }

    private void highlightValidMoves(BoardLocation square) {
//        System.out.println(square);
        if (square == null) return; // don't bother

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
        if (highlighted && !frozen) {
            // The Highlights
            g.setColor(Color.green);
            for (BoardLocation validMove : validMoves) {

//                System.out.println("Attempting to fillRect");
                graphics.fillRect((validMove.getX() - 1) * SQUARE_SIZE, (validMove.getY() - 1) * SQUARE_SIZE,
                        SQUARE_SIZE, SQUARE_SIZE);
            }
//            g.setColor(Color.orange);
//            g.fillRect((focusSquare.getX() - 1) * SQUARE_SIZE, (focusSquare.getY() - 1) * SQUARE_SIZE,
//                    SQUARE_SIZE, SQUARE_SIZE);
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
