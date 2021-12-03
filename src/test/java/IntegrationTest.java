import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    ConnectionSearch connectionSearch;

    @Before
    public void setUp() {
        List<Pair<LineNameType, StopNameType>> lines = new ArrayList<>() {{
            add(new Pair<>(new LineNameType("line1"), new StopNameType("stopA")));
        }};
        Map<StopNameType, List<LineNameType>> stops = new HashMap<>() {{
            put(new StopNameType("stopA"), new ArrayList<>(List.of(new LineNameType("line1"))));
            put(new StopNameType("stopB"), new ArrayList<>() {{
                add(new LineNameType("line1"));
            }});
            put(new StopNameType("stopC"),
                    new ArrayList<>(List.of(new LineNameType("line1"))));
        }};
        Map<TimeType, Integer> line1NumOfPass = new HashMap<>(Map.of(new TimeType(0), 1, new TimeType(2), 0));

        Map<Pair<LineNameType, Integer>, Quartet<TimeDiffType, Map<TimeType, Integer>, Integer, StopNameType>> lineSegments = new HashMap<>() {{
            put(new Pair<>(new LineNameType("line1"), 0),
                    new Quartet<>(new TimeDiffType(1), line1NumOfPass, 1, new StopNameType("stopB")));
            put(new Pair<>(new LineNameType("line1"), 1),
                    new Quartet<>(new TimeDiffType(1), line1NumOfPass, 1, new StopNameType("stopC")));
        }};
        MemoryFactoriesFactory mFF = new MemoryFactoriesFactory(lines, stops, lineSegments);

        connectionSearch = new ConnectionSearch(mFF.createLineFactory(), mFF.createLineSegmentFactory(),
                mFF.createStopFactory());
    }

    @Test
    public void testNotFirstBus() {
        StopNameType from = new StopNameType("stopA");
        StopNameType to = new StopNameType("stopC");
        TimeType startTime = new TimeType(0);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);

        ConnectionDataType expected = new ConnectionDataType(
                new ArrayList<>(Arrays.asList(new LineNameType("START"), new LineNameType("line1"), new LineNameType("line1"))),
                new ArrayList<>(Arrays.asList(new StopNameType("stopA"), new StopNameType("stopB"), new StopNameType("stopC"))),
                new ArrayList<>(Arrays.asList(new TimeType(0), new TimeType(3), new TimeType(4)))
        );

        assertEquals(expected, searched);
    }
}
