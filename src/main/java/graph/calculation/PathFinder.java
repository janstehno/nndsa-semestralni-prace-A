package graph.calculation;

import graph.model.Graph;
import graph.model.Path;
import graph.model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PathFinder<G extends Graph<V>, V extends Vertex, P extends Path<V>> {

    private final G graph;

    public PathFinder(G graph) {
        this.graph = graph;
    }

    protected abstract P createPath(Integer id, List<V> vertices);

    public Set<P> findAllPaths() {
        Set<P> foundPaths = new HashSet<>();

        for (V start : this.graph.getStarts()) {
            P path = createPath(1, new ArrayList<>());
            depthFirstSearch(start, path, foundPaths);
        }

        return foundPaths;
    }

    private void depthFirstSearch(V current, P path, Set<P> foundPaths) {
        path.addNode(current);
        if (current.isEnd() && path.getNodes().size() > 1) {
            foundPaths.add(createPath(foundPaths.size() + 1, new ArrayList<>(path.getNodes())));
        }

        for (V neighbor : this.graph.getNeighborsAt(current)) {
            // circular paths
            if (path.containsNode(neighbor)) continue;

            if (neighbor.isCrossroad()) {
                crossroadSearch(neighbor, current, path, foundPaths);
            } else {
                // recursive call
                depthFirstSearch(neighbor, path, foundPaths);
            }
        }

        path.removeLast();
    }

    private void crossroadSearch(V crossroad, V previous, P path, Set<P> foundPaths) {
        List<V> nextNodes = this.graph.getNeighborsAtCrossroad(crossroad, previous);
        for (V next : nextNodes) {
            // circular paths
            if (path.containsNode(next) || next.equals(crossroad)) continue;

            // add crossroad node
            path.addNode(crossroad);
            // recursive call
            depthFirstSearch(next, path, foundPaths);
            path.removeLast();
        }
    }

}
