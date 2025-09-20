package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import Board.Board;

public class Piece {
    public int col, row;         // Mantıksal konum (tahta üzerindeki sütun/satır)
    public int xPos, yPos;       // Grafiksel konum (piksel cinsinden)
    public boolean isWhite;      // Taşın rengi
    public String name;          // Taşın adı (örn. "Rook")
    public int value;
    public boolean hasMoved = false; // Taşın değeri

    public Board board;          // Sahip olduğu tahta
    public Image sprite;         // Çizilecek görüntü

    protected BufferedImage sheet;
    protected int sheetScale = 64; // Sprite sheet içindeki her taşın boyutu (varsayılan)

    // ✔️ Genişletilmiş kurucu metod
    public Piece(Board board, int col, int row, boolean isWhite) {
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * board.titleSize;
        this.yPos = row * board.titleSize;
        this.isWhite = isWhite;

        try {
            sheet = ImageIO.read(getClass().getResourceAsStream("/Models/pieces.png"));
            if (sheet != null) {
                sheetScale = sheet.getWidth() / 6; // 6 sütun: Piyon, Kale, At, Fil, Vezir, Şah
            } else {
                System.err.println("Piece.png not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading piece spritesheet: Piece.png");
        }
    }

    // ✔️ Sprite sheet'ten taşın görselini al
    public BufferedImage getSprite(int sheetCol, int sheetRow) {
        return sheet.getSubimage(sheetCol * sheetScale, sheetRow * sheetScale, sheetScale, sheetScale);
    }

    // ✔️ Taşı çiz (ekran pozisyonuna göre)
    public void draw(Graphics2D g2d) {
        if (sprite != null) {
            g2d.drawImage(sprite, xPos, yPos, board.titleSize, board.titleSize, null);
        }
    }

    // ❌ Gerekli değil ama yedek: tüm sheet'i çizer (debug için)
    public void paint(Graphics2D g2d) {
        if (sheet != null) {
            g2d.drawImage(sheet, xPos, yPos, sheetScale, sheetScale, null);
        }
    }
    public boolean canMoveTo(int newCol, int newRow) {
        return false; // Genel taş için hareket kuralı yok
    }

}
