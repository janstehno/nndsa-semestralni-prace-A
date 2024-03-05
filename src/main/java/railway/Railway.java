package railway;

import model.Graph;

import java.util.List;
import java.util.Set;

public class Railway extends Graph<RailwaySwitch, Route, DisjointRoutes> {
    public Railway(Set<RailwaySwitch> railwaySwitches) {
        super(railwaySwitches);
    }

    @Override
    protected Route createPath(String pathId, List<RailwaySwitch> vertices) {
        return new Route(pathId, vertices);
    }

    @Override
    protected DisjointRoutes createDisjointSet(String setId, int count, List<Route> paths) {
        return new DisjointRoutes(setId, count, paths);
    }
}
