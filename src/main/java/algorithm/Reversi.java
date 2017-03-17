package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by chan8 on 2017-03-17.
 */
public class Reversi {
    BufferedWriter bw;      // for Test
    private static final int GOAL_DEPTH = 13;
    private static final int MIN_VALUE = Integer.MIN_VALUE / 2;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final int WHITE = 1;
    private static final int BLACK = -1;
    private static final int BLANK_SPACE = 0;
    private static final int[] dX = {1, -1, 0, 0};
    private static final int[] dY = {0, 0, -1, 1};

    private AlphaBetaPruner alphaBetaPruner;
    private boolean print;

    public Reversi() throws IOException {
    }

    public void initialize() {
        this.alphaBetaPruner = new AlphaBetaPruner();
        this.print = false;
    }

    public void initialize(BufferedWriter bw) {
        this.alphaBetaPruner = new AlphaBetaPruner();
        this.bw = bw;
        this.print = true;
    }

    /*
   (0, 0) -> x x x x x x x x
             x x x x x x x x
             x x x x x x x x
             x x x x x x x x
             x x x x x x x x
             x x x x x x x x
             x x x x x x x x
   (7, 0) -> x x x x x x x x <- (7, 7)
     */
    public Point isPossibleToPut(int turn, int[][] board) {
        Queue<Point> canPutPoints = nextQueue(board, turn);
        Node root = makeTree(canPutPoints, board, turn);

        Point maximizedPoint = alphaBetaPruner.getMaximizedPoint(root);

//        Node node = root;
//        int depth = 1;
//        while (true) {
//            if (node.getChildren().size() == 0)
//                break;
//            node = node.getChildren().get(0);
//            System.out.println(depth);
//            depth++;
//        }

        return maximizedPoint;
    }

    private void printBoard(int[][] board) throws IOException {
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

    private Queue<Point> nextQueue(int[][] board, int turn) {
        Queue<Point> canPutPoints = new LinkedList<Point>();

        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                if (board[i][j] != BLANK_SPACE)
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

    private boolean canPut(int x, int y, int[][] board, int turn) {
        for (int i = 0; i < 4; ++i) {
            int nx = x + dX[i];
            int ny = y + dY[i];
            if (isInsideBoard(nx, ny) == true) {
                if (board[ny][nx] == (-1 * turn)) {
                    while (true) {
                        nx += dX[i];
                        ny += dY[i];
                        if (isInsideBoard(nx, ny) == true) {
                            if (board[ny][nx] == turn)
                                return true;
                        } else {
                            return false;
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
                            break;
                        }
                    }
                    if (change == true) {
                        for (int j = 0; j < idx; ++j) {
                            score++;
                            nx += dX[i];
                            ny += dY[i];
                        }
                    }
                }
            }
        }
        return score;
    }

    private int[][] putDisks(int[][] board, Point next, int turn) {
        int score = 0;
        int x = next.getX();
        int y = next.getY();
        board[y][x] = turn;

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
                            break;
                        }
                    }

                    if (change == true) {
                        for (int j = 0; j < idx; ++j) {
                            board[ny][nx] = turn;
                            nx += dX[i];
                            ny += dY[i];
                        }
                    }
                }
            }
        }
        return board;
    }

    private int[][] deepCopyBoard(int[][] board) {
        int[][] copiedBoard = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; ++i)
            for (int j = 0; j < WIDTH; ++j)
                copiedBoard[j][i] = board[j][i];
        return copiedBoard;
    }

    private Node makeTree(Queue<Point> queue, int[][] board, int turn) {
        Node rootNode = new Node();
        while (!queue.isEmpty())
            rootNode.getChildren().add(new Node(queue.poll()));

        recursiveAddChildren(rootNode, 0, board, turn);
        return rootNode;
    }

    private void recursiveAddChildren(Node parent, int depth, int[][] board, int turn) {
        if (depth >= GOAL_DEPTH)
            return;

        if (print == true) {
            try {
                printBoard(board);
                if (parent.getValue() != null)
                    bw.write("\n ============ " + parent.getValue().getScore() + " ============ \n");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < parent.getChildren().size(); ++i) {
            Node children = parent.getChildren().get(i);
            Point next = children.getValue();
            int[][] copiedBoard = deepCopyBoard(board);

            putDisks(copiedBoard, next, turn);      // Put disk and get changed board
            Queue<Point> queue = nextQueue(copiedBoard, turn * -1);     // Which point can opponent put in changed board

            while (!queue.isEmpty())
                children.getChildren().add(new Node(queue.poll()));

            recursiveAddChildren(children, depth + 1, copiedBoard, turn * -1);
        }
    }
}

