package com.espmail.utils.dao;

/**
 * 
 * Clase que contiene informaci�n del par�metro de un m�todo.
 * 
 */
class Parametro {
	// la posicion en el array de parametros.
	private final int indice;
	// en caso de que acceda a un parametro con punto lo que hay a la derecha
	private final String property;

	/**
	 * Pone un indice y un nombre al parametro
	 * 
	 * @param indice
	 *            posicion del parametro
	 * @param property
	 *            propiedad del par�metro
	 */
	public Parametro(int indice, String property) {
		this.indice = indice;
		this.property = property;
	}

	/**
	 * Devuelve el indice del parametro
	 * 
	 * @return int con el �ndice
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * Devuelve la propiedad del parametro.
	 * 
	 * @return String con la propiedad
	 */
	public String getProperty() {
		return property;
	}
}
