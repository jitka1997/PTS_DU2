import org.javatuples.Triplet;

import java.util.Map;
import java.util.Optional;

public interface LineSegmentInterface {
    public Map.Entry<TimeType, StopNameType> nextStop(TimeType startTime);

    public Triplet<TimeType, StopNameType, Boolean> nextStopAndUpdateReachable(TimeType departure,
            TimeType startTime);

    public void incrementCapacity(TimeType time);
}
