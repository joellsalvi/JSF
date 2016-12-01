package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String pageViewId = context.getViewRoot().getViewId();
		
		if("/login.xhtml".equals(pageViewId) || "/novoUsuario.xhtml".equals(pageViewId)) {
			return;
		}
		
		if(context.getExternalContext().getSessionMap().get("usuarioLogado") != null) {
			return;
		}
		
		NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
		
		navigationHandler.handleNavigation(context, null, "login?faces-redirect=true");
		
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
