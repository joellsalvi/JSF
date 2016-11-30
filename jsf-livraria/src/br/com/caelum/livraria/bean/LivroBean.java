package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped //ViewScoped Mantem a seção enquanto a view está sendo trabalhada - padrão é RequestScoped, onde é criado 1 sessão de LivroBean por request
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	
	public void carregaLivro() {
		setLivro(new DAO<Livro>(Livro.class).buscaPorId(livroId));
	}

	public void adicionarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		livro.adicionaAutor(autor);
	}
	
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("sucess", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if(this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		FacesContext.getCurrentInstance().addMessage("sucess", new FacesMessage("Livro inserido com sucesso!"));
		
		livro = new Livro();
	}

	public void deveComecarCom1(FacesContext fc, UIComponent ui, Object value) throws ValidatorException {
		if(!value.toString().startsWith("1")) {
			fc.addMessage("error", new FacesMessage("ISBN deve começar com dígito 1"));
		}
	}
	
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public void limpar() {
		livro = new Livro();
	}
	
	public void alterar(Livro livro) {
		this.livro = livro;
	}

	public void remover(Livro livro) {
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removerAutor(autor);
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}
