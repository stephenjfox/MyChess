package chess.controller;

import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;
import chess.model.board.BoardLocation;
import chess.model.board.NumberCruncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Stephen on 6/9/2014.
 * In project: NewChess
 */
public class UserInputHandler {

    public void runTextPromptGame() {

        while (true) {

            displayAvailablePieces(GameController.isWhiteTurn());

            String prompted = promptForInput();
//            boolean validCommand = analyzeMove(prompted);

            if (true) {

                getInstructionFromString(prompted).execute();

            }

            ChessHelp.printPlayerTurn();

        }

    }
//
//    private boolean analyzeMove(String prompted) {
//
//
//        return false;
//    }

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

            input.append(" ").append(buff.readLine());

        } catch (IOException e) {
            System.err.println("Failed to append to location selection to the StringBuilder");
        }

        System.out.println("DEBUG:"+input.toString());
        return input.toString();
    }

    private void displayAvailablePieces(boolean b) {

        ArrayList<BoardLocation> squaresWithColor =
                GameController.containerForTheGame.getCheckFinder().pullSquaresWithColor(b);

        MoveProjector mp = new MoveProjector(GameController.containerForTheGame);

        squaresWithColor.removeIf(

                square ->
                        mp.projectValidMoves(
                                square, NumberCruncher.pieceMaxRange(square.getPresentPiece())
                        ).size() == 0
        );

        squaresWithColor.forEach(System.out::println);

        for (int i = 0, squaresWithColorSize = squaresWithColor.size(); i < squaresWithColorSize; i++) {

            BoardLocation boardLocation = squaresWithColor.get(i);

            System.out.println( (i + 1) + ") " + boardLocation.getName());
        }

    }

    /**
     * Parse the String parameter to match with the MovePieceInstruction;
     * If no match is found, null is returned
     * @param buffReturn String variable to be
     * @return an appropriate Instruction from the parts of the string input
     */
    private Instruction getInstructionFromString(String buffReturn) {

        String[] possibleInstruction = buffReturn.split("\\s");

        if(buffReturn.matches(FileInputHandler.MOVE_ONE_REGEX)) {

            return new MovePieceInstruction(possibleInstruction);
        }

//        if(buffReturn.matches(FileInputHandler.MOVE_TWO_REGEX))
//            return new MoveTwoInstruction(possibleInstruction);

        return null;
    }

}
