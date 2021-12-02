import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class StopProxyTest {
    Stops stops;
    StopProxy stop;

    @Before
    public void setUp() {
        Stops.initialize(new FakeStopFactory(new ArrayList<>(Arrays.asList("fake stop"))));
        stops = Stops.getInstance();
        stop = new StopProxy(new StopNameType("fake stop"));
    }

    @Test
    public void testForwardsUpdatesToStops() {
        stop.updateReachableAt(new TimeType(3), Optional.empty());
        TimeType real = stop.getReachableAtTime();
        assertEquals(new TimeType(3), real);
        stop.updateReachableAt(new TimeType(4), Optional.empty());
        assertEquals(new TimeType(4), stops.getReachableAt(stop.getName()).getKey());
    }

}