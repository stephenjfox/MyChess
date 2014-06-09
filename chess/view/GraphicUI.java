package chess.view;

import chess.controller.instruction.Instruction;
import chess.model.board.ChessBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * Created by Stephen on 5/27/2014.
 */
public class GraphicUI implements UserInterface {

    private ChessBoard focusBoard;

    public GraphicUI(ChessBoard board) {
        this.focusBoard = board;
    }

    @Override
    public void drawBoard() {

        // TODO: the black and white alternating grid of "clickables"

    }

    @Override
    public void informTheUser(String input) {

    }

    public void captureInput(Collection<Instruction> userInputs) {

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

        String buffReturn = "";

        try {
            buffReturn = buff.readLine();
        } catch (IOException e) {
            System.err.println("BufferedReader failed to capture input");
        }

        userInputs.add(getInstructionFromString(buffReturn));

    }

    private Instruction getInstructionFromString(String buffReturn) {

        return null;
    }

}
