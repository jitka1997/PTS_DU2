import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LineMemoryFactory implements LineFactoryInterface {
    private final Map<LineNameType, Triplet<List<TimeType>, StopNameType, Integer>> lines;

    public LineMemoryFactory(
            Map<LineNameType, Triplet<List<TimeType>, StopNameType, Integer>> lines) {
        this.lines = Collections.unmodifiableMap(lines);
    }

    @Override
    public Line createLine(LineNameType lineName, LineSegmentFactoryInterface lineSegmentFactory) {
        if (!lines.containsKey(lineName)) throw new IllegalArgumentException("No such line");
        Triplet<List<TimeType>, StopNameType, Integer> line = lines.get(lineName);
        return new Line(lineName, line.getValue0(), line.getValue1(), line.getValue2(),
                lineSegmentFactory);
    }
}
