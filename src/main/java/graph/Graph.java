package graph;

import com.google.gson.JsonObject;
import misc.Utils;

import java.util.*;

public class Graph {
    private final List<Edge> edges = new ArrayList<>();
    private final List<Integer> starts = new ArrayList<>();
    private final List<Integer> ends = new ArrayList<>();
    private final Map<Integer, List<Edge>> crossroads = new HashMap<>();

    public List<Integer> getStarts() {
        return starts;
    }

    public List<Integer> getEnds() {
        return ends;
    }

    public Graph(JsonObject json) {
        edges.addAll(Utils.parseEdges(json.getAsJsonArray("EDGE")));
        starts.addAll(Utils.parseNodes(json.getAsJsonArray("START")));
        ends.addAll(Utils.parseNodes(json.getAsJsonArray("END")));
        crossroads.putAll(Utils.parseCrossroads(json.getAsJsonArray("CROSSROAD")));
    }

    public List<Path> findAllPaths() {
        List<Path> foundPaths = new ArrayList<>();

        for (Integer startNode : starts) {
            // loop through *ends* because first task has some *ends* also marked as *starts*
            // if *starts* and *ends* are disjoint, this loop and condition are not needed
            for (Integer endNode : ends) {
                if (startNode.equals(endNode)) continue;
                List<Integer> path = new ArrayList<>();
                depthFirstSearch(startNode, endNode, path, foundPaths);
            }
        }

        return foundPaths;
    }

    private void depthFirstSearch(Integer current, Integer end, List<Integer> path, List<Path> foundPaths) {
        path.add(current);
        if (current.equals(end)) {
            String pathId = String.format("P%d", foundPaths.size() + 1);
            foundPaths.add(new Path(pathId, path));
            return;
        }

        List<Integer> neighbors = getNeighbors(current);
        for (Integer neighbor : neighbors) {
            // this condition is not needed for one-way oriented java.graph
            if (!path.contains(neighbor)) {
                if (crossroads.containsKey(neighbor)) {
                    // if neighbor is a crossroad
                    List<Edge> nextEdges = crossroads.get(neighbor);
                    for (Edge edge : nextEdges) {
                        if (edge.from().equals(current)) {
                            // add crossroad node
                            path.add(neighbor);
                            // recursive call
                            depthFirstSearch(edge.to(), end, path, foundPaths);
                            path.remove(path.size() - 1);
                        }
                    }
                } else {
                    // recursive call
                    depthFirstSearch(neighbor, end, path, foundPaths);
                }
            }
            path.remove(path.size() - 1);
        }
    }

    private List<Integer> getNeighbors(Integer node) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from().equals(node)) {
                neighbors.add(edge.to());
            }
        }
        return neighbors;
    }

    public List<DisjointSet> findAllDisjointSets(List<Path> allPaths, int count) {
        List<DisjointSet> foundSets = new ArrayList<>();

        DisjointSet disjointSet = new DisjointSet("D1", count, new ArrayList<>());
        findDisjointSet(allPaths, disjointSet, foundSets);

        return foundSets;
    }

    private void findDisjointSet(List<Path> allPaths, DisjointSet disjointSet, List<DisjointSet> foundSets) {
        if (disjointSet.isSetFound()) {
            String setId = String.format("D%d", foundSets.size() + 1);
            foundSets.add(new DisjointSet(setId, disjointSet.count(), new ArrayList<>(disjointSet.paths())));
            return;
        }

        for (Path path : allPaths) {
            if (!disjointSet.paths().isEmpty() && !disjointSet.isPathDisjointWithSet(path)) {
                continue;
            }
            disjointSet.add(path);
            // sublist contains paths that the *current* path was not compared with yet
            List<Path> remainingPaths = new ArrayList<>(allPaths.subList(allPaths.indexOf(path) + 1, allPaths.size()));
            // recursive call
            findDisjointSet(remainingPaths, disjointSet, foundSets);
            disjointSet.remove(disjointSet.paths().size() - 1);
        }
    }
}