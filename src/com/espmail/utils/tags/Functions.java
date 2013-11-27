package com.espmail.utils.tags;

import java.util.Collection;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.espmail.modelo.maestras.Categoria;
import com.espmail.modelo.maestras.Idioma;
import com.espmail.modelo.maestras.Ocupacion;
import com.espmail.modelo.maestras.Pais;
import com.espmail.modelo.maestras.Sector;

import com.espmail.utils.contexto.Contexto;

/**
 * Distintas funciones de genéricas.
 * @author Luis Cubillo.
 *
 */
public class Functions {

	private static final String[] DIAS = {
		"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
		"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
		"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
	};

	private static final String[] MESES = {
		"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
	};

	private static String DIA = DIAS[Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1];
	
	private static String MES = MESES[Calendar.getInstance().get(Calendar.MONTH)];
	
	private static int ANHO = Calendar.getInstance().get(Calendar.YEAR);

	private static String ANHO_ST = Integer.toString(ANHO);
	
	private static String[] ANHOS = new String[ANHO - 2000 + 1];
	
	static {
		for (int i = ANHO, j = 0; i >= 2000; i--, j++) {
			ANHOS[j] = Integer.toString(i);
		}
	}
		
	/**
	 * Collection con los paises a los que pertenece el webmaster.
	 * @return Collection con los paises a los que pertenece.
	 */
	public static Collection paises() {
		return Pais.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Collection idiomas() {
		return Idioma.values();
	}

	public static Collection categorias(){
		return Categoria.values();
	}
	
	public static Collection sectores(){
		return Sector.values();
	}
	
	public static Collection ocupaciones(){
		return Ocupacion.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Idioma idioma() {
		return Contexto.getInstance().getIdioma().getLanguage()
			.equals("pt")? Idioma.PORTUGUES : Idioma.ESPANHOL;
	}

	/**
	 * 
	 * @param req
	 * @param rol
	 * @return
	 */
	public static boolean isUserInRole(HttpServletRequest req, String rol) {
		return req.isUserInRole(rol);
	}

    /**
     *
     * @param req
     * @param rol
     * @return
     */
    public static boolean isUserInRole(HttpServletRequest req, String [] rol) {
        int i=0;
        for(;!req.isUserInRole(rol[i]) && i<rol.length ; i++);
        if(i>=rol.length)
            return false;
        else
            return req.isUserInRole(rol[i]);
	}
    /**
	 * Devuelve los días de un mes como un array de String cada día es un
	 * String con el formato 01,02,...,31.
	 * @return String[] con los días de un mes.
	 */
	public static boolean inArray(Object valor, Object[] array) {
		if (valor == null || array == null) {
			return false;
		}

		String st = valor.toString();

		for (int i = 0; i < array.length; i++) {
			if (st.equals(array[i].toString())) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public static String[] dias() {
		return DIAS;
	}
	
	/**
	 * Devuelve los meses de un año como un array de String cada mes es un
	 * String con el formato 01,02,...,12 
	 * @return String[] con los meses de un año.
	 */
	public static String[] meses() {
		return MESES;
	}
	
	/**
	 * Método que devuelve un array con todos los años desde el 2000 al actual
	 * @return
	 */
	public static String[] anhos() {
		return ANHOS;
	}
	
	/**
	 * Devuelve el día del mes como un String.
	 * @return String con el dia del mes 
	 */
	public static String dia() {
		return DIA;
	}
	
	/**
	 * Devuelve el número del mes en es que estamos como un String.
	 * @return String con el mes.
	 */
	public static String mes() {
		return MES;
	}
	
	/**
	 * Año en el que estamos como String.
	 * @return String con el año en el que estamos.
	 */
	public static String anho() {
		return ANHO_ST;
	}
}
