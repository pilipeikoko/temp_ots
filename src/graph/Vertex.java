package graph;

import figures.Point;

public class Vertex {
    public String identifier;
    public Point point;
    public int type;

    Vertex(Point point,int type) {
        this.point = point;
        this.identifier = "";
        this.type = type;
    }

    Vertex(Point point, String identifier) {
        this.point = point;
        this.identifier = identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


}
