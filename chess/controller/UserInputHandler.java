package chess.controller;

import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;
import chess.model.board.BoardLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Stephen on 6/9/2014.
 * In project: NewChess
 */
public class UserInputHandler {

    final String SENTINEL = "32";
    private MoveProjector mp = new MoveProjector(GameController.containerForTheGame);


    public void runTextPromptGame() {


        while (true) {

            displayMovablePieces(
                    getMovablePieces(GameController.isWhiteTurn())
            );

            String prompted = promptForInput();
            boolean validCommand = analyzeMove(prompted);

            Instruction executable = getInstructionFromString(prompted);
            if (executable != null) {

                executable.execute();

            }
            else {
                System.err.println("Bad input, please try again");
            }

            ChessHelp.printPlayerTurn();

        }

    }

    private boolean analyzeMove(String prompted) {

        return false;
    }

    private String promptForInput() {

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder input = new StringBuilder();

        System.out.println("Please select a piece (in format [a-e][1-8]");
        try {

            input.append(buff.readLine());

        }
        catch (IOException e) {
            System.err.println("Failed to append to piece selection to the StringBuilder");
        }

        System.out.println("Please select a destination (in format [a-e][1-8]");
        try {

            input.append(" ").append(buff.readLine());

        }
        catch (IOException e) {
            System.err.println("Failed to append to location selection to the StringBuilder");
        }

        if(input.toString().contains(SENTINEL)) {

            System.out.println("Process terminated");
            System.exit(0);

        }

        return input.toString();
    }

    private void displayMovablePieces(ArrayList<BoardLocation> thePieces) {

        if (thePieces.size() == 0) System.out.println("You're cutting me too much");
        for (int i = 0, squaresWithColorSize = thePieces.size(); i < squaresWithColorSize; i++) {

            BoardLocation boardLocation = thePieces.get(i);

            System.out.println((i + 1) + ") " + boardLocation.getName());
        }
    }

    private ArrayList<BoardLocation> getMovablePieces(boolean b) {

        ArrayList<BoardLocation> squaresWithColor =
                GameController.containerForTheGame.getCheckFinder().pullSquaresWithColor(b);

        squaresWithColor.forEach(
                f -> System.out.println(f + " " + f.getPresentPiece().fancyName())
        );

        ArrayList<BoardLocation> movables = new ArrayList<>();

        for (BoardLocation friendlySquare : squaresWithColor) {

            ArrayList<BoardLocation> validMoves =
                    mp.getValidMoves(friendlySquare);

            if(validMoves.size() > 0) movables.add(friendlySquare);
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
