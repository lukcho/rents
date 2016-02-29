package viviendas.controller.carga;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import viviendas.model.conn.entities.GEN_Areas;
import viviendas.model.conn.entities.GEN_Sitios;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrSitioPeriodo;
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

	private String prdId;
	private Integer areId;
	private String sitGenero;
	
	// atributos de bloque
	private boolean bcampos;

	private List<String> lsitios;
	private List<ArrSitioPeriodo> lsitper;

	public SitiosBean() {
		manager = new ManagerCarga();
		bcampos = true;
		lsitios = new ArrayList<String>();
		lsitper = new ArrayList<ArrSitioPeriodo>();
	}

	/**
	 * @return the lsitper
	 */
	public List<ArrSitioPeriodo> getLsitper() {
		return lsitper;
	}

	/**
	 * @param lsitper
	 *            the lsitper to set
	 */
	public void setLsitper(List<ArrSitioPeriodo> lsitper) {
		this.lsitper = lsitper;
	}

	/**
	 * @return the areId
	 */
	public Integer getAreId() {
		return areId;
	}

	/**
	 * @param areId
	 *            the areId to set
	 */
	public void setAreId(Integer areId) {
		this.areId = areId;
	}

	/**
	 * @return the prdId
	 */
	public String getPrdId() {
		return prdId;
	}

	/**
	 * @param prdId
	 *            the prdId to set
	 */
	public void setPrdId(String prdId) {
		this.prdId = prdId;
	}

	/**
	 * @return the bcampos
	 */
	public boolean isBcampos() {
		return bcampos;
	}

	/**
	 * @param bcampos
	 *            the bcampos to set
	 */
	public void setBcampos(boolean bcampos) {
		this.bcampos = bcampos;
	}

	/**
	 * @return the lsitios
	 */
	public List<String> getLsitios() {
		return lsitios;
	}

	/**
	 * @param lsitios
	 *            the lsitios to set
	 */
	public void setLsitios(List<String> lsitios) {
		this.lsitios = lsitios;
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

	public List<ArrSitioPeriodo> getListSitiosPer() {
		try {
			lsitper = manager.SitiosByPeriodo(prdId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return lsitper;
	}

	/**
	 * Lista de periodos
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistPeriodo() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		ArrPeriodo per = manager.PeriodoAct();
		if (per != null)
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
		lista.add(new SelectItem(Funciones.estadoMasculino,
				Funciones.estadoMasculino + " : "
						+ Funciones.valorEstadoMasculino));
		lista.add(new SelectItem(Funciones.estadoFemenino,
				Funciones.estadoFemenino + " : "
						+ Funciones.valorEstadoFemenino));
		return lista;
	}

	/**
	 * Lista de sitios
	 * 
	 * @return lista de items de estados
	 */
	public List<SelectItem> getlistSitios() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		try {
			for (GEN_Sitios a : manager.findAllSitiosXArea(areId, null)) {
				if (añadido(a) == false) {
					lista.add(new SelectItem(a.getSit_nombre(), a
							.getSit_nombre()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lista = null;
		}
		return lista;
	}

	/**
	 * Metodo de comprobacion si un sitio ya esta asignado
	 * 
	 * @param s
	 * @return
	 */
	public boolean añadido(GEN_Sitios s) {
		Integer v = 0;
		for (ArrSitioPeriodo sit : manager.todosSitios()) {
			if (sit.getSitNombre().trim().equals(s.getSit_nombre().trim())) {
				v = 100;
				break;
			}
		}
		if (v == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Metodo para activar las selecciones y mostrar las listas en base a un periodo
	 */
	public void validarYCarga() {
		if (prdId == null || prdId.equals("-1")) {
			bcampos = true;
		} else {
			bcampos = false;
		}
		this.getlistSitios();
		this.getListSitiosPer();
	}

	/**
	 * Metodo para cargar los sitios 
	 */
	public void cargarSitios() {
		this.getlistSitios();
	}

	/**
	 * Metodo para insertar sitios
	 */
	public void insertarSitios() {
		try {
			for (String sit : lsitios) {
				if (sitGenero == null || sitGenero.equals("-1")) {
					Mensaje.crearMensajeWARN("Debe seleccionar el Genero antes de Insertar");
					break;
				} else {
					GEN_Sitios s = manager.findSitioById(sit);
					manager.insertarSitio(s.sit_id, prdId, s.sit_nombre,
							s.sit_capacidad, s.sit_capacidad, new BigDecimal(
									s.sit_costo_arriendo), sitGenero);
				}
			}
			this.getlistSitios();
			this.getListSitiosPer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para eliminar un sitio asignado
	 * 
	 * @param sitio
	 */
	public void eliminar(ArrSitioPeriodo sitio) {
		manager.eliminarSitio(sitio);
		this.getListSitiosPer();
	}
}
