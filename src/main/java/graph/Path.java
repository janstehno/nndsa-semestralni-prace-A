package graph;

import misc.Printer;

import java.util.ArrayList;
import java.util.List;

public record Path(String id, List<Integer> nodes) {

    public Path(String id, List<Integer> nodes) {
        this.id = id;
        this.nodes = new ArrayList<>(nodes);
    }

    public boolean isDisjoint(Path path) {
        for (Integer node : path.nodes()) {
            if (this.nodes.contains(node)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(Printer.formatYellow(String.format("%s ", id)));
        result.append("(");
        int p = 1;
        for (Integer node : this.nodes) {
            result.append(String.format("%s", node));
            if (p < this.nodes.size()) {
                result.append(", ");
            }
            p++;
        }
        result.append(")");
        return result.toString();
    }
}
