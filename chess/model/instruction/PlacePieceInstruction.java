package chess.model.instruction;

/**
 * Created by Stephen on 5/15/2014.
 */
public class PlacePieceInstruction extends Instruction {
    public PlacePieceInstruction() {
        super();
    }

    public PlacePieceInstruction(String[] writInstruction) {
        if (writInstruction.length == 3) {
            parsedInstruction = writInstruction;
        } else {
            parsedInstruction = new String[]{"King", "l", "e4"};
            // Revert to the dummy
            System.err.println("This PlacePieceInstruction() is broken");
        }
    }

    @Override
    public void execute() {

    }
}
