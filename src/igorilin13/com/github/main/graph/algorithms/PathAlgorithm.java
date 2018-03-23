package igorilin13.com.github.main.graph.algorithms;

import igorilin13.com.github.main.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PathAlgorithm<T> extends BaseGraphAlgorithm<T> {
    private final T sourceName;

    protected PathAlgorithm(Graph<T> graph, boolean needsWrappers, T sourceName) {
        super(graph, needsWrappers);
        this.sourceName = sourceName;
    }

    public String getPathAsString(T destName) {
        List<Graph.Edge<T>> path = getPath(destName);
        if (path == null) {
            return "";
        }
        if (path.size() == 0) {
            return destName.toString();
        }
        return nonEmptyPathToString(path);
    }

    protected abstract PathVertexWrapper<T> findVertexWrapper(T name);

    public List<Graph.Edge<T>> getPath(T destName) {
        PathVertexWrapper<T> source = findVertexWrapper(sourceName);
        PathVertexWrapper<T> dest = findVertexWrapper(destName);
        if (source == null || dest == null) {
            return null;
        }
        if (source == dest) {
            return Collections.emptyList();
        }
        try {
            return buildPath(source, dest);
        } catch (NoPathExistsException e) {
            return null;
        }
    }

    private List<Graph.Edge<T>> buildPath(PathAlgorithm.PathVertexWrapper<T> source,
                                          PathAlgorithm.PathVertexWrapper<T> dest) throws NoPathExistsException {
        List<Graph.Edge<T>> res = new ArrayList<>();
        if (source == dest) {
            return res;
        }
        if (dest.getParent() == null) {
            throw new NoPathExistsException();
        }

        res.addAll(buildPath(source, dest.getParent()));
        res.add(dest.getEdge());

        return res;
    }

    private String nonEmptyPathToString(List<Graph.Edge<T>> path) {
        StringBuilder res = new StringBuilder();
        res.append(path.get(0).getFrom().getName());
        for (Graph.Edge<T> edge : path) {
            res.append(edge.getTo().getName());
        }
        return res.toString();
    }

    public int getPathWeight(T destName) {
        PathVertexWrapper<T> dest = findVertexWrapper(destName);
        return dest != null ? dest.getPathWeight() : 0;
    }

    public interface PathVertexWrapper<T> {
        PathVertexWrapper<T> getParent();

        Graph.Vertex<T> getVertex();

        int getPathWeight();

        Graph.Edge<T> getEdge();
    }

    private static class NoPathExistsException extends Exception {
    }
}
