package graph.calculation;

import graph.model.DisjointSet;
import graph.model.Path;
import graph.model.Vertex;

import java.util.*;

public abstract class DisjointPathsFinder<V extends Vertex, P extends Path<V>, D extends DisjointSet<V, P>> {

    private final Set<P> paths;

    public DisjointPathsFinder(Set<P> paths) {
        this.paths = paths;
    }

    protected abstract D createDisjointSet(Integer id, int count, Set<P> paths);

    public Map<Integer, Set<D>> findAllDisjointSets(int maxInDisjointSet) {
        Map<Integer, Set<D>> allFoundSets = new HashMap<>();
        for (int i = 2; i <= maxInDisjointSet; i++) {
            Set<D> disjointRoutes = findDisjointSets(i);
            if (disjointRoutes.isEmpty()) break;
            allFoundSets.put(i, disjointRoutes);
        }
        return allFoundSets;
    }

    private Set<D> findDisjointSets(int count) {
        Set<D> foundSets = new HashSet<>();
        D disjointSet = createDisjointSet(1, count, new HashSet<>());
        findDisjointSet(this.paths, disjointSet, foundSets);
        return foundSets;
    }

    private void findDisjointSet(Set<P> compareToPaths, DisjointSet<V, P> disjointSet, Set<D> foundSets) {
        if (disjointSet.isSetFound()) {
            foundSets.add(createDisjointSet(foundSets.size() + 1, disjointSet.getCount(), new HashSet<>(disjointSet.getPaths())));
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
