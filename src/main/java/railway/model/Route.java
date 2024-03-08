package railway.model;

import graph.model.Path;

import java.util.List;

public class Route extends Path<RailwaySwitch> {
    public Route(String id, List<RailwaySwitch> railwaySwitches) {
        super(id, railwaySwitches);
    }

    @Override
    public int compareTo(Path<RailwaySwitch> route) {
        Integer firstId = Integer.parseInt(this.getId().replaceAll("P", ""));
        Integer secondId = Integer.parseInt(route.getId().replaceAll("P", ""));
        return firstId.compareTo(secondId);
    }
}
