package viviendas.model.generic;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Mensaje {
	
	/**
	 * Crea un mensaje JSF
	 * 
	 * @param severidad
	 *            Puede tomar el valor de: <li>FacesMessage.SEVERITY_FATAL <li>
	 *            FacesMessage.SEVERITY_ERROR <li>FacesMessage.SEVERITY_WARN <li>
	 *            FacesMessage.SEVERITY_INFO
	 * @param mensaje
	 *            Contenido del mensaje
	 */
	public static void crearMensaje(Severity severidad, String mensaje) {
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(severidad);
		msg.setSummary(mensaje);
		// msg.setDetail(detalle);
		FacesContext context = FacesContext.getCurrentInstance();
		//context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, msg);
	}

	public static void crearMensajeERROR(String mensaje) {
		crearMensaje(FacesMessage.SEVERITY_ERROR, mensaje);
	}

	public static void crearMensajeWARN(String mensaje) {
		crearMensaje(FacesMessage.SEVERITY_WARN, mensaje);
	}

	public static void crearMensajeINFO(String mensaje) {
		crearMensaje(FacesMessage.SEVERITY_INFO, mensaje);
	}
}
