package com.espmail.utils.informes;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamSource;

import com.espmail.utils.fichero.Fichero;

/**
 * Clase que comprueba si se ha modificado el archivo y si se ha modificado lo carga.
 * 
 * @author Luis Cubillo
 * 
 */
public class FicheroTemplate extends Fichero {

	private final static TransformerFactory FACTORY = TransformerFactory
			.newInstance();

	private Templates template;

	/**
	 * Un fichero para comprobarlo con el nombre del fichero.
	 * 
	 * @param nombreFichero
	 * @throws IOException
	 */
	public FicheroTemplate(String nombreFichero) throws IOException {
		super(nombreFichero);
		load();
	}

	/**
	 * Crea la clase pasándole el nombre del fichero y el tiempo para verificar
	 * si ha cambiado el archivo
	 * 
	 * @param nombreFichero
	 *            Nombre del archivo
	 * @param time
	 *            Tiempo para verificar el cambio en el fichero en milisegundos.
	 * @throws IOException
	 *             Excepción en caso de que se produzca un error
	 */
	public FicheroTemplate(String nombreFichero, long time) throws IOException {
		super(nombreFichero, time);
		load();
	}

	/**
	 * Cambia el fichero.
	 */
	protected void reload(InputStream is) throws IOException {
		StreamSource source = new StreamSource(is);

		try {
			this.template = FACTORY.newTemplates(source);
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}
/**
 * 
 * @return Devuelve una instancia de la plantilla.
 * @throws TransformerException Excepcion que se lanza en caso de error.
 */
	public Transformer newTransformer() throws TransformerException {
		return this.template.newTransformer();
	}
}
