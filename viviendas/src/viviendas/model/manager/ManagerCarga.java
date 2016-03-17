package viviendas.model.manager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import viviendas.model.conection.SingletonJDBC;
import viviendas.model.conn.entities.GEN_Areas;
import viviendas.model.conn.entities.GEN_Sitios;
import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.dao.entities.ArrMatriculadoPK;
import viviendas.model.dao.entities.ArrNegado;
import viviendas.model.dao.entities.ArrNegadoPK;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrReserva;
import viviendas.model.dao.entities.ArrSitioPeriodo;
import viviendas.model.dao.entities.ArrSitioPeriodoPK;
import viviendas.model.generic.Funciones;
import viviendas.model.generic.Mensaje;

/**
 * Contiene la l�gica de negocio para realizar el proceso de reserva de sitios
 * 
 * @author
 * 
 */
public class ManagerCarga {
	private ManagerDAO mngDao;
	private SingletonJDBC conDao;

	// POSICIONES DEL ARRAY DE DATOS DE PERSONAS
	private int POSICION_CEDULA = 0;
	private int POSICION_NOMBRE = 1;
	private int POSICION_FECHA = 2;
	private int POSICION_NIVEL = 3;
	private int POSICION_CARRERA = 4;
	private int POSICION_CORREO_INS = 5;
	private int POSICION_CORREO = 6;
	private int POSICION_GENERO = 7;
	
	// POSICIONES DEL ARRAY DE DATOS DE LISTA NEGRA
	private int LN_CEDULA = 0;
	private int LN_RAZON = 1;

	private String[] encabezados = { "C�DULA", "NOMBRE_COMPLETO",
			"FECHA_NACIMIENTO", "NIVEL", "CARRERA", "CORREO_INSTITUCIONAL",
			"CORREO_GENERAL", "GENERO"};
	
	private String[] encabezados2 = { "C�DULA", "RAZON"};

	public ManagerCarga() {
		mngDao = new ManagerDAO();
		conDao = SingletonJDBC.getInstance();
	}

