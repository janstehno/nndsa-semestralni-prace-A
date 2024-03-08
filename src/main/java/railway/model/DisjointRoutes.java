package railway.model;

import graph.model.DisjointSet;

import java.util.Set;

public class DisjointRoutes extends DisjointSet<RailwaySwitch, Route> {
    public DisjointRoutes(String id, int count, Set<Route> paths) {
        super(id, count, paths);
    }

    @Override
    public int compareTo(DisjointSet<RailwaySwitch, Route> disjointRoutes) {
        Integer firstId = Integer.parseInt(this.getId().replaceAll("D", ""));
        Integer secondId = Integer.parseInt(disjointRoutes.getId().replaceAll("D", ""));
        return firstId.compareTo(secondId);
    }
}
