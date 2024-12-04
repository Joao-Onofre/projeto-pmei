package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

import java.time.LocalDateTime;

public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

    // Cria um novo sensor
    public void create(long sensorId, SensorType sensorType, StatusType statusType, LocalDateTime timestamp, String currentValue)
        throws Exception {
        try {
            Sensor sensor = new Sensor(sensorId, sensorType, statusType, timestamp, currentValue);
            entityManager.persist(sensor);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
