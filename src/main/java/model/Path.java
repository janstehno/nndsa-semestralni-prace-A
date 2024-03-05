package model;

import misc.Printer;

import java.util.ArrayList;
import java.util.List;

public abstract class Path<V extends Vertex> {

    private final String id;
    private final List<V> nodes;

    public Path(String id, List<V> nodes) {
        this.id = id;
        this.nodes = new ArrayList<>(nodes);
    }

    public String getId() {
        return id;
    }

    public List<V> getNodes() {
        return nodes;
    }

    public V getLastNode() {
        return this.nodes.get(this.nodes.size() - 1);
    }

    public void addNode(V node) {
        this.nodes.add(node);
    }

    public void removeNode(V node) {
        this.nodes.remove(node);
    }

    public boolean isDisjoint(Path<V> path) {
        for (V node : path.getNodes()) {
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
        for (V node : this.nodes) {
            result.append(String.format("%s", node.getId()));
            if (p < this.nodes.size()) {
                result.append(", ");
            }
            p++;
        }
        result.append(")");
        return result.toString();
    }
}
