package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.User;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Hasher;

import java.util.List;

@Stateless
public class UserBean {
	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private Hasher hasher;

	public boolean exists(String username) {
		Query query = entityManager.createQuery(
			"SELECT COUNT(s.username) FROM User s WHERE s.username = :username",
			Long.class
		);
		query.setParameter("username", username);
		return (Long)query.getSingleResult() > 0L;
	}

	public void failIfExists(String username) throws MyEntityExistsException {
		if (exists(username)) {
			throw new MyEntityExistsException(
				"User with username '" + username + "' already exists");
		}
	}

	public User findOrFail(String username) {
		var user = entityManager.getReference(User.class, username);
		Hibernate.initialize(user);
		return user;
	}

	public boolean canLogin(String username, String password) {
		try {
			var user = find(username);
			return user.getPassword().equals(hasher.hash(password));
		} catch (MyEntityNotFoundException e) {
			return false;
		}
	}

	public User find(String username) throws MyEntityNotFoundException {
		var user = entityManager.find(User.class, username);
		if (user == null) {
			throw new MyEntityNotFoundException(
				"User with username '" + username + "' not found"
			);
		}
		return user;
	}

	public List<User> findAll() {
		return entityManager.createNamedQuery("getAllUsers", User.class).getResultList();
	}
}
