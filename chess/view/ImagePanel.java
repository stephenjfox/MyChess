package chess.view;

import chess.model.pieces.ChessPiece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(ChessPiece c) {
        super();
        try {
            this.image = ImageIO.read(new File("Hashmap directory for piece"));
        } catch (IOException e) {
            System.err.println("The ImagePanel threw up when I made it.");
        }
    }
}
