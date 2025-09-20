package Pieces;

import Board.Board;
import java.awt.image.BufferedImage;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;
        this.name = "Queen";

        if (sheet != null) {
            BufferedImage sub = getSprite(1, isWhite ? 0 : 1);
            this.sprite = sub.getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        }
    }
    @Override
    public boolean canMoveTo(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.col);
        int rowDiff = Math.abs(newRow - this.row);
        // Vezir yatay, dikey veya çapraz hareket eder
        boolean isStraight = (this.col == newCol || this.row == newRow);
        boolean isDiagonal = (colDiff == rowDiff);
        return (isStraight || isDiagonal) && !(this.col == newCol && this.row == newRow);
    }

}
