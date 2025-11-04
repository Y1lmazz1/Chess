package Board;

import Main.Move;
import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int cols = 8;
    public int rows = 8;
    public int titleSize = 85;
    public boolean whiteTurn = true;
    public int selectedCol = -1;
    public int selectedRow = -1;
    public boolean promotionPending = false;
    public int promotionCol = -1;
    public int promotionRow = -1;

    public java.util.List<int[]> validMoves = new ArrayList<>();

    public Piece selectPiece;
    ArrayList<Piece> pieceList = new ArrayList<>();

    Color lightSquareColor = new Color(227, 105, 207);
    Color darkSquareColor = new Color(172, 96, 234);

    public Board() {
        setPreferredSize(new Dimension(cols * titleSize, rows * titleSize));
        setupPieces();
    }

    private void setupPieces() {
        
        addPiece(new Rook(this, 0, 0, false));
        addPiece(new Knight(this, 1, 0, false));
        addPiece(new Bishop(this, 2, 0, false));
        addPiece(new Queen(this, 3, 0, false));
        addPiece(new King(this, 4, 0, false));
        addPiece(new Bishop(this, 5, 0, false));
        addPiece(new Knight(this, 6, 0, false));
        addPiece(new Rook(this, 7, 0, false));
        for (int i = 0; i < cols; i++) {
            addPiece(new Pawn(this, i, 1, false));
        }

        
        addPiece(new Rook(this, 0, 7, true));
        addPiece(new Knight(this, 1, 7, true));
        addPiece(new Bishop(this, 2, 7, true));
        addPiece(new Queen(this, 3, 7, true));
        addPiece(new King(this, 4, 7, true));
        addPiece(new Bishop(this, 5, 7, true));
        addPiece(new Knight(this, 6, 7, true));
        addPiece(new Rook(this, 7, 7, true));
        for (int i = 0; i < cols; i++) {
            addPiece(new Pawn(this, i, 6, true));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Color baseColor = ((col + row) % 2 == 0) ? lightSquareColor : darkSquareColor;
                g2d.setColor(baseColor);
                g2d.fillRect(col * titleSize, row * titleSize, titleSize, titleSize);
            }
        }

        if (selectedCol >= 0 && selectedRow >= 0) {
            g2d.setColor(new Color(255, 255, 0, 100));
            g2d.fillRect(selectedCol * titleSize, selectedRow * titleSize, titleSize, titleSize);
        }

        g2d.setColor(new Color(0, 255, 0, 100));
        for (int[] pos : validMoves) {
            int c = pos[0];
            int r = pos[1];
            g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
        }

        for (Piece piece : pieceList) {
            piece.draw(g2d);
        }
    }

    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidMove(Move move) {
        if (move.piece.isWhite != whiteTurn) {
            return false;
        }

        if (!move.piece.canMoveTo(move.newCol, move.newRow)) {
            return false;
        }

        Piece target = getPiece(move.newCol, move.newRow);
        if (target != null && target.isWhite == move.piece.isWhite) {
            return false;
        }

        if (!(move.piece instanceof Knight)) {
            if (!isPathClear(move)) {
                return false;
            }
        }

        if (move.piece instanceof King) {
            int colDiff = move.newCol - move.piece.col;
            if (Math.abs(colDiff) == 2) {
                if (colDiff == 2) {
                    if (!((King) move.piece).canCastleShort()) return false;
                } else if (colDiff == -2) {
                    if (!((King) move.piece).canCastleLong()) return false;
                }
            }
        }

        

        int oldCol = move.piece.col;
        int oldRow = move.piece.row;
        Piece capturedPiece = target;

       
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        if (capturedPiece != null) {
            pieceList.remove(capturedPiece);
        }

        
        boolean kingInCheck = false;
        int[] kingPos = getKingPosition(move.piece.isWhite);
        if (kingPos != null) {
            kingInCheck = isSquareAttacked(kingPos[0], kingPos[1], !move.piece.isWhite);
        }


        move.piece.col = oldCol;
        move.piece.row = oldRow;
        if (capturedPiece != null) {
            pieceList.add(capturedPiece);
        }

        if (kingInCheck) {
            return false; 
        }

        return true;
    }

   
    public int[] getKingPosition(boolean isWhite) {
        for (Piece p : pieceList) {
            if (p instanceof King && p.isWhite == isWhite) {
                return new int[] {p.col, p.row};
            }
        }
        return null;
    }

    public boolean isPathClear(Move move) {
        int startCol = move.piece.col;
        int startRow = move.piece.row;
        int endCol = move.newCol;
        int endRow = move.newRow;

        int colStep = Integer.compare(endCol, startCol);
        int rowStep = Integer.compare(endRow, startRow);

        int currentCol = startCol + colStep;
        int currentRow = startRow + rowStep;

        while (currentCol != endCol || currentRow != endRow) {
            if (getPiece(currentCol, currentRow) != null) {
                return false;
            }
            currentCol += colStep;
            currentRow += rowStep;
        }
        return true;
    }

    public void makeMove(Move move) {
        move.capture = getPiece(move.newCol, move.newRow);

        if (move.capture != null && move.capture.isWhite == move.piece.isWhite) {
            move.piece.xPos = move.piece.col * titleSize;
            move.piece.yPos = move.piece.row * titleSize;
            return;
        }

        if (move.piece instanceof King) {
            int colDiff = move.newCol - move.piece.col;
            if (Math.abs(colDiff) == 2) {
                if (colDiff == 2) {
                    Piece rook = getPiece(7, move.piece.row);
                    if (rook != null) {
                        rook.col = 5;
                        rook.xPos = 5 * titleSize;
                        rook.yPos = move.piece.row * titleSize;
                        rook.hasMoved = true;
                    }
                } else if (colDiff == -2) {
                    Piece rook = getPiece(0, move.piece.row);
                    if (rook != null) {
                        rook.col = 3;
                        rook.xPos = 3 * titleSize;
                        rook.yPos = move.piece.row * titleSize;
                        rook.hasMoved = true;
                    }
                }
            }
        }

        if (move.capture != null) {
            capture(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * titleSize;
        move.piece.yPos = move.newRow * titleSize;
        move.piece.hasMoved = true;

        if (move.piece instanceof Pawn) {
            if ((move.piece.isWhite && move.newRow == 0) || (!move.piece.isWhite && move.newRow == 7)) {
                promotionPending = true;
                promotionCol = move.newCol;
                promotionRow = move.newRow;
            }
        }

        whiteTurn = !whiteTurn;  
        repaint();
        checkGameEnd();

    }

    public void capture(Move move) {
        pieceList.remove(move.capture);
    }

    public void askPromotion() {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(this, "Choose promotion piece:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice >= 0) {
            promotePawn(choice);
        }
    }

    public void promotePawn(int choice) {
        Piece pawn = getPiece(promotionCol, promotionRow);
        if (pawn != null && pawn instanceof Pawn) {
            pieceList.remove(pawn);

            boolean isWhite = pawn.isWhite;
            Piece newPiece = null;

            switch (choice) {
                case 0: newPiece = new Queen(this, promotionCol, promotionRow, isWhite); break;
                case 1: newPiece = new Rook(this, promotionCol, promotionRow, isWhite); break;
                case 2: newPiece = new Bishop(this, promotionCol, promotionRow, isWhite); break;
                case 3: newPiece = new Knight(this, promotionCol, promotionRow, isWhite); break;
            }

            if (newPiece != null) {
                addPiece(newPiece);
            }

            promotionPending = false;
            promotionCol = -1;
            promotionRow = -1;
            repaint();
        }
    }

    public boolean isSquareAttacked(int col, int row, boolean byWhite) {
        for (Piece piece : pieceList) {
            if (piece.isWhite == byWhite) {
                if (piece.canMoveTo(col, row)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void checkGameEnd() {
        boolean isWhiteTurn = whiteTurn;
        boolean anyValidMove = false;

        for (Piece piece : pieceList) {
            if (piece.isWhite == isWhiteTurn) {
                for (int col = 0; col < cols; col++) {
                    for (int row = 0; row < rows; row++) {
                        Move move = new Move(piece.col, piece.row, col, row, piece);

                        if (isValidMove(move)) {
                            anyValidMove = true;
                            break;
                        }
                    }
                    if (anyValidMove) break;
                }
            }
            if (anyValidMove) break;
        }

        boolean kingInCheck = false;
        for (Piece p : pieceList) {
            if (p instanceof King && p.isWhite == isWhiteTurn) {
                kingInCheck = isSquareAttacked(p.col, p.row, !p.isWhite);
            }
        }

        if (!anyValidMove) {
            if (kingInCheck) {
                showGameOver(isWhiteTurn ? "Black Wins (Checkmate)" : "White Wins (Checkmate)");
            } else {
                showGameOver("Draw (Stalemate)");
            }
        }
    }
    public void showGameOver(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }


}

