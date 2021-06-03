package graph;

import figures.Point;
import graph.tasks.FindHamiltonsCycles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    public List<Vertex> setOfVertexes = new ArrayList<>();
    public List<Arc> setOfArcs = new ArrayList<>();

    public Graph() {

    }

    public void addVertex(Point point,int type) {
        setOfVertexes.add(new Vertex(point,type));
    }

    public void addVertex(Point point, String identifier) {
        setOfVertexes.add(new Vertex(point, identifier));
    }

    public void removeVertex(Point point) {
        Vertex vertex;
        for (int i = 0; i < setOfVertexes.size(); ++i) {
            vertex = setOfVertexes.get(i);
            if (vertex.point.equals(point)) {
                setOfVertexes.remove(vertex);
                removeAllIncidentalArcs(vertex.point);
                break;
            }
        }

    }

    private void removeAllIncidentalArcs(Point point) {
        for (int i = 0; i < setOfArcs.size(); ++i) {
            Arc currentArc = setOfArcs.get(i);
            if (currentArc.targetPoint != null && currentArc.sourcePoint != null
                    && (currentArc.sourcePoint.equals(point) || currentArc.targetPoint.equals(point))) {
                setOfArcs.remove(currentArc);
                --i;
            }
        }
    }


    public void addArc(Arc arc) {
        setOfArcs.add(arc);
    }

    public void removeArc(Point sourcePoint, Point targetPoint) {
        for (int i = 0; i < setOfArcs.size(); ++i) {
            if (setOfArcs.get(i).sourcePoint.equals(sourcePoint)
                    && setOfArcs.get(i).targetPoint.equals(targetPoint)) {
                setOfArcs.remove(i);
                break;
            }
        }
    }

    public int[][] getMatrix() {
        int[][] matrix;
        matrix = new int[setOfVertexes.size()][setOfVertexes.size()];

        for (int[] row : matrix) {
            Arrays.fill(row, 0);
        }

        for (Arc arc : setOfArcs) {
            int sourceIndex = findIndexOfVertex(arc.sourcePoint);
            int targetIndex = findIndexOfVertex(arc.targetPoint);

            boolean isDirected = arc.isDirected;

            matrix[sourceIndex][targetIndex] = 1;
            if (!isDirected) {
                matrix[targetIndex][sourceIndex] = 1;
            }
        }
        return matrix;
    }

    private int findIndexOfVertex(Point point) {
        for (int i = 0; i < setOfVertexes.size(); ++i) {
            if (setOfVertexes.get(i).point == point)
                return i;
        }
        return -1;
    }

    public ArrayList<ArrayList<Integer>> findAllHamiltonsCycles() {
        FindHamiltonsCycles findHamiltonsCycles = new FindHamiltonsCycles(this);
        findHamiltonsCycles.solveTask();
        return findHamiltonsCycles.hamiltonsCycles;
    }


}
