package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class AlertBean {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(AlertBean.class.getName());

    // Cria um novo alerta
    public void create(Alert alert) {
        try {
            LOGGER.info("Iniciando a criação do alerta: " + alert);

            // Definir timestamp atual caso não esteja definido
            if (alert.getTimestamp() == null) {
                alert.setTimestamp(LocalDateTime.now());  // Atribuindo o timestamp no momento da criação
            }

            // Persistir o alerta
            entityManager.persist(alert);
            LOGGER.info("Alerta persistido com sucesso: " + alert.getAlertId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao persistir o alerta: " + alert, e);
            throw new RuntimeException("Erro ao persistir o alerta", e);
        }
    }

    // Busca alerta por ID
    public Alert find(long alertId) throws MyEntityNotFoundException {
        try {
            Alert alert = entityManager.find(Alert.class, alertId);
            if (alert == null) {
                throw new MyEntityNotFoundException("Alert with ID " + alertId + " not found");
            }
            return alert;
        } catch (NoResultException e) {
            throw new MyEntityNotFoundException("Alert with ID " + alertId + " not found");
        }
    }

    // Busca todos os alertas
    public List<Alert> findAll() {
        return entityManager.createQuery("SELECT a FROM Alert a", Alert.class).getResultList();
    }

    // Busca alertas associados a um sensor específico
    public List<Alert> findBySensor(long sensorId) {
        return entityManager.createQuery(
                        "SELECT a FROM Alert a WHERE a.sensor.sensorId = :sensorId", Alert.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }

    public List<Alert> findBySensorIds(List<Long> sensorIds) {
        return entityManager.createQuery("SELECT a FROM Alert a WHERE a.sensor.id IN :sensorIds", Alert.class)
                .setParameter("sensorIds", sensorIds)
                .getResultList();
    }


    // Atualiza um alerta existente
    public void update(Alert alert) throws MyEntityNotFoundException {
        Alert existingAlert = find(alert.getAlertId());
        if (existingAlert != null) {
            existingAlert.setMessage(alert.getMessage());
            existingAlert.setTimestamp(LocalDateTime.now());

            // Check if the sensor associated with the alert exists
            if (existingAlert.getSensor() != null) {
                // Get the current value of the sensor
                Double currentValue = existingAlert.getSensor().getCurrentValue();

                // Check if the current value exceeds the threshold (20)
                if (currentValue != null && currentValue > 20) {
                    LOGGER.info("Threshold exceeded for sensor ID: " + existingAlert.getSensor().getSensorId());
                    checkAndCreateAlert(existingAlert.getSensor(), 20);
                }
            }

            entityManager.merge(existingAlert);
        }
    }

    // Remove um alerta
    public void delete(long alertId) throws MyEntityNotFoundException {
        Alert alert = find(alertId);
        if (alert != null) {
            entityManager.remove(alert);
        }
    }

    // Verifica se é necessário criar um alerta com base no valor do sensor
    public void checkAndCreateAlert(Sensor sensor, double threshold) {
        if (sensor.getCurrentValue() > threshold) {
            LOGGER.info("Threshold excedido para o sensor ID: " + sensor.getSensorId());

            // Cria o alerta
            Alert alert = new Alert();
            alert.setSensor(sensor);  // Associar o sensor ao alerta
            alert.setTimestamp(LocalDateTime.now());  // Definir timestamp atual
            alert.setMessage("Sensor value exceeded threshold: " + sensor.getCurrentValue());  // Mensagem

            // Persistir o alerta
            create(alert);
        }
    }
}
