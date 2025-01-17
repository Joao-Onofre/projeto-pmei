package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos.auth;


import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class AuthSetPasswordDTO implements Serializable {
	//-------------- Atributos ----------------
	@NotBlank
	private String oldPassword;
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;

	//-------------- Construtores ----------------
	public AuthSetPasswordDTO() {}

	public AuthSetPasswordDTO(String oldPassword, String newPassword, String confirmPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	//-------------- Metodos ----------------

	//-------------- Getters / Setters ----------------
	public @NotBlank String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(@NotBlank String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public @NotBlank String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(@NotBlank String newPassword) {
		this.newPassword = newPassword;
	}

	public @NotBlank String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(@NotBlank String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
