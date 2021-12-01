import entity.FinalLineSegmentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineSegmentDatabaseFactory implements LineSegmentFactoryInterface{
    private final EntityManager entityManager;

    public LineSegmentDatabaseFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LineSegment createLineSegment(LineNameType lineName, int i) {
        EntityTransaction transaction = entityManager.getTransaction();
        LineSegment newLineSegment;

        try {
            transaction.begin();

            String line_name = lineName.toString();
            int index = i;
            Query query = entityManager.createNativeQuery("select ls.line_name AS lineName, ls" +
                            ".place, " +
                            "ls.time_diff AS timeDiff, ls.capacity, ls.next_stop AS nextStop, " +
                            "lsnp.num_of_pass AS numOfPass, starting_time AS startingTime from " +
                            "line_segment ls, line_segs_num_of_pass lsnp where lsnp.place = ls" +
                            ".place and ls.line_name = lsnp.line_name and ls.line_name = '" + line_name + "' and ls.place = '" + index +
                            "' order by " +
                            "ls" +
                            ".line_name, " +
                            "ls" +
                            ".place",
                    FinalLineSegmentEntity.class);

            @SuppressWarnings("unchecked")
            List<FinalLineSegmentEntity> lineSegments =
                    (List<FinalLineSegmentEntity>) query.getResultList();

            if(lineSegments.size() == 0) throw new IllegalArgumentException();

            TimeDiffType timeDiff = null;
            int capacity = 0;
            StopNameType nextStop = null;
            TimeType startTime = null;
            Map<TimeType, Integer> numOfPass = new HashMap<>();


            for(FinalLineSegmentEntity lineSegmentEntity : lineSegments){
                timeDiff = new TimeDiffType(lineSegmentEntity.getTimeDiff());
                capacity = lineSegmentEntity.getCapacity();
                nextStop = new StopNameType(lineSegmentEntity.getNextStop());
                startTime = new TimeType(Long.parseLong(lineSegmentEntity.getStartingTime()));
                numOfPass.put(startTime, Integer.parseInt(lineSegmentEntity.getNumOfPass()));
            }

            newLineSegment = new LineSegment(timeDiff, numOfPass, capacity, lineName, nextStop);


            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        return newLineSegment;
    }
}
