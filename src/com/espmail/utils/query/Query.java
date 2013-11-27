package com.espmail.utils.query;

import java.lang.reflect.Array;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.SQLException;

/**
 * 
 * @author Luis Cubillo
 * @deprecated
 */
public class Query {
	private PreparedStatement st;
	private ResultSet rs;
	private int cont = 1;
	private final String query;
	
	public Query(Connection con, String query) throws QueryException  {
		this.query = query;

		try {
			this.st = con.prepareStatement(query);
		} catch(SQLException e) {
			throw new QueryException("Error creando statement", query, e);
		}
	}
	
	public void set(Object value) throws QueryException {
		try {
			if (value == null) {
				this.st.setNull(this.cont, Types.VARCHAR);
			} else if (value.getClass().isArray()) {
				StringBuffer sb = new StringBuffer();
				int length = Array.getLength(value);
				
				for (int i = 0; i < length; i++) {
					sb.append(Array.get(value, i).toString());
					
					if (i + 1 < length) {
						sb.append(",");
					}
				}
				
				this.st.setString(this.cont, sb.toString());
			} else if (value instanceof String) {
				this.st.setString(this.cont, (String) value);
			} else if (value instanceof Integer) {
				this.st.setInt(this.cont, ((Integer) value).intValue());
			} else if (value instanceof Long) {
				this.st.setLong(this.cont, ((Long) value).longValue());
			} else if (value instanceof Float) {
				this.st.setFloat(this.cont, ((Float) value).floatValue());
			} else if (value instanceof Double) {
				this.st.setDouble(this.cont, ((Double) value).doubleValue());
			} else if (value instanceof Timestamp) {
				this.st.setTimestamp(this.cont, (Timestamp) value);
			} else if (value instanceof Time) {
				this.st.setTime(this.cont, (Time) value);
			} else if (value instanceof Date) {
				this.st.setDate(this.cont, (Date) value);
			} else if (value instanceof java.util.Date) {
				this.st.setTimestamp(this.cont, new Timestamp(((java.util.Date) value).getTime()));
			} else {
				this.st.setString(this.cont, value.toString());
			}
			
			this.cont++;
		} catch (SQLException e) {
			throw new QueryException("Error en set", this.query, e);
		}
	}
	
	public int executeUpdate() throws QueryException {
		closeRS();

		try {
			return this.st.executeUpdate();
		} catch (SQLException e) {
			throw new QueryException("Error en executeUpdate", this.query, e);
		}
	}
	
	public ResultSet executeQuery() throws QueryException {
		closeRS();

		try {
			this.rs = this.st.executeQuery();
		} catch (SQLException e) {
			throw new QueryException("Error en executeQuery", this.query, e);
		}

		return this.rs;
	}
	
	public void restart() {
		this.cont = 0;
	}
	
	public void close() {
		closeRS();

		if (this.st != null) {
			try {
				this.st.close();
			} catch (SQLException e) {
				// Nada
			}
		}
	}
	
	private void closeRS() {
		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException e) {
				// Nada
			}
		}
	}
}
