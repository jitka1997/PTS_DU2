import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StopInterface {
    public Map.Entry<TimeType, LineNameType> getReachableAt();

    public TimeType getReachableAtTime();

    public void updateReachableAt(TimeType reachableAt, Optional<LineNameType> lineName);

    public List<LineNameType> getLines();

    public StopNameType getName();
}
