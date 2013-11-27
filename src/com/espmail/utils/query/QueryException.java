package com.espmail.utils.query;

/**
 * 
 * @author Luis Cubillo
 * @deprecated
 */
public class QueryException extends Exception {
	
	private final String query;

	public QueryException(String mensaje, Throwable cause) {
		super(mensaje, cause);
		this.query = null;
	}

	public QueryException(String mensaje, String query, Throwable cause) {
		super(mensaje, cause);
		this.query = query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(this.getMessage());
		sb.append("\n");
		sb.append(this.query);
		
		return sb.toString();
	}
}
