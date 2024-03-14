package graph.model;

import com.google.gson.annotations.SerializedName;
import misc.Printer;

import java.util.ArrayList;
import java.util.List;

public abstract class Path<V extends Vertex> implements Comparable<Path<V>> {

    @SerializedName("ID") private final Integer id;
    @SerializedName("NODES") private final List<V> nodes;

    public Path(Integer id, List<V> nodes) {
        this.id = id;
        this.nodes = new ArrayList<>(nodes);
    }

    public Integer getId() {
        return this.id;
    }

    public List<V> getNodes() {
        return this.nodes;
    }

    public void addNode(V node) {
        this.nodes.add(node);
    }

    public void removeLast() {
        this.nodes.remove(this.nodes.size() - 1);
    }

    public boolean containsNode(V node) {
        return this.nodes.contains(node);
    }

    public boolean isDisjoint(Path<V> path) {
        for (V node : path.getNodes()) {
            if (this.nodes.contains(node)) {
                return false;
            }
        }
        return true;
    }

    public String toString(boolean colored) {
        StringBuilder result = new StringBuilder();
        String id = String.format("P%s ", this.getId());
        result.append(colored ? Printer.formatYellow(id) : id);
        result.append("(");
        int p = 1;
        for (V node : this.getNodes()) {
            result.append(String.format("%s", node.getId()));
            if (p < this.getNodes().size()) {
                result.append(", ");
            }
            p++;
        }
        result.append(")");
        return result.toString();
    }
}
