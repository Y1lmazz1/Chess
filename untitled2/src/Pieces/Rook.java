package Pieces;

import Board.Board;
import java.awt.image.BufferedImage;

public class Rook extends Piece {
    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;
        this.name = "Rook";

        if (sheet != null) {
            BufferedImage sub = getSprite(4, isWhite ? 0 : 1);
            this.sprite = sub.getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        }
    }
    @Override
    public boolean canMoveTo(int newCol, int newRow) {
        
        return (this.col == newCol || this.row == newRow) && !(this.col == newCol && this.row == newRow);
    }




}

