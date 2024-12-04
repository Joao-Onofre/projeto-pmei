package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.SensorType;

@Stateless
public class SensorTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public SensorType findById(long id) {
        return entityManager.find(SensorType.class, id);
    }

    public SensorType findByName(String name) {
        try {
            return entityManager.createQuery("SELECT s FROM SensorType s WHERE s.type = :name", SensorType.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Handle exception (e.g., type not found)
        }
    }
}
