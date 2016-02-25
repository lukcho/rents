package viviendas.model.manager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import viviendas.model.conection.SingletonJDBC;
import viviendas.model.conn.entities.GEN_Areas;
import viviendas.model.conn.entities.GEN_Sitios;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrSitioPeriodo;

/**
 * Contiene la lógica de negocio para realizar el proceso de reserva de sitios
 * @author 
 *
 */
public class ManagerCarga {
	private ManagerDAO mngDao;
	private SingletonJDBC conDao;
	
	public ManagerCarga() {
		mngDao = new ManagerDAO();
		conDao = SingletonJDBC.getInstance();
	}
	
	/**
	 * Metodo para obtener todos los atributos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ArrPeriodo> todosPeriodos(){
		return mngDao.findAll(ArrPeriodo.class);
	}
	
	/**
	 * Metodo para obtener un atributo por id 
	 * @param per_id
	 * @return
	 * @throws Exception 
	 */
	public ArrPeriodo PeridoById(String per_id) throws Exception{
		return (ArrPeriodo) mngDao.findById(ArrPeriodo.class, per_id);
	}
	
	/**
	 * Metodo para insertar datos de un atributos
	 * @param per_id
	 * @param descripcion
	 * @param fecha_inicio
	 * @param fecha_fin
	 * @param estado
	 * @throws Exception
	 */
	public void insertarPeriodo(String per_id, String descripcion, Date fecha_inicio, Date fecha_fin,String estado)
			throws Exception {
		try {
			ArrPeriodo per= new ArrPeriodo();
			per.setPrdId(per_id);
			per.setPrdDescripcion(descripcion);
			per.setPrdFechaInicio(fecha_inicio);
			per.setPrdFechaFin(fecha_fin);
			per.setPrdEstado(estado);
			mngDao.insertar(per);
			System.out.println("Bien_insertar_periodo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Cierre del metodo
	
	/**
	 * Metodo para editar datos de un atributo
	 * @param per_id
	 * @param descripcion
	 * @param fecha_inicio
	 * @param fecha_fin
	 * @param estado
	 * @throws Exception
	 */
	public void editarPeriodo (String per_id, String descripcion, Date fecha_inicio, Date fecha_fin,String estado)
			throws Exception {
		try {
			ArrPeriodo per= this.PeridoById(per_id);
			per.setPrdDescripcion(descripcion);
			per.setPrdFechaInicio(fecha_inicio);
			per.setPrdFechaFin(fecha_fin);
			per.setPrdEstado(estado);
			mngDao.actualizar(per);
			System.out.println("Bien_editar_periodo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Cierre del metodo
	
	/**
	 * Metodo para editar solo el un campo de un atributo
	 * @param per_id
	 * @param estado
	 * @throws Exception
	 */
	public void editarPeriodoEstado (String per_id,String estado)
			throws Exception {
		try {
			ArrPeriodo per= this.PeridoById(per_id);
			per.setPrdEstado(estado);
			mngDao.actualizar(per);
			System.out.println("Bien_editar_periodo_estado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Cierre del metodo
	
	/**
	 * Metodo para obtener todos los periodos activos en caso de encontrarse
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ArrPeriodo> PeriodosActivos(){
		List<ArrPeriodo> lp= mngDao.findWhere(ArrPeriodo.class, "o.prdEstado='A'", null);
		if (lp==null ||lp.size()==0){
			return null;
		}else{
			return lp;
		}	
	}
	
	/**
	 * Metodo para obtener solo un periodo que esta activado
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrPeriodo PeriodoAct(){
		List<ArrPeriodo> lp= mngDao.findWhere(ArrPeriodo.class, "o.prdEstado='A'", null);
		if (lp==null ||lp.size()==0){
			return null;
		}else{
			return lp.get(0);
		}	
	}
	
	/**
	 * Metodo para obtener todos los atributos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ArrSitioPeriodo> todosSitios(){
		return mngDao.findAll(ArrSitioPeriodo.class);
	}
	
	/**
	 * Metodo para obtener un atributo por id 
	 * @param per_id
	 * @return
	 * @throws Exception 
	 */
	public ArrSitioPeriodo SitiosById(String per_id) throws Exception{
		return (ArrSitioPeriodo) mngDao.findById(ArrSitioPeriodo.class, per_id);
	}
	
//	public void insertarSitio(String sit_id, String descripcion, Date fecha_inicio, Date fecha_fin,String estado)
//			throws Exception {
//		try {
//			ArrSitioPeriodo per= new ArrSitioPeriodo();
//			per.setPrdId(per_id);
//			per.setPrdDescripcion(descripcion);
//			per.setPrdFechaInicio(fecha_inicio);
//			per.setPrdFechaFin(fecha_fin);
//			per.setPrdEstado(estado);
//			mngDao.insertar(per);
//			System.out.println("Bien_insertar_periodo");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}// Cierre del metodo
//	
//	/**
//	 * Metodo para editar datos de un atributo
//	 * @param per_id
//	 * @param descripcion
//	 * @param fecha_inicio
//	 * @param fecha_fin
//	 * @param estado
//	 * @throws Exception
//	 */
//	public void editarPeriodo (String per_id, String descripcion, Date fecha_inicio, Date fecha_fin,String estado)
//			throws Exception {
//		try {
//			ArrPeriodo per= this.PeridoById(per_id);
//			per.setPrdDescripcion(descripcion);
//			per.setPrdFechaInicio(fecha_inicio);
//			per.setPrdFechaFin(fecha_fin);
//			per.setPrdEstado(estado);
//			mngDao.actualizar(per);
//			System.out.println("Bien_editar_periodo");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}// Cierre del metodo
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Metodo para listar todas las Areas
	 * 
	 * @param order
	 * @return lista de Arr
	 * @throws Exception
	 */
	public List<GEN_Areas> findAllAreasActivas(String order)
			throws Exception {
		ResultSet r = null;
		List<GEN_Areas> li = new ArrayList<GEN_Areas>();
		if (order == null) {
			r = conDao.consultaSQL("Select * from gen_areas where are_estado='A';");
		} else {
			r = conDao.consultaSQL("Select * from gen_areas order by "
					+ order + ";");
		}
		if (r == null) {
			throw new Exception("La consulta no obtuvo resultados.");
		} else {
			while (r.next()) {
				GEN_Areas are = new GEN_Areas();
				are.setAre_id(r.getInt("are_id"));
				are.setAre_nombre(r.getString("are_nombre"));
				are.setGen_sector(r.getInt("sec_id"));
				are.setAre_descripcion(r.getString("are_descripcion"));
				are.setAre_estado(r.getString("are_estado").charAt(0));
				li.add(are);
			}
		}
		return li;
	}

	/**
	 * Metodo para buscar una Area por ID
	 * 
	 * @param per_id
	 * @return persona
	 * @throws Exception
	 */
	public GEN_Areas findAreasById(Integer per_id)
			throws Exception {
		ResultSet r = conDao
				.consultaSQL("Select * from gen_areas where are_id="
						+ per_id + ";");
		GEN_Areas are = new GEN_Areas();
		int cont = 0;
		if (r == null) {
			throw new Exception("La consulta no obtuvo resultados.");
		} else {
			while (r.next()) {
				are.setAre_id(r.getInt("are_id"));
				are.setAre_nombre(r.getString("are_nombre"));
				are.setGen_sector(r.getInt("sec_id"));
				are.setAre_descripcion(r.getString("are_descripcion"));
				are.setAre_estado(r.getString("are_estado").charAt(0));
				cont++;
			}
		}
		if (cont > 1)
			throw new Exception("La consulta obtuvo varios resultados.");
		return are;
	}
	
	/**
	 * Metodo para listar todas las Areas
	 * 
	 * @param order
	 * @return lista de Arr
	 * @throws Exception
	 */
	public List<GEN_Sitios> findAllSitiosActivos(String order)
			throws Exception {
		ResultSet r = null;
		List<GEN_Sitios> li = new ArrayList<GEN_Sitios>();
		if (order == null) {
			r = conDao.consultaSQL("Select * from gen_sitios where sit_estado='A';");
		} else {
			r = conDao.consultaSQL("Select * from gen_sitios order by "
					+ order + ";");
		}
		if (r == null) {
			throw new Exception("La consulta no obtuvo resultados.");
		} else {
			while (r.next()) {
				GEN_Sitios sit = new GEN_Sitios();
				sit.setSit_id(r.getInt("sit_id"));
				sit.setSit_capacidad(r.getInt("sit_capacidad"));
				sit.setSit_nombre(r.getString("sit_nombre"));
				sit.setSit_costo_arriendo(r.getDouble("sit_costo_arriendo"));
				sit.setSit_estado(r.getString("sit_estado").charAt(0));
				sit.setgEN_TipoSitios(r.getInt("tsi_id"));
				sit.setgEN_Areas(r.getInt("are_id"));
				li.add(sit);
			}
		}
		return li;
	}

	/**
	 * Metodo para buscar una Area por ID
	 * 
	 * @param per_id
	 * @return persona
	 * @throws Exception
	 */
	public GEN_Sitios findSitioById(Integer per_id)
			throws Exception {
		ResultSet r = conDao
				.consultaSQL("Select * from gen_sitios where sit_id="
						+ per_id + ";");
		GEN_Sitios sit = new GEN_Sitios();
		int cont = 0;
		if (r == null) {
			throw new Exception("La consulta no obtuvo resultados.");
		} else {
			while (r.next()) {
				sit.setSit_id(r.getInt("sit_id"));
				sit.setSit_capacidad(r.getInt("sit_capacidad"));
				sit.setSit_nombre(r.getString("sit_nombre"));
				sit.setSit_costo_arriendo(r.getDouble("sit_costo_arriendo"));
				sit.setSit_estado(r.getString("sit_estado").charAt(0));
				sit.setgEN_TipoSitios(r.getInt("tsi_id"));
				sit.setgEN_Areas(r.getInt("are_id"));
				cont++;
			}
		}
		if (cont > 1)
			throw new Exception("La consulta obtuvo varios resultados.");
		return sit;
	}
}
