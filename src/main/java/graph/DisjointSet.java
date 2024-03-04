package graph;

import misc.Printer;
import java.util.ArrayList;
import java.util.List;


public record DisjointSet(String id, int count, List<Path> paths) {

    public DisjointSet(String id, int count, List<Path> paths) {
        this.id = id;
        this.count = count;
        this.paths = new ArrayList<>(paths);
    }

    public void add(Path path) {
        this.paths.add(path);
    }

    public void remove(int i) {
        this.paths.remove(i);
    }

    public boolean isSetFound() {
        return this.paths.size() == count;
    }

    public boolean isPathDisjointWithSet(Path disjointPath) {
        for (Path path : paths) {
            if (!disjointPath.isDisjoint(path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(Printer.formatRed(String.format("%s ", id)));
        result.append("(");
        int p = 1;
        for (Path path : this.paths) {
            result.append(String.format("%s", path.id()));
            if (p < this.paths.size()) {
                result.append(", ");
            }
            p++;
        }
        result.append(")");
        return result.toString();
    }
}
