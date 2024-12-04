package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(
		name = "getAllAdministrators",
		query = "SELECT s FROM Administrator s ORDER BY s.name" // JPQL
	)
})
public class Administrator extends User {
	public Administrator() {
	}

	public Administrator(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
