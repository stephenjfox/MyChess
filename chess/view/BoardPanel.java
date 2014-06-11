package chess.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class BoardPanel extends JPanel {

    private Graphics graphics; // Assigned when the BoardPanel is drawn
    private int rows = 8, cols = rows;
    final int SQUARE_SIZE = 80;

    public BoardPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;

        g.fillRect(50, 50, SQUARE_SIZE, SQUARE_SIZE);

        for (int i = 0; i <= rows; i++) {

            for (int k = 0; k <= cols; k++) {

                if(k % 2 == 0 ^ i % 2 == 1) g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect(k * SQUARE_SIZE,
                        i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            }

        }
    }
}
