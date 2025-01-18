package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(SensorBean.class.getName());

    // Atualiza o sensor e verifica se há necessidade de criar alertas
    public void update(Sensor sensor, double threshold) throws Exception {
        Sensor existingSensor = entityManager.find(Sensor.class, sensor.getSensorId());

        if (existingSensor == null) {
            throw new MyEntityNotFoundException("Sensor with ID " + sensor.getSensorId() + " not found.");
        }

        // Calcula a diferença entre os valores
        double oldValue = existingSensor.getCurrentValue();
        double newValue = sensor.getCurrentValue();
        double difference = Math.abs(newValue - oldValue);

        // Atualiza os valores do sensor
        existingSensor.setCurrentValue(newValue);
        existingSensor.setTimestamp(new Date());

        // Se a diferença exceder o limite, cria um alerta
        if (difference > threshold) {
            Alert alert = new Alert(
                    "Sensor " + existingSensor.getSensorId() + " exceeded the value by " + difference,
                    new Date(),
                    existingSensor
            );
            entityManager.persist(alert);
            LOGGER.info("Alert generated: " + alert.getMessage());
        }
    }

    // Encontra um sensor pelo ID
    public Sensor find(long sensorId) throws Exception {
        try {
            return entityManager.createQuery(
                            "SELECT s FROM Sensor s WHERE s.sensorId = :sensorId", Sensor.class)
                    .setParameter("sensorId", sensorId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new MyEntityNotFoundException("Sensor with ID " + sensorId + " not found.");
        }
    }

    // Encontra todos os sensores
    public List<Sensor> findAll() {
        return entityManager.createQuery("SELECT s FROM Sensor s", Sensor.class).getResultList();
    }

    // Cria um novo sensor
    public void create(Sensor sensor) {
        try {
            if (sensor.getTimestamp() == null) {
                sensor.setTimestamp(new Date());
            }

            if (sensor.getCurrentValue() == null) {
                sensor.setCurrentValue(0.0); // Define o valor padrão
            }

            entityManager.persist(sensor);
            LOGGER.info("Sensor created: " + sensor.getSensorId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating sensor: " + sensor, e);
            throw new RuntimeException("Error creating sensor", e);
        }
    }

    // Atualiza os dados de um sensor (sem alertas)
    public void update(Sensor sensor) {
        try {
            Sensor existingSensor = entityManager.find(Sensor.class, sensor.getSensorId());

            if (existingSensor == null) {
                throw new MyEntityNotFoundException("Sensor with ID " + sensor.getSensorId() + " not found.");
            }

            existingSensor.setCurrentValue(sensor.getCurrentValue());
            existingSensor.setTimestamp(new Date());
            entityManager.merge(existingSensor);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating sensor: " + sensor, e);
            throw new RuntimeException("Error updating sensor", e);
        }
    }

    // Remove um sensor pelo ID
    public void delete(long sensorId) throws Exception {
        Sensor sensor = find(sensorId);

        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor with ID " + sensorId + " not found.");
        }

        entityManager.remove(sensor);
        LOGGER.info("Sensor deleted: " + sensorId);
    }
}
