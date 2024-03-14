package graph.model;

import com.google.gson.annotations.SerializedName;
import misc.Printer;

import java.util.*;

public abstract class DisjointSet<V extends Vertex, P extends Path<V>> implements Comparable<DisjointSet<V, P>> {

    @SerializedName("ID") private final Integer id;
    @SerializedName("COUNT") private final int count;
    @SerializedName("PATHS") private final Set<P> paths;

    public DisjointSet(Integer id, int count, Set<P> paths) {
        this.id = id;
        this.count = count;
        this.paths = new HashSet<>(paths);
    }

    public Integer getId() {
        return this.id;
    }

    public int getCount() {
        return this.count;
    }

    public Set<P> getPaths() {
        return this.paths;
    }

    public void addPath(P path) {
        this.paths.add(path);
    }

    public void removePath(P path) {
        this.paths.remove(path);
    }

    public boolean isSetFound() {
        return this.paths.size() == this.count;
    }

    public boolean isPathDisjointWithSet(P disjointPath) {
        for (P path : this.paths) {
            if (!disjointPath.isDisjoint(path)) {
                return false;
            }
        }
        return true;
    }

    public String toString(boolean colored) {
        StringBuilder result = new StringBuilder();
        String id = String.format("D%s ", this.getId());
        result.append(colored ? Printer.formatRed(id) : id);
        result.append("(");
        int p = 1;
        for (P path : this.getPaths()) {
            result.append(String.format("P%s", path.getId()));
            if (p < this.getPaths().size()) {
                result.append(", ");
            }
            p++;
        }
        result.append(")");
        return result.toString();
    }
}
