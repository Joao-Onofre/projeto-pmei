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
    private long pack;
    private int quantity;

    //------------------- Construtores ---------------------
    public PackageProductDTO() {

    }
    public PackageProductDTO(long id, long product, long pack, int quantity) {
        this.id = id;
        this.product = product;
        this.pack = pack;
        this.quantity = quantity;
    }

    //------------------- Metodos ---------------------
    // Converter entity para DTO
    public static PackageProductDTO from(PackageProduct packageProduct) {
        return new PackageProductDTO(
                packageProduct.getId(),
                packageProduct.getProduct().getId(),
                packageProduct.getPack().getId(),
                packageProduct.getQuantity()
        );
    }

    // Converte uma lista de entities para uma lista de DTOs
    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }
}
