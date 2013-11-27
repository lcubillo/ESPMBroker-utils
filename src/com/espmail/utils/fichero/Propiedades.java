package com.espmail.utils.fichero;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * Recarga un arhivo de propiedades en caso de que se modifique.
 * 
 * @author Luis Cubillo
 * 
 */
public class Propiedades extends Fichero {
	// Archivo de propiedades a recargar.
	private Properties props;

	/**
	 * Define el archivo properties a comprobar y cargar
	 * 
	 * @param nombreFichero
	 *            Nombre del fichero.
	 * @throws IOException
	 *             Excepción que se lanza en caso de error.
	 */
	public Propiedades(String nombreFichero) throws IOException {
		super(nombreFichero);
		this.props = new Properties();
	}

	/**
	 * Se le pasa el nombre del fichero y el tiempo a comprobar el archivo.
	 * 
	 * @param nombreFichero
	 *            Nombre del fichero
	 * @param time
	 *            Tiempo en milisegundos pasado el cual se comprobará el
	 *            fichero.
	 * @throws IOException
	 *             Excepción en caso de error
	 */
	public Propiedades(String nombreFichero, long time) throws IOException {
		super(nombreFichero, time);
		this.props = new Properties();
	}

	/**
	 * Recarga el fichero properties.
	 */
	protected final void reload(InputStream is) throws IOException {
		this.props.clear();
		this.props.load(is);
		reload();
	}

	/**
	 * Obtiene contenido de la propiedad para el nombre dado
	 * 
	 * @param name
	 *            Nombre de la propiedad.
	 * @return String con el contenido de la propiedad.
	 */
	public final String getProperty(String name) {
		return this.props.getProperty(name);
	}

	/**
	 * Por si se quiere hacer alguna otra cosa aparte de cargar las propiedades.
	 * 
	 */
	protected void reload() {
		// implementable
	}
}
