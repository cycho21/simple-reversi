package com.nexon.reversi;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by chanyeon on 2017-03-19.
 */
public class Utils {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final int WHITE = 1;
    private static final int BLACK = -1;
    private static final int BLANK_SPACE = 0;

    /**
     * 
     * @param board Current board
     * @return      Deepcopied board
     */
    public static int[][] deepCopyBoard(int[][] board) {
        int[][] copiedBoard = new int[HEIGHT][WIDTH];
        for (int j = 0; j < HEIGHT; ++j)
            for (int i = 0; i < WIDTH; ++i)
                copiedBoard[j][i] = board[j][i];
        return copiedBoard;
    }

    /**
     * 
     * @param board Game board to printed
     * @param bw    BufferedWriter
     * @throws IOException
     */
    public static void printBoard(int[][] board, BufferedWriter bw) throws IOException {
        bw.write("  ");
        for (int i = 0; i < WIDTH; ++i)
            bw.write((char) ('A' + i) + " ");
        bw.write("\n");
        for (int i = 0; i < HEIGHT; ++i) {
            bw.write((i + 1) + " ");
            for (int j = 0; j < WIDTH; ++j) {
                if (board[i][j] == BLACK)
                    bw.write((char) '\u25CF');
                if (board[i][j] == WHITE)
                    bw.write((char) '\u25cb');
                if (board[i][j] == BLANK_SPACE)
                    bw.write("  ");
            }
            bw.write("\n");
        }
        bw.write("\n");
        bw.flush();
    }

    /**
     * 
     * @param board Game board to printed
     * @throws IOException
     */
    public static void printBoardByStringBuilder(int[][] board) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%2s", " "));
        for (int i = 0; i < WIDTH; ++i)
            sb.append(String.format("%3s", (char) ('A' + i)));
        sb.append("\n");
        for (int i = 0; i < HEIGHT; ++i) {
            sb.append((i + 1) + " ");
            for (int j = 0; j < WIDTH; ++j) {
                if (board[i][j] == BLACK)
                    sb.append(String.format("%3s", (char) '\u25CF'));
                if (board[i][j] == WHITE)
                    sb.append(String.format("%3s", (char) '\u25CB'));
                if (board[i][j] == BLANK_SPACE)
                    sb.append(String.format("%3s", " "));
            }
            sb.append("\n");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }
}
