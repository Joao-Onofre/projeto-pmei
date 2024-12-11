package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {

    //---------------------- Attributes ----------------------
    private long id; // Entity's ID
    private String status;
    private String customerUsername;
    private String creationDate;
    private String deliveryDate;
    private List<PackageDTO> packageList;
    private List<ProductDTO> productList;

    //---------------------- Constructors ----------------------
    public OrderDTO() {
    }

    public OrderDTO(long id, String status, String customerUsername, String creationDate, String deliveryDate) {
        this.id = id;
        this.status = status;
        this.customerUsername = customerUsername;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.packageList = new ArrayList<>();
        this.productList = new ArrayList<>();
    }

    //---------------------- Methods ----------------------
    // Converter entity para DTO
    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus().getStatus(),
                order.getCustomer().getUsername(),
                order.getCreationDate() != null ? order.getCreationDate().toString() : null,
                order.getDeliveryDate() != null ? order.getDeliveryDate().toString() : null
        );
    }

    // Converte uma lista de entities para uma lista de DTOs
    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }

    //---------------------- Getters / Setters ----------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<PackageDTO> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageDTO> packageList) {
        this.packageList = packageList;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }
}
