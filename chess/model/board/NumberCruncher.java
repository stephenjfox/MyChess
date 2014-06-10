package chess.model.board;

import chess.model.pieces.ChessPiece;

/**
 * Created by Stephen on 6/6/2014.
 * In project: NewChess
 */
public class NumberCruncher {

    public static int pieceMaxRange(ChessPiece chessPiece) {

        switch (chessPiece.toString().toLowerCase()) {

            case "k":
//                System.out.println("King return");
                return 1;
            case "r":
//                System.out.println("Rook fallthrough");
            case "b":
//                System.out.println("Bishop fallthrough");
            case "q":
//                System.out.println("Queen fallthrough");
                return 8;
            case "n":
//                System.out.println("Knight case");
                return 2;
            case "p":
//                System.out.println("Pawn case");
                return 2;
            default:
//                System.out.println("Misread");
                return 1;
        }

//        return new HashMap<String, Integer>(){
//            {
//                put("k", 1);
//                put("q", 8);
//                put("b", 8);
//                put("n", 3);
//                put("r", 8);
//                put("p", 2);
//            }
//        }.get(chessPiece.toString().toLowerCase());

    }

}
