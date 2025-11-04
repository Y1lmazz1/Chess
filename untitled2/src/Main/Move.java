package Main;

import Pieces.Piece;

public class Move {

    public int oldCol;
    public int oldRow;
    public int newCol;
    public int newRow;

    public Piece piece;     
    public Piece capture;  

    public boolean isCastling = false;
    public boolean isPromotion = false;
    public boolean isEnPassant = false;

  
    public Move(int oldCol, int oldRow, int newCol, int newRow, Piece piece) {
        this.oldCol = oldCol;
        this.oldRow = oldRow;
        this.newCol = newCol;
        this.newRow = newRow;
        this.piece = piece;
    }

    
    public Move(Piece piece, int newCol, int newRow) {
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;
        this.piece = piece;
    }

   
    @Override
    public String toString() {
        return piece.name + " (" + oldCol + "," + oldRow + ") → (" + newCol + "," + newRow + ")";
    }
}

