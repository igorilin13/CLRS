package igorilin13.com.github.main.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.Graph;

import java.util.HashMap;
import java.util.Map;

public class TransitiveClosure<T> extends BaseAllShortestPaths<T> {
    public TransitiveClosure(Graph<T> graph) {
        super(graph);
    }

    @Override
    int[][] run() {
        int[][] adjMatrix = graph.toMatrix();
        int size = graph.size();

        int[][] initial = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j || adjMatrix[i][j] != 0) {
                    initial[i][j] = 1;
                }
            }
        }
        Map<Integer, int[][]> result = new HashMap<>();
        result.put(0, initial);

        for (int k = 1; k <= size; k++) {
            int[][] next = new int[size][size];
            int[][] current = result.get(k - 1);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    next[i][j] = current[i][j] | (current[i][k - 1] & current[k - 1][j]);
                }
            }
            result.put(k, next);
        }

        return result.get(size);
    }
}
