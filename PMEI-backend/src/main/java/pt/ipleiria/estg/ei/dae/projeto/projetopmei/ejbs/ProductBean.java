package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs.typesBeans.ProductTypeBean;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductTypeBean productTypeBean;

    public Product find(long id) {
        var product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new RuntimeException("product " + id + " not found");
        }
        return product;
    }

    public List<Product> findByName(String name) {
        return entityManager.createNamedQuery("getProductByName", Product.class).setParameter("name", "%" + name + "%").getResultList();
    }

    public List<Product> findAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product create(String name, String description, float price, long productTypeId) {
        ProductType productType = productTypeBean.find(productTypeId);
        var product = new Product(name, description, price, productType);
        entityManager.persist(product);
        return product;
    }

    public void update(long id, String name, String description, float price, long productTypeId) {
        Product product = this.find(id);
        ProductType productType = productTypeBean.find(productTypeId);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setProductType(productType);

        entityManager.merge(product); // Save the changes to the database
    }


    public void delete(long id) {
        Product product = this.find(id);
        entityManager.remove(product);
    }
}
