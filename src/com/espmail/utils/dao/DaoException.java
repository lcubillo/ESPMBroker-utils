/**
 * 
 */
package com.espmail.utils.dao;

import java.io.IOException;
import java.util.Properties;

/**
 * Excepcion creada para los errores en la construccion, validación y ejecución
 * de los metodos del dao
 * 
 */

public class DaoException extends Exception {

	private static final long serialVersionUID = -7773610966116416080L;

	// int con el codigo del error
	private int codigoError;

	// lee el archivo DaoException.properties
	private final static Properties PROP = new Properties();
	static {
		String nombre = DaoException.class.getName().replaceAll("\\.", "/")
				+ ".properties";
		try {
			PROP.load(DaoException.class.getClassLoader().getResourceAsStream(
					nombre));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * errores y sus códigos. Se leen del archivo .properties
	 */
	public static final int ERRORGENERICO = 1;

	public static final int ERRORINTERFACE = 2;

	public static final int ERRORPARAMETROS = 3;

	public static final int ERRORQUERYFILAS = 4;

	public static final int ERRORNUMEROPARAMETROS = 5;

	public static final int ERRORVALORQUERY = 6;

	public static final int ERRORCOLUMNASQUERY = 7;

	public static final int ERRORQUERYSELECT = 8;

	public static final int ERRORQUERYVACIA = 9;

	public static final int ERRORPARAMETROSQUERY = 10;

	public static final int ERRORXMLNOEXISTE = 11;

	/**
	 * Se llama al constructor padre con la situacion y la descripción del error
	 * 
	 * @param codigo
	 *            Codigo del error
	 * @param situacion
	 *            Situación del error.
	 */
	public DaoException(int codigo, String situacion) {
		super(situacion + " -->" + PROP.getProperty("Error." + codigo));
		this.codigoError = codigo;
	}

	/**
	 * Devuelve el código del error.
	 * 
	 * @return int con el código del error
	 */
	public int getCodigoError() {
		return this.codigoError;

	}

	/**
	 * Para los errores con la base de datos y otros, se asigna el código de
	 * error genérico 1 y se llama al padre con la excepción y la situación
	 * 
	 * @param t
	 *            La excepcion a capturar.
	 * @param situacion
	 *            Situación donde se lanza la excepción.
	 */
	public DaoException(Throwable t, String situacion) {
		super(situacion + " -->" + PROP.getProperty("Error.1"), t);
		this.codigoError = 1;
	}

}
