package chess.viewmodel;

import chess.model.board.BoardLocation;
import chess.model.board.TextChessBoard;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stephen on 5/9/2014.
 */
public class FileInputHandler {

    String PLACEMENT_REGEX = "[KkQqBbNnRrPp][ld][A-Ha-h][1-8]";
    String MOVE_ONE_REGEX = "[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\*?";
    String MOVE_TWO_REGEX = "[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\s[A-Ha-h][1-8]";

    @SuppressWarnings("serial")
	HashMap<Character, String> pieceCharRelator = new HashMap<Character, String>(){{
        put('k', "King");
        put('q', "Queen");
        put('b', "Bishop");
        put('n', "Knight");
        put('r', "Rook");
        put('p', "Rook");
    }};
    // The piece is what's important: it tells you want can be done and how
    // The user provides instruction for the piece to do things
    
    public void executeFromFile(File file) {
        File workable = file;
        List<String> parseAbles = null;
        try {
            parseAbles = Files.readAllLines(workable.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            ErrorLogger.logError(e.getLocalizedMessage() + "\nThe FIH.execute..()'s Files.readAllLines() failed");
        }
        try { // Shot blocking NullPointer Exceptions
            for (String parseAble : parseAbles) {
                // determineOperation(parseAble).operate(parseAble);
                if(parseAble.matches(PLACEMENT_REGEX)) startingPlacement(parseAble);

                if (parseAble.matches(MOVE_ONE_REGEX)) movePiece(parseAble);

                // Input will match one move, but check to see if it matches it twice
                if (parseAble.matches(MOVE_TWO_REGEX)) moveTwoPieces(parseAble);

                if ((!parseAble.matches(MOVE_ONE_REGEX) && !parseAble.matches(MOVE_TWO_REGEX)
                        && !parseAble.matches(PLACEMENT_REGEX))) {
                    sayInvalid(parseAble);
                }
            }
        } catch (NullPointerException e) {
            ErrorLogger.logError("The parseAble hit null or .operate() was called on a null");
        }
    }

    private void startingPlacement(String piecePattern) {
        String[] theParts = parse(piecePattern);
        // DEBUG LOGIC
        System.out.println(theParts[1] + " " + theParts[0] + " placed on "+ theParts[2] + theParts[3]);
        // DEBUG LOGIC
    }

    private String[] parse(String parseAble) {
        String[] parts = new String[4];
        char[] wordSplit = parseAble.toLowerCase().toCharArray();
        // will be 4 characters long
        parts[0] = pieceCharRelator.get(wordSplit[0]); // Pawn, king, queen, rook, knight, bishop
        parts[1] = wordSplit[1] == 'd' ? "Black" : "White"; // black or white type
        parts[2] = String.valueOf(wordSplit[2]); // lettered column [a, b, c, d, e, f, g, h]
        parts[3] = String.valueOf(wordSplit[3]); // the numbered row [1, 2, 3, 4, 5, 6, 7, 8]

        return parts;
    }

    private void movePiece(String piecePattern) {
        String[] parts = piecePattern.split("\\s");
           // DEBUG LOGIC
        if(parts[1].contains("*")) {
        	System.out.println("Piece at " + parts[0] + " moves to capture " + parts[1].substring(0, 2));
        	
        }
        else 
        	System.out.println("Piece at " + parts[0] + " moves to " + parts[1]);
        // DEBUG LOGIC
    }

    private void moveTwoPieces(String piecePattern) {
        String[] parts = piecePattern.split("\\s");
        // DEBUG LOGIC
        System.out.println("Piece at " + parts[0] + " move to " + parts[1] +
                " and piece at " + parts[2] + " is moving to " + parts[3]);
        // DEBUG LOGIC
    }
    // DEBUG LOGIC
    private void sayInvalid(String piecePattern) {
        System.out.println(piecePattern + " twas an invalid move");
    }
    // DEBUG LOGIC
    
/*
    enum Operation {
        PlacePiece {
            @Override
            void operate(String piecePattern)
        },
        MovePiece {
            @Override
            void operate(String piecePattern)
        },
        MoveTwoPieces {
            @Override
            void operate
        },
        InvalidMove {
            @Override
            void operate
        };

        Operation() {
        }


        abstract void operate(String piecePattern);
    }*/
}
