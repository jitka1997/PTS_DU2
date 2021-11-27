import java.util.*;

public class ConnectionSearch {
    private final LineFactoryInterface lineFactory;
    private final LineSegmentFactoryInterface lineSegmentFactory;
    private final StopFactoryInterface stopFactory;
    private Stops stops;
    private Lines lines;

    public ConnectionSearch(LineFactoryInterface lineFactory,
            LineSegmentFactoryInterface lineSegmentFactory, StopFactoryInterface stopFactory) {
        this.lineFactory = lineFactory;
        this.lineSegmentFactory = lineSegmentFactory;
        this.stopFactory = stopFactory;
    }

    public ConnectionDataType search(StopNameType from, StopNameType to, TimeType startTime) {
        stops = new Stops(stopFactory);
        lines = new Lines(lineFactory, lineSegmentFactory);
        stops.setStartingStop(from, startTime);
        StopNameType nextStop;
        TimeType nextTime = startTime;
        while (true) {
            Optional<Map.Entry<StopNameType, TimeType>> earliest = stops.earliestReachableAfter(
                    nextTime);
            if (!earliest.isPresent())
                throw new IllegalArgumentException("Connection doesn't exist");
            nextStop = earliest.get().getKey();
            nextTime = earliest.get().getValue();

            if (nextStop.equals(to)) break;
            List<LineNameType> linesToProcess = stops.getLines(nextStop);
            lines.updateReachable(linesToProcess, nextStop, nextTime);
        }

        // reconstruct route
        StopNameType lastStop = nextStop;
        List<StopNameType> routeStops = new ArrayList<>();
        List<LineNameType> routeLines = new ArrayList<>();
        List<TimeType> routeArrivals = new ArrayList<>();
        while (true) {
            Map.Entry<TimeType, LineNameType> last = stops.getReachableAt(lastStop);
            routeStops.add(lastStop);
            routeLines.add(last.getValue());
            routeArrivals.add(last.getKey());
            if (lastStop.equals(from)) break;
            lastStop = lines.updateCapacityAndGetPreviousStop(last.getValue(), lastStop,
                    last.getKey());

        }
        Collections.reverse(routeArrivals);
        Collections.reverse(routeLines);
        Collections.reverse(routeStops);
        return new ConnectionDataType(routeLines, routeStops, routeArrivals);
    }
}
