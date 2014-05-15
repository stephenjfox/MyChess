package chess.controller.instruction;

/**
 * Created by Stephen on 5/15/2014.
 */
public abstract class Instruction {
    protected String[] parsedInstruction;
    protected Instruction() {
        parsedInstruction = new String[]{"King", "l", "e4"};
        // Dummy in the middle of board is an obvious error
    }

    public abstract void execute();

}
