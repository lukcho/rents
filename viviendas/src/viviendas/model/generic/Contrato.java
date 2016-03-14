package viviendas.model.generic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import viviendas.model.dao.entities.ArrMatriculado;
import viviendas.model.dao.entities.ArrPeriodo;

public class Contrato {
	private static String DIR_CARPETA = File.separatorChar+"contratos";
	
	public static String generarContrato(ArrMatriculado estudiante, ArrPeriodo periodo, String nombreDepartamento) throws FileNotFoundException, DocumentException{
		String carpetaContratos = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath(DIR_CARPETA);
		
		/*CONTENIDO DEL DOCUMENTO*/
		String cabecera = "Comparecen a la celebraci�n del presente contrato de arrendamiento, "
				+ "por una parte, la EMPRESA P�BLICA YACHAY EP, legalmente representada por la "
				+ "Ing. Paola Soria Maldonado, Gerente Comercial, en calidad de delegada de la "
				+ "m�xima autoridad, conforme se desprende de la Resoluci�n N� YACHAY-EP-GG-2015-0012 "
				+ "de 13 de mayo de 2015, a quien en adelante se la denominar� �LA ARRENDADORA�; y, por "
				+ "otra el Sr.(ta) estudiante "+estudiante.getMatNombre().toUpperCase()+" con c�dula de "
				+ "ciudadan�a No. "+estudiante.getId().getPerDni()+"; "
				+ "por sus propios y personales derechos, a quien en adelante y para efectos de este contrato se "
				+ "denominar�, �ARRENDATARIO(A)�; las partes capaces para contratar y obligarse se comprometen con "
				+ "el presente instrumento al tenor de las siguientes cl�usulas:";
		
		String c11 = "1.1.- Mediante Suplemento al Registro Oficial No. 48 de 16 de octubre de 2009, "
				+ "se public� la Ley Org�nica de Empresas P�blicas, expedida por el Pleno de la Comisi�n "
				+ "Legislativa y de Fiscalizaci�n de la Asamblea Nacional;";
		
		String c12 = "1.2.- Mediante Decreto Ejecutivo No. 1457 de 13 de marzo de 2013, publicado en el Registro "
				+ "Oficial Suplemento No. 922 de 28 de marzo de 2013, el Presidente Constitucional de la Rep�blica "
				+ "cre� la EMPRESA P�BLICA YACHAY EP, la cual tiene como objeto el desarrollo de actividades econ�micas "
				+ "relacionadas a la administraci�n del Proyecto Ciudad del Conocimiento YACHAY que, entre otras, incluye: "
				+ "1. La administraci�n de Zonas de Especial Desarrollo Econ�mico que se creare para el electo; 2. Administraci�n "
				+ "de la concesi�n y arrendamiento de los espacios f�sicos de las Zonas de Especial Desarrollo Econ�mico;";
		
		String c13 = "1.3.- En sesi�n llevada a cabo el d�a 28 de marzo de 2013, el Directorio de la EMPRESA P�BLICA YACHAY EP, "
				+ "mediante Resoluci�n No. 01-DIR-YACHAY EP-28-03-2013, design� al Mgs. H�ctor Rodr�guez Ch�vez como Gerente General "
				+ "de la EMPRESA P�BLICA YACHAY EP;";
		
		String c14 = "1.4.- Mediante Resoluci�n No. CSP-2013-04EX-03 de 20 de septiembre de 2013, el Consejo Sectorial de la "
				+ "Producci�n autoriz� el establecimiento de la Zona Especial de Desarrollo Econ�mico YACHAY, de tipolog�as "
				+ "a) Tecnol�gica, b) Diversificaci�n Industrial y c) Servicios Log�sticos, territorio con una extensi�n de "
				+ "4200 hect�reas que se encuentra ubicada en el cant�n San Miguel de Urcuqu�, al norte de la provincia de Imbabura. ";
		
		String c15 = "1.5.- Mediante �Acta de Entrega Recepci�n Provisional de Inmuebles� suscrita el 30 de agosto de 2013, "
				+ "el Servicio de Gesti�n Inmobiliaria del Sector P�blico � INMOBILIAR- entreg� a la Empresa P�blica YACHAY "
				+ "EP la posesi�n del predio expropiado denominado �HACIENDA SAN JOSɔ, cuya transferencia de dominio se "
				+ "encuentra en tr�mite por cuanto los demandados interpusieron Recurso de Casaci�n de la sentencia dictada "
				+ "dentro del juicio de expropiaci�n iniciado en su contra.";
		
		String c16 = "1.6.- Mediante Resoluci�n N� YACHAY-EP-GG-2015-0012 de 13 de mayo de 2015, el se�or Gerente General de "
				+ "la Empresa P�blica YACHAY EP deleg� a la Gerente Comercial la suscripci�n de los contratos de arrendamiento, "
				+ "entre esta Empresa P�blica y los se�ores Docentes y Estudiantes de la Universidad de Investigaci�n de Tecnolog�a "
				+ "Experimental YACHAY, respecto de las residencias estudiantiles situadas en la Ciudad del Conocimiento YACHAY.";
		
		String c17 = "1.7.- Mediante memorando Nro. YACHAY-GC-2015-0409-MI de 07 de octubre de 2015, la Gerente Comercial Encargada, "
				+ "determin� el plazo y canon de arrendamiento que debe constar en los contratos de arrendamiento de las residencias "
				+ "estudiantiles";
		
		String c18 = "1.8.- Por tratarse de un contrato de arrendamiento cuya caracter�stica de uso no es de largo plazo, �ste se "
				+ "regir� por los usos y costumbres de la actividad, de conformidad con el art�culo 8 de la Resoluci�n INCOP "
				+ "No. 013-09 de 06 de marzo de 2009. ";
		
		String c21p1 ="2.1.- Forman parte de este Contrato, los siguientes documentos que son conocidos por las partes:";
		
		String c21p2 ="Los que acreditan la capacidad legal de los comparecientes;";
		
		String c21l1 ="El Acta de Entrega Recepci�n del bien inmueble destinado al arrendamiento;";
		
		String c21l2 ="Anexo No. 1, que contiene el listado del mobiliario, enseres y l�nea blanca existentes dentro de la residencia; y,";
		
		String c21l3 ="Resoluci�n N� YACHAY-EP-GG-2015-0012 de 13 de mayo de 2015, mediante la cual el Gerente General de la Empresa P�blica "
				+ "YACHAY EP deleg� a la Gerente Comercial la suscripci�n de los contratos de arrendamiento,  entre esta Empresa P�blica y los se�ores "
				+ "Docentes y Estudiantes de la Universidad YACHAY.";
		
		String c31 = "3.1.- Las partes de com�n acuerdo convienen en celebrar el presente contrato de arrendamiento, por medio del cual "
				+ "LA ARRENDADORA entrega en arrendamiento al ARRENDATARIO(A), la Vivienda "+nombreDepartamento+" de la Ciudad del Conocimiento Yachay, "
				+ "para utilizarlos de manera exclusiva para vivienda, mientras sea estudiante de la Universidad de Investigaci�n de Tecnolog�a "
				+ "Experimental YACHAY, la cual incluye mobiliario, enseres y l�nea blanca, cuya descripci�n y caracter�sticas se encuentran "
				+ "descritas en el Anexo No. 1, que es parte integrante del presente contrato.";
		
		String c32 = "3.2.- El inmueble entregado en arrendamiento ser� destinado exclusivamente para la vivienda, prohibi�ndose  expresamente "
				+ "el subarriendo del mismo.";
		
		String c41 = "4.1.- El canon mensual que el Arrendatario  pagar� a la Arrendadora es  de USD $ 55.00 (CINCUENTA Y CINCO 00/100 d�lares "
				+ "de los Estados Unidos de Am�rica), monto que incluye la al�cuota correspondiente.";
		
		String c42p1 = "4.2.- El valor antes mencionado ser� cancelado por EL ARRENDATARIO(A), dentro de los primeros 10 d�as de cada mes, en "
				+ "cualquiera de las siguientes formas:";
		
		String c42l1 = "Efectivo: En las oficinas de la Gerencia Comercial  ubicadas en la Hacienda San Eloy; o, en la Tienda Yachay Store.";
		
		String c42l2 = "Tarjeta de cr�dito: En la Tienda Yachay Store (cr�dito corriente).";
		
		String c42l3 = "Dep�sitos o transferencias bancarias: En la cuenta corriente No. 3001120648, sub l�nea 170202, del Banco Nacional de Fomento. ";
		
		String c42p2 = "Una vez realizado el pago, el ARRENDATARIO(A) deber� acudir a las oficinas de la Gerencia Comercial, con el respaldo "
				+ "del pago efectuado, a fin que la ARRENDADORA otorgue el respectivo comprobante de pago.";
		
		String c43 = "4.3.- En caso de que el ARRENDATARIO(A) no cancele oportunamente los c�nones mensuales de arrendamiento, la Empresa P�blica "
				+ "YACHAY EP pondr� en conocimiento de la Universidad de Investigaci�n de Tecnolog�a Experimental YACHAY sobre este particular, a fin "
				+ "que se establezcan las sanciones correspondientes; y, se niegue una nueva solicitud de arrendamiento por este motivo; sin perjuicio "
				+ "de que el ARRENDADOR inicie las acciones administrativas y legales correspondientes para el cobro respectivo.";
		
		String c51 = "5.1.- El presente contrato entrar� en vigencia desde el "+Funciones.dateToLetters(periodo.getPrdFechaInicio())+", y su plazo "
				+ "concluir� el "+Funciones.dateToLetters(periodo.getPrdFechaFin())+".";
		
		String c61 = "6.1.- El ARRENDATARIO(A), se obliga a:";
		
		String c61l1 = "Respetar la extensi�n del espacio f�sico y los bienes muebles asignados por la Empresa P�blica YACHAY E.P.;";
		
		String c61l2 = "El ARRENDATARIO(A) no podr� destinar los espacios f�sicos a otro fin distinto que el establecido en el objeto del presente contrato;";
		
		String c61l3 = "No consumir bebidas alcoh�licas ni sustancias estupefacientes y psicotr�picas en las instalaciones del bien arrendado por la Empresa P�blica YACHAY EP;";
		
		String c61l4 = "Mantener el inmueble arrendado y sus implementos, descritos en el Anexo No. 1 del presente Instrumento, en buenas condiciones, dar "
				+ "el mantenimiento, conservar el  orden y limpieza adecuados y entregarlo en la misma forma en la que lo recibieron a trav�s de la suscripci�n "
				+ "del Acta de Entrega Recepci�n;";
		
		String c61l5 = "Cancelar cumplidamente el canon y la al�cuota mensual de arrendamiento;";
		
		String c61l6 = "No subarrendar, ceder o transferir por cualquier concepto, el inmueble arrendado;";
		
		String c61l7 = "Las mejoras que impliquen cambios en la estructura interna del inmueble arrendado, deber�n ser autorizadas por la ARRENDADORA por escrito, "
				+ "y asumidas por el ARRENDATARIO cuando estas sean desarmables; el ARRENDATARIO(A) deber� retirarlas a la finalizaci�n del contrato, sin que esto "
				+ "cause el detrimento del bien entregado en arrendamiento;";
		
		String c61l8 = "Responder por sus propios actos y por los de terceros que se encontraren en el inmueble arrendado;";
		
		String c61l9 = "No podr� provocar algazaras o reyertas;";
		
		String c61l10 = "El ARRENDATARIO(A) est� obligado a usar la vivienda arrendada seg�n los t�rminos o esp�ritu del Contrato; y no podr�n, en consecuencia, hacerla "
				+ "servir a otros objetos que los convenidos, o a falta de convenci�n expresa, a los que la cosa est� naturalmente destinada, o que deban presumirse, "
				+ "atentas las circunstancias del contrato o la costumbre del pa�s.";
		
		String c61l11 = "Si el ARRENDATARIO(A) contraviene esta regla, podr� la ARRENDADORA reclamar la terminaci�n del contrato con indemnizaci�n de perjuicios, "
				+ "o limitarse a �sta indemnizaci�n, dejando subsistir el arriendo;";
		
		String c61l12 = "Cumplir con las obligaciones establecidas en el Contrato de forma �gil y oportuna;";
		
		String c61l13 = "En caso de da�os producidos por parte del ARRENDATARIO(A), tanto a la vivienda como a los bienes existentes en ella, por inobservancia "
				+ "de cualquiera de las obligaciones antes se�aladas, �ste acepta cubrir los costos que ocasionen las reparaciones o restituciones.";
		
		String c61l14 = "Queda terminantemente prohibido el ingreso de mascotas u otros animales al inmueble entregado en arrendamiento.";
		
		String c61l15 = "Permitir al delegado de YACHAY E.P realizar inspecciones mensuales al inmueble con el fin de verificar el estado de conservaci�n y "
				+ "cumplimiento del Contrato.  ";
		
		String c62 = "6.2.- La ARRENDADORA o Empresa P�blica YACHAY E.P. se obliga a:";
		
		String c62l1 = "Entregar en �ptimas condiciones y debidamente equipada, la vivienda materia del presente contrato;";
		
		String c62l2 = "Realizar las reparaciones de la vivienda, que no se deban a da�os ocasionados intencionalmente o mal uso del bien arrendado; ";
		
		String c62l3 = "Dar soluci�n a los problemas relacionados con da�os y servicios durante la vigencia del contrato, en forma oportuna;";
		
		String c62l4 = "A mantener el bien arrendado en estado de servir para el fin que ha sido arrendado;  y,";
		
		String c62l5 = "A trav�s del Administrador del Contrato, realizar visitas domiciliarias con el objeto de vigilar el adecuado mantenimiento, "
				+ "orden y limpieza, de los bienes entregados en arrendamiento.";
		
		String c63 = "6.3.- La ARRENDADORA y el ARRENDATARIO(A), se obligan a:";
		
		String c63l1 = "Levantar un Acta, cuyo objeto es dejar constancia del estado en el que se entrega y recibe el bien, con los enceres propios de �ste.";
		
		String c71 = "7.1.- Con el prop�sito de precautelar el buen estado del inmueble y del mobiliario, enseres y l�nea blanca entregados en arrendamiento, "
				+ "el ARRENDATARIO(A) da su autorizaci�n a la Empresa P�blica YACHAY, en la persona del Administrador del Contrato, a acceder al �rea arrendada "
				+ "una vez al mes, mientras dure el respectivo contrato de arrendamiento.";
		
		String c72 = "7.2.- La custodia de los bienes que forman parte de la vivienda entregada por la ARRENDADORA en el inmueble arrendado, es de su exclusiva "
				+ "responsabilidad, por tanto asume la obligaci�n de disponer las medidas correspondientes a objeto de precautelar su integridad, sin que esto "
				+ "afecte el normal desenvolvimiento de las actividades del ARRENDATARIO(A). Sin embargo, el ARRENDATARIO(A) es responsable del buen uso y trato "
				+ "adecuado de los mismos.";
		
		String c81 = "8.1.- En caso que el ARRENDATARIO(A) decidiera dar por terminado el Contrato en forma unilateral, la ARRENDADORA no tendr� derecho a "
				+ "indemnizaciones o reclamo alguno, siempre y cuando se le notifique por lo menos con treinta (30) d�as de anticipaci�n; y no existan da�os "
				+ "causados a los bienes arrendados.";
		
		String c82 = "8.2.- En caso de existir mora en el pago de dos pensiones locativas mensuales de arrendamiento, se faculta a la ARRENDADORA para que "
				+ "solicite el desahucio correspondiente.";
		
		String c91 = "9.1.- El ARRENDATARIO(A) de manera expresa declara que recibe el bien materia de este contrato de arrendamiento en perfectas condiciones, "
				+ "y se obliga a conservarlo de igual manera, para lo que, de ser necesario, repondr� o reparar� los bienes pertenecientes a dicho inmueble, en "
				+ "caso que sobre �stos se produzca alg�n da�o proveniente del mal uso y trato inadecuado. El ARRENDATARIO(A) declara ser responsables por los "
				+ "da�os que causare sobre el bien inmueble entregado.";
		
		String c101 = "10.1.- La Empresa P�blica YACHAY E.P. designa como �Administrador del Contrato� al Director de Promoci�n, Servicio al Cliente y Ventas; "
				+ "persona con quien el ARRENDATARIO(A), deber� canalizar y coordinar todas las obligaciones contractuales aqu� convenidas. El Administrador "
				+ "del Contrato ser� el encargado de velar por el cumplimiento de las normas legales y compromisos contractuales por parte del ARRENDATARIO(A).";
		
		String c102 = "10.2.- Respecto de su gesti�n reportar� a la Gerencia Administrativa Financiera de YACHAY EP, debiendo comunicar todos los aspectos "
				+ "operativos, t�cnicos, econ�micos y de cualquier naturaleza que pudieren afectar al cumplimiento del objeto del contrato.";
		
		String c111 = "11.1.- El contrato terminar� por las siguientes causales:";
		
		String c111l1 = "Por vencimiento del plazo;";
		
		String c111l2 = "Por mutuo acuerdo de las partes;";
		
		String c111l3 = "Por muerte del ARRENDATARIO(A) o disoluci�n de la Empresa P�blica YACHAY EP;";
		
		String c111l4 = "Por sentencia o laudo ejecutoriados que declaren la nulidad del contrato o la resoluci�n del mismo ha pedido de la ARRENDADORA. ";
		
		String c112 = "11.2.- Terminaci�n por parte de la Arrendadora: La ARRENDADORA podr� dar por terminado el arrendamiento y, por consiguiente, exigir la "
				+ "desocupaci�n y entrega del local arrendado antes de vencido el plazo legal, s�lo por una de las siguientes causas:";
		
		String c112l1 = "Cuando la falta de pago de las dos pensiones locativas mensuales se hubieren mantenido hasta la fecha en que se produjo la "
				+ "citaci�n de la demanda al ARRENDATARIO(A);";
		
		String c112l2 = "Peligro de destrucci�n o ruina del edificio en la parte que comprende el bien arrendado y que haga necesaria la reparaci�n;";
		
		String c112l3 = "Algazaras o reyertas ocasionadas por el ARRENDATARIO(A);";
		
		String c112l4 = "Incumplimiento de las obligaciones estipuladas en el presente contrato, respecto de la parte incumplida.";
		
		String c112l5 = "Destino del local arrendado a un objeto il�cito o distinto del convenido;";
		
		String c112l6 = "Subarriendo o traspaso de sus derechos, realizado por el ARRENDATARIO(A), sin tener autorizaci�n escrita para ello;";
		
		String c112l7 = "Ejecuci�n por el ARRENDATARIO(A) en el local arrendado de obras no autorizadas por la ARRENDADORA;";
		
		String c112l8 = "Resoluci�n de la ARRENDADORA de demoler el local para nueva edificaci�n. En ese caso, deber� citarse legalmente al ARRENDATARIO(A) "
				+ "con la solicitud de desahucio, con un (1) mes de anticipaci�n por lo menos, a la fecha fijada, para la demolici�n, la que s�lo podr� ser "
				+ "tramitada cuando se acompa�en los planos aprobados y el permiso de la Municipalidad respectiva para iniciar la obra.";
				
		String c112l9 = "Decisi�n de la ARRENDADORA de ocupar el inmueble arrendado, siempre y cuando justifique legalmente la necesidad de hacerlo, y que no "
				+ "tiene otro inmueble que ocupar. ";
		
		String c112l10 = "Si el ARRENDATARIO(A) no aprobara el semestre de estudios habiendo reprobado acad�micamente.";
		
		String c113 = "11.3.- Terminaci�n por Mutuo Acuerdo.- Cuando por circunstancias imprevistas, t�cnicas o econ�micas, o causas de fuerza mayor o caso "
				+ "fortuito, no fuere posible o conveniente para los intereses de las partes o una de ellas, continuar con el objeto de este contrato, �stas "
				+ "podr�n, por mutuo acuerdo, convenir en la extinci�n de todas o algunas de las obligaciones contractuales, en el estado que se encuentren. "
				+ "La terminaci�n por mutuo acuerdo no implicar� renuncia a derechos causados o adquiridos a favor de la ARRENDADORA o el ARRENDATARIO(A).";
		
		String c114 = "11.4.- Terminaci�n Unilateral.- LA ARRENDADORA podr� dar por terminado unilateral y anticipadamente el presente contrato, previa "
				+ "notificaci�n escrita al ARRENDATARIO(A) con treinta (30) d�as de anticipaci�n, sin que haya derecho a indemnizaci�n alguna a favor de la otra parte.";
		
		String c121 = "12.1.- Si se suscitaren divergencias o controversias en la interpretaci�n o ejecuci�n del presente contrato, las partes tratar�n de "
				+ "llegar a un acuerdo que solucione el problema.  De no existir dicho acuerdo, podr�n someter la controversia al proceso de mediaci�n como "
				+ "un sistema alternativo de soluci�n de conflictos reconocido por la Constituci�n de la Rep�blica del Ecuador y la Ley Org�nica de la "
				+ "Procuradur�a General del Estado; para lo cual las partes estipulan acudir al Centro de Mediaci�n de la Procuradur�a General del Estado en Imbabura.";
		
		String c122 = "12.2.- Por el contrario, si las partes no llegaren a un acuerdo, las controversias deber�n sustanciarse ante la Unidad Judicial de lo "
				+ "Contencioso Administrativo de la provincia de Imbabura, observando lo previsto en la ley de la materia.";
		
		String c123 = "12.3.- La legislaci�n aplicable a este Contrato es exclusivamente la correspondiente a la Rep�blica del Ecuador. En todo caso, el "
				+ "ARRENDATARIO(A) renuncia a utilizar la v�a diplom�tica para todo reclamo relacionado con este contrato. Si el ARRENDATARIO(A) incumplieren "
				+ "este compromiso, la ARRENDADORA podr� dar por terminado unilateralmente el contrato.";
		
		String c131 = "13.1.- A efectos de cualquier aviso o notificaci�n que las partes deban dirigirse en virtud del presente Contrato, el mismo se "
				+ "efectuar� por escrito y se considerar� dado, entregado o realizado desde el momento en que el documento correspondiente se entregue "
				+ "al destinatario en su respectiva direcci�n o por medio de una notificaci�n escrita entregada personalmente. Con este fin las direcciones "
				+ "de las partes son las siguientes:";
		
		String c132 = "13.2.- Cualquier cambio de direcci�n deber� ser notificado por escrito a la otra parte para que surta sus efectos legales, de lo "
				+ "contrario tendr�n validez los avisos efectuados a las direcciones antes indicadas.  En caso de no constar la direcci�n el ARRENDATARIO(A), "
				+ "la ARRENDADORA tramitar� directamente con la Universidad de Tecnolog�a Experimental Yachay la notificaci�n de las comunicaciones que les corresponda.";
		
		String c133 = "13.3.- Las controversias deben tramitarse de conformidad con la Cl�usula D�cima Segunda de este contrato.";
		
		String c141p1 = "14.1.- Libre y voluntariamente, previo el cumplimiento de todos los requisitos exigidos por las leyes de la materia, las partes declaran "
				+ "expresamente su aceptaci�n a todo lo convenido en el presente contrato de arrendamiento, a cuyas estipulaciones se someten.";
		
		String c141p2 = "Para constancia de lo estipulado, las partes firman en TRES (3) ejemplares de igual tenor y contenido. Dado en Urcuqu�, a los";
		
		/*ESTRUCTURA DEL DOCUMENTO*/
		Document documento = new Document();
		
		PdfWriter.getInstance(documento, new FileOutputStream(carpetaContratos+File.separatorChar+periodo.getPrdId()+"_"+estudiante.getId().getPerDni()+".pdf"));
		
		documento.setPageSize(PageSize.A4);
        documento.setMargins(45, 45, 45, 45); //(float marginLeft, float marginRight, float marginTop, float marginBottom)
		
		Font titulo = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
		Font subtitulo = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD);
		Font parrafo = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
		
