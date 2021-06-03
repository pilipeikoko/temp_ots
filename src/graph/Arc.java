package graph;

import figures.Point;

public class Arc {

    public Point sourcePoint;
    public Point targetPoint;
    public boolean isDirected;
    public int weight = 1;

    public Arc(Point sourcePoint, Point targetPoint, boolean isDirected) {
        this.isDirected = isDirected;
        this.sourcePoint = sourcePoint;
        this.targetPoint = targetPoint;
    }

    public Arc(Point sourcePoint, Point targetPoint, boolean isDirected, int weight) {
        this.isDirected = isDirected;
        this.sourcePoint = sourcePoint;
        this.targetPoint = targetPoint;
        this.weight = weight;
    }
}
