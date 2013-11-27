package com.espmail.utils.informes;
/**
 * Clase para las excepciones que se produzcan en la generación de informes. 
 * @author Luis Cubillo
 *
 */
public class GeneradorInformesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * Crea la excepción. 
 * @param cause Causa de la excepción.
 */
	public GeneradorInformesException(Throwable cause) {
		super(cause);
	}
}
