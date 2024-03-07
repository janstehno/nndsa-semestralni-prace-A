package railway.model;

import graph.model.Vertex;
import graph.model.VertexType;

import java.util.List;

public class RailwaySwitch extends Vertex {
    public RailwaySwitch(Integer id, VertexType vertexType, List<Integer> neighbors) {
        super(id, vertexType, neighbors);
    }
}
