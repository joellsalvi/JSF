package br.com.caelum.livraria.dao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDAO {

	public boolean findByEmailAndSenha(String email, String senha) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u "
				+ " where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);
		
		try {
			query.getSingleResult();
		} catch (NoResultException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário não encontrado no sistema, por favor tente novamente"));
			return false;
		}
		
		em.close();
		
		return true;
	}

}
