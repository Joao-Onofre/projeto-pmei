package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

import java.time.LocalDateTime;
import java.util.List;

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

    // Encontra por ID
    public Sensor find(long sensorId) throws Exception {
        Sensor sensor = entityManager.find(Sensor.class, sensorId);
        if (sensor == null) {
            throw new Exception("Sensor with ID " + sensorId + " not found");
        }
        return sensor;
    }

    // Encontra TODOS os sensores
    public List<Sensor> findAll(){
        return entityManager.createQuery("select s from Sensor s").getResultList();
    }

    // Update de um sensor
    public void update(long sensorId, SensorType sensorType, StatusType statusType, LocalDateTime timestamp, String currentValue)
            throws Exception {
        Sensor sensor = find(sensorId);

        sensor.setSensorType(sensorType);
        sensor.setStatus(statusType);
        sensor.setTimestamp(timestamp);
        sensor.setCurrentValue(currentValue);

        try {
            entityManager.merge(sensor);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // Delete sensor
    public void delete(long sensorId)
            throws Exception {
        Sensor sensor = find(sensorId);
        entityManager.remove(sensor);
    }
}
