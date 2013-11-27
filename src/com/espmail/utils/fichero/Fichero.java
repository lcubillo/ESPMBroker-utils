package com.espmail.utils.fichero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase Abstracta que cada cierto tiempo comprueba que si se ha modificado un fichero
 * si se modifica hace alguna operación que se declarará en la clase de la que extienda.
 */
public abstract class Fichero {
	// variable para hacer el log y no tener que declararla cada vez que se
	// ejecute
	private final static Log LOG = LogFactory.getLog(Fichero.class);

	// define el tiempo en el que se va a despertar el hilo para ver si ha
	// cambiado el fichero.
	private final static long MIN_MIRA = 1000 * 60 * 5; // 5 minutos

	// nombre del fichero
	private final String nombreFichero;

	// tiempo en milisegundos de la última actualización
	private long ultimaActualizacion = 0;

	/**
	 * Pone el nombre del fichero y el tiempo en el que se despertará el hilo.
	 * 
	 * @param nombreFichero
	 *            Nombre del fichero a comprobar.
	 * @throws IOException
	 *             Excepcion en caso de error
	 */
	public Fichero(String nombreFichero) throws IOException {
		this(nombreFichero, MIN_MIRA);
	}

	/**
	 * Pone el nombre de fichero y crea el hilo que lo comprueba.
	 * 
	 * @param nombreFichero
	 *            Nombre de fichero
	 * @param time
	 *            Tiempo a comprobar
	 * @throws IOException
	 *             Excepción que se lanza en caso de cualquier error.
	 */
	public Fichero(String nombreFichero, long time) throws IOException {
		this.nombreFichero = nombreFichero;
		new Thread(new Miron(this, time)).start();
	}

	/**
	 * Carga el fichero siempre. No hace la verificación de modificación.
	 * 
	 * @throws IOException
	 *             Excepción en caso de que el fichero no exista.
	 */
	public final void load() throws IOException {
		File file = new File(this.nombreFichero);

		if (!file.exists()) {
			throw new FileNotFoundException("File not found " + nombreFichero);
		}

		carga(file, true);
	}

	/**
	 * Comprueba que se haya modificado y si se ha modificado carga el fichero.
	 * 
	 */
	private final void test() {
		File file = new File(this.nombreFichero);

		if (file.lastModified() > this.ultimaActualizacion) {
			try {
				carga(file, false);
			} catch (IOException e) {
				LOG.error("Error recargando propiedades " + this.nombreFichero,
						e);
			}
		}
	}

	/**
	 * Comprueba si se ha modificado el fichero y si se ha modificado llama a
	 * reload. También se puede forzar la recarga con el parámetro obligatorio.
	 * 
	 * @param file
	 *            Archivo a comproar.
	 * @param obligatorio
	 *            Si se pone a true se llama a reload aunque no se haya
	 *            modificado el fichero. Si es false se mira si se ha modificado
	 *            el fichero.
	 * @throws IOException
	 *             Excepción que se lanza en caso de error
	 */
	private synchronized void carga(File file, boolean obligatorio)
			throws IOException {
		if (obligatorio || file.lastModified() > this.ultimaActualizacion) {
			this.ultimaActualizacion = System.currentTimeMillis();
			FileInputStream is = new FileInputStream(file);
			reload(is);
			is.close();
		}
	}

	/**
	 * Según la implementación se puede hacer lo que sea.
	 * 
	 * @param is
	 *            El archivo como un InputStream.
	 * @throws IOException
	 *             Excepción en caso de error en la carga.
	 */
	protected abstract void reload(InputStream is) throws IOException;

	/**
	 * Hilo que comprueba el fichero.
	 * 
	 * @author
	 * 
	 */
	private class Miron implements Runnable {
		// fichero a comprobar
		private final Fichero prop;

		// tiempo en el que se despierta.
		private final long time;

		/**
		 * Crea el hilo con el fichero y el periodo de tiempo en el que
		 * comprobará si el fichero ha cambiado
		 * 
		 * @param prop
		 *            Fichero a comprobar.
		 * @param time
		 *            El tiempo que pasa entre las comprobaciones.
		 */
		public Miron(Fichero prop, long time) {
			this.prop = prop;
			this.time = time;
		}

		/**
		 * Duerme el hilo un periodo de tiempo y al despertar comprueba si ha
		 * cambiado el fichero y si ha cambiado lo recarga.
		 */
		public void run() {
			boolean parar = false;

			while (!parar) {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					LOG
							.error("Miron interrupido "
									+ this.prop.nombreFichero, e);
					parar = true;
				}

				this.prop.test();
			}
		}
	}
}
