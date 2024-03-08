package graph.calculation;

import graph.model.DisjointSet;
import graph.model.Path;
import graph.model.Vertex;

import java.util.HashSet;
import java.util.Set;

public abstract class DisjointPathsFinder<V extends Vertex, P extends Path<V>, D extends DisjointSet<V, P>> {

    private final Set<P> paths;

    public DisjointPathsFinder(Set<P> paths) {
        this.paths = paths;
    }

    protected abstract D createDisjointSet(String setId, int count, Set<P> paths);

    public Set<D> findAllDisjointSets(int count) {
        Set<D> foundSets = new HashSet<>();
        D disjointSet = createDisjointSet("D1", count, new HashSet<>());
        findDisjointSet(this.paths, disjointSet, foundSets);
        return foundSets;
    }

    private void findDisjointSet(Set<P> compareToPaths, DisjointSet<V, P> disjointSet, Set<D> foundSets) {
        if (disjointSet.isSetFound()) {
            String setId = String.format("D%d", foundSets.size() + 1);
            foundSets.add(createDisjointSet(setId, disjointSet.getCount(), new HashSet<>(disjointSet.getPaths())));
            return;
        }

        // contains paths that the *current* path was not compared with yet
        Set<P> remainingPaths = new HashSet<>(compareToPaths);

        for (P path : compareToPaths) {
            remainingPaths.remove(path);
            if (!disjointSet.getPaths().isEmpty() && !disjointSet.isPathDisjointWithSet(path)) {
                continue;
            }
            disjointSet.addPath(path);
            // recursive call
            findDisjointSet(remainingPaths, disjointSet, foundSets);
            disjointSet.removePath(path);
        }
    }
}
