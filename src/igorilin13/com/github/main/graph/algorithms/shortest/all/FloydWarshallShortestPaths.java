package igorilin13.com.github.main.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.Graph;

import java.util.HashMap;
import java.util.Map;

public class FloydWarshallShortestPaths<T> extends BaseAllShortestPaths<T> {
    public FloydWarshallShortestPaths(Graph<T> graph) {
        super(graph);
    }

    @Override
    int[][] run() {
        int[][] adjMatrix = getAdjMatrix();
        int size = adjMatrix.length;
        Map<Integer, int[][]> paths = new HashMap<>();
        paths.put(0, adjMatrix);
        for (int k = 0; k < size; k++) {
            int[][] currentPath = paths.get(k);
            int[][] nextPath = currentPath.clone();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (currentPath[i][k] != POS_INF && currentPath[k][j] != POS_INF) {
                        nextPath[i][j] = Math.min(currentPath[i][j], currentPath[i][k] + currentPath[k][j]);
                    }
                }
            }
            paths.put(k + 1, nextPath);
        }
        return paths.get(size);
    }
}
