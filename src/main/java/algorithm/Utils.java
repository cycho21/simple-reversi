package algorithm;

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

    public static int[][] deepCopyBoard(int[][] board) {
        int[][] copiedBoard = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; ++i)
            for (int j = 0; j < WIDTH; ++j)
                copiedBoard[j][i] = board[j][i];
        return copiedBoard;
    }

    public static void printBoard(int[][] board, BufferedWriter bw) throws IOException {
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                if (board[i][j] == BLACK)
                    bw.write("B ");
                if (board[i][j] == WHITE)
                    bw.write("W ");
                if (board[i][j] == BLANK_SPACE)
                    bw.write(". ");
            }
            bw.write("\n");
        }
        bw.write("\n");
        bw.flush();
    }
}
