package chess.controller;

import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;
import chess.model.board.BoardLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static chess.controller.GameController.*;

/**
 * Created by Stephen on 6/9/2014.
 * In project: NewChess
 */
public class UserInputHandler {

    final String SENTINEL = "32", GOOD_INPUT = "[a-h][1-8]";
    private MoveProjector mp = new MoveProjector(containerForTheGame);


    public void runTextPromptGame() {


        do {
            // Show the player's turn
            ChessHelp.printPlayerTurn();

            // Show their available movable pieces
            displayMovables(
                    getMovablePieces(isWhiteTurn())
            );

            // Prompt
            String firstInput = promptForInput("Enter the starting location in [a-h][1-8] format");

            // Check the validity of the input
            if (analyzeMove(firstInput)) {

                // Present second set of choices: possible destinations of selected piece
                displayMovables(
                        getValidMoves(firstInput)
                );

                // Receive the input
                String secondInput = promptForInput("Enter the destination location in [a-h][1-8] format");

                // Match the regex
                if ((secondInput).matches(GOOD_INPUT)) {

                    Instruction executable = getInstructionFromString(firstInput + " " + secondInput);

                    // Execute
                    executable.execute();

                } else {
                    System.err.println("Poor input, please start over.");
                }

            } else {
                System.err.println("Improper selection");
            }

            ChessHelp.promotePawn();

            ChessHelp.callCheck();

        } while (true);

    }

    private ArrayList<BoardLocation> getValidMoves(String secondInput) {

        // Grab the board
        BoardLocation[][] functionalBoard = containerForTheGame.getFunctionalBoard();

        // Create BoardLocation wrapper instance to get the goodies
        BoardLocation inputWrapper = new BoardLocation(secondInput);

        // Get the actual square that holds that stuff I want
        BoardLocation actualSquare = functionalBoard[inputWrapper.getY() - 1][inputWrapper.getX() - 1];

        // return the moves that are good
        return mp.getValidMoves(actualSquare);
    }

    private boolean analyzeMove(String prompted) {

        return prompted.matches(GOOD_INPUT) && teamMatches(new BoardLocation(prompted));
    }

    private boolean teamMatches(BoardLocation location) {

        BoardLocation[][] fBoard = containerForTheGame.getFunctionalBoard();


        return fBoard[location.getY()-1][location.getX()-1].getPresentPiece().isWhite() == isWhiteTurn();
    }

    private String promptForInput(String prompt) {

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder input = new StringBuilder();

        System.out.println(prompt);
        try {

            input.append(buff.readLine());

        }
        catch (IOException e) {
            System.err.println("Failed to append to piece selection to the StringBuilder");
        }

        if(input.toString().contains(SENTINEL)) {

            System.out.println("Process terminated");
            System.exit(0);

        }

        return input.toString();
    }

    private void displayMovables(ArrayList<BoardLocation> thePieces) {

        if (thePieces.size() == 0) {
            System.out.println("You're cutting me too much");
            return;
        }

        for (int i = 0, squaresWithColorSize = thePieces.size(); i < squaresWithColorSize; i++) {

            BoardLocation boardLocation = thePieces.get(i);

            System.out.println((i + 1) + ") " + boardLocation.getName());
        }
    }

    private ArrayList<BoardLocation> getMovablePieces(boolean b) {

        ArrayList<BoardLocation> squaresWithColor =
                containerForTheGame.getCheckFinder().pullSquaresWithColor(b);

//        squaresWithColor.forEach(
//                f -> System.out.println(f + " " + f.getPresentPiece().fancyName())
//        );

        ArrayList<BoardLocation> movables = new ArrayList<>();

        for (BoardLocation friendlySquare : squaresWithColor) {

//            System.out.printf("Testing %s\n", friendlySquare.getName());
            ArrayList<BoardLocation> validMoves =
                    mp.getValidMoves(friendlySquare);

            if(validMoves.size() > 0) {
                movables.add(friendlySquare);
//                System.out.println("Added a possible piece square");
            }
        }

        return movables;

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

        return null;
    }

}
