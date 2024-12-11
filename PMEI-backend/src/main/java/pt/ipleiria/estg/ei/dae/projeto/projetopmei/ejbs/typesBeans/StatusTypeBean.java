package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.StatusType;

import java.util.List;

@Stateless
public class StatusTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public StatusType findById(long id) {
        return entityManager.find(StatusType.class, id);
    }

    public StatusType findByName(String name) {
        List<StatusType> result = entityManager.createQuery("SELECT s FROM StatusType s WHERE s.status = :name", StatusType.class)
                .setParameter("name", name)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);  // Return null if no result found
    }

    public void create(StatusType statusType) {
        entityManager.persist(statusType);
    }
}


