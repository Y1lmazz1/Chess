package Main;

import Board.Board;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Chess");
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Board board = new Board();

   
        input mouseInput = new input(board);
        board.addMouseListener(mouseInput);
        board.addMouseMotionListener(mouseInput);

        frame.add(board);

        frame.setVisible(true);
    }
}
