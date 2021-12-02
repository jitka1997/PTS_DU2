import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class LineTest {
    Line line;

    @Before
    public void setUp() {
        List<String> stops = new ArrayList<>(List.of("stop1", "stop2", "stop3"));
        LineSegmentFactoryInterface lineSegmentFactory = new FakeLineSegmentFactory(stops);
        line = new Line(
                new LineNameType("line"),
                new ArrayList<>(List.of(new TimeType(1))),
                new StopNameType("start"),
                2,
                lineSegmentFactory
        );
    }

    @Test
    public void testUpdateCapacityAndGetPreviousStop() {
        StopNameType real = line.updateCapacityAndGetPreviousStop(new StopNameType("stop2"), new TimeType(10));
        assertEquals("stop1", real.getName());
        assertEquals(FakeLineSegment.getNumOfPass(), 1);
    }

}