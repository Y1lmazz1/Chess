package Pieces;

import Board.Board;
import java.awt.image.BufferedImage;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;
        this.name = "Pawn";

        if (sheet != null) {
            BufferedImage sub = getSprite(5, isWhite ? 0 : 1); // 0. sütun = Pawn
            this.sprite = sub.getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        }
    }
    @Override
    public boolean canMoveTo(int newCol, int newRow) {
        int direction = isWhite ? -1 : 1; // Beyaz yukarı, siyah aşağı

        // İleri hareket (aynı sütun)
        if (this.col == newCol) {
            // 1 kare ileri
            if (this.row + direction == newRow && board.getPiece(newCol, newRow) == null) {
                return true;
            }
            // İlk hamlede 2 kare ileri
            if ((isWhite && this.row == 6 || !isWhite && this.row == 1) &&
                    this.row + 2 * direction == newRow &&
                    board.getPiece(newCol, newRow) == null &&
                    board.getPiece(newCol, this.row + direction) == null) {
                return true;
            }
        }
        // Çaprazda rakip taşı alma
        else if (Math.abs(this.col - newCol) == 1 && this.row + direction == newRow) {
            Piece target = board.getPiece(newCol, newRow);
            if (target != null && target.isWhite != this.isWhite) {
                return true;
            }
        }
        return false;
    }


}
