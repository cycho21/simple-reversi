import algorithm.Point;
import algorithm.Reversi;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by chan8 on 2017-03-17.
 */
public class ReversiTest {
    private static final int GOAL_DEPTH = 6;
    private static final int MIN_VALUE = Integer.MIN_VALUE / 2;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final int WHITE = 1;
    private static final int BLACK = -1;
    private static final int BLANK_SPACE = 0;
    private static final int[] dX = {1, -1, 0, 0};
    private static final int[] dY = {0, 0, -1, 1};

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

        reversi.initialize();
        Point maximizedPoint = reversi.isPossibleToPut(BLACK, testData);
        System.out.println(maximizedPoint.toString());
    }
}
