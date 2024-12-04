package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

@Stateless
public class StatusTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public StatusType findById(long id) {
        return entityManager.find(StatusType.class, id);
    }

    public StatusType findByName(String name) {
        try {
            return entityManager.createQuery("SELECT s FROM StatusType s WHERE s.status = :name", StatusType.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Handle exception (e.g., type not found)
        }
    }
}

