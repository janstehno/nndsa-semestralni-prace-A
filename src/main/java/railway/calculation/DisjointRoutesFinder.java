package railway.calculation;

import graph.calculation.DisjointPathsFinder;
import railway.model.DisjointRoutes;
import railway.model.RailwaySwitch;
import railway.model.Route;

import java.util.List;

public class DisjointRoutesFinder extends DisjointPathsFinder<RailwaySwitch, Route, DisjointRoutes> {

    public DisjointRoutesFinder(List<Route> paths) {
        super(paths);
    }

    @Override
    protected DisjointRoutes createDisjointSet(String setId, int count, List<Route> paths) {
        return new DisjointRoutes(setId, count, paths);
    }
}
