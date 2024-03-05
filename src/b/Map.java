package b;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    public static final int MODE_NVH = 0;
    public static final int MODE_NVA = 1;

    private static final int DOT_EMPTY = 0;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;
    private static final int DOT_PADDING = 5;

    private int stateGameOver;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;


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
    private boolean isGameOver;
    private boolean isInitialized;


    Map() {
        addMouseListener((MouseAdapter) mouseReleased(e) {
            super.mouseReleased(e);
            update(e);
        });
        isInitialized = false;
    }
    private void update(MouseEvent e) {
        if (isGameOver || isInitialized) return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!isValidCell(cellX,cellY) || !isEmptyCell(cellX,cellY))
                return;
        field[cellY][cellX] = DOT_HUMAN;
        if (checkEndGame(DOT_HUMAN, STATE_WIN_HUMAN)) return;
        aiTorn();
        repaint();
        if (checkEndGame(DOT_AI, STATE_WIN_AI)) return;
    }

    private boolean checkEndGame(int dot, int stateGameOver) {
        if (checkWin(dot)) {
            this.stateGameOver =stateGameOver;
            isGameOver = true;
            repaint();
            return  true;
        }
        if (isMapFull()) {
            this.stateGameOver = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;

    }

    void startNewGame(int gameMode, int fieldSizeX, int fieldSizeY,int winLength) {
        this.fieldSizeY = fieldSizeY;
        this.fieldSizeX = fieldSizeX;
        this.winLength = winLength;
        field = new int[fieldSizeY][fieldSizeX];
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (isInitialized) return;
        int width = getWidth();
        int height =getHeight();
        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;
        g.setColor(Color.BLACK);
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0,y,width,y);
        }
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x,0, x, height);
        }
        for ( int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isEmptyCell(x, y)) continue;
                if (field[y][x] ==DOT_HUMAN) {
                    g.setColor(new Color(1,1,255));
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }else if (field[y][x] == DOT_AI) {
                    g.setColor(Color.red);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING *2,
                            cellHeight - DOT_PADDING *2);
                }else  {
                    throw new RuntimeException(
                            String.format("Cant recognize cell field[%d][%d]: %d", y, x, field[y][x]));
                }
            }
        }
        if (isGameOver) {
            showMassageGameOver(g);
        }
    }
    private void showMassageGameOver(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,200,getWidth(),70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 40));
        switch (stateGameOver) {
            case STATE_DRAW:
                g.drawString(MSG_DRAW, 100, getHeight() / 2);
                break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI, 20, getHeight() / 2);
                break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN, 70 , getHeight() / 2);
                break;
            default:
                throw new RuntimeException("Unexpected gameOver state" + stateGameOver);
        }
    }
    //Ход ПК
    private void aiTorn()  {
        if (turnAIWinCell()) return; //проверим, не выйграет-ли игрок на следущей клетке
        if (turnHumanWinCell()) return; //проверим, не выйграет-ли пк на следущей клетке
        int x, y;
        do {                               // или пк ходит в случайную клетку
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        }while (!isEmptyCell(x, y));
        field[y][x] = DOT_AI;
    }

    //  Проверка, может ли выйграть ПК
    private boolean turnAIWinCell() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX ; j++) {
                if(isEmptyCell(j, i)) {
                    field[i][j] = DOT_AI;
                    if (checkWin(DOT_AI)) return true;
                    field[i][j] = DOT_EMPTY;
                }
            }
        }
        return  false;
    }

    // проверка, выйграет-ли игрок свом следующим ходом
    private boolean turnHumanWinCell() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = DOT_HUMAN;
                    if (checkWin(DOT_HUMAN)) {
                        field[i][j] = DOT_AI;
                        return true;
                    }
                    field[i][j] = DOT_EMPTY;
                }
            }
        }
        return false;
    }

    //Проверка на победу
    private  boolean checkWin(int c) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY;j++) {
                if (checkLine(i, j, 1, 0, winLength,c)) return  true; //Проверка линии по х
                if (checkLine(i, j, 1, 1, winLength,c)) return  true;//Проверка по диагонали х у
                if (checkLine(i, j, 0, 1, winLength,c)) return  true;//Проверка линии по у
                if (checkLine(i, j, 1, -1, winLength,c)) return  true;//Проверка по диагонали х -у
            }
        }
        return false;
    }

    //проверка линии
    private boolean checkLine(int x, int y, int vx, int vy, int len, int c) {
        final int far_x = x + (len - 1) * vx;
        final int far_y = y + (len - 1) * vy;
        if (!isValidCell(far_x, far_y))return false;
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != c)return false;
        }
        return true;
    }

    //ничья?
    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == DOT_EMPTY) return  false;
            }
        }
        return true;
    }

    //Ячейка то вообще правильная?
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    //А пустая?
    private boolean isEmptyCell(int x,int y) {
        return  field[y][x] == DOT_EMPTY;
    }
}