		Paragraph pTitulo = new Paragraph("CONTRATO DE ARRENDAMIENTO", titulo);
		pTitulo.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph pCabecera = new Paragraph(cabecera, parrafo);
		pCabecera.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC11 = new Paragraph(c11, parrafo);
		pC11.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC12 = new Paragraph(c12, parrafo);
		pC12.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC13 = new Paragraph(c13, parrafo);
		pC13.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC14 = new Paragraph(c14, parrafo);
		pC14.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC15 = new Paragraph(c15, parrafo);
		pC15.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC16 = new Paragraph(c16, parrafo);
		pC16.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC17 = new Paragraph(c17, parrafo);
		pC17.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC18 = new Paragraph(c18, parrafo);
		pC18.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC21p1 = new Paragraph(c21p1, parrafo);
		pC21p1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC21p2 = new Paragraph(c21p2, parrafo);
		pC21p2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC21l1 = new Paragraph(c21l1, parrafo);
		pC21l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC21l2 = new Paragraph(c21l2, parrafo);
		pC21l2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC21l3 = new Paragraph(c21l3, parrafo);
		pC21l3.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc21 = new List(true,true);
		lc21.setLowercase(true);
		lc21.setIndentationLeft(20);
		lc21.setIndentationLeft(20);
		lc21.add(new ListItem(pC21l1));
		lc21.add(new ListItem(pC21l2));
		lc21.add(new ListItem(pC21l3));
		
