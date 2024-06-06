package es.iecisa.jfaces;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class RequestInterceptor implements PhaseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // Do your job here which should run before the render response phase.
    	System.out.println( "before phase:" + event);
    	System.out.println(" source:" + event.getSource());
    	System.out.println(" la ip: " + event.getFacesContext().getExternalContext().getRequest() );
    	System.out.println(" la aplicacion" + event.getFacesContext().getApplication() ); 
    	
    	
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        // Do your job here which should run after the render response phase.
    	System.out.println( "afer phase:" + event);
    	System.out.println( "before phase:" + event);
    	System.out.println(" source:" + event.getSource());
    	System.out.println(" la ip: " + event.getFacesContext().getExternalContext().getRequest() );
    	System.out.println(" la aplicacion" + event.getFacesContext().getApplication() );
    }

}
