package viviendas.model.generic;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Funciones {
	
	private static  final int num_provincias = 24;
	/**
	 * Atributos para el manejo de estados
	 */
	public static String estadoActivo = "A";
	public static String valorEstadoActivo = "Activo";
	public static String estadoInactivo = "I";
	public static String valorEstadoInactivo = "Inactivo";
	public static String estadoEnviado = "E";
	public static String valorEstadoEnviado = "Enviado";
	public static String estadoNegado = "N";
	public static String valorEstadoNegado = "Negado";
	
	public static String hostWS = "http://10.1.0.158:8080/sgupy/";
	
	public static Boolean validacionCedula(String cedula){
        //verifica que los dos primeros d�gitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
        int prov = Integer.parseInt(cedula.substring(0, 2));
        if (!((prov > 0) && (prov <= num_provincias) || prov==30)) {
        	//addError("La c�dula ingresada no es v�lida");
        	System.out.println("Error: cedula ingresada mal");
            return false;
        }
        //verifica que el �ltimo d�gito de la c�dula sea v�lido
        int[] d = new int[10];
        //Asignamos el string a un array
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }
        int imp = 0;
        int par = 0;
        //sumamos los duplos de posici�n impar
        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }
        //sumamos los digitos de posici�n par
        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }
        //Sumamos los dos resultados
        int suma = imp + par;
        //Restamos de la decena superior
        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) + "0") - suma;
        //Si es diez el d�cimo d�gito es cero        
        d10 = (d10 == 10) ? 0 : d10;
        //si el d�cimo d�gito calculado es igual al digitado la c�dula es correcta
        if (d10 == d[9]) {
        	return true;
        }else {
        	//addError("La c�dula ingresada no es v�lida");
        	return false;
        }
	}
	
	/**
	 * Convierte un cadena en codigo MD5
	 * @param input entrada de cadena para convertirla en MD5
	 * @return String MD5
	 */
	public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * Convierte un cadena en codigo SHA2
	 * @param input  entrada de cadena para convertirla en SHA2
	 * @return String MD5
	 */
	public String getSHA2(String input) {
	    try {
	        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
	        String salt = "some_random_salt";
	        String passWithSalt = input + salt;
	        byte[] passBytes = passWithSalt.getBytes();
	        byte[] passHash = sha256.digest(passBytes);             
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< passHash.length ;i++) {
	            sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));         
	        }
	        String generatedPassword = sb.toString();
	        return generatedPassword;
	    } catch (NoSuchAlgorithmException e) { 
	    	e.printStackTrace(); 
	    	return null;
	    }       
	}
	
	/**
	 * Transforma una fecha a String
	 * @param fecha
	 * @return String
	 */
	public static String dateToString(Date fecha){
		DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		if(fecha==null)
			return "";
		else
			return formato.format(fecha).toString();
	}
	
	/**
	 * Transforma un string de fecha en Date
	 * @param fecha
	 * @return Date
	 * @throws ParseException
	 */
	public static Date stringToDate(String fecha) throws ParseException{
		DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		if(fecha.isEmpty())
			return null;
		else
			return formato.parse(fecha);
	}
	
	/**
	 * Evalua si una cadena es numerica
	 * @param cadena
	 * @return
	 */
	public static boolean isNumeric(String cadena){
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	/**
	 * Evalua un dato que viene desde un JSON
	 * @param dato
	 * @return String
	 */
	public static String evaluarDatoWS(Object dato){
		if(dato!=null)
			return dato.toString();
		else
			return "";
	}
	
	/**
	 * Evalua un String para convertirlo a Entero
	 * @param dato
	 * @return Integer
	 */
	public static Integer evaluarString(String dato){
		if(dato.isEmpty())
			return 0;
		else
			return Integer.parseInt(dato);
	}
	
	/**
	 * Genera un pass unico
	 * @return pass
	 */
	public static String genPass(){
		String pass = ""; 
		for (int i = 0; i < 4; i++) {
			pass+=(char)(Math.random()*25+97)+""+(int)(Math.random()*9+1);
		}
		return pass;
	}
	
	/**
	 * Convierte una cadena en otra con codificaci�n utf-8
	 * @param cadena
	 * @return String
	 */
	public static String utf8Sting(String cadena){
		try {
			return new String(cadena.getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "Error utf8Sting";
		}
	}
	
	/**
	 * Transforma un timestamp a String
	 * @param estFechaIni
	 * @return String
	 */
	public static String timestampToString(Timestamp estFechaIni) {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(estFechaIni);
	}
}
