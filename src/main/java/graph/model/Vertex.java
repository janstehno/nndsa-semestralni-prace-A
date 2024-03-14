package graph.model;

import com.google.gson.annotations.SerializedName;
import misc.Printer;

import java.util.ArrayList;
import java.util.List;

public abstract class Vertex implements Comparable<Vertex>{
    @SerializedName("ID") Integer id;
    @SerializedName("TYPE") VertexType type;
    @SerializedName("NEIGHBORS") List<Integer> neighbors;

    public Vertex(Integer id, VertexType type, List<Integer> neighbors) {
        this.id = id;
        this.type = type;
        this.neighbors = neighbors;
    }

    public Integer getId() {
        return this.id;
    }

    public VertexType getType() {
        if (this.type != null) return this.type;
        return VertexType.V;
    }

    public boolean isCrossroad() {
        return getType().equals(VertexType.C);
    }

    public boolean isStart() {
        return getType().equals(VertexType.S) || getType().equals(VertexType.SE);
    }

    public boolean isEnd() {
        return getType().equals(VertexType.E) || getType().equals(VertexType.SE);
    }

    public List<Integer> getNeighbors() {
        if (this.neighbors != null) return this.neighbors;
        return new ArrayList<>();
    }

    public String toString(boolean colored) {
        String id = String.format("(%s)%s", this.getType(), this.getId());
        return (colored ? Printer.formatPurple(id) : id) + (this.getNeighbors().isEmpty() ? "" : " " + this.getNeighbors());
    }
}
