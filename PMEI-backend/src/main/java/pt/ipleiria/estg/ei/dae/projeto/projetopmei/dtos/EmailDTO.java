package pt.ipleiria.estg.ei.dae.projeto.projetopmei.dtos;

import pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class EmailDTO implements Serializable {
	//-------------- Atributos ----------------
	private String subject;
	private String body;

	//-------------- Construtores ----------------
	public EmailDTO() {

	}

	public EmailDTO(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}

	//-------------- Metodos ----------------

	//-------------- Getters / Setters ----------------
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
