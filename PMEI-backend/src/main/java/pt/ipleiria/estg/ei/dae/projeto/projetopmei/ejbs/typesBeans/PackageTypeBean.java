package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

@Stateless
public class PackageTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String type){
        var packageType = new PackageType(type);
        entityManager.persist(packageType);
    }

    public PackageType find(String typeName) {
        PackageType packageType = entityManager.find(PackageType.class, typeName);
        return packageType;
    }
}
