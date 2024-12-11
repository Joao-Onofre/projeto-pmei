package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(PackageType packageType, Order order){
        var newPackage = new Package(packageType, order);
        entityManager.persist(newPackage);

        order.getPackageList().add(newPackage);
    }

    public Package find(long id){
        var packagee = entityManager.find(Package.class, id);
        return packagee;
    }
    public List<Package> findAll(){
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }
    public Package findWithProducts(long id){
        var packagee = this.find(id);
        Hibernate.initialize(packagee.getPackageProducts());
        return packagee;
    }
    public Package findWithSensors(long id){
        var packagee = this.find(id);
        Hibernate.initialize(packagee.getSensorList());
        return packagee;
    }
}
