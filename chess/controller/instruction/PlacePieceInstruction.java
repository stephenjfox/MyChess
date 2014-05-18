package chess.controller.instruction;

import chess.controller.ChessHelp;
import chess.controller.GameController;

/**
 * Created by Stephen on 5/15/2014.
 */
public class PlacePieceInstruction extends Instruction {
    private String[] instruction;

    public PlacePieceInstruction(String... instruction) {
        this.instruction = instruction;
    }

    public void execute() {
        GameController.containerForTheGame.placePiece(
                ChessHelp.getNewPiece(
                        instruction[0], instruction[1].charAt(0)
                ), // More lines, more often than not, = MORE LEGIBLE
                instruction[2]
        );
    }


}
