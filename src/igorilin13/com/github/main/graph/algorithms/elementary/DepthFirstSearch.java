package igorilin13.com.github.main.graph.algorithms.elementary;

import igorilin13.com.github.main.graph.Graph;
import igorilin13.com.github.main.graph.algorithms.BaseGraphAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DepthFirstSearch<T> extends BaseGraphAlgorithm<T> {
    private int currentTime;

    public DepthFirstSearch(Graph<T> graph) {
        super(graph, true);
        run();
    }

    @Override
    protected BaseVertexWrapper createWrapper(Graph.Vertex<T> vertex) {
        return new VertexWrapper(vertex);
    }

    private void run() {
        for (BaseVertexWrapper vertex : vertexWrappers.values()) {
            if (((VertexWrapper) vertex).color == Color.WHITE) {
                dfsVisit((VertexWrapper) vertex);
            }
        }
    }

    private void dfsVisit(VertexWrapper current) {
        currentTime++;
        current.discoveryTime = currentTime;
        current.color = Color.GRAY;
        for (BaseVertexWrapper baseWrapper : current.getAdjWrappers()) {
            VertexWrapper adjVertex = (VertexWrapper) baseWrapper;
            if (adjVertex.color == Color.WHITE) {
                //adjVertex.parent = current;
                dfsVisit(adjVertex);
            }
        }
        current.color = Color.BLACK;
        currentTime++;
        current.finishTime = currentTime;
    }

    public List<Graph.Vertex<T>> getPreordering() {
        List<BaseVertexWrapper> wrappers = new ArrayList<>(vertexWrappers.values());
        wrappers.sort(Comparator.comparingInt(baseVertexWrapper -> ((VertexWrapper) baseVertexWrapper).discoveryTime));
        return toVertexList(wrappers);
    }

    public List<Graph.Vertex<T>> getReversePostordering() {
        List<BaseVertexWrapper> wrappers = new ArrayList<>(vertexWrappers.values());
        wrappers.sort((first, second) -> Integer.compare(((VertexWrapper) second).finishTime, ((VertexWrapper) first).finishTime));
        return toVertexList(wrappers);
    }

    private class VertexWrapper extends BaseVertexWrapper {
        private Color color;
        private int discoveryTime;
        private int finishTime;
        //private VertexWrapper parent;

        private VertexWrapper(Graph.Vertex<T> vertex) {
            super(vertex);
            color = Color.WHITE;
        }
    }
}
