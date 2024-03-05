package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class Vertex {
    @SerializedName("ID") Integer id;
    @SerializedName("TYPE") VertexType vertexType;
    @SerializedName("NEIGHBORS") List<Integer> neighbors;

    public Vertex(Integer id, VertexType vertexType, List<Integer> neighbors) {
        this.id = id;
        this.vertexType = vertexType;
        this.neighbors = neighbors;
    }

    public Integer getId() {
        return this.id;
    }

    public boolean isCrossroad() {
        return this.vertexType.equals(VertexType.C) && this.neighbors.size() % 2 == 0;
    }

    public boolean isStart() {
        return this.vertexType.equals(VertexType.S) || this.vertexType.equals(VertexType.SE);
    }

    public boolean isEnd() {
        return this.vertexType.equals(VertexType.E) || this.vertexType.equals(VertexType.SE);
    }

    public boolean hasNeighbors() {return !this.neighbors.isEmpty();}

    public List<Integer> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public String toString() {
        return String.format("%d(%s, %s)", this.id, this.vertexType.toString(), this.neighbors);
    }
}
