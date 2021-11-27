import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Stops {
    private Map<StopNameType, Stop> stops;
    private final StopFactoryInterface stopFactory;

    public Stops(StopFactoryInterface stopFactory) {
        this.stopFactory = stopFactory;
    }

    public void setStartingStop(StopNameType stopName, TimeType startTime) {
        Stop startingStop = stopFactory.createStop(stopName);
        stops.put(stopName, startingStop);
        startingStop.updateReachableAt(TimeType.plus(startTime, new TimeType(1)), Optional.empty());
    }

    public List<LineNameType> getLines(StopNameType stopName) {
        return stops.get(stopName).getLines();
    }

    public Optional<Map.Entry<StopNameType, TimeType>> earliestReachableAfter(TimeType time) {
        Stop earliestStop = null;
        TimeType earliestTime = null;
        for (Stop stop : stops.values()) {
            TimeType tmpTime = stop.getReachableAt().getKey();
            if (tmpTime.compareTo(time) > 0 && (earliestStop == null || tmpTime.compareTo(
                    earliestTime) <= 0)) {
                earliestStop = stop;
                earliestTime = tmpTime;
            }
        }
        if (earliestStop == null) return Optional.empty();
        return Optional.of(Map.entry(earliestStop.getName(), earliestTime));
    }
}
