package b;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    public static final int MODE_NVH = 0;
    public static final int MODE_NVA = 1;

    private static final int DOT_EMPTY = '0';
    private static final int DOT_HUMAN = '1';
    private static final int DOT_AI = '2';

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW ="Ничья!";

    private static final Random RANDOM = new Random();

    private int [][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int cellWidth;
    private int cellHeight;


    Map() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int cellX = e.getX() / cellWidth;
                int cellY = e.getY() / cellHeight;
                System.out.println("x=" + cellX + "y=" +cellY);
            }
        });
    }

    void startNewGame(int gameMode, int fieldSizeX, int fieldSizeY,int winLength) {
        this.fieldSizeY = fieldSizeY;
        this.fieldSizeX = fieldSizeX;
        this.winLength = winLength;
        field = new int[fieldSizeY][fieldSizeX];
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int heigth =getHeight();
        cellWidth = width / fieldSizeX;
        cellHeight = heigth / fieldSizeY;
        g.setColor(Color.BLACK);
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0,y,width,y);
        }
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x,0, x, heigth);

        }
    }
}
