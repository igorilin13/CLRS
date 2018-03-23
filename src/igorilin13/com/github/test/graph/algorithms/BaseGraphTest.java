package igorilin13.com.github.test.graph.algorithms;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.util.CallSuper;
import org.junit.Before;

public abstract class BaseGraphTest {
    protected Graph<Integer> graph;

    @Before
    @CallSuper
    public void setUp() {
        graph = Graph.GraphBuilder.fromMatrix(getAdjMatrix());
    }

    protected abstract int[][] getAdjMatrix();
}
