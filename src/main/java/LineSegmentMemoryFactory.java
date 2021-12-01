import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.Map;

public class LineSegmentMemoryFactory implements LineSegmentFactoryInterface{
    private final Map<Pair<LineNameType, Integer>, Quartet<TimeDiffType, Map<TimeType, Integer>,
            Integer, StopNameType>> lineSegments;

    public LineSegmentMemoryFactory(
            Map<Pair<LineNameType, Integer>,
                    Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType>> lineSegments){

        this.lineSegments = lineSegments;
    }

    @Override
    public LineSegment createLineSegment(LineNameType lineName, int i) {
        if(!lineSegments.containsKey(new Pair<>(lineName, i)))
            throw new IllegalArgumentException("No such line segment");
        Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType> lineSegment =
                lineSegments.get(new Pair<>(lineName, i));
        return new LineSegment(lineSegment.getValue0(), lineSegment.getValue1(),
                lineSegment.getValue2(), lineName, lineSegment.getValue3());
    }
}
