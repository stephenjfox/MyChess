package chess.controller;

import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Stephen on 6/9/2014.
 * In project: NewChess
 */
public class UserInputHandler {

    public void runTextPromptGame() {

        while (true) {

            String prompted = promptForInput();

            getInstructionFromString(prompted).execute();

            ChessHelp.printPlayerTurn();

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
