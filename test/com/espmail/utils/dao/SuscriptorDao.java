package com.espmail.utils.dao;

import com.espmail.utils.dao.DaoException;

public interface SuscriptorDao {
//void
	void guardar(Suscriptor suscriptor) throws DaoException;
	
	void guardarFallo(Suscriptor suscriptor)throws DaoException;
	
	void insert(Suscriptor suscriptor)throws DaoException;
	
	void eliminar(Integer codigo)throws DaoException;
	
	Suscriptor findByCodigo(Integer codigo)throws DaoException;
	
	Suscriptor[] find(String lista, String pais)throws DaoException;
	
	
}
