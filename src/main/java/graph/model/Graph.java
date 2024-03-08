package graph.model;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Graph<V extends Vertex> {
    private final Set<V> vertices;

    private final Map<V, List<V>> edges = new HashMap<>();

    public Graph(Set<V> vertices) {
        this.vertices = new HashSet<>(vertices);
        createEdges();
    }

    public Set<V> getStarts() {
        return this.vertices.stream().filter(Vertex::isStart).collect(Collectors.toSet());
    }

    public Set<V> getEnds() {
        return this.vertices.stream().filter(Vertex::isEnd).collect(Collectors.toSet());
    }

    public List<V> getNeighborsAt(V vertex) {
        return edges.get(vertex);
    }

    public List<V> getNeighborsAtCrossroad(V crossroad, V previous) {
        List<V> neighbors = getNeighborsAt(crossroad);
        List<V> next = new ArrayList<>();
        boolean previousFound = false;

        for (V neighbor : neighbors) {
            if (previousFound) {
                next.add(neighbor);
                previousFound = false;
            }
            if (neighbor.equals(previous)) {
                previousFound = true;
            }
        }

        return next;
    }

    public V getVertexById(Integer id) {
        return this.vertices.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

    private void createEdges() {
        for (V vertex : vertices) {
            List<V> neighbors = new ArrayList<>();
            for (Integer neighborId : vertex.getNeighbors()) {
                V neighbor = getVertexById(neighborId);
                if (neighbor != null) neighbors.add(getVertexById(neighborId));
            }
            edges.put(vertex, neighbors);
        }
    }
}