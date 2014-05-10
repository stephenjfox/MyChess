package chess.viewmodel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Stephen on 5/9/2014.
 */
public class FileInputHandler {

    String PLACEMENT_REGEX = "[KkQqBbNnRrPp][ld][A-Ha-h][1-8]";
    String MOVE_ONE_REGEX = "[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\*?";
    String MOVE_TWO_REGEX = "[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\s[A-Ha-h][1-8]";

    private Operation determineOperation(String inputLine) {
        Operation returnable = null;
        if(inputLine.matches(PLACEMENT_REGEX))
            returnable = Operation.PlacePiece;
        if (inputLine.matches(MOVE_ONE_REGEX)) {
            returnable = Operation.MovePiece;
        }
        // Input will match one move, but check to see if it matches it twice
        if (inputLine.matches(MOVE_TWO_REGEX)) {
            returnable = Operation.MoveTwoPieces;
        }
        if(returnable == null) {
            returnable = Operation.InvalidMove;
        }

        return returnable;
    }
    public void executeFromFile(File file) {
        File workable = file;
        List<String> parseAbles = null;
        try {
            parseAbles = Files.readAllLines(workable.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            ErrorLogger.logError(e.getLocalizedMessage() + "\nThe FIH.execute..()'s Files.readAllLines() failed");
        }
        try {
            for (String parseAble : parseAbles) {
                determineOperation(parseAble).operate(parseAble);
            }
        } catch (NullPointerException e) {
            ErrorLogger.logError("The parseAble hit null or .operate() was called on a null");
        }
    }


    enum Operation {
        PlacePiece {
            @Override
            void operate(String piecePattern) {
                String[] theParts = parse(piecePattern);
                System.out.println(theParts[1] + " " + theParts[0] + " placed on "+ theParts[2] + theParts[3]);
            }

            private String[] parse(String parseAble) {
                String[] parts = new String[4];
                char[] wordSplit = parseAble.toLowerCase().toCharArray();
                // will be 4 characters long
                parts[0] = retrievePieceName(wordSplit[0]);
                parts[1] = wordSplit[1] == 'd' ? "Black" : "White";
                parts[2] = String.valueOf(wordSplit[2]);
                parts[3] = String.valueOf(wordSplit[3]);

                return parts;
            }
        },
        MovePiece {
            @Override
            void operate(String piecePattern) {
                String[] parts = piecePattern.split("\\s");
                System.out.println("Piece at " + parts[0] + " move to " + parts[1]);
            }
        },
        MoveTwoPieces {
            @Override
            void operate(String piecePattern) {
                String[] parts = piecePattern.split("\\s");
                System.out.println("Piece at " + parts[0] + " move to " + parts[1] +
                        " and piece at " + parts[2] + " is moving to " + parts[3]);
            }
        },
        InvalidMove {
            @Override
            void operate(String piecePattern) {
                System.out.println(piecePattern + " twas an invalid move");
            }
        };

        Operation() {
        }

        String retrievePieceName(char c) {
            switch(c){
                case 'k':
                    return "King";
                case 'q':
                    return "Queen";
                case 'b':
                    return "Bishop";
                case 'n':
                    return "Pawn";
                case 'r':
                    return "Rook";
                case 'p':
                    return "Pawn";
                default:
                    return "SwitchFail";
            }
        }

        abstract void operate(String piecePattern);
    }
}
