package igorilin13.com.github.main.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.Graph;

import java.util.HashMap;
import java.util.Map;

public class MatrixShortestPaths<T> extends BaseAllShortestPaths<T> {
    public MatrixShortestPaths(Graph<T> graph) {
        super(graph);
    }

    @Override
    int[][] run() {
        int[][] adjMatrix = getAdjMatrix();
        int size = adjMatrix.length;
        Map<Integer, int[][]> paths = new HashMap<>();
        int currentPathLength = 1;
        paths.put(currentPathLength, adjMatrix);
        while (currentPathLength < size - 1) {
            int[][] currentPath = paths.get(currentPathLength);
            paths.put(2 * currentPathLength, extendShortestPaths(currentPath, currentPath));
            currentPathLength *= 2;
        }
        return paths.get(currentPathLength);
    }

    private int[][] extendShortestPaths(int[][] pathsMatrix, int[][] adjMatrix) {
        int size = pathsMatrix.length;
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = POS_INF;
                for (int k = 0; k < size; k++) {
                    if (pathsMatrix[i][k] != POS_INF && adjMatrix[k][j] != POS_INF) {
                        result[i][j] = Math.min(result[i][j], pathsMatrix[i][k] + adjMatrix[k][j]);
                    }
                }
            }
        }
        return result;
    }

}