		Paragraph pC31 = new Paragraph(c31, parrafo);
		pC31.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC32 = new Paragraph(c32, parrafo);
		pC32.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC41 = new Paragraph(c41, parrafo);
		pC41.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC42p1 = new Paragraph(c42p1, parrafo);
		pC42p1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC42l1 = new Paragraph(c42l1, parrafo);
		pC42l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC42l2 = new Paragraph(c42l2, parrafo);
		pC42l2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC42l3 = new Paragraph(c42l3, parrafo);
		pC42l3.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc42 = new List();
		lc42.setIndentationLeft(20);
		lc42.add(new ListItem(pC42l1));
		lc42.add(new ListItem(pC42l2));
		lc42.add(new ListItem(pC42l3));
		
		Paragraph pC42p2 = new Paragraph(c42p2, parrafo);
		pC42p2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC43 = new Paragraph(c43, parrafo);
		pC43.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC51 = new Paragraph(c51, parrafo);
		pC51.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61 = new Paragraph(c61, subtitulo);
		pC61.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l1 = new Paragraph(c61l1, parrafo);
		pC61l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l2 = new Paragraph(c61l2, parrafo);
		pC61l2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l3 = new Paragraph(c61l3, parrafo);
		pC61l3.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l4 = new Paragraph(c61l4, parrafo);
		pC61l4.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l5 = new Paragraph(c61l5, parrafo);
		pC61l5.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l6 = new Paragraph(c61l6, parrafo);
		pC61l6.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l7 = new Paragraph(c61l7, parrafo);
		pC61l7.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l8 = new Paragraph(c61l8, parrafo);
		pC61l8.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l9 = new Paragraph(c61l9, parrafo);
		pC61l9.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l10 = new Paragraph(c61l10, parrafo);
		pC61l10.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l11 = new Paragraph(c61l11, parrafo);
		pC61l11.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l12 = new Paragraph(c61l12, parrafo);
		pC61l12.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l13 = new Paragraph(c61l13, parrafo);
		pC61l13.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l14 = new Paragraph(c61l14, parrafo);
		pC61l14.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC61l15 = new Paragraph(c61l15, parrafo);
		pC61l15.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc61 = new List(true,true);
		lc61.setLowercase(true);
		lc61.setIndentationLeft(20);
		lc61.add(new ListItem(pC61l1));
		lc61.add(new ListItem(pC61l2));
		lc61.add(new ListItem(pC61l3));
		lc61.add(new ListItem(pC61l4));
		lc61.add(new ListItem(pC61l5));
		lc61.add(new ListItem(pC61l6));
		lc61.add(new ListItem(pC61l7));
		lc61.add(new ListItem(pC61l8));
		lc61.add(new ListItem(pC61l9));
		lc61.add(new ListItem(pC61l10));
		lc61.add(new ListItem(pC61l11));
		lc61.add(new ListItem(pC61l12));
		lc61.add(new ListItem(pC61l13));
		lc61.add(new ListItem(pC61l14));
		lc61.add(new ListItem(pC61l15));
		
