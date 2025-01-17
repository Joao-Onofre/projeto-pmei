package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

public class ProductWithQuantityDTO {
    private long id;
    private String name;
    private String description;
    private float price;
    private long productTypeId;
    private String productTypeName;
    private int quantity;

    public ProductWithQuantityDTO() {

    }

    public ProductWithQuantityDTO(long id, String name, String description, float price, long productTypeId, String productTypeName, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static ProductWithQuantityDTO from(Product product, int quantity) {
        return new ProductWithQuantityDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getProductType().getId(),
                product.getProductType().getType(),
                quantity
        );
    }
}

