package com.espmail.utils.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espmail.utils.contexto.Contexto;

/**
 * Clase que tiene los métodos de un interfaz. Contiene una o mas peticiones a
 * la base de datos las ejecuta secuencialmente.
 */
class Metodo {
	// peticiones a la base de datos
	private final List querys = new ArrayList();

	// parametros para las peticiones.
	private final List params = new ArrayList();

	// nombre del método
	private final String nombre;

	// El tipo que tiene que devolver
	private Class tipoDevolver;

	// Si el tipo a devolver es array indica el tipo que le corresponde al array
	// sino es array igual que valorDevolver
	private Class tipoComponente;

	// Indica si es tipo simple o si el array es de tipo simple
	private boolean esSimple;

	// Indica si es un array.
	private boolean esArray;

	/**
	 * Construye un método con su nombre, la/s query/s (en caso de varias
	 * separadas por comas) y los parámetros para las querys.
	 * 
	 * @param nombre
	 *            Nombre del método.
	 * @param querys
	 *            Peticion/es a la base de datos.
	 * @param param
	 *            Parametros para la/s peticion/es a la base de datos.
	 */
	public Metodo(String nombre, String querys, String param) {
		this.nombre = nombre;
		// trato los parametros y los guardo

		if (param != null) {
			String[] parametros = param.split(",\\s*");

			for (int i = 0; i < parametros.length; i++) {
				this.params.add(parametros[i]);
			}
		}

		// trato la query
		String[] q = querys.split(";");

		for (int i = 0; i < q.length; i++) {
			this.querys.add(new Query(q[i].trim(), this.nombre));
		}
	}

	/**
	 * Devuelve el nombre del método.
	 * 
	 * @return String con el nombre.
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Devuelve una lista con la/s peticion/es a la base de datos.
	 * 
	 * @return
	 */
	public List getQuerys() {
		return this.querys;
	}
	
	/**
	 * devuelve el número de parametros de una funcion.
	 * 
	 * @return entero con el número de parámetros
	 */
	public int getNumeroParametros() {
		return this.params.size();
	}

	/**
	 * Devuelve el tipo de dato que devuelve este método
	 * 
	 * @return Class con el dato a devolver.
	 */
	public Class getTipoDevolver() {
		return this.tipoDevolver;
	}

	/**
	 * Devuelve el tipo a devolver, si fuera un array el tipo del array.
	 * 
	 * @return Class con el tipo a devolver.
	 */
	public Class getTipoComponente() {
		return this.tipoComponente;
	}

	/**
	 * Valida que pasandole el método tenga el mismo número de parámetros y
	 * valida la/s query que tenga asociadas, llena la clase parametro y el tipo
	 * a devolver
	 * 
	 * @param method
	 *            Metodo a comparar.
	 */
	public void init(Method method) throws DaoException {
		// coger los tipos de metodo y validar los parametros
		this.tipoDevolver = method.getReturnType();

		if (this.tipoDevolver.isArray()) {
			this.esArray = true;
			this.tipoComponente = this.tipoDevolver.getComponentType();
		} else if (this.tipoDevolver == List.class) {
			this.esArray = true;
			this.tipoComponente = HashMap.class;
		} else {
			this.tipoComponente = this.tipoDevolver;
		}

		this.esSimple = this.esTipoSimple(this.tipoComponente);
		Class[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length != this.params.size()) {
			throw new DaoException(DaoException.ERRORNUMEROPARAMETROS,
					this.nombre);
		}

		Iterator itr = this.querys.iterator();
		// hago las comprobaciones de las querys

		while (itr.hasNext()) {
			Query quer = (Query) itr.next();
			quer.init(this.params);
			String senten = quer.getSentencia().trim().toLowerCase();

			if (itr.hasNext() && senten.startsWith("select")) {
				throw new DaoException(DaoException.ERRORQUERYSELECT,
						this.nombre);
			}
		}
	}

