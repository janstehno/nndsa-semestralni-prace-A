package railway.model;

import graph.model.Graph;

import java.util.Set;

public class Railway extends Graph<RailwaySwitch> {
    public Railway(Set<RailwaySwitch> railwaySwitches) {
        super(railwaySwitches);
    }
}
