package graph.calculation;

import misc.Printer;
import graph.model.Graph;
import graph.model.Path;
import graph.model.Vertex;

import java.util.ArrayList;
import java.util.List;

public abstract class PathFinder<G extends Graph<V>, V extends Vertex, P extends Path<V>> {

    private final G graph;

    public PathFinder(G graph) {
        this.graph = graph;
    }

    protected abstract P createPath(String pathId, List<V> vertices);

    public List<P> findAllPaths() {
        List<P> foundPaths = new ArrayList<>();

        for (V start : graph.getStarts()) {
            P path = createPath("P1", new ArrayList<>());
            depthFirstSearch(start, path, foundPaths);
        }

        return foundPaths;
    }

    private void depthFirstSearch(V current, P path, List<P> foundPaths) {
        path.addNode(current);
        if (current.isEnd() && path.getNodes().size() > 1) {
            String pathId = String.format("P%d", foundPaths.size() + 1);
            foundPaths.add(createPath(pathId, new ArrayList<>(path.getNodes())));
        }

        for (V neighbor : graph.getNeighborsOf(current)) {
            try {
                if (neighbor.isCrossroad()) {
                    // if neighbor is a crossroad
                    V next = graph.getNextAtCrossroad(neighbor, current);
                    if (next.equals(neighbor)) continue;
                    // add crossroad node
                    path.addNode(neighbor);
                    // recursive call
                    depthFirstSearch(next, path, foundPaths);
                    path.removeNode(neighbor);
                } else {
                    // recursive call
                    depthFirstSearch(neighbor, path, foundPaths);
                }
            } catch (Exception e) {
                System.out.println(Printer.formatRed("Neighbor does not exist."));
            }
        }

        path.removeNode(current);
    }

}
