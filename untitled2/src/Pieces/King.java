package Pieces;

import Board.Board;
import java.awt.image.BufferedImage;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;
        this.name = "King";

        if (sheet != null) {
            BufferedImage sub = getSprite(0, isWhite ? 0 : 1);
            this.sprite = sub.getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        }
    }

    @Override
    public boolean canMoveTo(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.col);
        int rowDiff = Math.abs(newRow - this.row);

        if (rowDiff == 0 && colDiff == 2) {
            return true; 
        }

        return (colDiff <= 1 && rowDiff <= 1) && !(colDiff == 0 && rowDiff == 0);
    }

    public boolean canCastleShort() {
        if (this.hasMoved) return false;

        Piece rook = board.getPiece(7, this.row);
        if (!(rook instanceof Rook) || rook.isWhite != this.isWhite || rook.hasMoved) return false;

        if (board.getPiece(5, this.row) != null) return false;
        if (board.getPiece(6, this.row) != null) return false;

        if (board.isSquareAttacked(4, this.row, !this.isWhite)) return false;
        if (board.isSquareAttacked(5, this.row, !this.isWhite)) return false;
        if (board.isSquareAttacked(6, this.row, !this.isWhite)) return false;

        return true;
    }

    public boolean canCastleLong() {
        if (this.hasMoved) return false;

        Piece rook = board.getPiece(0, this.row);
        if (!(rook instanceof Rook) || rook.isWhite != this.isWhite || rook.hasMoved) return false;

        if (board.getPiece(1, this.row) != null) return false;
        if (board.getPiece(2, this.row) != null) return false;
        if (board.getPiece(3, this.row) != null) return false;

        if (board.isSquareAttacked(4, this.row, !this.isWhite)) return false;
        if (board.isSquareAttacked(3, this.row, !this.isWhite)) return false;
        if (board.isSquareAttacked(2, this.row, !this.isWhite)) return false;

        return true;
    }
}

