import java.sql.Time;
import java.util.List;
import java.util.Optional;

public class Stop {
    private final String name;
    private Optional<Time> reachableAt;
    private Optional<LineNameType> reachableVia;
    private final List<LineNameType> lines;

    public Stop(String name, List<LineNameType> lines) {
        this.name = name;
        this.lines = lines;
    }

    public Optional<Time> getReachableAt() {
        return reachableAt;
    }

    public void setReachableAt(Optional<Time> reachableAt) {
        this.reachableAt = reachableAt;
    }

    public List<LineNameType> getLines() {
        return lines;
    }
}
