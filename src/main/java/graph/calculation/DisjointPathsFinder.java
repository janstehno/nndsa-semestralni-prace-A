package graph.calculation;

import graph.model.DisjointSet;
import graph.model.Path;
import graph.model.Vertex;

import java.util.ArrayList;
import java.util.List;

public abstract class DisjointPathsFinder<V extends Vertex, P extends Path<V>, D extends DisjointSet<V, P>> {

    private final List<P> paths;

    public DisjointPathsFinder(List<P> paths) {
        this.paths = paths;
    }

    protected abstract D createDisjointSet(String setId, int count, List<P> paths);

    public List<D> findAllDisjointSets(int count) {
        List<D> foundSets = new ArrayList<>();
        D disjointSet = createDisjointSet("D1", count, new ArrayList<>());
        findDisjointSet(this.paths, disjointSet, foundSets);
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
