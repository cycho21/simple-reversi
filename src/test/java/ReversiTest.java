import algorithm.Point;
import algorithm.Reversi;
import algorithm.Utils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by chan8 on 2017-03-17.
 */
public class ReversiTest {
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final int WHITE = 1;
    private static final int BLACK = -1;
    private static final int[] dX = {1, -1, 0, 0};
    private static final int[] dY = {0, 0, -1, 1};
    private int white;
    private int black;

    @Test
    public void testIsPossibleToPut() {
        Reversi reversi = null;
        try {
            reversi = new Reversi();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] testData = new int[8][8];
        testData[3][3] = WHITE;
        testData[3][4] = BLACK;
        testData[4][3] = BLACK;
        testData[4][4] = WHITE;

//      for (int i = 0; i < WIDTH; ++i)
//            for (int j = 0; j < HEIGHT; ++j)
//                testData[j][i] = WHITE;
//        testData[3][0] = BLACK;
//        testData[3][3] = BLANK_SPACE;


        int turn = BLACK;

        reversi.initialize(6);
        int passCount = 0;
        white = 0;
        black = 0;
        while (true) {
            long startTime = System.currentTimeMillis();
            Point maximizedPoint = reversi.isPossibleToPut(turn, testData);
            long endTime = System.currentTimeMillis();
            if (maximizedPoint == null) {
                if (countPoint(testData) == 64) {
                    if (white < black)
                        System.out.println("BLACK WIN :: WHITE : " + white + " : BLACK : " + black);
                    if (black < white)
                        System.out.println("WHITE WIN :: WHITE : " + white + " : BLACK : " + black);
                    if (black == white)
                        System.out.println("DRAW!");
                    break;
                }

                if (passCount == 1) {
                    System.out.println("GAME IS END!");
                    break;
                } else {
                    System.out.println("TURN PASSED..");
                    passCount++;
                }
            }
            System.out.println(maximizedPoint.toString());
            testData = reversi.putDisks(testData, maximizedPoint, turn);
            passCount = 0;
            System.out.println((double) (endTime - startTime) / 1000 + " secs");

//            try {
//                Utils.printBoard(testData, bw);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            
            turn *= -1;
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
