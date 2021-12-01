import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class IntegrationTestMemory {
    ConnectionSearch connectionSearch;


    // TODO: nezabudni zavriet entity managera
    @Before
    public void setUp() {
        List<Pair<LineNameType, StopNameType>> lines = new ArrayList<>() {{
            add(new Pair<>(new LineNameType("line1"), new StopNameType("stop1")));
            add(new Pair<>(new LineNameType("line2"), new StopNameType("stop2")));
        }};
        Map<StopNameType, List<LineNameType>> stops = new HashMap<>() {{
            put(new StopNameType("stop1"), new ArrayList<>(List.of(new LineNameType("line1"))));
            put(new StopNameType("stop2"), new ArrayList<>() {{
                add(new LineNameType("line1"));
                add(new LineNameType("line2"));
            }});
            put(new StopNameType("stop3"),
                    new ArrayList<>(List.of(new LineNameType("line1"), new LineNameType("line2"))));
        }};
        Map<TimeType, Integer> line1NumOfPass = new HashMap<>(Map.of(new TimeType(1), 1, new TimeType(3), 1));
        Map<TimeType, Integer> line2NumOfPass = new HashMap<>(Map.of(new TimeType(2), 1));

        Map<Pair<LineNameType, Integer>, Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType>> lineSegments = new HashMap<>() {{
            put(new Pair<>(new LineNameType("line1"), 0),
                    new Quartet<>(new TimeDiffType(1), line1NumOfPass, 5, new StopNameType("stop2")));
            put(new Pair<>(new LineNameType("line1"), 1),
                    new Quartet<>(new TimeDiffType(2), line1NumOfPass, 5, new StopNameType("stop3")));
            put(new Pair<>(new LineNameType("line2"), 0),
                    new Quartet<>(new TimeDiffType(3), line2NumOfPass, 5, new StopNameType("stop3")));
        }};
        MemoryFactoriesFactory mFF = new MemoryFactoriesFactory(lines, stops, lineSegments);

        connectionSearch = new ConnectionSearch(mFF.createLineFactory(), mFF.createLineSegmentFactory(),
                mFF.createStopFactory());
    }

    @Test
    public void testOneLineSegmentRoute() {
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop2");
        TimeType startTime = new TimeType(1);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
        ConnectionDataType expected = new ConnectionDataType(
                new ArrayList<>(Arrays.asList(new LineNameType("START"), new LineNameType("line1"))),
                new ArrayList<>(Arrays.asList(new StopNameType("stop1"), new StopNameType("stop2"))),
                new ArrayList<>(Arrays.asList(new TimeType(1), new TimeType(2))));
        assertEquals(expected, searched);
    }

    @Test
    public void testMoreLineSegmentsRoute() {
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop3");
        TimeType startTime = new TimeType(1);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
        ConnectionDataType expected = new ConnectionDataType(
                new ArrayList<>(Arrays.asList(new LineNameType("START"), new LineNameType("line1"), new LineNameType("line1"))),
                new ArrayList<>(Arrays.asList(new StopNameType("stop1"), new StopNameType("stop2"), new StopNameType("stop3"))),
                new ArrayList<>(Arrays.asList(new TimeType(1), new TimeType(2), new TimeType(4)))
        );
        assertEquals(expected, searched);
    }

    //TODO: nie na konecnej zacinajuci test


}