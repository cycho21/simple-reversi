package com.nexon.reversi.algorithm;

import java.util.ArrayList;

/**
 * Created by chan8 on 2017-03-17.
 */
public class AlphaBetaPruner {
    private static final int MAX_VALUE = Integer.MAX_VALUE / 2;
    private static final int MIN_VALUE = Integer.MIN_VALUE / 2;

    /**
     * @param root Root node of minimax tree
     * @return Maximized point of tree
     */
    public Point getMaximizedPoint(Node root) {
        Pair result = getMaxByAlphaBetaPruning(root, MIN_VALUE, MAX_VALUE, true);

        return result.getPoint();
    }

    private Pair getMaxByAlphaBetaPruning(Node node, int alpha, int beta, boolean maximumPlayerTurn) {
        int bestValue = 0;
        Point bestChildren = null;

        ArrayList<Node> children = node.getChildren();
        if (children.size() == 0) {
            bestValue = node.getValue().getScore();
            bestChildren = node.getValue();
        } else if (maximumPlayerTurn == true) {
            for (int i = 0; i < children.size(); ++i) {
                int childrenValue = getMaxByAlphaBetaPruning(children.get(i), bestValue, beta, false).getValue();
                bestValue = Math.max(bestValue, childrenValue);
                if (bestValue <= childrenValue)
                    bestChildren = children.get(i).getValue();
                if (beta <= bestValue)
                    break;
            }
        } else {
            bestValue = beta;
            for (int i = 0; i < children.size(); ++i) {
                int childrenValue = getMaxByAlphaBetaPruning(children.get(i), alpha, bestValue, true).getValue();
                bestValue = Math.min(bestValue, childrenValue);
                if (bestValue >= childrenValue)
                    bestChildren = children.get(i).getValue();
                if (bestValue <= alpha)
                    break;
            }
        }
        return new Pair(bestChildren, bestValue);
    }

    class Pair {
        private Point point;
        private int value;

        public Pair(Point point, int value) {
            this.point = point;
            this.value = value;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}

