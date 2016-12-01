package br.com.caelum.livraria.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private Integer id;
	private String senha;
	
	@Email
	private String email;
	@Transient
	private String confirmarSenha;
	@Transient
	private String confirmarEmail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getConfirmarEmail() {
		return confirmarEmail;
	}

	public void setConfirmarEmail(String confirmarEmail) {
		this.confirmarEmail = confirmarEmail;
	}

}
