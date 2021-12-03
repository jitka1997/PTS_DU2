import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.util.*;

public class MemoryFactoriesFactory {
    private final List<Pair<LineNameType, StopNameType>> lines;
    private final Map<StopNameType, List<LineNameType>> stops;
    private final Map<Pair<LineNameType, Integer>, Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType>> lineSegments;

    public MemoryFactoriesFactory(List<Pair<LineNameType, StopNameType>> lines,
            Map<StopNameType, List<LineNameType>> stops,
            Map<Pair<LineNameType, Integer>, Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType>> lineSegments) {
        this.lines = lines;
        this.stops = stops;
        this.lineSegments = lineSegments;
    }


    public StopMemoryFactory createStopFactory() {
        return new StopMemoryFactory(stops);
    }

    public LineMemoryFactory createLineFactory() {
        Map<LineNameType, Triplet<List<TimeType>, StopNameType, Integer>> tmpLines = new HashMap<>();
        for (Pair<LineNameType, StopNameType> line : lines) {
            List<TimeType> startTimes = new ArrayList<TimeType>(
                    lineSegments.get(new Pair<>(line.getValue0(), 0)).getValue1().keySet());
            Collections.sort(startTimes);
            int lineSegCount = 0;
            for (Map.Entry<Pair<LineNameType, Integer>, Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType>> lineSeg : lineSegments.entrySet())
                if (lineSeg.getKey().getValue0().equals(line.getValue0()))lineSegCount++;
            tmpLines.put(line.getValue0(),
                    new Triplet<>(startTimes, line.getValue1(), lineSegCount));
        }
        return new LineMemoryFactory(tmpLines);
    }

    public LineSegmentMemoryFactory createLineSegmentFactory() {
        return new LineSegmentMemoryFactory(lineSegments);
    }
}
