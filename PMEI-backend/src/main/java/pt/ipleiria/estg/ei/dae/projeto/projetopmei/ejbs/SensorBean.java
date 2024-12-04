package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

    // Cria um novo sensor
    public void create(Long sensorId, SensorType sensorType, StatusType statusType, LocalDateTime timestamp, Double currentValue) {
        Sensor sensor = new Sensor(sensorId, sensorType, statusType, timestamp, currentValue);
        entityManager.persist(sensor);
    }

    // Encontra por ID
    public Sensor find(long sensorId) throws Exception {
        Sensor sensor = entityManager.find(Sensor.class, sensorId);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor with ID " + sensorId + " not found");
        }
        return sensor;
    }

    // Encontra TODOS os sensores
    public List<Sensor> findAll(){
        return entityManager.createQuery("select s from Sensor s").getResultList();
    }

    // Update de um sensor
    public void update(Long sensorId, SensorType sensorType, StatusType statusType, LocalDateTime timestamp, Double currentValue)
            throws MyEntityNotFoundException, Exception {
        Sensor sensor = find(sensorId);

        sensor.setSensorType(sensorType);
        sensor.setStatusType(statusType);
        sensor.setTimestamp(timestamp);
        sensor.setCurrentValue(currentValue);
        entityManager.merge(sensor);
    }


    // Delete sensor
    public void delete(long sensorId)
            throws Exception {
        Sensor sensor = find(sensorId);
        entityManager.remove(sensor);
    }
}
