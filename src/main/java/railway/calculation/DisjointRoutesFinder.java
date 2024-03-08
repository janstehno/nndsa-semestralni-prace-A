package railway.calculation;

import graph.calculation.DisjointPathsFinder;
import railway.model.DisjointRoutes;
import railway.model.RailwaySwitch;
import railway.model.Route;

import java.util.Set;

public class DisjointRoutesFinder extends DisjointPathsFinder<RailwaySwitch, Route, DisjointRoutes> {

    public DisjointRoutesFinder(Set<Route> paths) {
        super(paths);
    }

    @Override
    protected DisjointRoutes createDisjointSet(String setId, int count, Set<Route> paths) {
        return new DisjointRoutes(setId, count, paths);
    }
}
