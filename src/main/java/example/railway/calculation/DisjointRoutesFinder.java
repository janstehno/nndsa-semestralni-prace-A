package example.railway.calculation;

import graph.calculation.DisjointPathsFinder;
import example.railway.model.DisjointRoutes;
import example.railway.model.RailwaySwitch;
import example.railway.model.Route;

import java.util.Set;

public class DisjointRoutesFinder extends DisjointPathsFinder<RailwaySwitch, Route, DisjointRoutes> {

    public DisjointRoutesFinder(Set<Route> paths) {
        super(paths);
    }

    @Override
    protected DisjointRoutes createDisjointSet(Integer id, int count, Set<Route> paths) {
        return new DisjointRoutes(id, count, paths);
    }
}
