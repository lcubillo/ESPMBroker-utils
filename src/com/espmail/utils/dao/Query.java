package com.espmail.utils.dao;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import org.apache.commons.beanutils.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Clase que tiene la petición a la base de datos.
 * 
 */
class Query {
	// Para acceder sólo cuando se cree la clase y no cada vez que acceda al
	// execute
	private static Log LOG = LogFactory.getLog(Query.class);

	// La sql.
	private String sentencia;

	// nombre del metodo al que pertenece la query
	private final String metodo;

	// Lista de parámetros para la query.
	private final List parametros = new ArrayList();
	
	/**
	 * Almaceno la sentencia que luego se tratará en el init
	 * 
	 * @param sentencia
	 */
	public Query(String sentencia, String metodo) {
		this.sentencia = sentencia.trim();
		this.metodo = metodo;
	}

	/**
	 * Devuelve la sentencia sql que se ejecutará.
	 * 
	 * @return String con la sql
	 */
	public String getSentencia() {
		return sentencia;
	}

	/**
	 * Trata la sentencia y llena la lista de parametros
	 * 
	 * verifica que los parámetros de la query esten en los parámetros del
	 * método.
	 * 
	 * @param parametros
	 *            Parámetros del método.
	 */
	public void init(List parametros) throws DaoException {

		if (this.sentencia.length() == 0) {
			throw new DaoException(DaoException.ERRORQUERYVACIA, this.metodo);
		}

		StringBuffer sentenciaTratada = new StringBuffer();
		StringTokenizer cortes = new StringTokenizer(this.sentencia, "#");

		while (cortes.hasMoreTokens()) {
			sentenciaTratada.append(cortes.nextToken());

			if (cortes.hasMoreTokens()) {
				sentenciaTratada.append("?");
				String param = cortes.nextToken();
				int fin = param.indexOf(".");
				String property = null;
				
				if (fin > 0) {
					property = param.substring(fin + 1);
					param = param.substring(0, fin);
				}
				
				int posicion = parametros.indexOf(param);

				if (posicion < 0) {
					throw new DaoException(DaoException.ERRORPARAMETROSQUERY,
							this.metodo);
				}

				Parametro parametro = new Parametro(posicion, property);
				this.parametros.add(parametro);
			}
		}

		this.sentencia = sentenciaTratada.toString();
		
		
	}

	/**
	 * Asigna un valor para el prepareStatement
	 * 
	 * @param value
	 *            Objeto que devuelve
	 * @param cont
	 *            Contador para asignar el valor de la query
	 * @param st
	 *            PreparedStatement sobre el que se va a asignar el valor
	 * @throws SQLException
	 *             Excepcion en caso de error
	 */
	private void ponValor(Object value, int cont, PreparedStatement st)
			throws SQLException {
		if (value == null) {
			st.setNull(cont, Types.VARCHAR);
		} else if (value.getClass().isArray()) {
			StringBuffer sb = new StringBuffer();
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				sb.append(Array.get(value, i).toString());
				if (i + 1 < length) {
					sb.append(",");
				}
			}
			st.setString(cont, sb.toString());
		} else if (value instanceof String) {
			st.setString(cont, (String) value);
		} else if (value instanceof Integer) {
			st.setInt(cont, ((Integer) value).intValue());
		} else if (value instanceof Long) {
			st.setLong(cont, ((Long) value).longValue());
		} else if (value instanceof Float) {
			st.setFloat(cont, ((Float) value).floatValue());
		} else if (value instanceof Double) {
			st.setDouble(cont, ((Double) value).doubleValue());
		} else if (value instanceof Timestamp) {
			st.setTimestamp(cont, (Timestamp) value);
		} else if (value instanceof Time) {
			st.setTime(cont, (Time) value);
		} else if (value instanceof Date) {
			st.setDate(cont, (Date) value);
		} else if (value instanceof java.util.Date) {
			st.setTimestamp(cont, new Timestamp(((java.util.Date) value)
					.getTime()));
		} else {
			st.setString(cont, value.toString());
		}
	}

	/**
	 * Sustituye el valor de los parámetros por los datos que contienen y
	 * ejecuta la query aunque el commit se hace en el método.
	 * 
	 * @param conexion conexión con la base de datos.
	 * @param valoresParams array de objetos con los valores de los parámetros
	 * @return ResultSet si es un select,si es un insert,delete o un update devuelve un Integer y Boolean en cualquier otro caso.
	 * @throws SQLException En caso de erro con la conexion
	 * @throws DaoException En caso de error con la query y los parametros.
	 */
	public Object execute(Connection conexion, Object[] valoresParams)
			throws SQLException, DaoException {
		
		StringBuffer mensaje = new StringBuffer(this.metodo);
		mensaje.append("(");
		PreparedStatement st = null;
		boolean callable = this.sentencia.startsWith("{");

		if (callable) {

			st = conexion.prepareCall(this.sentencia);
		} else {

			st = conexion.prepareStatement(this.sentencia);			
		}

		Iterator itr = this.parametros.iterator();

		
		for (int i = 1; itr.hasNext(); i++) {

			Parametro parametro = (Parametro) itr.next();
			int indice = parametro.getIndice();
			Object valor = valoresParams[indice];
			String prop = parametro.getProperty();

			if (prop == null) {
				mensaje.append(valor).append(" ");
				ponValor(valor, i, st);
			} else {
				try {
					Object obj = PropertyUtils.getProperty(valor, prop);
					mensaje.append(obj).append(" ");
					ponValor(obj, i, st);
				} catch (IllegalAccessException e) {
					throw new DaoException(e, this.sentencia);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					throw new DaoException(e, this.sentencia);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					throw new DaoException(e, this.sentencia);
				}
			}
		}


		mensaje.append(")");
		LOG.debug(mensaje.toString());
		String sentenciaComparar = this.sentencia.toLowerCase();
      LOG.debug(this.sentencia);
		if (sentenciaComparar.startsWith("select")) {

			return st.executeQuery();
		}

		if (sentenciaComparar.startsWith("insert")
				|| sentenciaComparar.startsWith("delete")
				|| sentenciaComparar.startsWith("update")) {

			return new Integer(st.executeUpdate());
		}

		return new Boolean(st.execute());
	}
}
