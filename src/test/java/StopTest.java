import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class StopTest {
    Stop startingStop;
    Stop stop;

    @Before
    public void setUp() {
        List<LineNameType> lines = new ArrayList<>(
                Arrays.asList(new LineNameType("line1"), new LineNameType("line2")));
        startingStop = new Stop(new StopNameType("start"), lines);
        stop = new Stop(new StopNameType("stop"), lines);
    }

    @Test
    public void testStartingStopsLineIsSTART() {
        startingStop.updateReachableAt(new TimeType(1), Optional.empty());
        LineNameType expected = new LineNameType("START");
        assertEquals(expected, startingStop.getReachableAt().getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrownWhenGettingEmptyReachableAt(){
        stop.getReachableAt();
    }

    @Test
    public void testStop() {
        stop.updateReachableAt(new TimeType(1), Optional.of(new LineNameType("line1")));
        Map.Entry<TimeType, LineNameType> expected = Map.entry(new TimeType(1), new LineNameType("line1"));
        assertEquals(expected, stop.getReachableAt());
    }

}