import entity.FinalLineEntity;
import entity.FinalStopEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class LineDatabaseFactory implements LineFactoryInterface {
    private final EntityManager entityManager;

    public LineDatabaseFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Line createLine(LineNameType lineName, LineSegmentFactoryInterface lineSegmentFactory) {
        EntityTransaction transaction = entityManager.getTransaction();
        Line newLine;

        try {
            transaction.begin();

            String line_name = lineName.toString();
            Query query = entityManager.createNamedQuery("lineByName");
            query.setParameter(1, line_name);

            @SuppressWarnings("unchecked") List<FinalLineEntity> lines = (List<FinalLineEntity>) query.getResultList();

            if (lines.size() == 0) throw new IllegalArgumentException();

            FinalLineEntity lineEntity = lines.get(0);
            LineNameType newLineName = new LineNameType(lineEntity.getLineName());
            StopNameType firstStop = new StopNameType(lineEntity.getFirstStop());
            int numOfLineSegs = lineEntity.getNumOfLineSegs();
            String[] t = lineEntity.getStartingTimes().split(",");
            List<TimeType> times = new ArrayList<>();
            for (String time : t) {
                times.add(new TimeType(Long.parseLong(time)));
            }

            newLine = new Line(newLineName, times, firstStop, numOfLineSegs, lineSegmentFactory);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        return newLine;
    }
}
