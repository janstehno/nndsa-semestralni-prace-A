package example.railway.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import misc.Printer;
import example.railway.calculation.DisjointRoutesFinder;
import example.railway.calculation.RouteFinder;
import example.railway.model.DisjointRoutes;
import example.railway.model.Railway;
import example.railway.model.RailwaySwitch;
import example.railway.model.Route;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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
        List<RailwaySwitch> sorted = new TreeSet<>(railway.getVertices()).stream().toList();
        System.out.println(Printer.formatGreen(String.format("%d vertices", sorted.size())));
        for (RailwaySwitch node : sorted) {
            if (sorted.indexOf(node) == sorted.size() - 1) {
                System.out.printf("%s\n", node.toString(true));
                continue;
            }
            System.out.format("%s, ", node.toString(true));
        }
    }

    private void printRoutes(Set<Route> routes) {
        System.out.println(Printer.formatGreen(String.format("%d routes", routes.size())));
        if (this.style == 1 || this.style == 3) {
            for (Route route : new TreeSet<>(routes)) {
                System.out.println(route.toString(true));
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
                    System.out.print(routes.toString(true));
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

    private Map<Integer, Set<DisjointRoutes>> findDisjointRoutes(Railway railway, Set<Route> routes) {
        DisjointRoutesFinder disjointRoutesFinder = new DisjointRoutesFinder(routes);
        int maxNumberOfDisjointRoutesInGroup = Math.min(railway.getStarts().size(), railway.getEnds().size());

        Map<Integer, Set<DisjointRoutes>> disjointRoutes = disjointRoutesFinder.findAllDisjointSets(maxNumberOfDisjointRoutesInGroup);
        printDisjointRoutes(disjointRoutes);

        int numberOfDisjointRoutes = disjointRoutes.values().stream().mapToInt(Set::size).sum();
        System.out.println(Printer.formatGreen(String.format("%d disjoint groups", numberOfDisjointRoutes)));

        return disjointRoutes;
    }

    private void saveToTxt(String task, Railway railway, Set<Route> routes, Map<Integer, Set<DisjointRoutes>> disjointRoutes) throws IOException {
        String folder = "out/" + task + "/txt/";
        try {
            File directory = new File(folder);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new IOException();
                }
            }
        } catch (Exception e) {
            System.out.println(Printer.formatRed("Failed to create directory"));
        }

        try (PrintWriter verticesWriter = new PrintWriter(folder + "vertices.txt"); PrintWriter routesWriter = new PrintWriter(folder + "routes.txt");
             PrintWriter disjointRoutesWriter = new PrintWriter(folder + "disjoint-routes.txt")) {

            verticesWriter.println("VERTICES:");
            for (RailwaySwitch vertex : new TreeSet<>(railway.getVertices())) {
                verticesWriter.println(vertex.toString(false));
            }
            System.out.println("Vertices successfully exported to " + Printer.formatYellow(folder + "vertices.txt"));

            routesWriter.println("ROUTES:");
            for (Route route : new TreeSet<>(routes)) {
                routesWriter.println(route.toString(false));
            }
            System.out.println("Routes successfully exported to " + Printer.formatYellow(folder + "routes.txt"));

            disjointRoutesWriter.println("DISJOINT ROUTES:");
            for (Map.Entry<Integer, Set<DisjointRoutes>> entry : disjointRoutes.entrySet()) {
                disjointRoutesWriter.println();
                disjointRoutesWriter.println("DISJOINT GROUPS OF " + entry.getKey() + ":");
                for (DisjointRoutes disjointRoute : new TreeSet<>(entry.getValue())) {
                    disjointRoutesWriter.println(disjointRoute.toString(false));
                }
            }
            System.out.println("Disjoint routes successfully exported to " + Printer.formatYellow(folder + "disjoint-routes.txt"));
        }
    }

    private void saveToJson(String task, Railway railway, Set<Route> routes, Map<Integer, Set<DisjointRoutes>> disjointRoutes) throws IOException {
        String folder = "out/" + task + "/json/";
        try {
            File directory = new File(folder);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new IOException();
                }
            }
        } catch (Exception e) {
            System.out.println(Printer.formatRed("Failed to create directory"));
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter writer = new PrintWriter(folder + "vertices.json")) {
            String json = gson.toJson(railway.getVertices());
            writer.println(json);
            System.out.println("Vertices successfully exported to " + Printer.formatYellow(folder + "vertices.json"));
        }
        try (PrintWriter writer = new PrintWriter(folder + "routes.json")) {
            String json = gson.toJson(routes.stream().map(Route::toMap).collect(Collectors.toList()));
            writer.println(json);
            System.out.println("Routes successfully exported to " + Printer.formatYellow(folder + "routes.json"));
        }
        try (PrintWriter writer = new PrintWriter(folder + "disjoint-routes.json")) {
            String json = gson.toJson(disjointRoutes.entrySet()
                                                    .stream()
                                                    .flatMap(entry -> entry.getValue().stream())
                                                    .map(DisjointRoutes::toMap)
                                                    .collect(Collectors.toList()));
            writer.println(json);
            System.out.println("Disjoint routes successfully exported to " + Printer.formatYellow(folder + "disjoint-routes.json"));
        }
    }

    public void run(String task, String fileName) {
        System.out.format("\n%s\n", task);
        Railway railway = parseRailwayFrom(fileName);
        if (railway != null) {
            printVertices(railway);
            Set<Route> routes = findRoutes(railway);
            Map<Integer, Set<DisjointRoutes>> disjointRoutes = findDisjointRoutes(railway, routes);
            try {
                saveToTxt(task.replaceAll(" ", "-").toLowerCase(), railway, routes, disjointRoutes);
                saveToJson(task.replaceAll(" ", "-").toLowerCase(), railway, routes, disjointRoutes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
