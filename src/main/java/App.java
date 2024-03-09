import example.railway.utils.RailwayUtils;

public class App {
    public static void main(String[] args) {
        RailwayUtils railwayUtils = new RailwayUtils();
        railwayUtils.run("FIRST TASK (CALIBRATION)", "src/main/resources/graph_first.json");
        railwayUtils.run("SECOND TASK", "src/main/resources/graph_second.json");
    }
}