		Paragraph pC62 = new Paragraph(c62, subtitulo);
		pC62.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC62l1 = new Paragraph(c62l1, parrafo);
		pC62l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC62l2 = new Paragraph(c62l2, parrafo);
		pC62l2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC62l3 = new Paragraph(c62l3, parrafo);
		pC62l3.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC62l4 = new Paragraph(c62l4, parrafo);
		pC62l4.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC62l5 = new Paragraph(c62l5, parrafo);
		pC62l5.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc62 = new List(true,true);
		lc62.setLowercase(true);
		lc62.setIndentationLeft(20);
		lc62.add(new ListItem(pC62l1));
		lc62.add(new ListItem(pC62l2));
		lc62.add(new ListItem(pC62l3));
		lc62.add(new ListItem(pC62l4));
		lc62.add(new ListItem(pC62l5));
		
		Paragraph pC63 = new Paragraph(c63, subtitulo);
		pC63.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC63l1 = new Paragraph(c63l1, parrafo);
		pC63l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc63 = new List(true,true);
		lc63.setLowercase(true);
		lc63.setIndentationLeft(20);
		lc63.add(new ListItem(pC63l1));
		
		Paragraph pC71 = new Paragraph(c71, parrafo);
		pC71.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC72 = new Paragraph(c72, parrafo);
		pC72.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC81 = new Paragraph(c81, parrafo);
		pC81.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC82 = new Paragraph(c82, parrafo);
		pC82.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC91 = new Paragraph(c91, parrafo);
		pC91.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC101 = new Paragraph(c101, parrafo);
		pC101.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC102 = new Paragraph(c102, parrafo);
		pC102.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC111 = new Paragraph(c111, parrafo);
		pC111.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC111l1 = new Paragraph(c111l1, parrafo);
		pC111l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC111l2 = new Paragraph(c111l2, parrafo);
		pC111l2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC111l3 = new Paragraph(c111l3, parrafo);
		pC111l3.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC111l4 = new Paragraph(c111l4, parrafo);
		pC111l4.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc111 = new List(true,true);
		lc111.setLowercase(true);
		lc111.setIndentationLeft(20);
		lc111.add(new ListItem(pC111l1));
		lc111.add(new ListItem(pC111l2));
		lc111.add(new ListItem(pC111l3));
		lc111.add(new ListItem(pC111l4));
		
