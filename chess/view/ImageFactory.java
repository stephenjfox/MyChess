package chess.view;

import chess.controller.ChessHelp;
import chess.model.pieces.ChessPiece;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Stephen on 6/11/2014.
 * In project: NewChess
 */
public class ImageFactory {

    private BufferedImage image;

    public ImageFactory(ChessPiece c) {
        String theDir = ChessHelp.getPieceImageDir(c);

        try {
            this.image = ImageIO.read(new File(theDir));
        }
        catch (IOException e) {
            System.err.println("The ImagePanel threw up when I made it.");
        }

    }


    public BufferedImage getImage() {
        return image;
    }
}