	/**
	 * Metodo para obtener todos los atributos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ArrPeriodo> todosPeriodos() {
		return mngDao.findAll(ArrPeriodo.class);
	}

	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	public ArrPeriodo PeriodoById(String per_id) throws Exception {
		return (ArrPeriodo) mngDao.findById(ArrPeriodo.class, per_id);
	}
	
	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	public ArrReserva ReservaById(String res_id) throws Exception {
		return (ArrReserva) mngDao.findById(ArrReserva.class, res_id);
	}

	/**
	 * Metodo para insertar datos de un atributos
	 * 
	 * @param per_id
	 * @param descripcion
	 * @param fecha_inicio
	 * @param fecha_fin
	 * @param estado
	 * @throws Exception
	 */
	public void insertarPeriodo(String per_id, String descripcion,
			Date fecha_inicio, Date fecha_fin, String estado) throws Exception {
		try {
			ArrPeriodo per = new ArrPeriodo();
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
	 * 
	 * @param per_id
	 * @param descripcion
	 * @param fecha_inicio
	 * @param fecha_fin
	 * @param estado
	 * @throws Exception
	 */
	public void editarPeriodo(String per_id, String descripcion,
			Date fecha_inicio, Date fecha_fin, String estado) throws Exception {
		try {
			ArrPeriodo per = this.PeriodoById(per_id);
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
	 * 
	 * @param per_id
	 * @param estado
	 * @throws Exception
	 */
	public void editarPeriodoEstado(String per_id, String estado)
			throws Exception {
		try {
			ArrPeriodo per = this.PeriodoById(per_id);
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
	public List<ArrPeriodo> PeriodosActivos() {
		List<ArrPeriodo> lp = mngDao.findWhere(ArrPeriodo.class,
				"o.prdEstado='A'", null);
		if (lp == null || lp.size() == 0) {
			return null;
		} else {
			return lp;
		}
	}

	/**
	 * Metodo para obtener solo un periodo que esta activado
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrPeriodo PeriodoAct() {
		List<ArrPeriodo> lp = mngDao.findWhere(ArrPeriodo.class,
				"o.prdEstado='A'", null);
		if (lp == null || lp.size() == 0) {
			return null;
		} else {
			return lp.get(0);
		}
	}

	/**
	 * Metodo para obtener todos los atributos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ArrSitioPeriodo> todosSitios() {
		return mngDao.findAll(ArrSitioPeriodo.class);
	}

	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 *            , art_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrSitioPeriodo SitiosById(String per_id, Integer art_id)
			throws Exception {
		List<ArrSitioPeriodo> ls = mngDao.findWhere(ArrSitioPeriodo.class,
				"o.id.prdId='" + per_id + "' and o.id.artId=" + art_id + "",
				null);
		if (ls.isEmpty()) {
			return null;
		} else {
			return ls.get(0);
		}
	}

	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ArrSitioPeriodo> SitiosByPeriodo(String per_id)
			throws Exception {
		List<ArrSitioPeriodo> ls = mngDao.findWhere(ArrSitioPeriodo.class,
				"o.id.prdId='" + per_id + "'", null);
		if (ls.isEmpty()) {
			return null;
		} else {
			return ls;
		}
	}

	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	public ArrSitioPeriodoPK SitiosPkById(String per_id) throws Exception {
		return (ArrSitioPeriodoPK) mngDao.findById(ArrSitioPeriodoPK.class,
				per_id);
	}

	/**
	 * Metodo para insertar un
	 * 
	 * @param art_id
	 * @param prd_id
	 * @param nombre
	 * @param capacidad
	 * @param libres
	 * @param valor_arriendo
	 * @param genero
	 * @throws Exception
	 */
	public void insertarSitio(Integer art_id, String prd_id, String nombre,
			Integer capacidad, Integer libres, BigDecimal valor_arriendo,
			String genero) throws Exception {
		try {
			ArrSitioPeriodo per = new ArrSitioPeriodo();
			ArrSitioPeriodoPK sp_pk = new ArrSitioPeriodoPK();
			sp_pk.setPrdId(prd_id);
			sp_pk.setArtId(art_id);
			per.setId(sp_pk);
			per.setSitCapacidad(capacidad);
			per.setSitGenero(genero);
			per.setSitLibres(libres);
			per.setSitNombre(nombre);
			per.setSitValorArriendo(valor_arriendo);
			ArrPeriodo p = this.PeriodoById(prd_id);
			per.setArrPeriodo(p);
			mngDao.insertar(per);
			System.out.println("Bien_insertar_sitio-periodo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Cierre del metodo

	/**
	 * Metod para eliminar un ArrSitioPeriodo
	 * 
	 * @param sit
	 */
	public void eliminarSitio(ArrSitioPeriodo sit) {
		try {
			mngDao.eliminar(ArrSitioPeriodo.class, sit.getId());
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("El sitio no puede ser eliminado porque cuenta con una reserva asignada");
		}
	}
	
	/**
	 * Metod para eliminar un ArrSitioPeriodo
	 * 
	 * @param sit
	 */
	public void eliminarReserva(ArrReserva res) {
		try {
				//proceso para a�adir 1 a SitioPeriodo
				ArrSitioPeriodo r = new ArrSitioPeriodo();
				r=SitiosById(res.getArrSitioPeriodo().getId().getPrdId(), res.getArrSitioPeriodo().getId().getArtId());
				r.setSitCapacidad(res.getArrSitioPeriodo().getSitCapacidad()+1);
				mngDao.actualizar(r);
				mngDao.eliminar(ArrReserva.class, res.getResId());
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para editar datos de un atributo
	 * 
	 * @param per_id
	 * @param descripcion
	 * @param fecha_inicio
	 * @param fecha_fin
	 * @param estado
	 * @throws Exception
	 */
	public void editarSitio(Integer art_id, String prd_id, Integer capacidad,
			BigDecimal valor_arriendo, String genero) throws Exception {
		try {
			ArrSitioPeriodo sp = this.SitiosById(prd_id, art_id);
			sp.setSitCapacidad(capacidad);
			sp.setSitGenero(genero);
			sp.setSitLibres(capacidad);
			sp.setSitValorArriendo(valor_arriendo);
			mngDao.actualizar(sp);
			System.out.println("Bien_editar_sitio");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Cierre del metodo

	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ArrReserva> ReservaByPeriodo(String per_id)
			throws Exception {
		List<ArrReserva> ls = mngDao.findWhere(ArrReserva.class,
				"o.arrSitioPeriodo.id.prdId='" + per_id + "'", null);
		if (ls.isEmpty()) {
			return null;
		} else {
			return ls;
		}
	}
	
	/**
	 * Valida la estructura de encabezados de excel
	 * 
	 * @param row
	 * @return
	 */
	public boolean validarEncabezadosExcel(Cell[] row) {
		if (row.length==8){
			if (row[POSICION_CEDULA].getContents().equals(encabezados[POSICION_CEDULA])
				&& row[POSICION_CARRERA].getContents().equals(encabezados[POSICION_CARRERA])
				&& row[POSICION_CORREO].getContents().equals(encabezados[POSICION_CORREO])
				&& row[POSICION_CORREO_INS].getContents().equals(encabezados[POSICION_CORREO_INS])
				&& row[POSICION_FECHA].getContents().equals(encabezados[POSICION_FECHA])
				&& row[POSICION_GENERO].getContents().equals(encabezados[POSICION_GENERO])
				&& row[POSICION_NIVEL].getContents().equals(encabezados[POSICION_NIVEL])
				&& row[POSICION_NOMBRE].getContents().equals(encabezados[POSICION_NOMBRE])){
				return true;
			} else{
					return false;
				}
		} else{
			return false;
		}
		
	}
	
	/**
	 * Valida la estructura de encabezados de excel
	 * 
	 * @param row
	 * @return
	 */
	public boolean validarEncabezadosExcel2(Cell[] row) {
		if (row.length==2){
			if (row[LN_CEDULA].getContents().equals(encabezados2[LN_CEDULA])
				&& row[LN_RAZON].getContents().equals(encabezados2[LN_RAZON])){
				return true;
			} else{
					return false;
				}
		} else{
			return false;
		}
	}

	/**
	 * Valida que los datos no sean vacio o null
	 * 
	 * @param column
	 * @return String
	 */
	public String validarFilaExcel(Cell[] column) {
		String errores = "";
		// validar cedula
		if (column[POSICION_CEDULA].getContents() == null
				|| column[POSICION_CEDULA].getContents().trim().isEmpty()) {
			errores += " C�DULA ESTUDIANTE vac�o, ";
		}
		// validar nombre
		if (column[POSICION_NOMBRE].getContents() == null
				|| column[POSICION_NOMBRE].getContents().trim().isEmpty()) {
			errores += " NOMBRE ESTUDIANTE vac�o, ";
		}
		// validar fecha nacimiento
		if (column[POSICION_FECHA].getContents() == null
				|| column[POSICION_FECHA].getContents().trim().isEmpty()) {
			errores += " FECHA NACIMIENTO vac�o, ";
		}
		// validar nivel
		if (column[POSICION_NIVEL].getContents() == null
				|| column[POSICION_NIVEL].getContents().trim().isEmpty()) {
			errores += " NIVEL vac�o, ";
		}
		// validar carrera
		if (column[POSICION_CARRERA].getContents() == null
				|| column[POSICION_CARRERA].getContents().trim().isEmpty()) {
			errores += " CARRERA vac�o, ";
		}
		// validar correo_institucional
		if (column[POSICION_CORREO_INS].getContents() == null
				|| column[POSICION_CORREO_INS].getContents().trim().isEmpty()) {
			errores += " CORREO INSTITUCIONAL vac�o, ";
		} else {
			if (Funciones.validarEmail(column[POSICION_CORREO_INS]
					.getContents().trim()) != true)
				errores += " CORREO INSTITUCIONAL invalido, ";
		}
		// validar correo
		if (column[POSICION_CORREO].getContents() == null
				|| column[POSICION_CORREO].getContents().trim().isEmpty()) {
			errores += " CORREO GENERAL vac�o, ";
		} else {
			if (Funciones.validarEmail(column[POSICION_CORREO].getContents()
					.trim()) != true)
				errores += " CORREO GENERAL invalido, ";
		}
		// validar genero
		if (column[POSICION_GENERO].getContents() == null
				|| column[POSICION_GENERO].getContents().trim().isEmpty()) {
			errores += " GENERO vac�o, ";
		}
		// retornar errores
		return errores;
	}
	
	/**
	 * Valida que los datos no sean vacio o null
	 * 
	 * @param column
	 * @return String
	 */
	public String validarFilaExcel2(Cell[] column) {
		String errores = "";
		// validar cedula
		if (column[LN_CEDULA].getContents() == null
				|| column[LN_CEDULA].getContents().trim().isEmpty()) {
			errores += " C�DULA ESTUDIANTE vac�o, ";
		}
		// retornar errores
		return errores;
	}
	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ArrNegado> NegadoByPeriodo(String per_id)
			throws Exception {
		List<ArrNegado> ls = mngDao.findWhere(ArrNegado.class,
				"o.id.prdId='" + per_id + "'", null);
		if (ls.isEmpty()) {
			return null;
		} else {
			return ls;
		}
	}
	
	/**
	 * Metodo para obtener un atributo por id
	 * 
	 * @param per_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ArrMatriculado> MatriculadoByPeriodo(String per_id)
			throws Exception {
		List<ArrMatriculado> ls = mngDao.findWhere(ArrMatriculado.class,
				"o.id.prdId='" + per_id + "'", null);
		if (ls.isEmpty()) {
			return null;
		} else {
			return ls;
		}
	}
	
	/**
	 * Crea una instancia de Matriculados mediante una Lista de String
	 * @param datosPersona
	 * @return SinfPersonal
	 * @throws Exception
	 */
	public ArrMatriculado crearMatriculado(List<String> datosMatriculados, String prd_id) throws Exception{
		ArrMatriculado mat= new ArrMatriculado();
		ArrMatriculadoPK mat_pk = new ArrMatriculadoPK();
		mat_pk.setPerDni(datosMatriculados.get(this.POSICION_CEDULA));
		mat_pk.setPrdId(prd_id);
		mat.setId(mat_pk);
		mat.setArrPeriodo(this.PeriodoById(prd_id));
		mat.setMatCarrera(datosMatriculados.get(this.POSICION_CARRERA));
		mat.setMatCorreo(datosMatriculados.get(this.POSICION_CORREO));
		mat.setMatCorreoIns(datosMatriculados.get(this.POSICION_CORREO_INS));
		mat.setMatFechaNacimiento(Funciones.stringToDateF(datosMatriculados.get(this.POSICION_FECHA)));
		mat.setMatGenero(datosMatriculados.get(this.POSICION_GENERO));
		mat.setMatNivel(datosMatriculados.get(this.POSICION_NIVEL));
		mat.setMatNombre(datosMatriculados.get(this.POSICION_NOMBRE));
		return mat;
	}
	
	/**
	 * Crea una instancia de Matriculados mediante una Lista de String
	 * @param datosPersona
	 * @return SinfPersonal
	 * @throws Exception
	 */
	public ArrNegado crearNegado(List<String> datosNegros, String prd_id) throws Exception{
		ArrNegado neg = new ArrNegado();
		ArrNegadoPK neg_pk = new ArrNegadoPK();
		neg_pk.setPerDni(datosNegros.get(this.LN_CEDULA));
		neg_pk.setPrdId(prd_id);
		neg.setId(neg_pk);
		neg.setNegRazon(datosNegros.get(LN_RAZON));
		return neg;
	}
	
	/**
	 * Permite el ingreso y actualizaci�n de datos de personal
	 * @param listadoPersonal Personas dentro de un periodo determinado
	 * @throws Exception
	 */
	public void ingresarMatriculado(List<ArrMatriculado> listadoMatriculados) throws Exception{
		for (ArrMatriculado matri : listadoMatriculados) {
			if(existePersonal(matri.getId())){
				actualizarMatriculado(matri);
			}else{
				insertarMatriculado(matri);
			}
		}
	}
	
	/**
	 * Permite el ingreso y actualizaci�n de datos de personal
	 * @param listadoPersonal Personas dentro de un periodo determinado
	 * @throws Exception
	 */
	public void ingresarNegado(List<ArrNegado> listadoN) throws Exception{
		for (ArrNegado neg : listadoN) {
			if(existeNegado(neg.getId())){
				actualizarNegado(neg);
			}else{
				insertarNegado(neg);
			}
		}
	}
	
	/**
	 * Guarda en la base de datos una persona
	 * @param person
	 * @throws Exception
	 */
	private void insertarMatriculado(ArrMatriculado person) throws Exception{
		mngDao.insertar(person);
	}
	
	/**
	 * Guarda en la base de datos una persona
	 * @param person
	 * @throws Exception
	 */
	private void insertarNegado(ArrNegado person) throws Exception{
		mngDao.insertar(person);
	}
	
	/**
	 * Actualiza los datos de una persona
	 * @param person
	 * @throws Exception
	 */
	private void actualizarMatriculado(ArrMatriculado person) throws Exception{
		ArrMatriculado matriculadoExistente = (ArrMatriculado) mngDao.findById(ArrMatriculado.class, person.getId());
		matriculadoExistente.setMatCarrera(person.getMatCarrera());
		matriculadoExistente.setMatCorreo(person.getMatCorreo());
		matriculadoExistente.setMatCorreoIns(person.getMatCorreoIns());
		matriculadoExistente.setMatFechaNacimiento(person.getMatFechaNacimiento());
		matriculadoExistente.setMatGenero(person.getMatGenero());
		matriculadoExistente.setMatNivel(person.getMatNivel());
		matriculadoExistente.setMatNombre(person.getMatNombre());
		mngDao.actualizar(person);
	}
	
	/**
	 * Actualiza los datos de una persona
	 * @param person
	 * @throws Exception
	 */
	private void actualizarNegado(ArrNegado person) throws Exception{
		ArrNegado negadoExistente = (ArrNegado) mngDao.findById(ArrNegado.class, person.getId());
		negadoExistente.setNegRazon(person.getNegRazon());
		mngDao.actualizar(person);
	}
	
	/**
	 * Verifica la existencia de una persona
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private boolean existePersonal(ArrMatriculadoPK id) throws Exception{
		ArrMatriculado personalExistente = (ArrMatriculado) mngDao.findById(ArrMatriculado.class, id);
		if(personalExistente!=null)
			return true;
		else
			return false;
	}
	
	/**
	 * Verifica la existencia de una persona
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private boolean existeNegado(ArrNegadoPK id) throws Exception{
		ArrNegado personalExistente = (ArrNegado) mngDao.findById(ArrNegado.class, id);
		if(personalExistente!=null)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Actualiza los datos de una persona
	 * @param person
	 * @throws Exception
	 */
	public void cambiarEstado(ArrReserva reserva) throws Exception{
		ArrReserva sReserva = (ArrReserva) mngDao.findById(ArrReserva.class, reserva.getResId());
		Date fecha = new Date();
		sReserva.setResFechaHoraFinalizacion(new Timestamp(fecha.getTime()));
		sReserva.setResFechaFinalizacion(fecha);
		sReserva.setResEstado("F");
		mngDao.actualizar(sReserva);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Metodo para listar todas las Areas
	 * 
	 * @param order
	 * @return lista de Arr
	 * @throws Exception
	 */
	public List<GEN_Areas> findAllAreasActivas(String order) throws Exception {
		ResultSet r = null;
		List<GEN_Areas> li = new ArrayList<GEN_Areas>();
		if (order == null) {
			r = conDao
					.consultaSQL("Select * from gen_areas where are_estado='A';");
		} else {
			r = conDao.consultaSQL("Select * from gen_areas order by " + order
					+ ";");
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
	public GEN_Areas findAreasById(Integer per_id) throws Exception {
		ResultSet r = conDao
				.consultaSQL("Select * from gen_areas where are_id=" + per_id
						+ ";");
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
	public List<GEN_Sitios> findAllSitiosActivos(String order) throws Exception {
		ResultSet r = null;
		List<GEN_Sitios> li = new ArrayList<GEN_Sitios>();
		if (order == null) {
			r = conDao
					.consultaSQL("Select * from gen_sitios where sit_estado='A';");
		} else {
			r = conDao.consultaSQL("Select * from gen_sitios order by " + order
					+ ";");
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
	 * Metodo para listar todas las Areas Activas de un Area
	 * 
	 * @param order
	 * @return lista de Arr
	 * @throws Exception
	 */
	public List<GEN_Sitios> findAllSitiosXArea(Integer id_area, String order)
			throws Exception {
		ResultSet r = null;
		List<GEN_Sitios> li = new ArrayList<GEN_Sitios>();
		if (order == null) {
			r = conDao
					.consultaSQL("Select * from gen_sitios where sit_estado='A' and are_id="
							+ id_area + ";");
		} else {
			r = conDao.consultaSQL("Select * from gen_sitios order by " + order
					+ ";");
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
	public GEN_Sitios findSitioById(Integer per_id) throws Exception {
		ResultSet r = conDao
				.consultaSQL("Select * from gen_sitios where sit_id=" + per_id
						+ ";");
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

	/**
	 * Metodo para buscar una Area por ID
	 * 
	 * @param per_id
	 * @return persona
	 * @throws Exception
	 */
	public GEN_Sitios findSitioById(String nom) throws Exception {
		ResultSet r = conDao
				.consultaSQL("Select * from gen_sitios where sit_nombre='"
						+ nom.trim() + "';");
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
