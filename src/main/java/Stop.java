import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Stop {
    private final StopNameType name;
    private Optional<TimeType> reachableAt;
    private Optional<LineNameType> reachableVia;
    private final List<LineNameType> lines;

    public Stop(StopNameType name, List<LineNameType> lines) {
        this.name = name;
        this.lines = lines;
        reachableVia = Optional.empty();
        reachableAt = Optional.empty();
    }

    public Map.Entry<TimeType, LineNameType> getReachableAt() {
        if (!reachableAt.isPresent()) throw new IllegalArgumentException();
        // it's starting stop
        if (!reachableVia.isPresent())
            return Map.entry(reachableAt.get(), new LineNameType("START"));
        return Map.entry(reachableAt.get(), reachableVia.get());
    }

    public TimeType getReachableAtTime() {
        if (!reachableAt.isPresent()) throw new IllegalArgumentException();
        return reachableAt.get();
    }

    public void updateReachableAt(TimeType reachableAt, Optional<LineNameType> lineName) {
        this.reachableAt = Optional.of(reachableAt);
        reachableVia = lineName;
    }

    public List<LineNameType> getLines() {
        return lines;
    }

    public StopNameType getName() {
        return name;
    }
}
