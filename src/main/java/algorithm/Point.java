package algorithm;

/**
 * Created by chan8 on 2017-03-17.
 */
public class Point {
    private boolean canPut;
    private int direction;
    private int x;
    private int y;
    private int score;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isCanPut() {
        return canPut;
    }

    public void setCanPut(boolean canPut) {
        this.canPut = canPut;
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
