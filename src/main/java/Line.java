import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Line {
    private final LineNameType name;
    private final List<TimeType> startingTimes;
    private final StopNameType firstStop;
    private final List<LineSegmentInterface> lineSegments;
    private final int numberOfLineSegments;
    private final LineSegmentFactoryInterface lineSegmentFactory;

    public Line(LineNameType name, List<TimeType> startingTimes, StopNameType firstStop,
            int numberOfLineSegments, LineSegmentFactoryInterface lineSegmentFactory) {
        this.name = name;
        this.numberOfLineSegments = numberOfLineSegments;
        this.lineSegmentFactory = lineSegmentFactory;
        this.startingTimes = startingTimes;
        this.firstStop = firstStop;
        this.lineSegments = new ArrayList<>();
    }

    private LineSegmentInterface getOrLoad(int i) {
        if (i < lineSegments.size()) return lineSegments.get(i);
        lineSegments.add(lineSegmentFactory.createLineSegment(name, i));
        return lineSegments.get(i);
    }

    private Map.Entry<TimeType, Integer> durationFromFirstStop(StopNameType to, int previous) {
        int toUpdate = -1;
        StopNameType previousNextStop = firstStop;
        TimeType duration = new TimeDiffType(0);
        if (previousNextStop.equals(to)) return Map.entry(duration, 0);
        for (int i = 0; i < numberOfLineSegments; i++) {
            LineSegmentInterface lineSegment = getOrLoad(i);
            if (previousNextStop.equals(to)) {
                toUpdate = i - previous;
                break;
            }
            Map.Entry<TimeType, StopNameType> pair = lineSegment.nextStop(duration);
            duration = pair.getKey();
            previousNextStop = pair.getValue();
        }
        if (toUpdate == -1 && previousNextStop.equals(to)) {
            toUpdate = numberOfLineSegments - previous;
        }
        return Map.entry(duration, toUpdate);
    }

    public void updateReachable(StopNameType stopName, TimeType time) {
        //find duration from firstStop to stopName
        Map.Entry<TimeType, Integer> pair = durationFromFirstStop(stopName, 0);
        int toUpdate = pair.getValue();
        TimeType duration = pair.getKey();


        // if stopName is last stop do nothing
        if (toUpdate == numberOfLineSegments) return;


        // find first possible bus that arrives at stopName before (or at the same time as) time
        Map.Entry<TimeType, TimeType> bestBus = null;
        for (int i = 0; i < startingTimes.size(); i++) {
            TimeType tmp = TimeType.plus(startingTimes.get(i), duration);
            if (tmp.compareTo(time) >= 0) {
                if (bestBus == null) bestBus = Map.entry(tmp, startingTimes.get(i));
                else if (startingTimes.get(i).compareTo(bestBus.getValue()) <= 0)
                    bestBus = Map.entry(tmp, startingTimes.get(i));
            }
        }

        // if such bus doesn't exist don't do anything
        if (bestBus == null) return;


        // update stopName's reachableAt
        LineSegmentInterface lineSegment = getOrLoad(toUpdate);
        TimeType departure = bestBus.getKey();
        TimeType startTime = bestBus.getValue();
        int indexOfStartTime = startingTimes.indexOf(startTime);
        while(indexOfStartTime < startingTimes.size()){
            startTime = startingTimes.get(indexOfStartTime);
            departure = TimeType.plus(duration, startTime);
            if(lineSegment.nextStopAndUpdateReachable(departure, startTime).getValue2()) break;
            indexOfStartTime++;
        }
    }

    public StopNameType updateCapacityAndGetPreviousStop(StopNameType stopName, TimeType arrival) {
        Map.Entry<TimeType, Integer> pair = durationFromFirstStop(stopName, 1);
        int toUpdate = pair.getValue();
        TimeType duration = pair.getKey();

        // segment to update
        Map.Entry<TimeType, StopNameType> segmentToUpdate = lineSegments.get(toUpdate).nextStop(
                new TimeType(0));
        TimeType previousTimeDiff = segmentToUpdate.getKey();

        // update capacity
        TimeType starTime = null;
        for (TimeType startingTime : startingTimes) {
            if (TimeType.minus(TimeType.plus(startingTime, duration), previousTimeDiff).compareTo(
                    arrival) <= 0) {
                if (starTime == null) starTime = startingTime;
                else if (startingTime.compareTo(starTime) < 0) starTime = startingTime;
            }
        }
        int indexOfStartTime = startingTimes.indexOf(starTime);
        while (indexOfStartTime < startingTimes.size()){
            starTime = startingTimes.get(indexOfStartTime);
            if(lineSegments.get(toUpdate).incrementCapacity(starTime)){
                lineSegmentFactory.updateNumOfPass(name, lineSegments.get(toUpdate).nextStop(starTime).getValue(), starTime);
                break;
            }
            indexOfStartTime++;
        }

        // previous stop
        StopNameType previousStop;
        if (toUpdate == 0) previousStop = firstStop;
        else previousStop = lineSegments.get(toUpdate - 1).nextStop(new TimeType(0)).getValue();
        return previousStop;
    }

    public LineNameType getName() {
        return name;
    }
}
