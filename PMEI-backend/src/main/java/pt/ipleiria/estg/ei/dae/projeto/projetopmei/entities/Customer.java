package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(
		name = "getAllCustomers",
		query = "SELECT s FROM Customer s ORDER BY s.name" // JPQL
	)
})
public class Customer extends User {
	public Customer() {
	}

	public Customer(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
