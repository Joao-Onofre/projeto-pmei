package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class AuthDTO implements Serializable {
	//-------------- Atributos ----------------
	@NotBlank
	private String username;
	@NotBlank
	private String password;

	//-------------- Construtores ----------------
	public AuthDTO() {}

	public AuthDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	//-------------- Metodos ----------------

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
}
