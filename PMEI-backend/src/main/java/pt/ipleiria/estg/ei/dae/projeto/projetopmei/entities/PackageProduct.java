package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PackageProduct {

    //------------------ Atributos ---------------------
    @Id
    private long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Package pack;
    private int quantity;


    //------------------- Construtor --------------------
    public PackageProduct() {
    }
    public PackageProduct(long id, Product product, Package pack, int quantity) {
        this.id = id;
        this.product = product;
        this.pack = pack;
        this.quantity = quantity;
    }

    //------------------ Metodos -------------------
    //------------------ Getters / Setters --------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Package getPack() {
        return pack;
    }
    public void setPack(Package pack) {
        this.pack = pack;
    }
}
