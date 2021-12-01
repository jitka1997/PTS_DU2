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
            Query query = entityManager.createNamedQuery("stopByName");
            query.setParameter(1, stop_name);

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
