package Pieces;

import Board.Board;
import java.awt.image.BufferedImage;

public class Bishop extends Piece {
    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;
        this.name = "Bishop";

        if (sheet != null) {
            BufferedImage sub = getSprite(2, isWhite ? 0 : 1);
            this.sprite = sub.getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        }
    }
    @Override
    public boolean canMoveTo(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.col);
        int rowDiff = Math.abs(newRow - this.row);
        
        return colDiff == rowDiff && colDiff != 0;
    }


}

