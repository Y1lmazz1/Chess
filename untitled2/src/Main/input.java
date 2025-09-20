package Main;

import Board.Board;
import Pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class input extends MouseAdapter {
    private Board board;

    public input(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / board.titleSize;
        int row = e.getY() / board.titleSize;

        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null && pieceXY.isWhite == board.whiteTurn) {
            board.selectPiece = pieceXY;
            board.selectedCol = col;
            board.selectedRow = row;

            // Geçerli hamleleri hesapla
            board.validMoves.clear();
            for (int c = 0; c < board.cols; c++) {
                for (int r = 0; r < board.rows; r++) {
                    Move testMove = new Move(pieceXY, c, r);
                    if (board.isValidMove(testMove)) {
                        board.validMoves.add(new int[]{c, r});
                    }
                }
            }

            board.repaint();
        } else {
            board.selectPiece = null;
            board.selectedCol = -1;
            board.selectedRow = -1;
            board.validMoves.clear();
            board.repaint();
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.selectPiece != null) {
            board.selectPiece.xPos = e.getX() - board.titleSize / 2;
            board.selectPiece.yPos = e.getY() - board.titleSize / 2;
            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.titleSize;
        int row = e.getY() / board.titleSize;

        if (board.selectPiece != null) {
            Move move = new Move(board.selectPiece, col, row);
            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                board.selectPiece.xPos = board.selectPiece.col * board.titleSize;
                board.selectPiece.yPos = board.selectPiece.row * board.titleSize;
            }
        }
        if (board.promotionPending) {
            board.askPromotion();
            return;  // Terfi bitmeden başka hareket engellenir
        }

        // Seçimi temizle
        board.selectPiece = null;
        board.selectedCol = -1;
        board.selectedRow = -1;
        board.validMoves.clear();
        board.repaint();
    }

}
