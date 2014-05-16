package chess.controller.instruction;

import chess.controller.GameController;
import chess.viewmodel.ErrorLogger;

/**
 * Created by Stephen on 5/15/2014.
 */
public class MovePieceInstruction extends Instruction {
    private String[] instruction;

    public MovePieceInstruction(String... instruction) {
        this.instruction = instruction;
    }

    public void execute() {
        System.out.println("For DEBUG\n"+instruction[0] + " " + instruction[1]);
        try {
            GameController.containerForTheGame.movePiece(
                    instruction[0], instruction[1]
            );
        } catch (NullPointerException e) {
            ErrorLogger.logError("Something in the movePiece() threw an error.");
        }
    }
}
