package com.espmail.utils.contexto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;

/**
 * Esta clase representa el contexto en el cual se ejecuta una petición. Tiene
 * como propiedad el idioma del usuario y un método para obtener conexiones.
 * Además tiene métodos para almacenar propiedades globales.
 * 
 * @author Luis Cubillo
 * 
 */
public abstract class Contexto {

	private static ThreadLocal<Contexto> HOLDER = new ThreadLocal(){

      @Override
      protected Object initialValue() {
         Contexto ctx = null;
         
         try {
				ctx = (Contexto) TYPE.newInstance();
				
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
         
         return ctx;
      }
   };

	/** Propiedades genericas */
	private static final Properties PROPS = new Properties();

	// Hay que fijarla antes se utilizará de otra forma en un futuro.
	public static Class TYPE;
	
	private Connection transaccion = null;

	private Locale idioma = new Locale("es");
	
	/**
	 * Constructor.
	 *
	 */
	protected Contexto() {

	}

	/**
	 * Devuelve el idioma asociado al hilo
	 * 
	 * @return Locale con el idioma asociado.
	 */
	public Locale getIdioma() {
		return this.idioma;
	}

	/**
	 * Establece el idioma del hilo.
	 * 
	 * @param idioma
	 *            Locale con el nuevo idioma.
	 */
	public void setIdioma(Locale idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método para obtener el valor de una propiedad
	 * 
	 * @param nombre Nombre de la propiedad
	 * 
	 * @return El valor de la propiedad o null si no existe
	 */
	public static String getPropiedad(String nombre) {
		return PROPS.getProperty(nombre);
	}
	
	/**
	 * Fija el valor de una propiedad.
	 * 
	 * @param nombre Nombre de la propiedad
	 * @param valor Valor de la propiedad
	 */
	public static void setPropiedad(String nombre, String valor) {
		PROPS.setProperty(nombre, valor);
	}

	/**
	 * Devuelve la conexion con la base de datos.
	 * 
	 * @return Connection con la conexion para la base de datos
	 * @throws SQLException
	 *             En caso de error lanza una SQLException
	 */
	public abstract Connection getConexion() throws SQLException;

	/**
	 * Obtiene una instancia de la clase.
	 * 
	 * @return Contexto Devuelve la clase.
	 */
	public static Contexto getInstance() {		

		return HOLDER.get();
	}

	public Connection getTransaccion(){
			return this.transaccion;		
	}	
	
	public void setTransaccion(Connection transaccion) {
		this.transaccion = transaccion;
	}
	
}
