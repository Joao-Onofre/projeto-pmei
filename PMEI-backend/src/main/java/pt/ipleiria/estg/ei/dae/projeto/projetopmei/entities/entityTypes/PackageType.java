package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes;

import jakarta.persistence.*;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PackageType {

    //-------------- Atributos ----------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    @OneToMany(mappedBy = "packageType")
    private List<Package> packages;

    //-------------- Construtores ----------------
    public PackageType() {
    }
    public PackageType(String type) {
        this.type = type;
        this.packages = new ArrayList<Package>();
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

    public List<Package> getPackages() {
        return packages;
    }
    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
