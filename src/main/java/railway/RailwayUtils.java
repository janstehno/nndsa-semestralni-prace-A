package railway;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import misc.Printer;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class RailwayUtils {

    private static Railway graph(String fileName) {
        try {
            Type list = new TypeToken<Set<RailwaySwitch>>() {}.getType();
            Set<RailwaySwitch> railwaySwitches = new Gson().fromJson(new FileReader(fileName), list);
            return new Railway(railwaySwitches);
        } catch (IOException e) {
            return null;
        }
    }

    private static void printAllPaths(List<Route> paths) {
        for (Route path : paths) {
            System.out.println(path);
        }
    }

    private static void printAllDisjointPaths(List<DisjointRoutes> disjointRoutes) {
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

    public static void run(String title, String fileName, int printList) {
        System.out.println();
        System.out.println();
        System.out.println(title);

        Railway railway = graph(fileName);

        if (railway != null) {
            List<Route> paths = railway.findAllPaths();
            System.out.println(Printer.formatBlue(String.format("%d paths", paths.size())));
            if (printList == 1 || printList == 3) printAllPaths(paths);

            int numberOfDisjointSets = 0;

            for (int i = 2; i <= Math.min(railway.getStarts().size(), railway.getEnds().size()); i++) {
                List<DisjointRoutes> disjointRoutes = railway.findAllDisjointSets(paths, i);
                numberOfDisjointSets += disjointRoutes.size();
                if (disjointRoutes.isEmpty()) break;
                System.out.println(Printer.formatBlue(String.format("%d disjoint %ds", disjointRoutes.size(), i)));
                if (printList == 2 || printList == 3) printAllDisjointPaths(disjointRoutes);
            }

            System.out.println(Printer.formatGreen(String.format("%d disjoint groups", numberOfDisjointSets)));
        }
    }

    public static int getStyle() {
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

}
