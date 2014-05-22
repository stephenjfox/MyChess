package chess.controller;

import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;
import chess.controller.instruction.MoveTwoInstruction;
import chess.controller.instruction.PlacePieceInstruction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stephen on 5/9/2014.
 */
public class FileInputHandler {

	final String PLACEMENT_REGEX = "[KkQqBbNnRrPp][ld][A-Ha-h][1-8]";
	final String MOVE_ONE_REGEX = "[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\*?";
	final String MOVE_TWO_REGEX = "[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\s[A-Ha-h][1-8]\\s[A-Ha-h][1-8]";

    @SuppressWarnings("serial")
    HashMap<Character, String> pieceCharRelator = new HashMap<Character, String>() {
        {
            put('k', "King");
            put('q', "Queen");
            put('b', "Bishop");
            put('n', "Knight");
            put('r', "Rook");
            put('p', "Pawn");
        }
    };

	// The piece is what's important: it tells you want can be done and how
	// The user provides instruction for the piece to do things

	public ArrayList<Instruction> executeFromFile(File file) {
		File workable = file;
		List<String> parseAbles = null;
		try {
			parseAbles = Files.readAllLines(workable.toPath(),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			ErrorLogger.logError(e.getLocalizedMessage()
					+ "\nThe FIH.execute..()'s Files.readAllLines() failed");
		}

        ArrayList<Instruction> instructions = new ArrayList<>();
        // Instructions for pieces on the board

		try { // Shot blocking NullPointer Exceptions
			for (String parseAble : parseAbles) {
				// determineOperation(parseAble).operate(parseAble);
				if (parseAble.matches(PLACEMENT_REGEX))
					instructions.add(startingPlacement(parseAble));

				if (parseAble.matches(MOVE_ONE_REGEX))
					instructions.add(movePiece(parseAble));

				// Input will match one move, but check to see if it matches it
				// twice
				if (parseAble.matches(MOVE_TWO_REGEX))
					instructions.add(moveTwoPieces(parseAble));

				if ((!parseAble.matches(MOVE_ONE_REGEX)
						&& !parseAble.matches(MOVE_TWO_REGEX) && !parseAble
							.matches(PLACEMENT_REGEX))) {
					sayInvalid(parseAble);
				}
			}
		} catch (NullPointerException e) {
			ErrorLogger
					.logError("The parseAble hit null or .operate() was called on a null");
		}
        return instructions;
	}

	private Instruction startingPlacement(String piecePattern) {
		String[] theParts = parsePlacementString(piecePattern);
		// part[0] = string rep of piece
        // part[1] = string white or black
        // part[2] is as below

		// DEBUG LOGIC
		System.out.println((theParts[1].equals("l") ? "White" : "Black") + " " + theParts[0] + " placed on "
				+ theParts[2]);
		// DEBUG LOGIC

        return new PlacePieceInstruction(theParts);
	}

	private Instruction movePiece(String piecePattern) {
		String[] parts = piecePattern.split("\\s");

		// DEBUG LOGIC
		if (parts[1].contains("*")) {
            parts[1] = parts[1].substring(0, 2);
            System.out.println("Piece at " + parts[0] + " moves to capture "
                    + parts[1]);
        } else
			System.out
					.println("Piece at " + parts[0] + " moves to " + parts[1]);
		// DEBUG LOGIC

        return new MovePieceInstruction(parts);
	}

	private Instruction moveTwoPieces(String piecePattern) {
		String[] parts = piecePattern.split("\\s");
		// DEBUG LOGIC
		System.out.println("Piece at " + parts[0] + " move to " + parts[1]
				+ " and piece at " + parts[2] + " is moving to " + parts[3]);
		// DEBUG LOGIC
        return new MoveTwoInstruction(parts);
	}

	// DEBUG LOGIC
	private void sayInvalid(String piecePattern) {
		System.err.println(piecePattern + " twas an invalid move");
	}
	// DEBUG LOGIC

	private String[] parsePlacementString(String parseAble) {
		String[] parts = new String[3];
		char[] wordSplit = parseAble.toLowerCase().toCharArray();
		// will be 4 characters long
        parts[0] = pieceCharRelator.get(wordSplit[0]); // Pawn, king, queen,
														// rook, knight, bishop
		parts[1] = wordSplit[1] == 'd' ? "d" : "l"; // black or white
															// type
		parts[2] = String.valueOf(wordSplit[2]) + String.valueOf(wordSplit[3]); // lettered column [a, b, c, d,
													// e, f, g, h]
//		parts[3] = String.valueOf(wordSplit[3]); // the numbered row [1, 2, 3,
													// 4, 5, 6, 7, 8]
		return parts;
	}

}
