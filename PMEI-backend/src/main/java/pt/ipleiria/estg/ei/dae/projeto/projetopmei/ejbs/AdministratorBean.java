package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Administrator;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Hasher;

import java.util.List;

@Stateless
public class AdministratorBean {
	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private Hasher hasher;
	@EJB
	private UserBean userBean;

	public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
		userBean.failIfExists(username);
		try {
			var administrator = new Administrator(username, hasher.hash(password), name, email);
			entityManager.persist(administrator);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public List<Administrator> findAll() {
		return entityManager.createNamedQuery("getAllAdministrators", Administrator.class).getResultList();
	}

	public Administrator find(String username) throws MyEntityNotFoundException {
		var administrator = entityManager.find(Administrator.class, username);
		if (administrator == null) {
			throw new MyEntityNotFoundException(
				"Administrator with username '" + username + "' not found"
			);
		}
		return administrator;
	}

	public void update(String username, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException {
		var administrator = find(username);

		administrator.setPassword(hasher.hash(password));
		administrator.setName(name);
		administrator.setEmail(email);

		try {
			entityManager.merge(administrator);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public void delete(String username) throws MyEntityNotFoundException {
		var administrator = find(username);
		entityManager.remove(administrator);
	}
}
