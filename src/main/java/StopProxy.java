import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StopProxy implements StopInterface {
    private Stop stop;
    private final StopNameType name;

    public StopProxy(StopNameType name) {
        stop = null;
        this.name = name;
    }

    public Map.Entry<TimeType, LineNameType> getReachableAt() {
        if (stop == null) stop = Stops.getInstance().makeStop(name);
        return stop.getReachableAt();
    }

    public TimeType getReachableAtTime() {
        if (stop == null) stop = Stops.getInstance().makeStop(name);
        return stop.getReachableAtTime();
    }

    public void updateReachableAt(TimeType reachableAt, Optional<LineNameType> lineName) {
        if (stop == null) stop = Stops.getInstance().makeStop(name);
        stop.updateReachableAt(reachableAt, lineName);
    }

    public List<LineNameType> getLines() {
        if (stop == null) stop = Stops.getInstance().makeStop(name);
        return stop.getLines();
    }

    public StopNameType getName() {
        return name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
