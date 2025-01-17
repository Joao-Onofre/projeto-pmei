package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO implements Serializable {
	//-------------- Atributos ----------------
	private String username;
	private String password;
	private String name;
	private String email;
	private String userType;

	//-------------- Construtores ----------------
	public UserDTO() {

	}

	public UserDTO(String username, String password, String name, String email, String userType) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.userType = userType;
	}

	//-------------- Metodos ----------------
	public static UserDTO from(User user) {
		return new UserDTO(
				user.getUsername(),
				user.getPassword(),
				user.getName(),
				user.getEmail(),
				user.getClass().getSimpleName()
		);
	}

	public static List<UserDTO> from(List<User> users) {
		return users.stream().map(UserDTO::from).collect(Collectors.toList());
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
