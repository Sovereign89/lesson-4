package org.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Homework {

    private static final int GAME_MAP_SIZE = 4;
    private static final int DOTS_TO_WIN = 4;
    private static final char DOT_EMPTY = '.';
    private static final char DOT_X = 'x';
    private static final char DOT_O = '0';
    private static char[][] gameMap;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил ИИ");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра окончена");
        SCANNER.close();
    }

    private static boolean checkWin(char symbol) {
        int row;
        int column;
        int diagonalLR = 0;
        int diagonalRL = 0;
        int diagonalReverseLR = 0;
        int diagonalReverseRL = 0;
        int k;

        for (int i = 0; i < GAME_MAP_SIZE; i++) {
            row = 0;
            column = 0;
            for (int j = 0; j < GAME_MAP_SIZE; j++) {
                if (gameMap[i][j] == symbol) {
                    row++;
                } else {
                    row = 0;
                }
                if (gameMap[j][i] == symbol) {
                    column++;
                } else {
                    column = 0;
                }
                if (row >= DOTS_TO_WIN || column >= DOTS_TO_WIN) {
                    return true;
                }
            }
            diagonalLR = 0;
            diagonalRL = 0;
            diagonalReverseLR = 0;
            diagonalReverseRL = 0;
            k  = 0;
            for (int j = i; j < GAME_MAP_SIZE; j++) {
                if (gameMap[k][j] == symbol) {
                    diagonalLR++;
                } else {
                    diagonalLR = 0;
                }
                if (gameMap[k][GAME_MAP_SIZE - j - 1] == symbol) {
                    diagonalRL++;
                } else {
                    diagonalRL = 0;
                }
                if (gameMap[j][k] == symbol) {
                    diagonalReverseLR++;
                } else {
                    diagonalReverseLR = 0;
                }
                if (gameMap[j][GAME_MAP_SIZE - k - 1] == symbol) {
                    diagonalReverseRL++;
                } else {
                    diagonalReverseRL = 0;
                }
                if (diagonalLR >= DOTS_TO_WIN || diagonalRL >= DOTS_TO_WIN || diagonalReverseLR >= DOTS_TO_WIN || diagonalReverseRL >= DOTS_TO_WIN) {
                    return true;
                }
                k++;
            }
        }
        return false;
    }

    private static void placeAISymbol(int x, int y) {
        if (!isCellValid(x, y)) {
            do {
                x = RANDOM.nextInt(GAME_MAP_SIZE);
                y = RANDOM.nextInt(GAME_MAP_SIZE);
            } while (!isCellValid(x, y));
        }
        gameMap[y][x] = DOT_O;
        System.out.println("Компьютер сходил в точку" + (x + 1) + " " + (y + 1));
    }

    private static void aiTurn() {
        int x = RANDOM.nextInt(GAME_MAP_SIZE);
        int y = RANDOM.nextInt(GAME_MAP_SIZE);

        for (int i = 0; i < GAME_MAP_SIZE; i++)
        {
            for (int j = 0; j < GAME_MAP_SIZE; j++)
            {
                if (isCellValid(j, i))
                {
                    gameMap[i][j] = DOT_O;
                    if (checkWin(DOT_O))
                    {
                        x = j;
                        y = i;
                        gameMap[i][j] = DOT_EMPTY;
                        placeAISymbol(x,y);
                        return;
                    }
                    gameMap[i][j] = DOT_X;
                    if (checkWin(DOT_X))
                    {
                        x = j;
                        y = i;
                        gameMap[i][j] = DOT_EMPTY;
                        placeAISymbol(x,y);
                        return;
                    }
                    gameMap[i][j] = DOT_EMPTY;
                }
            }
        }

        placeAISymbol(x,y);
    }

    private static boolean isMapFull() {
        for (int i = 0; i < GAME_MAP_SIZE; i++) {
            for (int j = 0; j < GAME_MAP_SIZE; j++) {
                if (gameMap[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координату X:");
            x = SCANNER.nextInt() - 1;
            System.out.print("Введите координату Y:");
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y));
        gameMap[y][x] = DOT_X;
    }

    private static boolean isCellValid(int x, int y) {
        try {
            return gameMap[y][x] == DOT_EMPTY;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private static void initMap() {
        gameMap = new char[GAME_MAP_SIZE][GAME_MAP_SIZE];
        for (int i = 0; i < GAME_MAP_SIZE; i++) {
            for (int j = 0; j < GAME_MAP_SIZE; j++) {
                gameMap[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i <= GAME_MAP_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < GAME_MAP_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < GAME_MAP_SIZE; j++) {
                System.out.print(gameMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}