package railway;

import model.Path;

import java.util.List;

public class Route extends Path<RailwaySwitch> {
    public Route(String id, List<RailwaySwitch> railwaySwitches) {
        super(id, railwaySwitches);
    }
}
