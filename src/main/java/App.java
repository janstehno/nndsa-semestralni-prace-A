import railway.RailwayUtils;

public class App {
    public static void main(String[] args) {
        int style = RailwayUtils.getStyle();
        RailwayUtils.run("FIRST TASK (CALIBRATION)", "src/main/resources/graph_first.json", style);
        RailwayUtils.run("SECOND TASK", "src/main/resources/graph_second.json", style);
    }
}