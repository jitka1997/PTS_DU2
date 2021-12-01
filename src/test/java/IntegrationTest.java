import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class IntegrationTest {
    ConnectionSearch connectionSearch;


    // TODO: nezabudni zavriet entity managera
    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LineSegmentFactoryInterface lineSegmentFactory =
                new LineSegmentDatabaseFactory(entityManager);
        LineFactoryInterface lineFactory = new LineDatabaseFactory(entityManager);
        StopFactoryInterface stopFactory = new StopDatabaseFactory(entityManager);
        connectionSearch = new ConnectionSearch(lineFactory, lineSegmentFactory, stopFactory);
    }

    @Test
    public void testOneLineSegmentRoute(){
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop2");
        TimeType startTime = new TimeType(1);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
        ConnectionDataType expected = new ConnectionDataType(new ArrayList<>(
                Arrays.asList(new LineNameType("START"), new LineNameType("line1"))),
                new ArrayList<>(Arrays.asList(new StopNameType("stop1"),
                        new StopNameType("stop2"))),
                new ArrayList<>(Arrays.asList(new TimeType(1), new TimeType(3))));
        assertEquals(expected, searched);
    }

    @Test
    public void testMoreLineSegmentsRoute(){
        StopNameType from = new StopNameType("stop1");
        StopNameType to = new StopNameType("stop6");
        TimeType startTime = new TimeType(1);
        ConnectionDataType searched = connectionSearch.search(from, to, startTime);
    }

    //TODO: nie na konecnej zacinajuci test


}