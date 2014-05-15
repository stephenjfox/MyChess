package chess.controller.instruction;

/**
 * Created by Stephen on 5/15/2014.
 */
public class MovePieceInstruction extends Instruction {
    public MovePieceInstruction() {
        super();
    }

    public MovePieceInstruction(String[] writInstruction) {
        if (writInstruction.length == 2) {
            parsedInstruction = writInstruction;
        } else {
            parsedInstruction = new String[]{"King", "l", "e4"};
            // Revert to the dummy
            System.err.println("This MovePieceInstruction() is broken");
        }
    }

    @Override
    public void execute() {

    }
}
