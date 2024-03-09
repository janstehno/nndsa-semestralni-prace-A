package example.railway.model;

import graph.model.Path;

import java.util.List;

public class Route extends Path<RailwaySwitch> {
    public Route(Integer id, List<RailwaySwitch> railwaySwitches) {
        super(id, railwaySwitches);
    }

    @Override
    public int compareTo(Path<RailwaySwitch> route) {
        return this.getId().compareTo(route.getId());
    }
}
