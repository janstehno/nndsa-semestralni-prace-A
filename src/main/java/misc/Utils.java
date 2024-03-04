package misc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import graph.DisjointSet;
import graph.Edge;
import graph.Graph;
import graph.Path;

import java.io.FileReader;
import java.util.*;

public class Utils {
    public static List<Integer> parseNodes(JsonArray array) {
        List<Integer> list = new ArrayList<>();
        for (JsonElement element : array) {
            list.add(element.getAsInt());
        }
        return list;
    }

    public static List<Edge> parseEdges(JsonArray array) {
        List<Edge> edges = new ArrayList<>();
        for (JsonElement element : array) {
            JsonArray edge = element.getAsJsonArray();
            Integer from = edge.get(0).getAsInt();
            Integer to = edge.get(1).getAsInt();
            edges.add(new Edge(from, to));
        }
        return edges;
    }

    public static Map<Integer, List<Edge>> parseCrossroads(JsonArray array) {
        Map<Integer, List<Edge>> crossroads = new HashMap<>();
        for (JsonElement element : array) {
            JsonObject crossroad = element.getAsJsonObject();
            Integer crossroadId = crossroad.get("V").getAsInt();
            JsonArray edgesArray = crossroad.getAsJsonArray("EDGE");
            List<Edge> crossroadEdges = new ArrayList<>();
            for (JsonElement edgeElement : edgesArray) {
                JsonArray edge = edgeElement.getAsJsonArray();
                Integer from = edge.get(0).getAsInt();
                Integer to = edge.get(1).getAsInt();
                crossroadEdges.add(new Edge(from, to));
                //{ "95": [java.graph.Edge[88,89], java.graph.Edge[87,90]] }
            }
            crossroads.put(crossroadId, crossroadEdges);
        }
        return crossroads;
    }

    private static Graph graph(String fileName) {
        try {
            Gson gson = new Gson();
            JsonObject graphJson = gson.fromJson(new FileReader(fileName), JsonObject.class);
            return new Graph(graphJson);
        } catch (Exception e) {
            return null;
        }
    }

    private static void printAllPaths(List<Path> paths) {
        for (Path path : paths) {
            System.out.println(path);
        }
    }

    private static void printAllDisjointPaths(List<DisjointSet> disjointSets) {
        int s = 1;
        for (DisjointSet set : disjointSets) {
            System.out.print(set);
            if (s % 4 == 0 || s == disjointSets.size()) {
                System.out.print("\n");
            } else {
                System.out.print(", ");
            }
            s++;
        }
    }

    public static void run(String title, String fileName, int printList) {
        System.out.println();
        System.out.println();
        System.out.println(title);
        Graph graph = graph(fileName);
        if (graph != null) {
            List<Path> paths = graph.findAllPaths();
            System.out.println(Printer.formatBlue(String.format("%d paths", paths.size())));
            if (printList == 1 || printList == 3) printAllPaths(paths);
            for (int i = 2; i <= Math.min(graph.getStarts().size(), graph.getEnds().size()); i++) {
                List<DisjointSet> disjointSets = graph.findAllDisjointSets(paths, i);
                if (disjointSets.isEmpty()) break;
                System.out.println(Printer.formatBlue(String.format("%d disjoint %ds", disjointSets.size(), i)));
                if (printList == 2 || printList == 3) printAllDisjointPaths(disjointSets);
            }
        }
    }

    public static int getStyle() {
        System.out.println("What summary to show?");
        System.out.println("1 - paths, 2 - disjoint groups, 3 - both, summary otherwise");
        Scanner scanner = new Scanner(System.in);
        int style;
        try {
            style = scanner.nextInt();
        } catch (Exception e) {
            style = 0;
        }
        scanner.close();
        return style;
    }

}
