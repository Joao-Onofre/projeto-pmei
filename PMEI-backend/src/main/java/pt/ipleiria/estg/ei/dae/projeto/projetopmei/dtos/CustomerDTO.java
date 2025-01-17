package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Administrator;
import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.Customer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO implements Serializable {
	//-------------- Atributos ----------------
	private String username;
	private String password;
	private String name;
	private String email;
	private String userType;

	//-------------- Construtores ----------------
	public CustomerDTO() {

	}

	public CustomerDTO(String username, String password, String name, String email, String userType) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.userType = userType;
	}

	//-------------- Metodos ----------------
	public static CustomerDTO from(Customer customers) {
		return new CustomerDTO(
				customers.getUsername(),
				customers.getPassword(),
				customers.getName(),
				customers.getEmail(),
				customers.getClass().getSimpleName()
		);
	}


	public static List<CustomerDTO> from(List<Customer> customers) {
		return customers.stream().map(CustomerDTO::from).collect(Collectors.toList());
	}

	//-------------- Getters / Setters ----------------
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
