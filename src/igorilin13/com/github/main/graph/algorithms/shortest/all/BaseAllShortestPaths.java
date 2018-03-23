package igorilin13.com.github.main.graph.algorithms.shortest.all;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;
import igorilin13.com.github.main.util.ArrayUtils;

public abstract class BaseAllShortestPaths<T> extends BaseGraphAlgorithm<T> {
    private final int[][] result;

    BaseAllShortestPaths(Graph<T> graph) {
        super(graph, false);
        result = run();
    }

    abstract int[][] run();

    int[][] getAdjMatrix() {
        return setNoPathToInfinity(graph.toMatrix());
    }

    private int[][] setNoPathToInfinity(int[][] adjMatrix) {
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                if (adjMatrix[i][j] == 0 && i != j) {
                    adjMatrix[i][j] = POS_INF;
                }
            }
        }
        return adjMatrix;
    }

    public int[][] getResult() {
        return ArrayUtils.copy(result);
    }
}
