package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import Board.Board;

public class Piece {
    public int col, row;       
    public int xPos, yPos;       
    public boolean isWhite;      
    public String name;          
    public int value;
    public boolean hasMoved = false;

    public Board board;          
    public Image sprite;        

    protected BufferedImage sheet;
    protected int sheetScale = 64; 

   
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
                sheetScale = sheet.getWidth() / 6;
            } else {
                System.err.println("Piece.png not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading piece spritesheet: Piece.png");
        }
    }

   
    public BufferedImage getSprite(int sheetCol, int sheetRow) {
        return sheet.getSubimage(sheetCol * sheetScale, sheetRow * sheetScale, sheetScale, sheetScale);
    }

   
    public void draw(Graphics2D g2d) {
        if (sprite != null) {
            g2d.drawImage(sprite, xPos, yPos, board.titleSize, board.titleSize, null);
        }
    }

    
    public void paint(Graphics2D g2d) {
        if (sheet != null) {
            g2d.drawImage(sheet, xPos, yPos, sheetScale, sheetScale, null);
        }
    }
    public boolean canMoveTo(int newCol, int newRow) {
        return false;
    }

}

