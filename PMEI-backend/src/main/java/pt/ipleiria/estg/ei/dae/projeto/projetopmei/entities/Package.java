package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p" // JPQL
        )
})
public class Package {

    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private PackageType packageType;
    @OneToMany(mappedBy = "pack",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PackageProduct> packageProducts = new ArrayList<>();
    @OneToMany(mappedBy = "pack", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Sensor> sensorList;
    @ManyToOne
    private Order order;

    //-------------- Construtores ----------------
    public Package() {
    }
    public Package(PackageType packageType, Order order) {
        this.packageType = packageType;
        this.sensorList = new ArrayList<Sensor>();
        this.order = order;
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public PackageType getPackageType() {
        return packageType;
    }
    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public List<PackageProduct> getPackageProducts() {
        return packageProducts;
    }
    public void setPackageProducts(List<PackageProduct> packageProducts) {
        this.packageProducts = packageProducts;
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }
    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
