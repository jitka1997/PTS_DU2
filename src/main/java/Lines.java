import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lines {
    private final Map<LineNameType, Line> lines;
    private final LinesFactoryInterface linesFactory;

    public Lines(LinesFactoryInterface linesFactory) {
        this.linesFactory = linesFactory;
        lines = new HashMap<>();
    }

    public void updateReachable(List<LineNameType> lines, StopNameType stopName, TimeType time) {
        for (LineNameType lineName : lines) {

            if (!this.lines.containsKey(lineName)) {
                this.lines.put(lineName, linesFactory.createLine(lineName));
            }

            this.lines.get(lineName).updateReachable(stopName, time);
        }
    }
}