		Paragraph pC112 = new Paragraph(c112, parrafo);
		pC112.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l1 = new Paragraph(c112l1, parrafo);
		pC112l1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l2 = new Paragraph(c112l2, parrafo);
		pC112l2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l3 = new Paragraph(c112l3, parrafo);
		pC112l3.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l4 = new Paragraph(c112l4, parrafo);
		pC112l4.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l5 = new Paragraph(c112l5, parrafo);
		pC112l5.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l6 = new Paragraph(c112l6, parrafo);
		pC112l6.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l7 = new Paragraph(c112l7, parrafo);
		pC112l7.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l8 = new Paragraph(c112l8, parrafo);
		pC112l8.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l9 = new Paragraph(c112l9, parrafo);
		pC112l9.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC112l10 = new Paragraph(c112l10, parrafo);
		pC112l10.setAlignment(Element.ALIGN_JUSTIFIED);
		
		List lc112 = new List(true,true);
		lc112.setLowercase(true);
		lc112.setIndentationLeft(20);
		lc112.add(new ListItem(pC112l1));
		lc112.add(new ListItem(pC112l2));
		lc112.add(new ListItem(pC112l3));
		lc112.add(new ListItem(pC112l4));
		lc112.add(new ListItem(pC112l5));
		lc112.add(new ListItem(pC112l6));
		lc112.add(new ListItem(pC112l7));
		lc112.add(new ListItem(pC112l8));
		lc112.add(new ListItem(pC112l9));
		lc112.add(new ListItem(pC112l10));
		
