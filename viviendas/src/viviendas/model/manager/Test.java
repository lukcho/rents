package viviendas.model.manager;

import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.generic.Funciones;

public class Test {

	public static void main(String[] args) {
		try {
			ManagerReserva m = new ManagerReserva();
			ArrMatriculado mat = m.buscarMatriculadoPeriodo("1003422365", "periodo");
			System.out.println(mat.getMatNombre());
			m.generarEnviarToken(mat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
