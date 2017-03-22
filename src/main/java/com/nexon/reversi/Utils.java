package com.nexon.reversi;

import com.nexon.reversi.conf.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chanyeon on 2017-03-19.
 */
public class Utils {
    private static int WIDTH;
    private static int HEIGHT;
    private static int WHITE;
    private static int BLACK;
    private static int BLANK_SPACE;

    static {
        WIDTH = Configuration.getConfiguration().getWIDTH();
        HEIGHT = Configuration.getConfiguration().getHEIGHT();
        WHITE = Configuration.WHITE;
        BLACK = Configuration.BLACK;
        BLANK_SPACE = Configuration.BLANK_SPACE;
    }

    /**
     * @param board Current board
     * @return Deep copied board
     */
    public static int[][] deepCopyBoard(int[][] board) {
        int[][] copiedBoard = new int[HEIGHT][WIDTH];
        for (int j = 0; j < HEIGHT; ++j)
            for (int i = 0; i < WIDTH; ++i)
                copiedBoard[j][i] = board[j][i];
        return copiedBoard;
    }

    /**
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
     * @param board Game board to printed
     * @throws IOException
     */
    public static void printBoardByStringBuilder(int[][] board) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%1s", "  "));
        for (int i = 0; i < WIDTH; ++i)
            sb.append(String.format("%2s", (char) ('A' + i)));
        sb.append("\n");
        for (int i = 0; i < HEIGHT; ++i) {
            sb.append(String.format("%2s", (i + 1) + " "));
            for (int j = 0; j < WIDTH; ++j) {
                if (board[i][j] == BLACK)
                    sb.append(String.format("%1s", (char) '\u25CF'));
                if (board[i][j] == WHITE)
                    sb.append(String.format("%1s", (char) '\u25CB'));
                if (board[i][j] == BLANK_SPACE)
                    sb.append(String.format("%2s", " "));
            }
            sb.append("\n");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    /**
     * @param filePath relative path of config.json
     * @return Parsed config.json
     */
    public static JSONObject parseConfiguration(String filePath) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/config.json"));
        } catch (IOException e) {
            System.out.println("Check config.json file in right place");
            return null;
        } catch (ParseException e) {
            System.out.println("Parse exception from parsing config.json");
            return null;
        }
        return jsonObject;
    }
}
