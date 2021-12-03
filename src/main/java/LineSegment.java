import org.javatuples.Triplet;

import java.util.Map;
import java.util.Optional;

public class LineSegment implements LineSegmentInterface{
    private final TimeDiffType timeToNextStop;
    private final Map<TimeType, Integer> numberOfPassengers;
    private final int capacity;
    private final LineNameType lineName;
    private final StopInterface nextStop;

    public LineSegment(TimeDiffType timeToNextStop, Map<TimeType, Integer> numberOfPassengers, int capacity,
            LineNameType lineName, StopNameType nextStop) {
        this.timeToNextStop = timeToNextStop;
        this.numberOfPassengers = numberOfPassengers;
        this.capacity = capacity;
        this.lineName = lineName;
        this.nextStop = new StopProxy(nextStop);
    }

    public Map.Entry<TimeType, StopNameType> nextStop(TimeType startTime) {
        return Map.entry(TimeType.plus(startTime, timeToNextStop), nextStop.getName());
    }

    public Triplet<TimeType, StopNameType, Boolean> nextStopAndUpdateReachable(TimeType departure,
            TimeType startTime) {

        boolean busIsFull = numberOfPassengers.get(startTime) >= capacity;
        TimeType arrival = TimeType.plus(departure, timeToNextStop);

        if (!busIsFull) {
            try {
                TimeType currentReachableTime = nextStop.getReachableAtTime();
                if (currentReachableTime.compareTo(arrival) > 0) {
                    nextStop.updateReachableAt(arrival, Optional.of(lineName));
                }
            } catch (IllegalArgumentException e) {
                nextStop.updateReachableAt(arrival, Optional.of(lineName));
            }
        }
        return new Triplet<>(arrival, nextStop.getName(), !busIsFull);
    }

    public boolean incrementCapacity(TimeType time) {
        if(numberOfPassengers.get(time) >= capacity) return false;
        //TODO asi aj zapisat do databazy, nech sa to ulozi pre dalsie vyhladavania
        numberOfPassengers.put(time, numberOfPassengers.get(time) + 1);
        return true;
    }

    @Override
    public String toString() {
        return "LineSegment{" + "timeToNextStop=" + timeToNextStop + ", numberOfPassengers=" + numberOfPassengers + ", capacity=" + capacity + ", lineName=" + lineName + ", nextStop=" + nextStop + '}';
    }
}
