package chess.controller.instruction;

import chess.controller.GameController;

/**
 * Created by Stephen on 5/15/2014.
 */
public class MoveTwoInstruction extends Instruction {
    private String[] instruction;

    public MoveTwoInstruction(String... instruction) {
        this.instruction = instruction;
    }

    public void execute() {
        GameController.containerForTheGame.moveTwoPiece(instruction);
        System.out.println("Move two pieces simultaneously");
    }

}
