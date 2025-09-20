package Main;

import Pieces.Piece;

public class Move {

    public int oldCol;
    public int oldRow;
    public int newCol;
    public int newRow;

    public Piece piece;      // Hareket eden taş
    public Piece capture;    // Alınan taş (varsa)

    public boolean isCastling = false;
    public boolean isPromotion = false;
    public boolean isEnPassant = false;

    // Eski hamle bilgisiyle oluştur (en kapsamlı kullanım)
    public Move(int oldCol, int oldRow, int newCol, int newRow, Piece piece) {
        this.oldCol = oldCol;
        this.oldRow = oldRow;
        this.newCol = newCol;
        this.newRow = newRow;
        this.piece = piece;
    }

    // Sadece taş ve yeni konum verilirse, eski konumu otomatik al
    public Move(Piece piece, int newCol, int newRow) {
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;
        this.piece = piece;
    }

    // İsteğe bağlı: Bilgileri yazdırmak için
    @Override
    public String toString() {
        return piece.name + " (" + oldCol + "," + oldRow + ") → (" + newCol + "," + newRow + ")";
    }
}
