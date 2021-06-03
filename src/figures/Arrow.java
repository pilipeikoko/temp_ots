package figures;

public class Arrow {

    public Point sourcePoint;
    public Point targetPoint;
    public boolean isDirected;

    public Arrow(Point sourcePoint, Point targetPoint, boolean isDirected) {
        this.sourcePoint = sourcePoint;
        this.targetPoint = targetPoint;
        this.isDirected = isDirected;
    }
}
