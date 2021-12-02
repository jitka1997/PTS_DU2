import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class StopsTest {
    Stops stops;

    @Before
    public void setUp() {
        List<String> stopsDatabase = new ArrayList<>(){{
            add("stop1");
            add("stop2");
            add("stop3");
        }};
        Stops.initialize(new FakeStopFactory(stopsDatabase));
        stops = Stops.getInstance();
    }

    @Test
    public void testEarliestReachableAfter() {
        stops.makeStop(new StopNameType("stop1")).updateReachableAt(new TimeType(2), Optional.of(new LineNameType("line1")));
        stops.makeStop(new StopNameType("stop2")).updateReachableAt(new TimeType(2), Optional.of(new LineNameType("line1")));
        stops.makeStop(new StopNameType("stop3")).updateReachableAt(new TimeType(3), Optional.of(new LineNameType("line1")));

        List<String> real = new ArrayList<>();
        real.add(stops.earliestReachableAfter(new TimeType(2)).get().getKey().getName());
        real.add(stops.earliestReachableAfter(new TimeType(2)).get().getKey().getName());
        if(real.get(0).equals("stop2")) Collections.swap(real, 0, 1);

        assertEquals("stop1",real.get(0));
        assertEquals("stop2", real.get(1));
        assertEquals("stop3", stops.earliestReachableAfter(new TimeType(2)).get().getKey().getName());
    }

}