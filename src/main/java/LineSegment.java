import java.util.Map;

public class LineSegment {
    private final TimeDiffType timeToNextStop;
    private final Map<TimeType, Integer> numberOfPassengers;
    private final int capacity;
    private final LineNameType lineName;
    private final Stop nextStop;

    public LineSegment(TimeDiffType timeToNextStop, Map<TimeType, Integer> numberOfPassengers,
            int capacity, LineNameType lineName, StopNameType nextStop,
            StopFactoryInterface stopFactory) {
        this.timeToNextStop = timeToNextStop;
        this.numberOfPassengers = numberOfPassengers;
        this.capacity = capacity;
        this.lineName = lineName;
        this.nextStop = stopFactory.createStop(nextStop);
    }
}
