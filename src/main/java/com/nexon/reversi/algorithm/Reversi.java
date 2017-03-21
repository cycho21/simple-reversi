package com.nexon.reversi.algorithm;

import com.nexon.reversi.Answer;
import com.nexon.reversi.Utils;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by chan8 on 2017-03-17.
 */
public class Reversi {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final int BLANK_SPACE = 0;
    private static final int[] dX = {1, -1, 0, 0, 1, -1, 1, -1};
    private static final int[] dY = {0, 0, -1, 1, 1, 1, -1, -1};
    private int GOAL_DEPTH = 6;
    private int[][] regionScore;

    private AlphaBetaPruner alphaBetaPruner;
    private boolean hasCornerPosition;

    public Reversi() throws IOException {
    }

    /**
     * @param depth MinMax algorithm depth. Depth be deeper, return time longer
     */
    public void initialize(int depth) {
        this.GOAL_DEPTH = depth;
        this.alphaBetaPruner = new AlphaBetaPruner();
        this.hasCornerPosition = false;
        regionScore = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; ++i) {
            if (HEIGHT - i == 1 || HEIGHT - i == 8) {
                regionScore[i] = new int[]{10000, 5000, 2500, 2500, 2500, 2500, 2500, 2500, 5000, 10000}; // first, last line
                continue;
            }
            if (HEIGHT - i == 2 || HEIGHT - i == 7) {
                regionScore[i] = new int[]{5000, 5000, 1000, 1000, 1000, 1000, 5000, 5000}; // second, and second from last line
                continue;
            }
            regionScore[i] = new int[]{1000, 500, 100, 100, 100, 100, 500, 1000}; // other lines
        }
    }

    /**
     * @param turn  Black or white. -1 is Black 1 is White.
     * @param board Current state of board. Two dimensional array. Width 8, Height 8.
     *              Black space 0, Black -1, White 1
     * @return Answer that has Maximized point(Point) and playable turn or not(boolean)
     */
    public Answer isPossibleToPut(int turn, int[][] board) {
        PriorityQueue<Point> canPutPoints = nextQueue(board, turn);

        Answer answer = new Answer();

        if (canPutPoints.size() == 0)
            return answer;
        Node root = makeTree(canPutPoints, board, turn);
        Point maximizedPoint = alphaBetaPruner.getMaximizedPoint(root);

        answer.setCanPlay(true);
        answer.setMaximizedPoint(maximizedPoint);
        return answer;
    }

    private PriorityQueue<Point> nextQueue(int[][] board, int turn) {
        PriorityQueue<Point> canPutPoints = new PriorityQueue<Point>(new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return o1.getScore() < o2.getScore() ? -1 : 1;
            }
        });

        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                if (board[j][i] != BLANK_SPACE)
                    continue;
                if (canPut(i, j, board, turn) == true) {
                    Point point = new Point(i, j);
                    point.setScore(getTurnDisks(i, j, board, turn));
                    canPutPoints.add(point);
                }
            }
        }

        return canPutPoints;
    }

    /**
     * @param x     x-position
     * @param y     y-position
     * @param board Current state of board. Two dimensional array. Width 8, Height 8.
     *              Black space 0, Black -1, White 1
     * @param turn  Black or white. -1 is Black 1 is White.
     * @return can put disk this turn or not
     */
    public boolean canPut(int x, int y, int[][] board, int turn) {
        for (int i = 0; i < 8; ++i) {
            int nx = x + dX[i];
            int ny = y + dY[i];
            if (isInsideBoard(nx, ny) == true) {
                if (board[ny][nx] == (-1 * turn)) {
                    while (true) {
                        nx += dX[i];
                        ny += dY[i];
                        if (isInsideBoard(nx, ny) == true) {
                            if (board[ny][nx] == turn) {
                                return true;
                            } else if (board[ny][nx] == (-1 * turn)) {
                                continue;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isInsideBoard(int x, int y) {
        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT)
            return true;
        else
            return false;
    }

    private int getTurnDisks(int x, int y, int[][] board, int turn) {
        int score = 0;

        for (int i = 0; i < 4; ++i) {
            int nx = x + dX[i];
            int ny = y + dY[i];
            if (isInsideBoard(nx, ny)) {
                if (board[ny][nx] != turn) {        // Next disk is not same player's disk
                    int idx = 0;
                    int endX = nx;
                    int endY = ny;
                    boolean change = true;
                    while (true) {
                        if (isInsideBoard(endX, endY)) {
                            if (board[endY][endX] == turn) {
                                change = true;
                                break;
                            } else if (board[endY][endX] == BLANK_SPACE) {
                                change = false;
                                break;
                            }
                            endX += dX[i];
                            endY += dY[i];
                            idx++;
                        } else {
                            change = false;
                            break;
                        }
                    }
                    if (change == true) {
                        for (int j = 0; j < idx; ++j) {
                            score += regionScore[ny][nx];
                            nx += dX[i];
                            ny += dY[i];
                        }
                    }
                }
            }
        }
        return score;
    }

    private boolean isBoundary(int x, int y) {
        return ((x == 0 || y == 0 || x == WIDTH - 1 || y == HEIGHT - 1)) ? true : false;
    }

    /**
     * @param board Current state of board. Two dimensional array. Width 8, Height 8.
     *              Black space 0, Black -1, White 1
     * @param next  Post that will place on board
     * @param turn  Black or white. -1 is Black 1 is White.
     * @return Deepcopied and placed next disk board
     */
    public int[][] putDisks(int[][] board, Point next, int turn) {
        int x = next.getX();
        int y = next.getY();
        board[y][x] = turn;
        Queue<Point> putQueue = new LinkedList<Point>();
        for (int i = 0; i < 8; ++i) {
            int nx = x + dX[i];
            int ny = y + dY[i];
            if (isInsideBoard(nx, ny)) {
                if (board[ny][nx] != turn) {        // Next disk is not same player's disk
                    int idx = 0;
                    int endX = nx;
                    int endY = ny;
                    boolean change = true;
                    while (true) {
                        if (isInsideBoard(endX, endY)) {
                            if (board[endY][endX] == turn) {
                                change = true;
                                break;
                            } else if (board[endY][endX] == BLANK_SPACE) {
                                change = false;
                                break;
                            }
                            endX += dX[i];
                            endY += dY[i];
                            idx++;
                        } else {
                            change = false;
                            break;
                        }
                    }

                    if (change == true) {
                        for (int j = 0; j < idx; ++j) {
                            putQueue.add(new Point(nx, ny));
                            nx += dX[i];
                            ny += dY[i];
                        }
                    }
                }
            }
        }

        while (!putQueue.isEmpty()) {
            Point head = putQueue.poll();
            board[head.getY()][head.getX()] = turn;
        }
        return board;
    }

    private Node makeTree(Queue<Point> queue, int[][] board, int turn) {
        Node rootNode = new Node();
        while (!queue.isEmpty())
            rootNode.getChildren().add(new Node(queue.poll()));
        recursiveAddChildren(rootNode, 0, board, turn);
        return rootNode;
    }

    private boolean isCorner(int x, int y) {
        return ((x == WIDTH && y == HEIGHT - 1) || (x == 0 && y == HEIGHT - 1) ||
                (x == WIDTH && y == 0) || (x == 0 && y == 0)) ? true : false;
    }

    private void recursiveAddChildren(Node parent, int depth, int[][] board, int turn) {
        if (depth >= GOAL_DEPTH)
            return;

        for (int i = 0; i < parent.getChildren().size(); ++i) {
            Node children = parent.getChildren().get(i);
            Point next = children.getValue();
            int[][] copiedBoard = Utils.deepCopyBoard(board);

            putDisks(copiedBoard, next, turn);      // Put disk and get changed board
            Queue<Point> queue = nextQueue(copiedBoard, turn * -1);     // Which point can opponent put in changed board

            while (!queue.isEmpty())
                children.getChildren().add(new Node(queue.poll()));

            recursiveAddChildren(children, depth + 1, copiedBoard, turn * -1);
        }
    }
}

