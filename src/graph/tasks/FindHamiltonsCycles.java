package graph.tasks;

import graph.Graph;

import java.util.ArrayList;

public class FindHamiltonsCycles implements GraphTask {

    private final int[][] matrix;

    public final ArrayList<ArrayList<Integer>> hamiltonsCycles = new ArrayList<>();

    public FindHamiltonsCycles(Graph graph) {

        matrix = graph.getMatrix();
    }

    @Override
    public void solveTask() {
        ArrayList<Integer> visited = new ArrayList<>();

        for (int i = 0; i < matrix.length; ++i) {
            visited.add(i);
            depthSearch(visited, i, i);
            visited.clear();
        }

    }

    private void depthSearch(ArrayList<Integer> visited, int currentVertex, int sourceVertex) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[currentVertex][i] != 0 && !contains(visited, i)) {
                visited.add(i);
                depthSearch(visited, i, sourceVertex);
                visited.remove(visited.size() - 1);
            } else if (matrix[currentVertex][i] != 0
                    && i == sourceVertex && visited.size() == matrix.length) {

                ArrayList<Integer> tempArrayList = new ArrayList<>(matrix.length);
                tempArrayList.addAll(visited);
                hamiltonsCycles.add(tempArrayList);
                break;
            }
        }
    }


    private boolean contains(ArrayList<Integer> visited, int currentVertex) {
        for (int j : visited) {
            if (j == currentVertex) {
                return true;
            }
        }
        return false;
    }
}
