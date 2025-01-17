package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.id" // JPQL
        ),
        @NamedQuery(
                name = "getProductByName",
                query = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)"
        )
})
public class Product {
    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private float price;
    @ManyToOne
    private ProductType productType;
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts = new ArrayList<>();

    //-------------- Construtores ----------------
    public Product() {

    }
    public Product(String name, String description, float price, ProductType productType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
