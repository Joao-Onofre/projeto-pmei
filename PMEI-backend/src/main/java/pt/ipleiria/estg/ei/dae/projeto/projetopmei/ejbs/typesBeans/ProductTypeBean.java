package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

import java.util.List;

@Stateless
public class ProductTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ProductType find(long id) {
        var productType = entityManager.find(ProductType.class, id);
        if (productType == null) {
            throw new RuntimeException("product type " + id + " not found");
        }
        return productType;
    }

    public List<ProductType> findAll() {
        return entityManager.createNamedQuery("getAllProductTypes", ProductType.class).getResultList();
    }

    public ProductType create(String type) {
        var productType = new ProductType(type);
        entityManager.persist(productType);
        return productType;
    }


    public void update(long id, String newType) {
        ProductType productType = find(id);
        productType.setType(newType);
        entityManager.merge(productType);
    }

    public void delete(long id) {
        ProductType productType = find(id);
        if (!productType.getProducts().isEmpty()) {
            throw new RuntimeException("Cannot delete product type with associated products");
        }
        entityManager.remove(productType);
    }
}
