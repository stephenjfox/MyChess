package chess.controller.instruction;

/**
 * Created by Stephen on 5/15/2014.
 */
public class MoveTwoInstruction extends Instruction {
    public MoveTwoInstruction() {
    }

    public MoveTwoInstruction(String[] writInstruction) {
        if (writInstruction.length == 4) {
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
