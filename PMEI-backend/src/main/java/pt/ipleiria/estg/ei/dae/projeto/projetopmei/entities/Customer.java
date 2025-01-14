package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@NamedQueries({
	@NamedQuery(
		name = "getAllCustomers",
		query = "SELECT s FROM Customer s ORDER BY s.name" // JPQL
	)
})
public class Customer extends User {

	@OneToMany(mappedBy = "customer")
	private List<Order> orderList;
	public Customer() {
	}

	public Customer(String username, String password, String name, String email) {
		super(username, password, name, email);
	}

	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
}
