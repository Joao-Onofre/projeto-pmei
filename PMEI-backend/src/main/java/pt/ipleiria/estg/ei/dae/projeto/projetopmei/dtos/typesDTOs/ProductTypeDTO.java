package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.entityTypes.ProductType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductTypeDTO implements Serializable {
    private long id;
    private String type;

    public ProductTypeDTO() {

    }

    // Constructor with parameters
    public ProductTypeDTO(long id, String type) {
        this.id = id;
        this.type = type;
    }

    // Getters and Setters
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

    public static ProductTypeDTO from(ProductType productType) {
        return new ProductTypeDTO(
                productType.getId(),
                productType.getType()
        );
    }

    public static List<ProductTypeDTO> from(List<ProductType> productTypes) {
        return productTypes.stream().map(ProductTypeDTO::from).collect(Collectors.toList());
    }
}
