package example.railway.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graph.model.Vertex;
import misc.Printer;
import example.railway.calculation.DisjointRoutesFinder;
import example.railway.calculation.RouteFinder;
import example.railway.model.DisjointRoutes;
import example.railway.model.Railway;
import example.railway.model.RailwaySwitch;
import example.railway.model.Route;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class RailwayUtils {

    private final int style;

    public RailwayUtils() {
        this.style = getStyle();
    }

    private int getStyle() {
        System.out.println("What lists to show? (optional)");
        System.out.println("1 - paths, 2 - disjoint groups, 3 - both");
        Scanner scanner = new Scanner(System.in);
        int style;
        try {
            style = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            style = 0;
        }
        scanner.close();
        return style;
    }

    private Railway parseRailwayFrom(String fileName) {
        try {
            Type list = new TypeToken<Set<RailwaySwitch>>() {}.getType();
            Set<RailwaySwitch> railwaySwitches = new Gson().fromJson(new FileReader(fileName), list);
            return new Railway(railwaySwitches);
        } catch (IOException e) {
            return null;
        }
    }

    private void printVertices(Railway railway) {
        List<RailwaySwitch> sorted = new ArrayList<>(railway.getVertices());
        sorted.sort(Comparator.comparing(Vertex::toString));

        System.out.println(Printer.formatGreen(String.format("%d vertices", sorted.size())));
        for (RailwaySwitch node : sorted) {
            if (sorted.indexOf(node) == sorted.size() - 1) {
                System.out.printf("%s\n", node);
                continue;
            }
            System.out.format("%s, ", node);
        }
    }

    private void printRoutes(Set<Route> routes) {
        System.out.println(Printer.formatGreen(String.format("%d routes", routes.size())));
        if (this.style == 1 || this.style == 3) {
            Set<Route> sorted = new TreeSet<>(routes);
            for (Route route : sorted) {
                System.out.println(route);
            }
        }
    }

    private void printDisjointRoutes(Map<Integer, Set<DisjointRoutes>> disjointRoutes) {
        for (Map.Entry<Integer, Set<DisjointRoutes>> entry : disjointRoutes.entrySet()) {
            System.out.println(Printer.formatBlue(String.format("%d disjoint %ds", entry.getValue().size(), entry.getKey())));
            if (this.style == 2 || this.style == 3) {
                int s = 1;
                Set<DisjointRoutes> sorted = new TreeSet<>(entry.getValue());
                for (DisjointRoutes routes : sorted) {
                    System.out.print(routes);
                    if (s % 4 == 0 || s == sorted.size()) {
                        System.out.print("\n");
                    } else {
                        System.out.print(", ");
                    }
                    s++;
                }
            }
        }
    }

    private Set<Route> findRoutes(Railway railway) {
        RouteFinder routeFinder = new RouteFinder(railway);

        Set<Route> routes = routeFinder.findAllPaths();
        printRoutes(routes);

        return routes;
    }

    private void findDisjointRoutes(Railway railway, Set<Route> routes) {
        DisjointRoutesFinder disjointRoutesFinder = new DisjointRoutesFinder(routes);
        int maxNumberOfDisjointRoutesInGroup = Math.min(railway.getStarts().size(), railway.getEnds().size());

        Map<Integer, Set<DisjointRoutes>> disjointRoutes = disjointRoutesFinder.findAllDisjointSets(maxNumberOfDisjointRoutesInGroup);
        printDisjointRoutes(disjointRoutes);

        int numberOfDisjointRoutes = disjointRoutes.values().stream().mapToInt(Set::size).sum();
        System.out.println(Printer.formatGreen(String.format("%d disjoint groups", numberOfDisjointRoutes)));
    }

    public void run(String title, String fileName) {
        System.out.format("\n%s\n", title);
        Railway railway = parseRailwayFrom(fileName);
        if (railway != null) {
            printVertices(railway);
            findDisjointRoutes(railway, findRoutes(railway));
        }
    }

}
