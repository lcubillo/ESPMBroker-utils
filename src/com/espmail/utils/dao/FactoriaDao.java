package com.espmail.utils.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Proxy;





import com.espmail.utils.contexto.Contexto;

/**
 * Clase pública para acceder al dao hace de intermediario entre el dao y el
 * programa. Si el interfaz ya existe no lo crea de nuevo y si no esta lo crea y
 * añade al mapa.
 */
public class FactoriaDao {
	// Instancia de la factoria
	private static final FactoriaDao INSTANCE = new FactoriaDao();

	// Mapa con todos los interfaces que se le pasen
	private final Map daos = new HashMap();

	public Object getDao(Class interfaz) throws DaoException {
		Object dao = this.daos.get(interfaz);
		
		if (dao == null) {
			dao = createDao(interfaz);
		}
		
		return dao;
	}

	public int executeUpdate(String query) throws DaoException {
		
		Contexto ctx = Contexto.getInstance();
		Connection conexion = ctx.getTransaccion();
		boolean hayTransaccion = conexion != null;
		Statement st = null;
		int res = 0;

		try {
			if (!hayTransaccion){
				conexion = ctx.getConexion();
			}
			st = conexion.createStatement();
			res = st.executeUpdate(query);
		}catch (SQLException e) {
			throw new DaoException(e,"executeUpdate");
		}
		finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) { /* Nada */ }
			}

			if (conexion != null) {
				try {
					if(!hayTransaccion){
						conexion.close();
					}
				}catch (SQLException e) {
					throw new DaoException(e,"executeUpdate");
				}
			}
		}
		
		return res;
	}
	

	public List executeQuery(String query) throws DaoException {
		
		Contexto ctx = Contexto.getInstance();
		Connection conexion = ctx.getTransaccion();
		boolean hayTransaccion = conexion != null;
		
		Statement st = null;
		ResultSet rs = null;
		List result = new ArrayList();
		
		try {
			if (!hayTransaccion){
				conexion = ctx.getConexion();
			}
			st = conexion.createStatement();
			rs = st.executeQuery(query);
			ResultSetMetaData datos = rs.getMetaData();
			int numColumnas = datos.getColumnCount();
			
			while (rs.next()) {
				Map registro = new HashMap();
					for (int i = 1; i <= numColumnas; i++) {
					registro.put(datos.getColumnLabel(i), rs.getObject(i));
				}

				result.add(registro);
			}
		}catch (SQLException e) {
			throw new DaoException(e,"executeQuery");
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* Nada */  }
			}

			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) { /* Nada */ }
			}

			if (conexion != null) {
				try {
					if(!hayTransaccion){
						conexion.close();
					}
				}catch (SQLException e){
					throw new DaoException(e,"executeQuery");
				}
			}
		
		}
		
		return result;
	}

	/**
	 * Si el interfaz que se le pasa no existe en el mapa se crea y añade si no
	 * devuelve el que ya estuviera en el mapa.
	 * 
	 * @param interfaz
	 *            Interfaz que contiene los datos a consultar.
	 * @return El dao.
	 * @throws DaoException
	 *             En caso de que se producjera algún error.
	 */
	private synchronized Object createDao(Class interfaz) throws DaoException {
		Object dao = this.daos.get(interfaz);

		if (dao == null) {
			dao = Proxy.newProxyInstance(interfaz.getClassLoader(),
					new Class[] { interfaz }, new Dao(interfaz));
			this.daos.put(interfaz, dao);
		}
		return dao;
	}

	/**
	 * Obtiene una instancia de la factoria.
	 * 
	 * @return La instancia de la factoria.
	 */
	public static FactoriaDao getInstance() {
		return INSTANCE;
	}
}
