package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producttypes")
@NamedQueries({
        @NamedQuery(
                name = "getAllProductTypes",
                query = "SELECT p FROM ProductType p ORDER BY p.type" // Use 'type' instead of 'name'
        )
})
public class ProductType {

    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;

    @OneToMany(mappedBy = "productType")
    private List<Product> products;

    //-------------- Construtores ----------------
    public ProductType() {
    }
    public ProductType(String type) {
        this.type = type;
        this.products = new ArrayList<Product>();
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------

    public long getId() {
        return id;
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
