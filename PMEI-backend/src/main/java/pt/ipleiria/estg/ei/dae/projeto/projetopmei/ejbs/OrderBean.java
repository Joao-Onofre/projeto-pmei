package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, PackageType packageType, Order order){
        var newPackage = new Package(id, packageType, order);
        entityManager.persist(packageType);
    }
}
