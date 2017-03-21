package com.nexon.reversi;

import com.nexon.reversi.algorithm.Point;
import com.nexon.reversi.algorithm.Reversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by chan8 on 2017-03-17.
 */
public class CliHelper {
    private static final int BLANK_SPACE = 0;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final int WHITE = 1;
    private static final int BLACK = -1;
    private int white;
    private int black;

    /**
     * 
     * @param args -d depth of minimax algorithm
     */
    public static void main(String... args) {
        int depth = 4;
        
        if (args.length >= 2) {
            int idx = 0;

            switch (args[idx]) {
                case "-d":
                    depth = Integer.parseInt(args[idx++], 10);
                    break;
                default:
                    break;
            }
            idx += 2;
        }

        CliHelper helper = new CliHelper();
        helper.startGame(depth);
    }

    private void startGame(int depth) {
        Reversi ai = null;
        try {
            ai = new Reversi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[][] board = new int[8][8];
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;

        int turn = BLACK;
        int passCount = 0;
        ai.initialize(depth);

        while (true) {

            try {
                Utils.printBoardByStringBuilder(board);     // Print board's current situation
            } catch (IOException e) {
                e.printStackTrace();
            }

            String line = "";
            if (turn == BLACK) {
                System.out.println("Player's turn");
                Answer answer = ai.isPossibleToPut(turn, board);
                if (answer.isCanPlay() == false) {
                    System.out.println("Passed!");
                    passCount++;
                    turn *= -1;
                    continue;
                } else {
                    passCount = 0;
                }

                try {
                    line = br.readLine().toUpperCase();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (line.length() < 2)
                    continue;

                int x = line.charAt(0) - 'A';
                int y = line.charAt(1) - '0' - 1;

                if (ai.canPut(x, y, board, turn) == false || board[y][x] != BLANK_SPACE) {
                    System.out.println("You can't put disk there");
                    continue;
                }

                if (x < 0 || x > 9 || y < 0 || y > 9) {
                    System.out.println("Invalid position");
                    continue;
                }

                Point point = new Point(x, y);
                ai.putDisks(board, point, turn);

                turn *= -1;
            } else {
                System.out.println("AI's turn");
                Answer answer = ai.isPossibleToPut(turn, board);

                if (answer.isCanPlay() == false) {
                    if (countPoint(board) == 64) {
                        if (white < black)
                            System.out.println("BLACK WIN :: WHITE : " + white + " : BLACK : " + black);
                        if (black < white)
                            System.out.println("WHITE WIN :: WHITE : " + white + " : BLACK : " + black);
                        if (black == white)
                            System.out.println("DRAW!");
                        break;
                    } else {
                        white = 0;
                        black = 0;
                    }

                    if (passCount == 1) {
                        System.out.println("GAME IS END!");
                        break;
                    } else {
                        System.out.println("TURN PASSED..");
                        passCount++;
                    }
                } else {
                    Point maximizedPoint = answer.getMaximizedPoint();
                    board = ai.putDisks(board, maximizedPoint, turn);
                    passCount = 0;
                }
                turn *= -1;
            }
        }
    }

    private int countPoint(int[][] board) {
        int total = 0;
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                if (board[j][i] == WHITE) {
                    white++;
                    total++;
                    continue;
                }
                if (board[j][i] == BLACK) {
                    black++;
                    total++;
                }
            }
        }
        return total;
    }
}