		Paragraph pC113 = new Paragraph(c113, parrafo);
		pC113.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC114 = new Paragraph(c114, parrafo);
		pC114.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC121 = new Paragraph(c121, parrafo);
		pC121.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC122 = new Paragraph(c122, parrafo);
		pC122.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC123 = new Paragraph(c123, parrafo);
		pC123.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC131 = new Paragraph(c131, parrafo);
		pC131.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC132 = new Paragraph(c132, parrafo);
		pC132.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC133 = new Paragraph(c133, parrafo);
		pC133.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC141p1 = new Paragraph(c141p1, parrafo);
		pC141p1.setAlignment(Element.ALIGN_JUSTIFIED);
		
		Paragraph pC141p2 = new Paragraph(c141p2, parrafo);
		pC141p2.setAlignment(Element.ALIGN_JUSTIFIED);
		
		PdfPCell t1c1f1 = new PdfPCell(new Phrase("LA ARRENDADORA:", subtitulo));
		t1c1f1.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell t1c2f1 = new PdfPCell(new Phrase("EL ARRENDADOR(A):", subtitulo));
		t1c2f1.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell t2c1f1 = new PdfPCell(new Phrase("PAOLA SORIA MALDONADO ", parrafo));
		t2c1f1.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell t2c2f1 = new PdfPCell(new Phrase(estudiante.getMatNombre().toUpperCase(), parrafo));
		t2c2f1.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell t2c1f2 = new PdfPCell(new Phrase("GERENTE COMERCIAL (E)", parrafo));
		t2c1f2.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell t2c2f2 = new PdfPCell(new Phrase("C.C. "+estudiante.getId().getPerDni(), parrafo));
		t2c2f2.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell t2c1f3 = new PdfPCell(new Phrase("EMPRESA P�BLICA YACHAY EP", subtitulo));
		t2c1f3.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell vacia = new PdfPCell(new Phrase("	", parrafo));
		vacia.setBorder(Rectangle.NO_BORDER);
				
