package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Alert;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Sensor;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

    private static final Logger LOGGER = Logger.getLogger(SensorBean.class.getName());

    // Método para encontrar ou criar o StatusType
    private StatusType findOrCreateStatusType(String status) {
        try {
            // Tenta encontrar um StatusType com o status fornecido
            return entityManager.createQuery(
                            "SELECT s FROM StatusType s WHERE s.status = :status",
                            StatusType.class)
                    .setParameter("status", status)
                    .getSingleResult();  // Retorna o StatusType existente
        } catch (NoResultException e) {
            // Se não encontrar, cria um novo StatusType
            StatusType newStatus = new StatusType(status);
            entityManager.persist(newStatus);  // Persiste o novo StatusType
            return newStatus;
        }
    }

    // Cria um novo sensor
    public void create(Sensor sensor) {
        try {
            // Log da criação do sensor
            LOGGER.info("Iniciando a criação do sensor: " + sensor);

            // Verificar se o StatusType já existe, se não, cria um novo
            if (sensor.getStatusType() != null) {
                StatusType existingStatus = findOrCreateStatusType(sensor.getStatusType().getStatus());
                sensor.setStatusType(existingStatus);  // Atualiza o sensor com o StatusType existente
                LOGGER.info("StatusType associado ao sensor: " + existingStatus.getId());
            }

            // Verificar se o SensorType já existe, se não, cria um novo
            if (sensor.getSensorType() != null && sensor.getSensorType().getId() == null) {
                entityManager.persist(sensor.getSensorType());  // Persiste o SensorType se necessário
                LOGGER.info("SensorType persistido: " + sensor.getSensorType().getId());
            }

            // Definir valores padrão para o currentValue e timestamp
            if (sensor.getCurrentValue() == null) {
                sensor.setCurrentValue(0.0);  // Valor padrão para currentValue
            }

            if (sensor.getTimestamp() == null) {
                sensor.setTimestamp(new java.util.Date());  // Define o timestamp com a data e hora atuais
            }

            // Persistir o Sensor
            entityManager.persist(sensor);
            LOGGER.info("Sensor persistido com sucesso: " + sensor.getSensorId());

        } catch (Exception e) {
            // Log de erro detalhado
            LOGGER.log(Level.SEVERE, "Erro ao persistir o sensor: " + sensor, e);
            e.printStackTrace();
            throw new RuntimeException("Erro ao persistir o sensor", e);
        }
    }

    // Encontra por ID
    public Sensor find(long sensorId) throws Exception {
        try {
            // Buscar o Sensor diretamente sem fazer join com outras tabelas
            Sensor sensor = entityManager.createQuery(
                            "SELECT s FROM Sensor s WHERE s.sensorId = :sensorId", Sensor.class)
                    .setParameter("sensorId", sensorId)
                    .getSingleResult();

            if (sensor == null) {
                throw new MyEntityNotFoundException("Sensor with ID " + sensorId + " not found");
            }
            return sensor;
        } catch (NoResultException e) {
            throw new MyEntityNotFoundException("Sensor with ID " + sensorId + " not found");
        }
    }

    // Encontra TODOS os sensores
    public List<Sensor> findAll(){
        return entityManager.createQuery("select s from Sensor s").getResultList();
    }

    public List<Sensor> findByTypeAndStatus(long sensortypeId, long statustypeId) {
        return entityManager.createQuery(
                        "SELECT s FROM Sensor s WHERE s.sensorType.id = :sensortypeId AND s.statusType.id = :statustypeId", Sensor.class)
                .setParameter("sensortypeId", sensortypeId)
                .setParameter("statustypeId", statustypeId)
                .getResultList();
    }



    // Update de um sensor
    public void update(Sensor sensor, double threshold) throws Exception {
        // Procurar o sensor existente pelo ID
        Sensor existingSensor = entityManager.find(Sensor.class, sensor.getSensorId());

        if (existingSensor == null) {
            throw new MyEntityNotFoundException("Sensor with ID " + sensor.getSensorId() + " not found.");
        }

        // Obter o valor atual e o valor novo
        double oldValue = existingSensor.getCurrentValue();
        double newValue = sensor.getCurrentValue();

        // Atualizar o valor do sensor
        existingSensor.setCurrentValue(newValue);
        existingSensor.setTimestamp(new Date());

        // Verificar se o valor atual excede o limite
        if (newValue > threshold) {
            double exceededAmount = newValue - threshold;

            // Criar um alerta
            Alert alert = new Alert();
            alert.setSensor(existingSensor);
            alert.setMessage("Exceeded the threshold by " + exceededAmount + " units.");

            // Persistir o alerta
            entityManager.persist(alert);

            LOGGER.info("Alert created: " + alert.getMessage());
        }

        // Atualizar o StatusType se necessário
        existingSensor.setStatusType(sensor.getStatusType());

        // Persistir as alterações do sensor
        entityManager.merge(existingSensor);
    }



    // Delete sensor
    public void delete(long sensorId)
            throws Exception {
        Sensor sensor = find(sensorId);
        entityManager.remove(sensor);
    }
}
