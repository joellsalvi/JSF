package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.UsuarioDAO;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();
	private boolean autenticarAposCriarUsuario = false;
	
	public String efetuarLogin() {
		boolean usuarioExistente = new UsuarioDAO().findByEmailAndSenha(this.usuario.getEmail(), this.usuario.getSenha());
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(usuarioExistente) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		
		return "login?faces-redirect=true";
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

	public String gravarUsuario() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			new DAO<Usuario>(Usuario.class).adiciona(this.usuario);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return "novoUsuario?faces-redirect=true";
		}
		
		context.addMessage(null, new FacesMessage("Usuário cadastrado com sucesso!"));
		
		if(isAutenticarAposCriarUsuario()) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}
		
		this.usuario = new Usuario();
		return "login?faces-redirect=true";
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public boolean isAutenticarAposCriarUsuario() {
		return autenticarAposCriarUsuario;
	}

	public void setAutenticarAposCriarUsuario(boolean autenticarAposCriarUsuario) {
		this.autenticarAposCriarUsuario = autenticarAposCriarUsuario;
	}

}
