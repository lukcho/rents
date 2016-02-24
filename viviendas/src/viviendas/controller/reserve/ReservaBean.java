package viviendas.controller.reserve;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import viviendas.model.manager.ManagerReserva;

@ManagedBean
@ViewScoped
public class ReservaBean implements Serializable{

	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = 6733054739694344059L;
	
	private ManagerReserva mngRes;
	
	private String dniEstudiante;
	private String token;
	
	public ReservaBean() {
		mngRes = new ManagerReserva();
	}
	
	

}
