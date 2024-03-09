package example.railway.calculation;

import graph.calculation.PathFinder;
import example.railway.model.Railway;
import example.railway.model.RailwaySwitch;
import example.railway.model.Route;

import java.util.List;

public class RouteFinder extends PathFinder<Railway, RailwaySwitch, Route> {

    public RouteFinder(Railway graph) {
        super(graph);
    }

    @Override
    protected Route createPath(Integer id, List<RailwaySwitch> vertices) {
        return new Route(id, vertices);
    }
}
