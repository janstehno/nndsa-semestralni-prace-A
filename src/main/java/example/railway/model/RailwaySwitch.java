package example.railway.model;

import graph.model.Vertex;
import graph.model.VertexType;

import java.util.List;

public class RailwaySwitch extends Vertex {
    public RailwaySwitch(Integer id, VertexType vertexType, List<Integer> neighbors) {
        super(id, vertexType, neighbors);
    }

    @Override
    public int compareTo(Vertex railwaySwitch) {
        if (this.getType() == railwaySwitch.getType()) return this.getId().compareTo(railwaySwitch.getId());
        return this.getType().getOrder().compareTo(railwaySwitch.getType().getOrder());
    }
}
