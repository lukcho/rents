package viviendas.controller.reserve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrReserva;
import viviendas.model.dao.entities.ArrSitioPeriodo;
import viviendas.model.generic.Funciones;
import viviendas.model.generic.Mensaje;
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
	private ArrSitioPeriodo sitio;
	private List<SelectItem> sitiosLibres;
	private List<ArrMatriculado> reservasSitio;
	
	private boolean tokenOk;
	private boolean mayorEdad;
	private boolean finalizado;
	
	public ReservaBean() {
		mngRes = new ManagerReserva();
		periodo = mngRes.buscarPeriodoActivo();
		sitiosLibres = new ArrayList<SelectItem>();
		reservasSitio = new ArrayList<ArrMatriculado>();
		tokenOk = false;
		mayorEdad = false;
		finalizado = false;
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
	 * @return the sitio
	 */
	public ArrSitioPeriodo getSitio() {
		return sitio;
	}

	/**
	 * @param sitio the sitio to set
	 */
	public void setSitio(ArrSitioPeriodo sitio) {
		this.sitio = sitio;
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
	
	/**
	 * @return the finalizado
	 */
	public boolean isFinalizado() {
		return finalizado;
	}

	/**
	 * Verifica si un estudiante se encuentra matriculado 
	 * para poder continuar con el proceso de reserva 
	 */
	public void verificarMatriculado(){
		try {
			if(periodo==null)
				Mensaje.crearMensajeERROR("ERROR AL BUSCAR PERIODO HABILITADO");
			else{
				estudiante = mngRes.buscarMatriculadoPeriodo(getDniEstudiante(), periodo.getPrdId());
				if(estudiante==null)
					Mensaje.crearMensajeWARN("Usted no puede realizar una reserva.");
				else{
					mngRes.generarEnviarToken(getEstudiante());
					RequestContext.getCurrentInstance().execute("PF('dlgtoken').show();");
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("Error verificar matr�cula: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Valida el token enviado al correo
	 */
	public void validarToken(){
		try {
			if(getToken()==null || getToken().trim().isEmpty())
				Mensaje.crearMensajeWARN("Debe ingresar su token para continuar.");
			else if(!mngRes.verificarTokenEstudiante(getEstudiante().getId(), getToken()))
				Mensaje.crearMensajeWARN("Su token es incorrecto. Verif�quelo.");
			else{
				reserva = mngRes.buscarReservaPorID(getDniEstudiante(), periodo.getPrdId());
				if(reserva!=null && reserva.getResEstado().equals(Funciones.estadoFinalizado))
					Mensaje.crearMensajeWARN("Usted ya posee una reserva finalizada:");
				else{
					if(reserva!=null)
						cargarReservaAnterior();					
					tokenOk = true;
					mayorEdad = Funciones.mayorDeEdad(getEstudiante().getMatFechaNacimiento());
					cargarSitiosLibres();
					RequestContext.getCurrentInstance().execute("PF('dlgtoken').hide();");
				}
			}			
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("Error al validar token: "+e.getMessage());
			e.printStackTrace();
		}
	}
		
	/**
	 * Carga los datos de una reserva no finalizada
	 */
	private void cargarReservaAnterior() {
		sitio = reserva.getArrSitioPeriodo();
		//cargarEstudiantesSitio();
	}

	/**
	 * Reenvio de token a estudiante
	 */
	public void reenviarToken(){
		try {
			mngRes.generarEnviarToken(getEstudiante());
			Mensaje.crearMensajeINFO("Token reenviado correctamente.");
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("Error al reenviar token: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Permite reservar un sitio
	 */
	public void reservarSitio(){
		try {
			if(!mayorEdad && (getDniRepresentante()==null || getNombreRepresentante()==null 
					|| getDniRepresentante().trim().isEmpty() || getDniRepresentante().length()<9 || !Funciones.isNumeric(getDniRepresentante())
					|| Funciones.validacionCedula(getDniRepresentante()) || getNombreRepresentante().trim().isEmpty()))
				Mensaje.crearMensajeWARN("Los datos de representante son requeridos, y la c�dula debe ser v�lida.");
			else{
				if(reserva!=null)//POSEE RESERVA
					modificarReserva();
				else
					ingresarReserva();
			}
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("Error al realizar reserva: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Ingresar una nueva reserva
	 */
	private void ingresarReserva() throws Exception{
		if(!mngRes.existeReservaPeriodo(getSitio().getId())){
			if(mayorEdad)
				mngRes.crearReserva(getEstudiante(), getSitio(), periodo.getPrdId(), null);
			else
				mngRes.crearReserva(getEstudiante(), getSitio(), periodo.getPrdId(), getDniRepresentante()+";"+getNombreRepresentante());
			finalizado = true;
			Mensaje.crearMensajeINFO("Reserva realizada correctamente, no olvide descargar su contrato.");
		}else{
			Mensaje.crearMensajeWARN("El sitio seleccionado ya esta copado, favor eliga otro.");
			cargarSitiosLibres();
		}
	}
	
	/**
	 * Modificar una reserva existente
	 */
	private void modificarReserva() throws Exception{
		if(!mngRes.existeReservaPeriodo(getSitio().getId())){
			if(mayorEdad)
				mngRes.modificarReserva(getEstudiante(), periodo.getPrdId(), getSitio(), null);
			else
				mngRes.modificarReserva(getEstudiante(), periodo.getPrdId(), getSitio(), getDniRepresentante()+";"+getNombreRepresentante());
			finalizado = true;
			Mensaje.crearMensajeINFO("Reserva realizada correctamente, no olvide descargar su contrato.");
		}else{
			Mensaje.crearMensajeWARN("El sitio seleccionado ya esta copado, favor eliga otro.");
			cargarSitiosLibres();
		}
	}
	
	/**
	 * Cargar List<SelectItem> de sitios disponibles
	 */
	private void cargarSitiosLibres(){
		List<ArrSitioPeriodo> listado = mngRes.sitiosLibresPorPeriodoGenero(getDniEstudiante(), getEstudiante().getMatGenero());
		getSitiosLibres().clear();
		if(listado!=null && !listado.isEmpty()){
			for (ArrSitioPeriodo sitio : listado) {
				getSitiosLibres().add(new SelectItem(sitio, sitio.getSitNombre()));
			}
		}	
	}
	
	/**
	 * Carga de estudiantes pertenecientes a un sitio
	 */
	public void cargarEstudiantesSitio(){
		List<ArrMatriculado> listado = mngRes.matriculadosEnSitioPorPeriodo(getSitio().getId().getArtId(), periodo.getPrdId());
		getReservasSitio().clear();
		if(listado!=null  && !listado.isEmpty())
			getReservasSitio().addAll(listado);
	}
}
