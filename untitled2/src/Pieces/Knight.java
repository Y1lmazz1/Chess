package Pieces;

import Board.Board;

import java.awt.image.BufferedImage;

public class Knight extends Piece {
    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);        this.row = row;
        this.col = col;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;
        this.name = "Knight";

        if (sheet != null && sheetScale > 0) {
            // 3. sütun: Knight, 0. satır: beyaz, 1. satır: siyah taşlar
            int yOffset = isWhite ? 0 : sheetScale;
            BufferedImage sub = sheet.getSubimage(3 * sheetScale, yOffset, sheetScale, sheetScale);
            this.sprite = sub.getScaledInstance(board.titleSize, board.titleSize, BufferedImage.SCALE_SMOOTH);
        } else {
            System.err.println("Sheet yüklenemedi, Knight sprite atanamadı.");
        }
    }
    @Override
    public boolean canMoveTo(int newCol, int newRow) {
        int colDiff = Math.abs(newCol - this.col);
        int rowDiff = Math.abs(newRow - this.row);

        return (colDiff == 2 && rowDiff == 1) || (colDiff == 1 && rowDiff == 2);
    }



}
