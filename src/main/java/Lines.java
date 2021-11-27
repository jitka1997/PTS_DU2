import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lines {
    private final Map<LineNameType, Line> lines;
    private final LineFactoryInterface lineFactory;
    private final LineSegmentFactoryInterface lineSegmentFactory;

    public Lines(LineFactoryInterface lineFactory, LineSegmentFactoryInterface lineSegmentFactory) {
        this.lineFactory = lineFactory;
        this.lineSegmentFactory = lineSegmentFactory;
        lines = new HashMap<>();
    }

    public void updateReachable(List<LineNameType> lines, StopNameType stopName, TimeType time) {
        for (LineNameType lineName : lines) {

            if (!this.lines.containsKey(lineName)) {
                this.lines.put(lineName, lineFactory.createLine(lineName, lineSegmentFactory));
            }

            this.lines.get(lineName).updateReachable(stopName, time);
        }
    }

    public StopNameType updateCapacityAndGetPreviousStop(LineNameType lineName,
            StopNameType stopName, TimeType time) {
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stopName, time);
    }
}
