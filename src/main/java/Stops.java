import java.util.*;

public class Stops {
    private Map<StopNameType, Stop> stops = new HashMap<>();
    private static StopFactoryInterface stopFactory = null;
    private static Stops instance = null;
    private Map<StopNameType, Stop> processedStops = new HashMap<>();
    private static boolean wasInitialised = false;

    private Stops() {
    }

    public static Stops getInstance() {
        return instance;
    }

    public static void initialize(StopFactoryInterface stopFactory) {
        if(wasInitialised) return;
        wasInitialised = true;
        Stops.stopFactory = stopFactory;
        instance = new Stops();
    }

    public void setStartingStop(StopNameType stopName, TimeType startTime) {
        Stop startingStop = stopFactory.createStop(stopName);
        processedStops.put(stopName, startingStop);
        startingStop.updateReachableAt(startTime, Optional.empty());
    }

    public List<LineNameType> getLines(StopNameType stopName) {
        return processedStops.get(stopName).getLines();
    }

    public Optional<Map.Entry<StopNameType, TimeType>> earliestReachableAfter(TimeType time) {
        Stop earliestStop = null;
        TimeType earliestTime = null;
        for (Stop stop : stops.values()) {
            TimeType tmpTime = stop.getReachableAt().getKey();
            if (tmpTime.compareTo(time) >= 0 && (earliestStop == null || tmpTime.compareTo(
                    earliestTime) <= 0)) {
                earliestStop = stop;
                earliestTime = tmpTime;
            }
        }
        if (earliestStop == null) return Optional.empty();
        processedStops.put(earliestStop.getName(), earliestStop);
        stops.remove(earliestStop.getName());
        return Optional.of(Map.entry(earliestStop.getName(), earliestTime));
    }

    public Map.Entry<TimeType, LineNameType> getReachableAt(StopNameType stopName) {
        return stops.get(stopName).getReachableAt();
    }

    public Map.Entry<TimeType, LineNameType> getReachableAtProcessed(StopNameType stopName) {
        return processedStops.get(stopName).getReachableAt();
    }

    public Stop makeStop(StopNameType stopName) {
        if (stops.containsKey(stopName)) return stops.get(stopName);
        stops.put(stopName, stopFactory.createStop(stopName));
        return stops.get(stopName);
    }

    public void clean() {
        stops = new HashMap<>();
        processedStops = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Stops{" + "stops=" + stops + '}';
    }
}
