package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductType {

    //-------------- Atributos ----------------
    @Id
    private long id;
    private String type;

    @OneToMany(mappedBy = "productType")
    private List<Product> products;

    //-------------- Construtores ----------------
    public ProductType() {
    }
    public ProductType(long id, String type) {
        this.id = id;
        this.type = type;
        this.products = new ArrayList<Product>();
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
