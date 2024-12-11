package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;

import java.util.List;

@Stateless
public class SensorTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public SensorType findById(long id) {
        return entityManager.find(SensorType.class, id);
    }

    public SensorType findByName(String name) {
        List<SensorType> result = entityManager.createQuery("SELECT s FROM SensorType s WHERE s.type = :name", SensorType.class)
                .setParameter("name", name)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);  // Return null if no result found
    }

    public void create(SensorType sensorType) {
        entityManager.persist(sensorType);
    }
}

