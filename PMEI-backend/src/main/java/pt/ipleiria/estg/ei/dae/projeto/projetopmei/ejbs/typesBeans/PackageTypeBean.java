package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.util.List;

@Stateless
public class PackageTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String type){
        var packageType = new PackageType(type);
        entityManager.persist(packageType);
    }

    public PackageType findByName(String typeName) {
        PackageType packageType = entityManager.find(PackageType.class, typeName);
        return packageType;
    }
    public PackageType findById(long typeId) {
        PackageType packageType = entityManager.find(PackageType.class, typeId);
        return packageType;
    }

    public List<PackageType> findAll() {
        List<PackageType> packageTypes = entityManager.createNamedQuery("getAllPackageTypes", PackageType.class).getResultList();
        for (PackageType packageType : packageTypes) {
            packageType.getType();
        }
        return packageTypes;
    }
}
