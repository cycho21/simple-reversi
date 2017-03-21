package com.nexon.reversi.algorithm;

import java.util.ArrayList;

/**
 * Created by chan8 on 2017-03-17.
 */
public class Node {
    private ArrayList<Node> children;
    private Point value;

    public Node() {
        this.children = new ArrayList<Node>();
    }

    public Node(Point value) {
        this.children = new ArrayList<Node>();
        this.value = value;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public Point getValue() {
        return value;
    }

    public void setValue(Point value) {
        this.value = value;
    }
}
