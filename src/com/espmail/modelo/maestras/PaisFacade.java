
package com.espmail.modelo.maestras;

import java.util.LinkedHashMap;
import java.util.Collection;


/**
 * Clase que obtiene los datos de la tabla paises y los almacena en una
 * linkedHashMap
 * @deprecated
 */
public class PaisFacade {
	// tabla con los paises
	// la clave es el código del pais y los valores son todos de tipo Pais
	private LinkedHashMap tablaPaises;

	//private final static String QUERY = "SELECT * FROM PAISES where cod not like 'NOP'";

	private static PaisFacade PAIS;

	/**
	 * Constructor. Busca en la base de datos en la tabla paises los paises y
	 * los guarda en la tablaPaises.
	 * 
	 */
	private PaisFacade() {
	}

	/**
	 * Obtiene el pais con el código que se le pasa
	 * 
	 * @param codigo
	 *            String con el código del pais
	 * @return Pais que tiene el código que se le pasa
	 */
	public Pais getPais(String codigo) {
		return  Pais.get(codigo);
	}

	/**
	 * Obtiene toda la lista de paises
	 * 
	 * @return Collection que tiene todos los paises de la base de datos.
	 */
	public Collection getPaises() {
		return Pais.values();
	}

	/**
	 * Devuelve la instancia de la clase (esto es una singleton)
	 * 
	 * @return PaisFacade que es la clase.
	 */
	public static PaisFacade getInstance() {

		if (PAIS == null)
			init();

		return PAIS;
	}

	/**
	 * permite no ralentizar en caso de que se acceda simultaneamente y que se
	 * asigne correctamente.
	 * 
	 */
	private synchronized static void init() {
		if (PAIS == null)
			PAIS = new PaisFacade();
	}
}
