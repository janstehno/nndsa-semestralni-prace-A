package railway.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import misc.Printer;
import railway.calculation.DisjointRoutesFinder;
import railway.calculation.RouteFinder;
import railway.model.DisjointRoutes;
import railway.model.Railway;
import railway.model.RailwaySwitch;
import railway.model.Route;

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

    private Railway createGraph(String fileName) {
        try {
            Type list = new TypeToken<Set<RailwaySwitch>>() {}.getType();
            Set<RailwaySwitch> railwaySwitches = new Gson().fromJson(new FileReader(fileName), list);
            return new Railway(railwaySwitches);
        } catch (IOException e) {
            return null;
        }
    }

    private void printRoutes(List<Route> routes) {
        System.out.println(Printer.formatGreen(String.format("%d routes", routes.size())));
        if (this.style == 1 || this.style == 3) {
            for (Route route : routes) {
                System.out.println(route);
            }
        }
    }

    private void printDisjointRoutes(List<DisjointRoutes> disjointRoutes) {
        System.out.println(Printer.formatBlue(String.format("%d disjoint %ds", disjointRoutes.size(), disjointRoutes.get(0).getCount())));
        if (this.style == 2 || this.style == 3) {
            int s = 1;
            for (DisjointRoutes routes : disjointRoutes) {
                System.out.print(routes);
                if (s % 4 == 0 || s == disjointRoutes.size()) {
                    System.out.print("\n");
                } else {
                    System.out.print(", ");
                }
                s++;
            }
        }
    }

    private List<Route> findRoutes(Railway railway) {
        RouteFinder routeFinder = new RouteFinder(railway);
        List<Route> routes = routeFinder.findAllPaths();
        printRoutes(routes);
        return routes;
    }

    private void findDisjointRoutes(Railway railway, List<Route> routes) {
        DisjointRoutesFinder disjointRoutesFinder = new DisjointRoutesFinder(routes);
        int numberOfDisjointSets = 0;
        int maxNumberOfDisjointRoutesInGroup = Math.min(railway.getStarts().size(), railway.getEnds().size());

        for (int i = 2; i <= maxNumberOfDisjointRoutesInGroup; i++) {
            List<DisjointRoutes> disjointRoutes = disjointRoutesFinder.findAllDisjointSets(i);
            if (disjointRoutes.isEmpty()) break;
            numberOfDisjointSets += disjointRoutes.size();
            printDisjointRoutes(disjointRoutes);
        }

        System.out.println(Printer.formatGreen(String.format("%d disjoint groups", numberOfDisjointSets)));
    }

    public void run(String title, String fileName) {
        System.out.format("\n%s\n", title);
        Railway railway = createGraph(fileName);
        if (railway != null) {
            findDisjointRoutes(railway, findRoutes(railway));
        }
    }

}