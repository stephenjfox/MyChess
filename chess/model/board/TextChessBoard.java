package chess.model.board;

import chess.model.pieces.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Stephen on 5/9/2014.
 */
public class TextChessBoard {
    LinkedHashMap<BoardLocation, ChessPiece> gameBoard;

    private final boolean white = true, black = false;

    {
        gameBoard = new LinkedHashMap<>();

        // Royalty for white
        gameBoard.put(new BoardLocation('A', 1), new Rook(white));
        gameBoard.put(new BoardLocation('B', 1), new Knight(white));
        gameBoard.put(new BoardLocation('C', 1), new Bishop(white));
        gameBoard.put(new BoardLocation('D', 1), new Queen(white));
        gameBoard.put(new BoardLocation('E', 1), new King(white));
        gameBoard.put(new BoardLocation('F', 1), new Bishop(white));
        gameBoard.put(new BoardLocation('G', 1), new Knight(white));
        gameBoard.put(new BoardLocation('H', 1), new Rook(white));

        // Pawn of light
        gameBoard.put(new BoardLocation('A', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('B', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('C', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('D', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('E', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('F', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('G', 2), new Pawn(white));
        gameBoard.put(new BoardLocation('H', 2), new Pawn(white));

        // Empty middle ground
        gameBoard.put(new BoardLocation('A', 3), null);
        gameBoard.put(new BoardLocation('B', 3), null);
        gameBoard.put(new BoardLocation('C', 3), null);
        gameBoard.put(new BoardLocation('D', 3), null);
        gameBoard.put(new BoardLocation('E', 3), null);
        gameBoard.put(new BoardLocation('F', 3), null);
        gameBoard.put(new BoardLocation('G', 3), null);
        gameBoard.put(new BoardLocation('H', 3), null);

        gameBoard.put(new BoardLocation('A', 4), null);
        gameBoard.put(new BoardLocation('B', 4), null);
        gameBoard.put(new BoardLocation('C', 4), null);
        gameBoard.put(new BoardLocation('D', 4), null);
        gameBoard.put(new BoardLocation('E', 4), null);
        gameBoard.put(new BoardLocation('F', 4), null);
        gameBoard.put(new BoardLocation('G', 4), null);
        gameBoard.put(new BoardLocation('H', 4), null);

        gameBoard.put(new BoardLocation('A', 5), null);
        gameBoard.put(new BoardLocation('B', 5), null);
        gameBoard.put(new BoardLocation('C', 5), null);
        gameBoard.put(new BoardLocation('D', 5), null);
        gameBoard.put(new BoardLocation('E', 5), null);
        gameBoard.put(new BoardLocation('F', 5), null);
        gameBoard.put(new BoardLocation('G', 5), null);
        gameBoard.put(new BoardLocation('H', 5), null);

        gameBoard.put(new BoardLocation('A', 6), null);
        gameBoard.put(new BoardLocation('B', 6), null);
        gameBoard.put(new BoardLocation('C', 6), null);
        gameBoard.put(new BoardLocation('D', 6), null);
        gameBoard.put(new BoardLocation('E', 6), null);
        gameBoard.put(new BoardLocation('F', 6), null);
        gameBoard.put(new BoardLocation('G', 6), null);
        gameBoard.put(new BoardLocation('H', 6), null);

        // Pawn of darkness
        gameBoard.put(new BoardLocation('A', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('B', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('C', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('D', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('E', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('F', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('G', 7), new Pawn(black));
        gameBoard.put(new BoardLocation('H', 7), new Pawn(black));

        // Royalty for black
        gameBoard.put(new BoardLocation('A', 8), new Rook(black));
        gameBoard.put(new BoardLocation('B', 8), new Knight(black));
        gameBoard.put(new BoardLocation('C', 8), new Bishop(black));
        gameBoard.put(new BoardLocation('D', 8), new Queen(black));
        gameBoard.put(new BoardLocation('E', 8), new King(black));
        gameBoard.put(new BoardLocation('F', 8), new Bishop(black));
        gameBoard.put(new BoardLocation('G', 8), new Knight(black));
        gameBoard.put(new BoardLocation('H', 8), new Rook(black));
    }

    public void printAll() {
        int incrementer = 0;
        for (Iterator<Map.Entry<BoardLocation, ChessPiece>> i = gameBoard.entrySet().iterator(); i.hasNext(); ) {
            if (incrementer % 8 == 0) {
                System.out.print("\n");
            }

            ChessPiece currentPiece = i.next().getValue();
            System.out.print
                    ("[ " +((currentPiece == null) ? "-" : currentPiece.toString())+ " ]");
            incrementer++;
        }

        System.out.println();
    }

    public void movePiece(BoardLocation location, BoardLocation newLocation) {
        ChessPiece toBeMoved = null;

        gameBoard.keySet().remove(gameBoard.get(location));

        printAll();
    }

}
