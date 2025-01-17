package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.OrderStatusType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class PackageTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public PackageType create(String type){
        var packageType = new PackageType(type);
        entityManager.persist(packageType);
        return packageType;
    }

    public PackageType update(long id, String type) throws MyEntityNotFoundException {
        PackageType packageType = findById(id);
        if(packageType == null){
            throw new MyEntityNotFoundException("Package Type" + id + "not found");
        }

        PackageType existingPackageType = findByName(type);
        if(existingPackageType != null){
            throw new RuntimeException("Package Type already exists");
        }

        System.out.println("PACKAGE TYPE ID: "+id);
        System.out.println("PACKAGE TYPE TYPE: "+type);
        //Previnir concorrencia
        entityManager.lock(packageType, LockModeType.OPTIMISTIC);
        packageType.setType(type);

        entityManager.merge(packageType);
        return packageType;

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
