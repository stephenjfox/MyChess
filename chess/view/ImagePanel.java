package chess.view;

import chess.controller.ChessHelp;
import chess.model.pieces.ChessPiece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
        String theDir = ChessHelp.getPieceImageDir(c);

        try {
            System.out.println(theDir);

            this.image = ImageIO.read(new File(theDir));

        } catch (IOException e) {
            System.err.println("The ImagePanel threw up when I made it.");
        }

        this.setPreferredSize(new Dimension(10, 10));
        JLabel jLabel = new JLabel(new ImageIcon(image));
        this.add(jLabel);
    }



}
