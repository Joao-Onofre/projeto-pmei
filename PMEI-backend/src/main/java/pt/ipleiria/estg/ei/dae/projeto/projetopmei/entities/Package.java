package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.PackageType;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Package {

    //-------------- Atributos ----------------
    @Id
    private long id;
    @ManyToOne
    private PackageType packageType;
    @OneToMany(mappedBy = "pack")
    private List<PackageProduct> packageProducts;
    @OneToMany(mappedBy = "pack")
    private List<Sensor> sensorList;
    @ManyToOne
    private Order order;

    //-------------- Construtores ----------------
    public Package() {
    }
    public Package(long id, String type, PackageType packageType, Order order) {
        this.id = id;
        this.packageType = packageType;
        this.packageProducts = new ArrayList<PackageProduct>();
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
