package viviendas.model.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.dao.entities.ArrMatriculadoPK;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrReserva;
import viviendas.model.dao.entities.ArrSitioPeriodo;
import viviendas.model.dao.entities.ArrSitioPeriodoPK;
import viviendas.model.generic.Funciones;
import viviendas.model.generic.Mail;

/**
 * Contiene la lógica de negocio para realizar el proceso de reserva de sitios
 * @author lcisneros
 *
 */
public class ManagerReserva {
	private ManagerDAO mngDao;
	
	public ManagerReserva() {
		mngDao = new ManagerDAO();
	}
	
	/**
	 * Devuelve el último periodo activo
	 * @return ArrPeriodo
	 */
	@SuppressWarnings("unchecked")
	public ArrPeriodo buscarPeriodoActivo(){
		List<ArrPeriodo> listado = mngDao.findWhere(ArrPeriodo.class, "o.prdEstado='A'", null);
		if(listado.isEmpty())
			return null;
		else
			return listado.get(0);
	}
	
	/**
	 * Busca un estudiante matriculado en un periodo
	 * @param perDNI 
	 * @param prdID
	 * @return ArrMatriculado
	 * @throws Exception
	 */
	public ArrMatriculado buscarMatriculadoPeriodo(String perDNI, String prdID) throws Exception{
		ArrMatriculadoPK pk = new ArrMatriculadoPK();
		pk.setPerDni(perDNI);
		pk.setPrdId(prdID);
		return (ArrMatriculado) mngDao.findById(ArrMatriculado.class, pk);
	}
	
	/**
	 * Genera y envía un mail con el token de acceso
	 * @param estudiante
	 * @throws Exception
	 */
	public void generarEnviarToken(ArrMatriculado estudiante) throws Exception{
		String token = Funciones.genPass();
		estudiante.setMatToken(token);
		mngDao.actualizar(estudiante);
		StringBuilder destino = new StringBuilder();
		if((estudiante.getMatCorreo()!=null || !estudiante.getMatCorreo().isEmpty()) && 
				(estudiante.getMatCorreoIns()!=null || !estudiante.getMatCorreoIns().isEmpty())){
			destino.append(estudiante.getMatCorreo());
			destino.append(",");
			destino.append(estudiante.getMatCorreoIns());
		}else if(estudiante.getMatCorreo()!=null || !estudiante.getMatCorreo().isEmpty())
			destino.append(estudiante.getMatCorreo());
		else if(estudiante.getMatCorreoIns()!=null || !estudiante.getMatCorreoIns().isEmpty())
			destino.append(estudiante.getMatCorreoIns());
		Mail.generateAndSendEmail(destino.toString(),
				"Token para reserva de vivienda",
				"Saludos cordiales, <br/>Su token de acceso es: "+token+
				"<br/><br/>Atentamente,<br/>Sistema de Arriendos Yachay.");
	}
	
	/**
	 * Verifica si el token ingresado es correcto
	 * @param matriculadoPK
	 * @param token
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verificarTokenEstudiante(ArrMatriculadoPK matriculadoPK, String token) throws Exception{
		ArrMatriculado estudiante = (ArrMatriculado) mngDao.findById(ArrMatriculado.class, matriculadoPK);
		if(estudiante.getMatToken()==null)
			throw new Exception("Usted no posee token");
		if(estudiante.getMatToken().equals(token))
			return true;
		else
			return false;
	}
	
	/**
	 * Carga los sitios que se encuentran libres en el periodo
	 * @param prdID
	 * @return List<ArrSitioPeriodo>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ArrSitioPeriodo> sitiosLibresPorPeriodo(String prdID) throws Exception{
		return mngDao.findWhere(ArrSitioPeriodo.class, "o.id.prdId='"+prdID+"'"
				+ " AND o.sitLibres>0", null);
	}
	
	/**
	 * Valida si aún existe espacio en un sitio 
	 * @param pkSitio
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean existeReservaPeriodo(ArrSitioPeriodoPK pkSitio) throws Exception{
		ArrSitioPeriodo sitio = (ArrSitioPeriodo) mngDao.findById(ArrSitioPeriodo.class, pkSitio);
		if(sitio.getSitLibres()>0)
			return true;
		else
			return false;
	}
	
	/**
	 * Crea una reserva de sitio
	 * @param estudiante
	 * @param sitioLibre
	 * @throws Exception
	 */
	public void crearReserva(ArrMatriculado estudiante, ArrSitioPeriodo sitioLibre) throws Exception{
		ArrReserva reserva = new ArrReserva();
		reserva.setPerDni(estudiante.getId().getPerDni());
		reserva.setResFechaCreacion(new Date());
		reserva.setResFechaHoraCreacion(new Timestamp(new Date().getTime()));
		reserva.setArrSitioPeriodo(sitioLibre);
		reserva.setResEstado(Funciones.estadoActivo);
		//reserva.setResContrato(generarContrato(estudiante));
		mngDao.insertar(reserva);
		//REDUCIR CAPACIDAD
		reducirCapacidadSitio(sitioLibre);
	}
	
	/**
	 * Actualiza una reserva existente
	 * @param perDNI
	 * @param prdID
	 * @param sitioLibre
	 * @throws Exception
	 */
	public void modificarReserva(String perDNI, String prdID, ArrSitioPeriodo sitioLibre) throws Exception{
		ArrReserva reserva = buscarReservaPorID(perDNI, prdID);
		ArrSitioPeriodo sitioAnterior = reserva.getArrSitioPeriodo();
		reserva.setResFechaCreacion(new Date());
		reserva.setResFechaHoraCreacion(new Timestamp(new Date().getTime()));
		reserva.setArrSitioPeriodo(sitioLibre);
		//reserva.setResContrato(generarContrato(estudiante));
		mngDao.actualizar(reserva);
		//AUMENTAR CAPACIDAD ANTERIOR
		aumentarCapacidadSitio(sitioAnterior);
		//REDUCIR CAPACIDAD NUEVA
		reducirCapacidadSitio(sitioLibre);
	}
	
	/**
	 * Genera el texto de contrato para esa reserva
	 * @param estudiante
	 * @return String
	 */
	public String generarContrato(ArrMatriculado estudiante){
		StringBuilder contrato = new StringBuilder();
		return contrato.toString();
	}
	
	/**
	 * Reducir capacidad de sitio
	 * @param sitioLibre
	 * @throws Exception
	 */
	public void reducirCapacidadSitio(ArrSitioPeriodo sitioLibre) throws Exception{
		sitioLibre.setSitLibres(sitioLibre.getSitLibres()-1);
		mngDao.actualizar(sitioLibre);
	}
	
	/**
	 * Aumentar capacidad de sitio
	 * @param sitioOcupado
	 * @throws Exception
	 */
	public void aumentarCapacidadSitio(ArrSitioPeriodo sitioOcupado) throws Exception{
		sitioOcupado.setSitLibres(sitioOcupado.getSitLibres()+1);
		mngDao.actualizar(sitioOcupado);
	}
	
	/**
	 * Busca reserva por ID
	 * @param perDNI
	 * @param prdID
	 * @return ArrReserva
	 * @throws Exception
	 */
	public ArrReserva buscarReservaPorID(String perDNI, String prdID) throws Exception{
		String pk = perDNI+prdID;
		return (ArrReserva) mngDao.findById(ArrReserva.class, pk);
	}
}
