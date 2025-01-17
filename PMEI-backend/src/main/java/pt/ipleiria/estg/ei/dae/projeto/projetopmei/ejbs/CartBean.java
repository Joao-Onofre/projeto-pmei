package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Cart;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Product;

@Stateless
public class CartBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Cart findByCustomer(Customer customer) {
        return entityManager.createQuery(
                        "SELECT c FROM Cart c LEFT JOIN FETCH c.products WHERE c.customer = :customer", Cart.class
                )
                .setParameter("customer", customer)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public void updateCart(Cart cart) {
        entityManager.merge(cart);
    }

    public Cart findOrCreateCartForCustomer(Customer customer) {
        Cart cart = findByCustomer(customer);
        if (cart == null) {
            cart = createCartForCustomer(customer);
        }
        return cart;
    }

    public Cart createCartForCustomer(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        entityManager.persist(cart);
        return cart;
    }

    public void addProductToCart(Cart cart, Product product) {
        cart.addProduct(product);
        entityManager.merge(cart);
    }

    public void removeProductFromCart(Cart cart, Product product) {
        cart.removeProduct(product);
        entityManager.merge(cart);
    }
}



