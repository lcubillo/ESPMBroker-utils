package com.espmail.utils;

import java.util.Locale;

/**
 * Clase que determina el tipo de idioma dependiendo de la url.
 * 
 * @author Luis Cubillo
 * 
 */
public class LocaleFinder {
	private final static Object[][] MAPEO = {	
		 { "br.", new Locale("pt", "PT") },
         { ".mx", new Locale("es", "MX") },
		 { ".ar", new Locale("es", "AR") },
		 { ".cl", new Locale("es", "CL") },
		 { ".pt", new Locale("pt", "PT") },
		 { ".com", new Locale("es", "ES")}					
        };

	/**
	 * Devuelve el tipo de idioma según el mapeo.
	 * 
	 * @param host
	 *            String con el host.
	 * @return Locale con el tipo local null si no es mexico, argentina,
	 */
	public static Locale find(String host) {

		if (host==null){
			Object[] tupla = MAPEO[5];
			Locale locale = (Locale) tupla[1];
			return locale;
		}
		for (int i = 0; i < MAPEO.length; i++) {
			Object[] tupla = MAPEO[i];
         
			if (host.indexOf((String) tupla[0]) != -1) {
				Locale locale = (Locale) tupla[1];
				return locale;
			}
		}
		return null;
	}
}
