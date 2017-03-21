package com.nexon.reversi;

import com.nexon.reversi.algorithm.Point;

/**
 * Created by chan8 on 2017-03-21.
 */
public class Answer {
    private Point maximizedPoint;
    private boolean canPlay;

    public Answer() {
    }

    public Point getMaximizedPoint() {
        return maximizedPoint;
    }

    public void setMaximizedPoint(Point maximizedPoint) {
        this.maximizedPoint = maximizedPoint;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }
}
