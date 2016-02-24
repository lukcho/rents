package viviendas.controller.reserve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrReserva;
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
	private ArrPeriodo periodo;
	private String dniRepresentante;
	private String nombreRepresentante;
	private ArrMatriculado estudiante;
	private ArrReserva reserva;
	private List<SelectItem> sitiosLibres;
	private List<ArrMatriculado> reservasSitio;
	
	private boolean tokenOk;
	private boolean mayorEdad;
	
	public ReservaBean() {
		mngRes = new ManagerReserva();
		periodo = mngRes.buscarPeriodoActivo();
		sitiosLibres = new ArrayList<SelectItem>();
		reservasSitio = new ArrayList<ArrMatriculado>();
	}

	/**
	 * @return the dniEstudiante
	 */
	public String getDniEstudiante() {
		return dniEstudiante;
	}

	/**
	 * @param dniEstudiante the dniEstudiante to set
	 */
	public void setDniEstudiante(String dniEstudiante) {
		this.dniEstudiante = dniEstudiante;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the dniRepresentante
	 */
	public String getDniRepresentante() {
		return dniRepresentante;
	}

	/**
	 * @param dniRepresentante the dniRepresentante to set
	 */
	public void setDniRepresentante(String dniRepresentante) {
		this.dniRepresentante = dniRepresentante;
	}

	/**
	 * @return the nombreRepresentante
	 */
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	/**
	 * @param nombreRepresentante the nombreRepresentante to set
	 */
	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}

	/**
	 * @return the estudiante
	 */
	public ArrMatriculado getEstudiante() {
		return estudiante;
	}

	/**
	 * @param estudiante the estudiante to set
	 */
	public void setEstudiante(ArrMatriculado estudiante) {
		this.estudiante = estudiante;
	}

	/**
	 * @return the reserva
	 */
	public ArrReserva getReserva() {
		return reserva;
	}

	/**
	 * @param reserva the reserva to set
	 */
	public void setReserva(ArrReserva reserva) {
		this.reserva = reserva;
	}

	/**
	 * @return the sitiosLibres
	 */
	public List<SelectItem> getSitiosLibres() {
		return sitiosLibres;
	}

	/**
	 * @return the reservasSitio
	 */
	public List<ArrMatriculado> getReservasSitio() {
		return reservasSitio;
	}

	/**
	 * @return the tokenOk
	 */
	public boolean isTokenOk() {
		return tokenOk;
	}

	/**
	 * @return the mayorEdad
	 */
	public boolean isMayorEdad() {
		return mayorEdad;
	}
	
	

}
