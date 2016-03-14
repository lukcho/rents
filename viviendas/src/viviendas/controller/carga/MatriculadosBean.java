package viviendas.controller.carga;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.dao.entities.ArrNegado;
import viviendas.model.dao.entities.ArrPeriodo;
import viviendas.model.dao.entities.ArrReserva;
import viviendas.model.generic.Mensaje;
import viviendas.model.manager.ManagerCarga;

/**
 * @author jestevez
 * 
 */
@SessionScoped
@ManagedBean
public class MatriculadosBean {

	// Atributos de la Clase
	private ManagerCarga manager;

	// Atributos de PK
	private String prdId;
	private String perId;

	// Atributos de insercion
	private String matNombre;
	private Date matFechaNacimiento;
	private String matCarrera;
	private String matCorreo;
	private String matCorreoIns;
	private String matGenero;
	private String matNivel;
	private String matRepresDni;
	private String matRepresNombre;

	private int NUMERO_COLUMNAS_EXCEL = 8;
	private int NUMERO_COLUMNAS_EXCEL2 = 2;
	
	List<ArrReserva> reservas;
	
	// listas de registros
	List<ArrMatriculado> matriculados;
	List<ArrNegado> negados;
	List<String> errores;

	private String e;

	private StreamedContent file;
	private StreamedContent file2;
	private StreamedContent contratoPdf;

	public MatriculadosBean() {
		manager = new ManagerCarga();
		matriculados = new ArrayList<ArrMatriculado>();
		errores = new ArrayList<String>();
		negados = new ArrayList<ArrNegado>();
		reservas = new ArrayList<ArrReserva>();
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/excel/excelbase.xls");
		InputStream stream2 = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/excel/excelbase2.xls");
		InputStream stream3 = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/excel/contrato.pdf");
		file = new DefaultStreamedContent(stream, "texto/xls",
				"archivo_Ejemplo_Matriculados.xls");
		file2 = new DefaultStreamedContent(stream2, "texto/xls",
				"archivo_Ejemplo_Negados.xls");
		contratoPdf = new DefaultStreamedContent(stream3, "applicatio/pdf",
				"contrato.xls");
	}

	/**
	 * @return the contratoPdf
	 */
	public StreamedContent getContratoPdf() {
		return contratoPdf;
	}

	/**
	 * @param contratoPdf the contratoPdf to set
	 */
	public void setContratoPdf(StreamedContent contratoPdf) {
		this.contratoPdf = contratoPdf;
	}

	/**
	 * @return the negados
	 */
	public List<ArrNegado> getNegados() {
		return negados;
	}

	/**
	 * @param negados
	 *            the negados to set
	 */
	public void setNegados(List<ArrNegado> negados) {
		this.negados = negados;
	}

	/**
	 * @return the file2
	 */
	public StreamedContent getFile2() {
		return file2;
	}

	/**
	 * @param file2
	 *            the file2 to set
	 */
	public void setFile2(StreamedContent file2) {
		this.file2 = file2;
	}

	/**
	 * @return the e
	 */
	public String getE() {
		return e;
	}

	/**
	 * @param e
	 *            the e to set
	 */
	public void setE(String e) {
		this.e = e;
	}

	/**
	 * @return the matriculados
	 */
	public List<ArrMatriculado> getMatriculados() {
		return matriculados;
	}

	/**
	 * @param matriculados
	 *            the matriculados to set
	 */
	public void setMatriculados(List<ArrMatriculado> matriculados) {
		this.matriculados = matriculados;
	}

	/**
	 * @return the errores
	 */
	public List<String> getErrores() {
		return errores;
	}

