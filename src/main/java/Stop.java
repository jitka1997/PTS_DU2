import java.sql.Time;
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
    }

    public Map.Entry<TimeType, LineNameType> getReachableAt() {
        if (!reachableAt.isPresent() || !reachableVia.isPresent())
            throw new IllegalArgumentException();
        return Map.entry(reachableAt.get(), reachableVia.get());
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
