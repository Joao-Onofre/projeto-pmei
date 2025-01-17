package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Cart;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CartDTO implements Serializable {
    private long id;
    private String customerId;
    private List<ProductWithQuantityDTO> products = new ArrayList<>();

    public CartDTO() {
    }

    public CartDTO(long id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<ProductWithQuantityDTO> getProducts() {
        return products;
    }
    public void setProducts(List<ProductWithQuantityDTO> products) {
        this.products = products;
    }

    public static CartDTO from(Cart cart) {
        CartDTO dto = new CartDTO(cart.getId(), cart.getCustomer().getUsername());
        cart.getProducts().forEach((product, quantity) -> {
            dto.products.add(ProductWithQuantityDTO.from(product, quantity));
        });
        return dto;
    }

    public static List<CartDTO> from(List<Cart> carts) {
        return carts.stream().map(CartDTO::from).collect(Collectors.toList());
    }
}