	/**
	 * @param errores
	 *            the errores to set
	 */
	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	/**
	 * @return the file
	 */
	public StreamedContent getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(StreamedContent file) {
		this.file = file;
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
	 * @return the perId
	 */
	public String getPerId() {
		return perId;
	}

	/**
	 * @param perId
	 *            the perId to set
	 */
	public void setPerId(String perId) {
		this.perId = perId;
	}

	/**
	 * @return the matNombre
	 */
	public String getMatNombre() {
		return matNombre;
	}

	/**
	 * @param matNombre
	 *            the matNombre to set
	 */
	public void setMatNombre(String matNombre) {
		this.matNombre = matNombre;
	}

	/**
	 * @return the matFechaNacimiento
	 */
	public Date getMatFechaNacimiento() {
		return matFechaNacimiento;
	}

	/**
	 * @param matFechaNacimiento
	 *            the matFechaNacimiento to set
	 */
	public void setMatFechaNacimiento(Date matFechaNacimiento) {
		this.matFechaNacimiento = matFechaNacimiento;
	}

	/**
	 * @return the matCarrera
	 */
	public String getMatCarrera() {
		return matCarrera;
	}

	/**
	 * @param matCarrera
	 *            the matCarrera to set
	 */
	public void setMatCarrera(String matCarrera) {
		this.matCarrera = matCarrera;
	}

	/**
	 * @return the matCorreo
	 */
	public String getMatCorreo() {
		return matCorreo;
	}

	/**
	 * @param matCorreo
	 *            the matCorreo to set
	 */
	public void setMatCorreo(String matCorreo) {
		this.matCorreo = matCorreo;
	}

	/**
	 * @return the matCorreoIns
	 */
	public String getMatCorreoIns() {
		return matCorreoIns;
	}

	/**
	 * @param matCorreoIns
	 *            the matCorreoIns to set
	 */
	public void setMatCorreoIns(String matCorreoIns) {
		this.matCorreoIns = matCorreoIns;
	}

	/**
	 * @return the matGenero
	 */
	public String getMatGenero() {
		return matGenero;
	}

	/**
	 * @param matGenero
	 *            the matGenero to set
	 */
	public void setMatGenero(String matGenero) {
		this.matGenero = matGenero;
	}

	/**
	 * @return the matNivel
	 */
	public String getMatNivel() {
		return matNivel;
	}

	/**
	 * @param matNivel
	 *            the matNivel to set
	 */
	public void setMatNivel(String matNivel) {
		this.matNivel = matNivel;
	}

	/**
	 * @return the matRepresDni
	 */
	public String getMatRepresDni() {
		return matRepresDni;
	}

	/**
	 * @param matRepresDni
	 *            the matRepresDni to set
	 */
	public void setMatRepresDni(String matRepresDni) {
		this.matRepresDni = matRepresDni;
	}

	/**
	 * @return the matRepresNombre
	 */
	public String getMatRepresNombre() {
		return matRepresNombre;
	}

	/**
	 * @param matRepresNombre
	 *            the matRepresNombre to set
	 */
	public void setMatRepresNombre(String matRepresNombre) {
		this.matRepresNombre = matRepresNombre;
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
	 * Lista de periodos
	 * 
	 * @return lista de items de estados
	 */
	public List<ArrReserva> getlistReserva() {
		try {
			ArrPeriodo per = manager.PeriodoAct();
			reservas = manager.ReservaByPeriodo(per.getPrdId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return reservas;
	}

	/**
	 * Maneja el proceso de selección, carga e inserción de datos de personas,
	 * partiendo de un archivo excel .XLS
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (prdId == null || prdId.isEmpty() || prdId.equals("-1")) {
				Mensaje.crearMensajeWARN("Debe seleccionar obligatoriamente un periodo");
			} else {

				if (event.getFile() == null)
					throw new Exception("No se ha seleccionado archivo");
				else {
					validarGuardarDatosExcel(event.getFile());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeERROR(e.getMessage());
		}
	}

	/**
	 * Maneja el proceso de selección, carga e inserción de datos de personas,
	 * partiendo de un archivo excel .XLS
	 * 
	 * @param event
	 */
	public void handleFileUpload2(FileUploadEvent event) {
		try {
			if (prdId == null || prdId.isEmpty() || prdId.equals("-1")) {
				Mensaje.crearMensajeWARN("Debe seleccionar obligatoriamente un periodo");
			} else {

				if (event.getFile() == null)
					throw new Exception("No se ha seleccionado archivo");
				else {
					validarGuardarDatosExcel2(event.getFile());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Mensaje.crearMensajeERROR(e.getMessage());
		}
	}

	/**
	 * Valida y Almacena los datos de excel
	 * 
	 * @param archivo
	 * @throws Exception
	 */
	public void validarGuardarDatosExcel(UploadedFile archivo) throws Exception {
		matriculados.clear();
		errores.clear();
		List<String> datosFila = new ArrayList<String>();
		// Toma la primera hoja
		Sheet hoja = Workbook.getWorkbook(archivo.getInputstream()).getSheet(0);
		// Validar estructura de excel
		if (!poseeEstructuraValida(hoja))
			throw new Exception("El archivo no posee la estructura correcta.");
		// Recorre todas las filas y columnas
		for (int i = 1; i < hoja.getRows(); i++) {
			if (filaValida(hoja.getRow(i), i + 1)) {
				datosFila.clear();
				for (int j = 0; j < NUMERO_COLUMNAS_EXCEL; j++) {
					datosFila.add(hoja.getCell(j, i).getContents().trim());
				}
				// Guardar datos en array
				matriculados.add(manager.crearMatriculado(datosFila, prdId));
			}
		}
		// ingresar personas
		manager.ingresarMatriculado(matriculados);
		// mostrar errores
		if (errores.size() > 0) {
			mostrarListaErrores();
			Mensaje.crearMensajeWARN("Existió errores dentro del archivo, "
					+ "pero los datos sin error fueron guardados.");
		} else
			Mensaje.crearMensajeINFO("Datos ingresados correctamente");

	}

	/**
	 * Valida y Almacena los datos de excel
	 * 
	 * @param archivo
	 * @throws Exception
	 */
	public void validarGuardarDatosExcel2(UploadedFile archivo)
			throws Exception {
		negados.clear();
		errores.clear();
		List<String> datosFila = new ArrayList<String>();
		// Toma la primera hoja
		Sheet hoja = Workbook.getWorkbook(archivo.getInputstream()).getSheet(0);
		// Validar estructura de excel
		if (!poseeEstructuraValida2(hoja))
			throw new Exception("El archivo no posee la estructura correcta.");
		// Recorre todas las filas y columnas
		for (int i = 1; i < hoja.getRows(); i++) {
			if (filaValida2(hoja.getRow(i), i + 1)) {
				datosFila.clear();
				for (int j = 0; j < NUMERO_COLUMNAS_EXCEL2; j++) {
					datosFila.add(hoja.getCell(j, i).getContents().trim());
				}
				// Guardar datos en array
				negados.add(manager.crearNegado(datosFila, prdId));
			}
		}
		// ingresar personas
		manager.ingresarNegado(negados);
		// mostrar errores
		if (errores.size() > 0) {
			mostrarListaErrores();
			Mensaje.crearMensajeWARN("Existió errores dentro del archivo, "
					+ "pero los datos sin error fueron guardados.");
		} else
			Mensaje.crearMensajeINFO("Datos ingresados correctamente");

	}

	/**
	 * Abre un popup con la lista de errores
	 */
	private void mostrarListaErrores() {
		e = "";
		RequestContext.getCurrentInstance().execute("PF('dlgerr').show()");
		for (String error : errores) {
			e = e + error + "\n";
			System.out.println(e);
		}
	}

	/**
	 * Valida la extructura de Excel
	 * 
	 * @param hoja
	 * @return boolean
	 */
	private boolean poseeEstructuraValida2(Sheet hoja) {
		return manager.validarEncabezadosExcel2(hoja.getRow(0));
	}

	/**
	 * Valida la extructura de Excel
	 * 
	 * @param hoja
	 * @return boolean
	 */
	private boolean poseeEstructuraValida(Sheet hoja) {
		return manager.validarEncabezadosExcel(hoja.getRow(0));
	}

	/**
	 * Valida los datos de una fila
	 * 
	 * @param column
	 * @param nroFila
	 * @return boolean
	 */
	private boolean filaValida(Cell[] column, int nroFila) {
		String error = manager.validarFilaExcel(column);
		if (error.isEmpty())
			return true;
		else {
			errores.add("Fila NRO " + nroFila + " : " + error);
			return false;
		}
	}

	/**
	 * Valida los datos de una fila
	 * 
	 * @param column
	 * @param nroFila
	 * @return boolean
	 */
	private boolean filaValida2(Cell[] column, int nroFila) {
		String error = manager.validarFilaExcel2(column);
		if (error.isEmpty())
			return true;
		else {
			errores.add("Fila NRO " + nroFila + " : " + error);
			return false;
		}
	}

	public void verPeriodo() {
		System.out.println(prdId);
	}
	
	/**
	 * metodo para eliminar una reserva
	 * 
	 * @param res
	 */
	public void eliminarR(ArrReserva res) {
		if (!res.getResEstado().equals("F")){
			manager.eliminarReserva(res);
		}else{
			Mensaje.crearMensajeWARN("El estado Finalizado del Contrato impide su eliminación");
		}
		
	}
	
	/**
	 * Metodo para activar el boton de finalización
	 * 
	 * @param est
	 * @return
	 */
	public boolean estado(String est){
		if (est.equals("F")){
			return true;
		}else{
			return false;
		}
	}
	
	public String finalizar(ArrReserva res){
		try {
			manager.cambiarEstado(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
