package example.railway.model;

import graph.model.DisjointSet;

import java.util.Set;

public class DisjointRoutes extends DisjointSet<RailwaySwitch, Route> {
    public DisjointRoutes(Integer id, int count, Set<Route> paths) {
        super(id, count, paths);
    }

    @Override
    public int compareTo(DisjointSet<RailwaySwitch, Route> disjointRoutes) {
        return this.getId().compareTo(disjointRoutes.getId());
    }
}
