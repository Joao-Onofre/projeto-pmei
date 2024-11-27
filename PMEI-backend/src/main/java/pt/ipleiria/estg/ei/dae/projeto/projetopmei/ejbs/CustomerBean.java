package pt.ipleiria.estg.ei.dae.projeto.projetopmei.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.security.Hasher;

import java.util.List;

@Stateless
public class CustomerBean {
	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private Hasher hasher;
	@EJB
	private UserBean userBean;

	public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
		userBean.failIfExists(username);
		try {
			var customer = new Customer(username, hasher.hash(password), name, email);
			entityManager.persist(customer);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public List<Customer> findAll() {
		return entityManager.createNamedQuery("getAllCustomers", Customer.class).getResultList();
	}

	public Customer find(String username) throws MyEntityNotFoundException {
		var customer = entityManager.find(Customer.class, username);
		if (customer == null) {
			throw new MyEntityNotFoundException(
				"Customer with username '" + username + "' not found"
			);
		}
		return customer;
	}

	public void update(String username, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException {
		var customer = find(username);

		customer.setPassword(hasher.hash(password));
		customer.setName(name);
		customer.setEmail(email);

		try {
			entityManager.merge(customer);
		} catch (ConstraintViolationException e) {
			throw new MyConstraintViolationException(e);
		}
	}

	public void delete(String username) throws MyEntityNotFoundException {
		var customer = find(username);
		entityManager.remove(customer);
	}
}
