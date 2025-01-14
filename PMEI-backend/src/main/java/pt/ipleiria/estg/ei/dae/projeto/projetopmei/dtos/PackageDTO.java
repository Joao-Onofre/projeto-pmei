package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PackageDTO implements Serializable {

    //------------------- Atributos ---------------------
    private long id;
    private long packageType;
    //private List<ProductDTO> products;
    private List<PackageProductDTO> packageProducts;

    //private List<SensorDTO> sensorList;
    private long order;

    //------------------- Construtores ---------------------
    public PackageDTO() {

    }

    public PackageDTO(long id, long packageType, long order, List<PackageProductDTO> packageProducts) {
        this.id = id;
        this.packageType = packageType;
        this.order = order;
        //this.products = new ArrayList<>();
        this.packageProducts = packageProducts;
        //this.sensorList = new ArrayList<>();
    }

    //------------------- Metodos ---------------------
    // Converter entity para DTO
    public static PackageDTO from(Package pack) {
        return new PackageDTO(
                pack.getId(),
                pack.getPackageType().getId(),
                pack.getOrder().getId(),
                pack.getPackageProducts().stream()
                        .map(PackageProductDTO::from) // Map each PackageProduct to PackageProductDTO
                        .collect(Collectors.toList()) // Collect into a list

        );
    }

    // Converte uma lista de entities para uma lista de DTOs
    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }



    //------------------- Atributos ---------------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getPackageType() {
        return packageType;
    }
    public void setPackageType(long packageType) {
        this.packageType = packageType;
    }

    /*
    public List<ProductDTO> getProducts() {
        return products;
    }
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
    */

    public List<PackageProductDTO> getPackageProducts() {
        return packageProducts;
    }
    public void setPackageProducts(List<PackageProductDTO> packageProducts) {
        this.packageProducts = packageProducts;
    }

    /*
    public List<SensorDTO> getSensorList() {
        return sensorList;
    }
    public void setSensorList(List<SensorDTO> sensorList) {
        this.sensorList = sensorList;
    }
    */

    public long getOrder() {
        return order;
    }
    public void setOrder(long order) {
        this.order = order;
    }
}
