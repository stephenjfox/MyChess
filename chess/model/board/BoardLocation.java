package chess.model.board;

/**
 * Created by Stephen on 5/9/2014.
 */
public class BoardLocation {
    /**
     * This class is the 1-based board locations on a standard 8x8 chessboard
     */
    int x, y;


    public BoardLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BoardLocation(char lowercase, int y) {
        this.x = intSwap(lowercase);
        this.y = y;
    }

    /**
     * @param xVal: char of horizontal value
     * @return appropriate int ('a' becomes 1, 'b' becomes 2, etc)
     */
    private int intSwap(char xVal) {
        switch (xVal) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
            default:
                return 0;
        }
    }
    /**
     * Returns a string representation of this point and its location
     * in the {@code (x,y)} coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be <code>null</code>.
     *
     * @return  a string representation of this point
     */
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
