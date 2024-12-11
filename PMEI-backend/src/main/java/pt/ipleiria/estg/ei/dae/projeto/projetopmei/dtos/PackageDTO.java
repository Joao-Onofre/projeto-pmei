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
    private String packageType;
    private List<PackageProductDTO> packageProducts;
    private List<SensorDTO> sensorList;
    private long order;

    //------------------- Construtores ---------------------
    public PackageDTO() {

    }

    public PackageDTO(long id, String packageType, long order) {
        this.id = id;
        this.packageType = packageType;
        this.packageProducts = new ArrayList<>();
        this.sensorList = new ArrayList<>();
        this.order = order;
    }

    //------------------- Metodos ---------------------
    // Converter entity para DTO
    public static PackageDTO from(Package packageEntity) {
        return new PackageDTO(
                packageEntity.getId(),
                packageEntity.getPackageType().getType(),
                packageEntity.getOrder().getId()
        );
    }

    // Converte uma lista de entities para uma lista de DTOs
    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }

    //------------------- Atributos ---------------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getPackageType() {
        return packageType;
    }
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public List<PackageProductDTO> getPackageProducts() {
        return packageProducts;
    }
    public void setPackageProducts(List<PackageProductDTO> packageProducts) {
        this.packageProducts = packageProducts;
    }

    public List<SensorDTO> getSensorList() {
        return sensorList;
    }
    public void setSensorList(List<SensorDTO> sensorList) {
        this.sensorList = sensorList;
    }

    public long getOrder() {
        return order;
    }
    public void setOrder(long order) {
        this.order = order;
    }
}
