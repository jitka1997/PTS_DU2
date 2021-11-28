import entity.FinalLineEntity;
import entity.FinalLineSegmentEntity;
import entity.FinalStopEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class StopDatabaseFactory implements StopFactoryInterface {
    private final EntityManager entityManager;

    public StopDatabaseFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Stop createStop(StopNameType stopName) {
        EntityTransaction transaction = entityManager.getTransaction();
        Stop newStop;

        try {
            transaction.begin();

            String stop_name = stopName.toString();
            Query query = entityManager.createNativeQuery("select array_to_string(array_agg" +
                            "(distinct ls.line_name), ',') AS lineNames, s.name  from stop s, " +
                            "line_segment ls, line l where (ls.next_stop = s.name or l" +
                            ".first_stop = s.name) and ls.line_name = l.line_name and s.name = '" + stop_name +
                            "' group by s" +
                            ".name order by s.name",
                    FinalStopEntity.class);

            @SuppressWarnings("unchecked") List<FinalStopEntity> stops = (List<FinalStopEntity>) query.getResultList();

            if(stops.size() == 0) throw new IllegalArgumentException();

            FinalStopEntity stopEntity = stops.get(0);
            StopNameType newStopName = new StopNameType(stopEntity.getName());
            String[] l = stopEntity.getLineNames().split(",");
            List<LineNameType> lines = new ArrayList<>();
            for (String line : l) {
                lines.add(new LineNameType(line));
            }

            newStop = new Stop(newStopName, lines);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        return newStop;
    }
}
