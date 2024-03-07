package railway.model;

import graph.model.Path;

import java.util.List;

public class Route extends Path<RailwaySwitch> {
    public Route(String id, List<RailwaySwitch> railwaySwitches) {
        super(id, railwaySwitches);
    }
}
