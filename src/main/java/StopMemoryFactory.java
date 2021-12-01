import java.util.List;
import java.util.Map;

public class StopMemoryFactory implements StopFactoryInterface {
    private final Map<StopNameType, List<LineNameType>> stops;

    public StopMemoryFactory(Map<StopNameType, List<LineNameType>> stops) {
        this.stops = stops;
    }

    @Override
    public Stop createStop(StopNameType stopName) {
        if (!stops.containsKey(stopName)) throw new IllegalArgumentException("No such stop");
        return new Stop(stopName, stops.get(stopName));
    }
}
