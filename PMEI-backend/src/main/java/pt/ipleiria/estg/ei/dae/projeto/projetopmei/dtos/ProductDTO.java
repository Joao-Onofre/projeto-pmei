package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {
    private long id;
    private String name;
    private String description;
    private float price;
    private long quantity;
    private long productTypeId;
    private String productTypeName;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, String description, float price, long quantity, long productTypeId, String productTypeName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }


    public long getQuantity() {
        return quantity;
    }
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getProductTypeId() {
        return productTypeId;
    }
    public void setProductTypeId(long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                0, // Default quantity as 0 when not provided
                product.getProductType().getId(),
                product.getProductType().getType()
        );
    }

    public static ProductDTO from(Product product, int quantity) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity,
                product.getProductType().getId(),
                product.getProductType().getType()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }

    public static List<ProductDTO> fromWithQuantity(List<Product> products, int quantity) {
        return products.stream()
                .map(product -> ProductDTO.from(product, quantity)) // Provide quantity as needed
                .collect(Collectors.toList());
    }
}
