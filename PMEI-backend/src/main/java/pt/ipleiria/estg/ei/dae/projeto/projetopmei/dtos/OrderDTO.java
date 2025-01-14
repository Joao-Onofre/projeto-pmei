package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {

    //---------------------- Attributes ----------------------
    private long id; // Entity's ID
    private long status;
    private String customerUsername;
    private String creationDate;
    private String deliveryDate;
    private String terminationDate;
    private boolean terminated;
    private List<PackageDTO> packageList;
    private List<ProductDTO> productList;

    //---------------------- Constructors ----------------------
    public OrderDTO() {
    }

    public OrderDTO(long id, long status, String customerUsername, String creationDate, String deliveryDate, String terminationDate, boolean terminated,List<PackageDTO> packageList) {
        this.id = id;
        this.status = status;
        this.customerUsername = customerUsername;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.terminationDate = terminationDate;
        this.terminated = terminated;
        this.packageList = packageList;

    }

    //---------------------- Methods ----------------------
    // Converter entity para DTO
    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus().getId(),
                order.getCustomer().getUsername(),
                order.getCreationDate() != null ? order.getCreationDate().toString() : null,
                order.getDeliveryDate() != null ? order.getDeliveryDate().toString() : null,
                order.getTerminationDate() != null ? order.getTerminationDate().toString() : null,
                order.isTerminated(),
                order.getPackageList().stream().map(PackageDTO::from).collect(Collectors.toList())
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

    public long getStatus() {
        return status;
    }
    public void setStatus(long status) {
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

    public String getTerminationDate() {
        return terminationDate;
    }
    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public boolean isTerminated() {
        return terminated;
    }
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
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
