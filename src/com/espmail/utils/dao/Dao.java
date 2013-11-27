package com.espmail.utils.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Clase que implementa un Acceso a la base de datos mediante un proxy de un
 * interface y un archivo xml el interface define los métodos y el xml tiene la
 * querys de los metodos
 * 
 * @author Luis Cubillo
 * 
 */
class Dao implements java.lang.reflect.InvocationHandler {
	// Mapa que contiene todos los métodos.
	private Map metodos;

	/**
	 * A partir del interface parsea el xml con el mismo nombre que el interface
	 * y valida los métodos parseados.
	 * 
	 * @param clase
	 *            Es el interface que tiene los metodos.
	 * @throws DaoException
	 *             Lanzada en caso de error en el interface o en el xml.
	 */
	public Dao(Class clase) throws DaoException {

		// typeName tiene el nombre de la clase quita los puntos y lo sustituye
		// por / y le pone el .xml al final
		String typeName = clase.getName().replaceAll("\\.", "/") + ".xml";
		InputStream is = clase.getClassLoader().getResourceAsStream(typeName);
		
		if (is == null) {
			throw new DaoException(DaoException.ERRORXMLNOEXISTE, typeName);
		}

		// parseamos el xml
		DaoXmlHandler handler = new DaoXmlHandler(clase);

		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(is));
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		this.metodos = handler.getMetodos();
		Method[] method = clase.getMethods();

		for (int i = 0; i < method.length; i++) {
			for (int j = i + 1; j < method.length; j++) {
				if (method[i].getName().equals(method[j].getName())
						&& method[i].getParameterTypes().length == method[j]
								.getParameterTypes().length) {
					throw new DaoException(DaoException.ERRORPARAMETROS,
							method[i].getName());
				}
			}
		}

		for (int i = 0; i < method.length; i++) {
			// Set claves=metodos.keySet();
			if (!metodos.containsKey(method[i].getName())) {
				throw new DaoException(DaoException.ERRORINTERFACE, method[i]
						.getName());
			}

			Metodo metodo = (Metodo) this.metodos.get(method[i].getName());
			metodo.init(method[i]);
		}
	}

	/**
	 * Ejecuta el método y sus querys correspondientes y devuelve un object con el resultado.
	 * No se valida si existe el método porque si no existe ya daría el fallo a
	 * la hora de construir los métodos de la clase.
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object obj, Method metodo, Object[] params)
			throws Throwable {
		Log LOG = LogFactory.getLog(Dao.class);
		
		Metodo met = (Metodo) metodos.get(metodo.getName());
		
		return met.execute(params);
	}

}