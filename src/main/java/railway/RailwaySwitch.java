package railway;

import model.Vertex;
import model.VertexType;

import java.util.List;

public class RailwaySwitch extends Vertex {
    public RailwaySwitch(Integer id, VertexType vertexType, List<Integer> neighbors) {
        super(id, vertexType, neighbors);
    }
}
