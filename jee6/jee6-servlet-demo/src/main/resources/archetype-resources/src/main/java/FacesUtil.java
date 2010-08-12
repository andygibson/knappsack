#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Simple Utility bean for making it easier to add error messages 
 * 
 * @author Andy Gibson
 * 
 */
public class FacesUtil {

	public static void addError(String message) {
		addError(message, null);
	}

	public static void addError(String message, String compId) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message);

		FacesContext.getCurrentInstance().addMessage(compId, msg);
	}
}
