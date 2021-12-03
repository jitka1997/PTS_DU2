import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class IntegrationTestDatabase {
    ConnectionSearch connectionSearch;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
            "default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    ;


    @Before
    public void setUp() {
        LineSegmentFactoryInterface lineSegmentFactory = new LineSegmentDatabaseFactory(
                entityManager);
        LineFactoryInterface lineFactory = new LineDatabaseFactory(entityManager);
        StopFactoryInterface stopFactory = new StopDatabaseFactory(entityManager);
        connectionSearch = new ConnectionSearch(lineFactory, lineSegmentFactory, stopFactory);
    }
// not sure how to open entity manager before every test and close after,
// putting entityManager = entityManagerFactory.createEntityManager() in @Before and uncommenting @After part isn't working
// now we don't close it after test, but it's not such a big deal because it is in test.
//    @After
//    public void runAfter(){
//        entityManager.close();
//        entityManagerFactory.close();
//    }

    @Test
    public void testOneLineSegmentRoute() {
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop2");
        TimeType startTime = new TimeType(1);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
        ConnectionDataType expected = new ConnectionDataType(new ArrayList<>(
                Arrays.asList(new LineNameType("START"), new LineNameType("line1"))),
                new ArrayList<>(
                        Arrays.asList(new StopNameType("stop1"), new StopNameType("stop2"))),
                new ArrayList<>(Arrays.asList(new TimeType(1), new TimeType(3))));
        assertEquals(expected, searched);
    }

    @Test
    public void testMoreLineSegmentsRoute() {
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop6");
        TimeType startTime = new TimeType(1);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
        ConnectionDataType expected = new ConnectionDataType(
                new ArrayList<>(Arrays.asList(
                        new LineNameType("START"),
                        new LineNameType("line1"),
                        new LineNameType("line1"),
                        new LineNameType("line1"),
                        new LineNameType("line1"),
                        new LineNameType("line1")
                )),
                new ArrayList<>(Arrays.asList(
                        new StopNameType("stop1"),
                        new StopNameType("stop2"),
                        new StopNameType("stop3"),
                        new StopNameType("stop4"),
                        new StopNameType("stop5"),
                        new StopNameType("stop6")
                )),
                new ArrayList<>(Arrays.asList(
                        new TimeType(1),
                        new TimeType(3),
                        new TimeType(6),
                        new TimeType(8),
                        new TimeType(9),
                        new TimeType(11)
                ))
        );
        assertEquals(expected, searched);
    }

    @Test
    public void testTransferLinesRoute(){
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop8");
        TimeType startTime = new TimeType(1);
        ConnectionDataType expected = new ConnectionDataType(
                new ArrayList<>(Arrays.asList(
                        new LineNameType("START"),
                        new LineNameType("line1"),
                        new LineNameType("line3"),
                        new LineNameType("line3")
                )),
                new ArrayList<>(Arrays.asList(
                        new StopNameType("stop1"),
                        new StopNameType("stop2"),
                        new StopNameType("stop5"),
                        new StopNameType("stop8")
                )),
                new ArrayList<>(Arrays.asList(
                        new TimeType(1),
                        new TimeType(3),
                        new TimeType(4),
                        new TimeType(6)
                ))
        );
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
        assertEquals(expected, searched);
    }

}