package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Order;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Package;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.PackageProduct;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackageProductDTO implements Serializable {

    //------------------- Atributos ---------------------
    private long id;
    private long product;
    private String productName;
    private long quantity;

    //------------------- Construtores ---------------------
    public PackageProductDTO() {

    }
    public PackageProductDTO(long id, long product, String productName, long quantity) {
        this.id = id;
        this.product = product;
        this.productName = productName;
        this.quantity = quantity;
    }

    //------------------- Metodos ---------------------
    // Converter entity para DTO
    public static PackageProductDTO from(PackageProduct packageProduct) {
        return new PackageProductDTO(
                packageProduct.getId(),
                packageProduct.getProduct().getId(),
                packageProduct.getProduct().getName(),
                packageProduct.getQuantity()
        );
    }

    public static List<PackageProductDTO> from(List<PackageProduct> packageProducts) {
        return packageProducts.stream().map(PackageProductDTO::from).collect(Collectors.toList());
    }

    //--------------------- Getters / Setters ---------------------------
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getProduct() {
        return product;
    }
    public void setProduct(long product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
