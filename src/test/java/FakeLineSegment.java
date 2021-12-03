import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class FakeLineSegment implements LineSegmentInterface{
    private final TimeDiffType timeToNextStop;
    private  static final Map<TimeType, Integer> numberOfPassengers = new HashMap<>(Map.of(new TimeType(1), 0));
    private final int capacity;
    private final LineNameType lineName;
    private final StopNameType nextStop;

    public FakeLineSegment(StopNameType nextStop) {
        this.timeToNextStop = new TimeDiffType(1);
        this.capacity = 1;
        this.lineName = new LineNameType("line");
        this.nextStop = nextStop;
    }


    @Override
    public Map.Entry<TimeType, StopNameType> nextStop(TimeType startTime) {
        return Map.entry(TimeType.plus(startTime, timeToNextStop), nextStop);
    }

    @Override
    public Triplet<TimeType, StopNameType, Boolean> nextStopAndUpdateReachable(TimeType departure,
            TimeType startTime) {
        return new Triplet<>(TimeType.plus(departure, timeToNextStop), nextStop, numberOfPassengers.get(startTime) < capacity);
    }

    @Override
    public boolean incrementCapacity(TimeType time) {
        numberOfPassengers.put(time, numberOfPassengers.get(time) + 1);
        return true;
    }

    public static int getNumOfPass(){
        return numberOfPassengers.get(new TimeType(1));
    }
}
