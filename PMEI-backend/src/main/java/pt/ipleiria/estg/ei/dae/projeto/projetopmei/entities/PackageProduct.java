package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class PackageProduct {

    //------------------ Atributos ---------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Package pack;
    private long quantity;

    //------------------- Construtor --------------------
    public PackageProduct() {
    }
    public PackageProduct(Product product, Package pack, long quantity) {
        this.product = product;
        this.pack = pack;
        this.quantity = quantity;
    }

    //------------------ Metodos -------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageProduct that = (PackageProduct) o;
        return Objects.equals(product, that.product) && Objects.equals(pack, that.pack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, pack);
    }
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

    public long getQuantity() {
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
