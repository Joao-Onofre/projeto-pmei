package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

@Entity
public class Product {
    //-------------- Atributos ----------------
    @Id
    private long id;
    private String name;
    private String description;
    private float price;
    @ManyToOne
    private ProductType productType;

    //-------------- Construtores ----------------
    public Product() {

    }
    public Product(long id, String name, String description, float price, ProductType productType) {
        this.id = id;
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
    public void setId(long id) {
        this.id = id;
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
}
