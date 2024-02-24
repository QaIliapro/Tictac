import java.util.Random;
import java.util.Scanner;

public class Tictak {
    private static final char DOT_EMPTY = ' ';
    private static final char DOT_HUMAN = 'x';
    private static final char DOT_AI = '0';
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    //inits field
    private static void initField() {
        fieldSizeX = 3;
        fieldSizeY = 3;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0 ; y < fieldSizeY; y++){
            for (int x = 0 ; x < fieldSizeX; x++){
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    //print field
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0)? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0;i < fieldSizeY; i++){
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();}
    //стандартный код без полей для printField.
/*    for (int y = 0; y < fieldSizeY; y++) {
            System.out.print("|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x]+ "|");
            }
            System.out.println();
        }
    }
     */

    //human turn
    private static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода X и Y(от 1 до 3) через пробел >>>");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;

        }while (!isValidCell(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    //check valid cell
    private static boolean isValidCell(int x,int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    //check empty cell
    private static boolean isCellEmpty(int x,int y){
        return field [y][x] == DOT_EMPTY;
    }

    //ai turn
    private static void aiTurn(){
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        }while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    //check draw
    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY)
                    return false;
            }
        }
        return true;
    }
    //check win
    private static boolean checkWin(char c) {
        //dia
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;
        //rar
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        //hor
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        return false;
    }


    public static void main(String[] args) {
        initField();
        printField();
        while (true) {
            humanTurn();
            printField();
            if(gameChecks(DOT_HUMAN,"HUMAN WIN!"))
                break;

            aiTurn();
            printField();
            if(gameChecks(DOT_AI,"COMPUTER WIN!"))
                break;
        }
    }
    private static boolean gameChecks(char dot, String msg){
        if(checkWin(dot)) {
            System.out.println(msg);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Draw");
            return true;
        }
        return false;
    }//[[[[[
}