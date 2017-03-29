/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.clasesAuxiliares;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lucodeveloper
 */

public class filtroUrl implements PhaseListener{

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        
        String currentPage = facesContext.getViewRoot().getViewId();
        
        boolean isPageLogin = currentPage.lastIndexOf("login.xhtml") > -1 ? true : false;
        
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        
        Object usuario = session.getAttribute("usuario");
        
        if(!isPageLogin &&  usuario== null){
            NavigationHandler nHandler = facesContext.getApplication().getNavigationHandler();
            nHandler.handleNavigation(facesContext, null, "/login.xhtml");
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return null; 
    }
    
}
