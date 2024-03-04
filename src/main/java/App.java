import misc.Utils;

public class App {
    public static void main(String[] args) {
        int style = Utils.getStyle();
        Utils.run("FIRST TASK (CALIBRATION)", "src/main/resources/graph_first.json", style);
        // Utils.run("CUSTOM 1 (CALIBRATION)", "src/main/resources/graph_crossroad.json", printingStyle);
        // Utils.run("CUSTOM 2 (CALIBRATION)", "src/main/resources/graph_disjoint.json", printingStyle);
        Utils.run("SECOND TASK", "src/main/resources/graph_second.json", style);
    }
}