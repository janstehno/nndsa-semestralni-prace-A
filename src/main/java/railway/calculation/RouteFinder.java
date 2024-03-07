package railway.calculation;

import graph.calculation.PathFinder;
import railway.model.Railway;
import railway.model.RailwaySwitch;
import railway.model.Route;

import java.util.List;

public class RouteFinder extends PathFinder<Railway, RailwaySwitch, Route> {

    public RouteFinder(Railway graph) {
        super(graph);
    }

    @Override
    protected Route createPath(String pathId, List<RailwaySwitch> vertices) {
        return new Route(pathId, vertices);
    }
}
