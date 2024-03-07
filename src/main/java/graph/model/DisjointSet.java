package graph.model;

import misc.Printer;

import java.util.ArrayList;
import java.util.List;

public abstract class DisjointSet<V extends Vertex, P extends Path<V>> {

    private final String id;
    private final int count;
    private final List<P> paths;

    public DisjointSet(String id, int count, List<P> paths) {
        this.id = id;
        this.count = count;
        this.paths = new ArrayList<>(paths);
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public List<P> getPaths() {
        return paths;
    }

    public void add(P path) {
        this.paths.add(path);
    }

    public void remove(int i) {
        this.paths.remove(i);
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
