import entity.FinalLineSegmentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineSegmentDatabaseFactory implements LineSegmentFactoryInterface {
    private final EntityManager entityManager;

    public LineSegmentDatabaseFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LineSegmentInterface createLineSegment(LineNameType lineName, int i) {
        EntityTransaction transaction = entityManager.getTransaction();
        LineSegmentInterface newLineSegment;

        try {
            transaction.begin();

            String line_name = lineName.toString();
            int index = i;
            Query query = entityManager.createNamedQuery("lineSegmentByLinenNameAnId");
            query.setParameter(1, line_name);
            query.setParameter(2, index);

            @SuppressWarnings("unchecked") List<FinalLineSegmentEntity> lineSegments = (List<FinalLineSegmentEntity>) query.getResultList();

            if (lineSegments.size() == 0) throw new IllegalArgumentException();

            TimeDiffType timeDiff = null;
            int capacity = 0;
            StopNameType nextStop = null;
            TimeType startTime = null;
            Map<TimeType, Integer> numOfPass = new HashMap<>();

            for (FinalLineSegmentEntity lineSegmentEntity : lineSegments) {
                timeDiff = new TimeDiffType(lineSegmentEntity.getTimeDiff());
                capacity = lineSegmentEntity.getCapacity();
                nextStop = new StopNameType(lineSegmentEntity.getNextStop());
                startTime = new TimeType(lineSegmentEntity.getStartingTime());
                numOfPass.put(startTime, lineSegmentEntity.getNumOfPass());
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

    public void updateNumOfPass(LineNameType lineName, StopNameType nextStop, TimeType startTime) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Query query = entityManager.createNativeQuery(
                    "update line_segs_num_of_pass l set num_of_pass = num_of_pass + 1 " +
                            "from line_segment ls where ls.place = l.place AND ls.line_name = l.line_name " +
                            "AND ls.next_stop = '" + nextStop + "' AND ls.line_name = '" +lineName +
                            "' AND l.starting_time = '" + startTime + "'");
            query.executeUpdate();

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