		PdfPTable tFirmas = new PdfPTable(3);
		tFirmas.setWidthPercentage(100);
		tFirmas.addCell(t1c1f1);
		tFirmas.addCell(vacia);
		tFirmas.addCell(t1c2f1);

		PdfPTable firmas = new PdfPTable(3);
		firmas.setWidthPercentage(100);
		firmas.addCell(t2c1f1);
		firmas.addCell(vacia);
		firmas.addCell(t2c2f1);
		firmas.addCell(t2c1f2);
		firmas.addCell(vacia);
		firmas.addCell(t2c2f2);
		firmas.addCell(t2c1f3);
		firmas.addCell(vacia);
		firmas.addCell(vacia);
		
		/*ESCRITURA DEL DOCUMENTO*/
		documento.open();
		documento.add(pTitulo);
		documento.add(Chunk.NEWLINE);
		documento.add(pCabecera);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA PRIMERA.- ANTECEDENTES:", subtitulo));
		documento.add(pC11);
		documento.add(Chunk.NEWLINE);
		documento.add(pC12);
		documento.add(Chunk.NEWLINE);
		documento.add(pC13);
		documento.add(Chunk.NEWLINE);
		documento.add(pC14);
		documento.add(Chunk.NEWLINE);
		documento.add(pC15);
		documento.add(Chunk.NEWLINE);
		documento.add(pC16);
		documento.add(Chunk.NEWLINE);
		documento.add(pC17);
		documento.add(Chunk.NEWLINE);
		documento.add(pC18);
		documento.add(Chunk.NEWLINE);documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA SEGUNDA.- DOCUMENTOS HABILITANTES:", subtitulo));
		documento.add(pC21p1);
		documento.add(Chunk.NEWLINE);
		documento.add(pC21p2);
		documento.add(Chunk.NEWLINE);
		documento.add(lc21);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA TERCERA.- OBJETO DEL CONTRATO DE ARRENDAMIENTO:", subtitulo));
		documento.add(pC31);
		documento.add(Chunk.NEWLINE);
		documento.add(pC32);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA CUARTA.- CANON MENSUAL DE ARRENDAMIENTO Y FORMA DE PAGO:", subtitulo));
		documento.add(pC41);
		documento.add(Chunk.NEWLINE);
		documento.add(pC42p1);
		documento.add(lc42);
		documento.add(Chunk.NEWLINE);
		documento.add(pC42p2);
		documento.add(Chunk.NEWLINE);
		documento.add(pC43);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA QUINTA.- PLAZO:", subtitulo));
		documento.add(pC51);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA SEXTA.- OBLIGACIONES DE LAS PARTES:", subtitulo));
		documento.add(pC61);
		documento.add(lc61);
		documento.add(Chunk.NEWLINE);
		documento.add(pC62);
		documento.add(lc62);
		documento.add(Chunk.NEWLINE);
		documento.add(pC63);
		documento.add(lc63);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA S�PTIMA.- NORMAS DE SEGURIDAD:", subtitulo));
		documento.add(pC71);
		documento.add(Chunk.NEWLINE);
		documento.add(pC72);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA OCTAVA.- FACULTADES DE LAS PARTES:", subtitulo));
		documento.add(pC81);
		documento.add(Chunk.NEWLINE);
		documento.add(pC82);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA NOVENA.- DECLARACI�N DEL ESTADO DEL INMUEBLE:", subtitulo));
		documento.add(pC91);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA D�CIMA.- ADMINISTRADOR DEL CONTRATO:", subtitulo));
		documento.add(pC101);
		documento.add(Chunk.NEWLINE);
		documento.add(pC102);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA D�CIMO PRIMERA.- TERMINACI�N DEL CONTRATO:", subtitulo));
		documento.add(pC111);
		documento.add(Chunk.NEWLINE);
		documento.add(lc111);
		documento.add(Chunk.NEWLINE);
		documento.add(pC112);
		documento.add(Chunk.NEWLINE);
		documento.add(lc112);
		documento.add(Chunk.NEWLINE);
		documento.add(pC113);
		documento.add(Chunk.NEWLINE);
		documento.add(pC114);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA D�CIMO SEGUNDA.- SOLUCI�N DE CONTROVERSIAS:", subtitulo));
		documento.add(pC121);
		documento.add(Chunk.NEWLINE);
		documento.add(pC122);
		documento.add(Chunk.NEWLINE);
		documento.add(pC123);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA D�CIMO TERCERA.- NOTIFICACIONES:", subtitulo));
		documento.add(pC131);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("LA ARRENDADORA:",subtitulo));
		documento.add(new Paragraph("AMAZONAS N26-146 Y LA NI�A.",parrafo));
		documento.add(new Paragraph("QUITO (PICHINCHA) � ECUADOR",parrafo));
		documento.add(new Paragraph("TEL�FONO: 3949100",parrafo));
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("EL ARRENDATARIO(A):",subtitulo));
		documento.add(new Paragraph(estudiante.getMatNombre().toUpperCase(),parrafo));
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("DIRECCI�N:",subtitulo));
		documento.add(new Paragraph(nombreDepartamento,parrafo));
		documento.add(new Paragraph("CIUDAD DEL CONOCIMIENTO YACHAY, PROYECTO YACHAY",parrafo));
		documento.add(new Paragraph("URCUQU� (IMBABURA) � ECUADOR",parrafo));
		documento.add(Chunk.NEWLINE);documento.add(Chunk.NEWLINE);
		documento.add(pC132);
		documento.add(Chunk.NEWLINE);
		documento.add(pC133);
		documento.add(Chunk.NEWLINE);
		documento.add(new Paragraph("CL�USULA D�CIMO CUARTA.- ACEPTACI�N DE LAS PARTES:", subtitulo));
		documento.add(pC141p1);
		documento.add(pC141p2);
		documento.add(Chunk.NEWLINE);
		documento.add(Chunk.NEWLINE);
		documento.add(tFirmas);
		documento.add(Chunk.NEWLINE);
		documento.add(Chunk.NEWLINE);
		documento.add(Chunk.NEWLINE);
		documento.add(firmas);
		documento.close();
		
		return periodo.getPrdId()+"_"+estudiante.getId().getPerDni()+".pdf";
	}
}
