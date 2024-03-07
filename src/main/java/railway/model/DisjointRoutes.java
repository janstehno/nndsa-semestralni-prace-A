package railway.model;

import graph.model.DisjointSet;

import java.util.List;

public class DisjointRoutes extends DisjointSet<RailwaySwitch, Route> {
    public DisjointRoutes(String id, int count, List<Route> paths) {
        super(id, count, paths);
    }
}
