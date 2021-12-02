import org.javatuples.Triplet;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class LineSegmentTest {
    Stops stops;
    LineSegmentInterface lineSegment;

    @Before
    public void setUp() {
        Stops.initialize(new FakeStopFactory(new ArrayList<>(List.of("stop"))));
        lineSegment = new LineSegment(
                new TimeDiffType(1),
                new HashMap<>(Map.of(new TimeType(1), 1)),
                1,
                new LineNameType("line"),
                new StopNameType("stop")
        );
    }

    @Test
    public void testNextNextStop() {
        Map.Entry<TimeType, StopNameType> real = lineSegment.nextStop(new TimeType(5));
        assertEquals(new TimeType(6), real.getKey());
        assertEquals(new StopNameType("stop"), real.getValue());
    }

    @Test
    public void testNextStopAndUpdateReachable(){
        Triplet<TimeType, StopNameType, Boolean> real = lineSegment.nextStopAndUpdateReachable(new TimeType(4), new TimeType(1));

        assertEquals(new TimeType(5), real.getValue0());
        assertEquals(new StopNameType("stop"), real.getValue1());
        assertFalse(real.getValue2());
    }

}