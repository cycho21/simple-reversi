package com.nexon.reversi.algorithm;

/**
 * Created by chan8 on 2017-03-17.
 */
public class Point {
    private int x;
    private int y;
    private int score;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("X : ").append(this.x).append(" Y : ").append(this.y).toString();
    }
}
