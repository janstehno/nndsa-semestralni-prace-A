package model;

import misc.Printer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Graph<V extends Vertex, P extends Path<V>, D extends DisjointSet<V, P>> {
    private final Set<V> vertices;

    public Graph(Set<V> vertices) {
        this.vertices = new HashSet<>(vertices);
    }

    private V getVertexById(Integer id) {
        Optional<V> vertex = this.vertices.stream().filter(v -> v.getId().equals(id)).findFirst();
        return vertex.orElse(null);
    }

    public Set<V> getVertices() {
        return this.vertices;
    }

    public Set<V> getStarts() {
        return this.vertices.stream().filter(Vertex::isStart).collect(Collectors.toSet());
    }

    public Set<V> getEnds() {
        return this.vertices.stream().filter(Vertex::isEnd).collect(Collectors.toSet());
    }

    protected abstract P createPath(String pathId, List<V> vertices);

    protected abstract D createDisjointSet(String setId, int count, List<P> paths);

    public List<P> findAllPaths() {
        List<P> foundPaths = new ArrayList<>();

        for (V start : getStarts()) {
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

        for (Integer neighborId : current.getNeighbors()) {
            try {
                V neighbor = getVertexById(neighborId);
                if (getVertexById(neighborId).isCrossroad()) {
                    // if neighbor is a crossroad
                    V next = getVertexById(neighbor.getNeighbors().get(neighbor.getNeighbors().indexOf(current.getId()) + 1));
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

    public List<D> findAllDisjointSets(List<P> allPaths, int count) {
        List<D> foundSets = new ArrayList<>();
        D disjointSet = createDisjointSet("D1", count, new ArrayList<>());
        findDisjointSet(allPaths, disjointSet, foundSets);
        return foundSets;
    }

    private void findDisjointSet(List<P> allPaths, DisjointSet<V, P> disjointSet, List<D> foundSets) {
        if (disjointSet.isSetFound()) {
            String setId = String.format("D%d", foundSets.size() + 1);
            foundSets.add(createDisjointSet(setId, disjointSet.getCount(), new ArrayList<>(disjointSet.getPaths())));
            return;
        }

        for (P path : allPaths) {
            if (!disjointSet.getPaths().isEmpty() && !disjointSet.isPathDisjointWithSet(path)) {
                continue;
            }
            disjointSet.add(path);
            // sublist contains paths that the *current* path was not compared with yet
            List<P> remainingPaths = new ArrayList<>(allPaths.subList(allPaths.indexOf(path) + 1, allPaths.size()));
            // recursive call
            findDisjointSet(remainingPaths, disjointSet, foundSets);
            disjointSet.remove(disjointSet.getPaths().size() - 1);
        }
    }
}