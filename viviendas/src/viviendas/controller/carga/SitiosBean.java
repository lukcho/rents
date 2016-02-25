package viviendas.controller.carga;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import viviendas.model.conn.entities.GEN_Areas;
import viviendas.model.conn.entities.GEN_Sitios;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrSitioPeriodoPK;
import viviendas.model.generic.Funciones;
import viviendas.model.generic.Mensaje;
import viviendas.model.manager.ManagerCarga;

/**
 * @author jestevez
 * 
 */
@SessionScoped
@ManagedBean
public class SitiosBean {

	// Atributos de la Clase
	private ManagerCarga manager;

	private ArrSitioPeriodoPK id;
	private Integer sitCapacidad;
	private Integer sitLibres;
	private String sitGenero;
	private String sitNombre;
	private BigDecimal sitValorArriendo;
	
	//atributos de bloque
	private boolean bcampos;
	
	private List<GEN_Sitios> lsitios;

	public SitiosBean() {
		manager = new ManagerCarga();
		bcampos=true;
		lsitios=new ArrayList<GEN_Sitios>();
	}

	/**
	 * @return the bcampos
	 */
	public boolean isBcampos() {
		return bcampos;
	}

	/**
	 * @param bcampos the bcampos to set
	 */
	public void setBcampos(boolean bcampos) {
		this.bcampos = bcampos;
	}

	/**
	 * @return the lsitios
	 */
	public List<GEN_Sitios> getLsitios() {
		return lsitios;
	}

	/**
	 * @param lsitios the lsitios to set
	 */
	public void setLsitios(List<GEN_Sitios> lsitios) {
		this.lsitios = lsitios;
	}

	/**
	 * @return the id
	 */
	public ArrSitioPeriodoPK getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ArrSitioPeriodoPK id) {
		this.id = id;
	}

	/**
	 * @return the sitCapacidad
	 */
	public Integer getSitCapacidad() {
		return sitCapacidad;
	}

	/**
	 * @param sitCapacidad
	 *            the sitCapacidad to set
	 */
	public void setSitCapacidad(Integer sitCapacidad) {
		this.sitCapacidad = sitCapacidad;
	}

	/**
	 * @return the sitLibres
	 */
	public Integer getSitLibres() {
		return sitLibres;
	}

	/**
	 * @param sitLibres
	 *            the sitLibres to set
	 */
	public void setSitLibres(Integer sitLibres) {
		this.sitLibres = sitLibres;
	}

	/**
	 * @return the sitGenero
	 */
	public String getSitGenero() {
		return sitGenero;
	}

	/**
	 * @param sitGenero
	 *            the sitGenero to set
	 */
	public void setSitGenero(String sitGenero) {
		this.sitGenero = sitGenero;
	}

	/**
	 * @return the sitNombre
	 */
	public String getSitNombre() {
		return sitNombre;
	}

	/**
	 * @param sitNombre
	 *            the sitNombre to set
	 */
	public void setSitNombre(String sitNombre) {
		this.sitNombre = sitNombre;
	}

	/**
	 * @return the sitValorArriendo
	 */
	public BigDecimal getSitValorArriendo() {
		return sitValorArriendo;
	}

	/**
	 * @param sitValorArriendo
	 *            the sitValorArriendo to set
	 */
	public void setSitValorArriendo(BigDecimal sitValorArriendo) {
		this.sitValorArriendo = sitValorArriendo;
	}

	/**
	 * Lista de periodos
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistPeriodo() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		ArrPeriodo per = manager.PeriodoAct();
		if (per!=null)
			lista.add(new SelectItem(per.getPrdId(), per.getPrdId()));
		return lista;
	}
	
	/**
	 * Lista de areas
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistAreas() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		try {
			for (GEN_Areas a : manager.findAllAreasActivas(null)) {
				lista.add(new SelectItem(a.getAre_id(), a.getAre_nombre()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lista = null;
		}
		return lista;
	}
	
	/**
	 * Lista de Genero
	 * 
	 * @return lista de items de genero
	 */
	public List<SelectItem> getlistGenero() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(Funciones.estadoMasculino, Funciones.estadoMasculino
				+ " : " + Funciones.valorEstadoMasculino));
		lista.add(new SelectItem(Funciones.estadoFemenino,
				Funciones.estadoFemenino + " : "
						+ Funciones.valorEstadoFemenino));
		return lista;
	}
	
	public void validar(){
		if (id.getPrdId()==null || id.getPrdId().equals("-1")){
			bcampos=true;
		}else{
			bcampos=false;
		}
		System.out.println(bcampos);
	}
	
}
