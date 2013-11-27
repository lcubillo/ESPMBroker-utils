package com.espmail.utils.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.espmail.utils.contexto.Contexto;

public class Transaccion {

	private Connection conexion = null;

	public Transaccion() throws DaoException {
		Contexto ctx = Contexto.getInstance();

		try {
			this.conexion = ctx.getConexion();
			ctx.setTransaccion(conexion);
			this.conexion.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DaoException(e, "Transaccion Constructor");
		}
	}

	public void close() {
		try {
			this.conexion.setAutoCommit(true);
			this.conexion.close();
		} catch (SQLException e) {
			// Nada
		}

		Contexto.getInstance().setTransaccion(null);
	}

	public void rollback() throws DaoException {
		try {
			this.conexion.rollback();
		} catch (SQLException e) {
			throw new DaoException(e, "Transaccion rollback");
		}
	}
	
	public void commit() throws DaoException {
		try {
			this.conexion.commit();
		} catch (SQLException e) {
			throw new DaoException(e, "Transaccion commit");
		}
	}
}
