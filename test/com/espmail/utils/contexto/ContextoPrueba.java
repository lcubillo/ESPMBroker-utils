/**
 * 
 */
package com.espmail.utils.contexto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Collection;

import com.espmail.modelo.maestras.Pais;
import com.espmail.modelo.maestras.PaisFacade;
import com.espmail.utils.contexto.Contexto;

/**
 * @author Luis Cubillo
 *
 */
public class ContextoPrueba extends Contexto {

	/**
	 * 
	 */
	public ContextoPrueba() throws Exception{
		
	}

	/* (non-Javadoc)
	 * @see com.espmail.modelo.contexto.Contexto#getConexion()
	 */
	public Connection getConexion() throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.0.1.81:1521:cmd", "aspmail", "j20a");
		return conn;
	}

	public static void main(String[]args) throws SQLException
	{
		Contexto.TYPE=ContextoPrueba.class;
	
			Collection lista=PaisFacade.getInstance().getPaises();
			System.out.print(lista.isEmpty());
		Contexto.getInstance().getConexion();
	}
}
