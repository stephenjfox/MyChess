package chess.ai;

import chess.controller.instruction.Instruction;
import chess.controller.instruction.MovePieceInstruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Stephen on 6/13/2014.
 * In project: NewChess
 */
public class ConsoleInputHandler {

    private static ConsoleInputHandler ourInstance = new ConsoleInputHandler();

    private static BufferedReader buff;
    public static ConsoleInputHandler getInstance() {
        return ourInstance;
    }

    private ConsoleInputHandler() {
    }

    {
        buff = new BufferedReader(new InputStreamReader(System.in));
    }

    static Instruction readInputLine() {

        String[] playersMove = new String[2];

        try {
            playersMove = buff.readLine().split("\\s");
        } catch (IOException e) {
            System.err.println("BufferedReader.readLine() threw exception\n" + e.getLocalizedMessage());
        }

        return new MovePieceInstruction(playersMove);
    }

}
