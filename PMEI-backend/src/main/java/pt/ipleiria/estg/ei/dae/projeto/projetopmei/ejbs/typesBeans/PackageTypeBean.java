package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

@Stateless
public class PackageTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String type){
        var packageType = new PackageType(id, type);
        entityManager.persist(packageType);
    }

}