	/**
	 * Ejecutará el método, ejecuta las peticiones a la base de datos de manera
	 * secuencial en caso de varias peticiones sólo puede haber una sóla select
	 * que estará en último lugar.
	 * 
	 * Al final de todas las peticiones en caso de no haber ningún error realiza
	 * un commit si no un rollback antes de lanzar una excepción.
	 * 
	 * @return Object con los datos devueltos
	 * @throws DaoException
	 *             Excepción para los errores que pueda haber.
	 */
	public Object execute(Object[] valoresParametros) throws DaoException {
		Log LOG = LogFactory.getLog(Metodo.class);
		
		Object resultado = null;
		
		Contexto ctx = Contexto.getInstance();
		
		Connection conexion = ctx.getTransaccion();
		
		boolean hayTransaccion = conexion != null;

		try {
			if (!hayTransaccion){
			
				conexion = ctx.getConexion();
				conexion.setAutoCommit(false);
			}
			
			Iterator itr = querys.iterator();

		
			while (itr.hasNext()) {
				Query query = (Query) itr.next();
				resultado = query.execute(conexion, valoresParametros);
			}

		
			if (resultado instanceof ResultSet) {// llenar un arrayList con
				// los datos y luego preguntar
				ResultSet rs = (ResultSet) resultado;
				ResultSetMetaData datos = rs.getMetaData();
				int numColumnas = datos.getColumnCount();
				ArrayList registros = new ArrayList();

				if (this.esSimple) {
					if (numColumnas > 1) {
						deshacer(conexion);
						throw new DaoException(DaoException.ERRORCOLUMNASQUERY,
								this.nombre);
					}

					while (rs.next()) {
						// registros.add(rs.getObject(1));
						if (rs.getObject(1) != null) {
							registros.add(ConvertUtils.convert(rs.getObject(1)
									.toString(), this.tipoComponente));
						} else {
							registros.add(null);
						}
					}
				} else if (Map.class.isAssignableFrom(this.tipoComponente)) {
					while (rs.next()) {
						Map item = (Map) this.tipoComponente.newInstance();

						for (int i = 1; i <= numColumnas; i++) {
							item.put(datos.getColumnLabel(i), rs.getObject(i));
						}

						registros.add(item);
					}
				} else {
					Constructor constructor = getConstructor(this.tipoComponente, numColumnas);

					if (constructor == null) {
						deshacer(conexion);
						throw new DaoException(DaoException.ERRORVALORQUERY,
								this.nombre);
					}

					while (rs.next()) {
						Object[] registro = new Object[numColumnas];

						for (int i = 1; i <= numColumnas; i++) {
							registro[i - 1] = rs.getObject(i);
						}

						registros.add(constructor.newInstance(registro));
					}
				}
				if (registros.size() == 0) {
					return null;
				}
				
				if (registros.size() > 1 && !this.esArray) {
					conexion.rollback();
					throw new DaoException(DaoException.ERRORQUERYFILAS,
							this.nombre);
				}

				if (this.esArray) {
					if (this.tipoDevolver == List.class) {
						return registros;
					}

					Object[] objetos = (Object[]) java.lang.reflect.Array
							.newInstance(this.tipoComponente, registros.size());
					return registros.toArray(objetos);
				}

				return registros.get(0);
			}
			
			if (!hayTransaccion){
			
				conexion.commit();
			
			}
		} catch (SQLException e) {
			deshacer(conexion);
			throw new DaoException(e, this.nombre);
		} catch (IllegalArgumentException e) {
			deshacer(conexion);
			throw new DaoException(e, this.nombre);
		} catch (InstantiationException e) {
			deshacer(conexion);
			throw new DaoException(e, this.nombre);
		} catch (IllegalAccessException e) {
			deshacer(conexion);
			throw new DaoException(e, this.nombre);
		} catch (InvocationTargetException e) {
			deshacer(conexion);
			throw new DaoException(e, this.nombre);
		} finally {
			if (conexion != null&&!hayTransaccion) {
				try {
					conexion.setAutoCommit(true);
					conexion.close();
				} catch (SQLException e) { /* Nada */ }
			}
		}

		return resultado;
	}

	private Constructor getConstructor(Class type, int numParametros) {
		Constructor[] constructores = type.getConstructors();

		for (int i = 0; i < constructores.length; i++) {
			if (constructores[i].getParameterTypes().length == numParametros) {
				return constructores[i];
			}
		}
		
		return null;
	}

	/**
	 * Indica si el Class que se le pasa es de tipo simple.
	 * 
	 * @param clase
	 *            Class a comparar.
	 * @return true si es de tipo simple false en caso contrario.
	 */
	private boolean esTipoSimple(Class clase) {
		if (clase.isPrimitive() || Number.class.isAssignableFrom(clase)
				|| String.class.isAssignableFrom(clase)
				|| Date.class.isAssignableFrom(clase)) {
			return true;
		}

		return false;
	}

	/**
	 * Hace un rollback.
	 * 
	 * @param conexion
	 *            conexion sobre la que se va a hacer un rollback
	 * @throws DaoException
	 *             Excepcion que se lanza en caso de haber un error en la
	 *             ejecución del rollback
	 */
	private void deshacer(Connection conexion) throws DaoException {
		if (conexion != null) {
			try {
				conexion.rollback();
			} catch (SQLException e) {
				throw new DaoException(e, this.nombre);
			}
		}
	}
}
