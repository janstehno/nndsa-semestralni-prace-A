package graph.model;

import misc.Printer;

import java.util.*;

public abstract class DisjointSet<V extends Vertex, P extends Path<V>> implements Comparable<DisjointSet<V, P>> {

    private final String id;
    private final int count;
    private final Set<P> paths;

    public DisjointSet(String id, int count, Set<P> paths) {
        this.id = id;
        this.count = count;
        this.paths = new HashSet<>(paths);
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public Set<P> getPaths() {
        return paths;
    }

    public void addPath(P path) {
        this.paths.add(path);
    }

    public void removePath(P path) {
        this.paths.remove(path);
    }

    public boolean isSetFound() {
        return this.paths.size() == count;
    }

    public boolean isPathDisjointWithSet(P disjointPath) {
        for (P path : paths) {
            if (!disjointPath.isDisjoint(path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(Printer.formatRed(String.format("%s ", this.id)));
        result.append("(");
        int p = 1;
        for (P path : this.paths) {
            result.append(String.format("%s", path.getId()));
            if (p < this.paths.size()) {
                result.append(", ");
            }
            p++;
        }
        result.append(")");
        return result.toString();
    }
}
