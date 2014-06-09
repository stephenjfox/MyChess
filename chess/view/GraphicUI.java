package chess.view;

import chess.controller.FileInputHandler;
import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;
import chess.model.board.ChessBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Stephen on 5/27/2014.
 */
public class GraphicUI implements UserInterface {

    private ChessBoard focusBoard;

    public GraphicUI(ChessBoard board) {
        this.focusBoard = board;
    }

    @Override
    public void drawBoard() {

        // TODO: the black and white alternating grid of "clickables"
        focusBoard.printBoard();

    }

    @Override
    public void informTheUser(String input) {

    }

    public void runTextPromptGame() {

        while (true) {

            String prompted = promptForInput();

            getInstructionFromString(prompted).execute();

        }

    }

    private String promptForInput() {

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder input = new StringBuilder();

        System.out.println("Please select a piece (in format [a-e][1-8]");

        try {

            input.append(buff.readLine());

        } catch (IOException e) {
            System.err.println("Failed to append to piece selection to the StringBuilder");
        }

        System.out.println("Please select a destination (in format [a-e][1-8]");

        try {

            input.append(buff.readLine());

        } catch (IOException e) {
            System.err.println("Failed to append to location selection to the StringBuilder");
        }

        return input.toString();
    }

    /**
     * Parse the String parameter to match with the MovePieceInstruction;
     * If no match is found, null is returned
     * @param buffReturn String variable to be
     * @return an appropriate Instruction from the parts of the string input
     */
        private Instruction getInstructionFromString(String buffReturn) {

        String[] possibleInstruction = buffReturn.split("\\s");

        if(buffReturn.matches(FileInputHandler.MOVE_ONE_REGEX))
            return new MovePieceInstruction(possibleInstruction);

//        if(buffReturn.matches(FileInputHandler.MOVE_TWO_REGEX))
//            return new MoveTwoInstruction(possibleInstruction);

        return null;
    }
}
