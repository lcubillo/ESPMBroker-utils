package com.espmail.modelo.suscriptor;

import java.util.List;

import com.espmail.utils.dao.DaoException;

public interface SuscriptorDao {

	Suscriptor get(String email) throws DaoException;
	
	Suscriptor findbycod(Integer codigo) throws DaoException;
     	
	void save(Suscriptor suscriptor) throws DaoException;
	
	void delete(Integer codigo, Integer codigoPedido, Integer causa) throws DaoException;

    void deleteSus(Integer codigo) throws DaoException;
    
    List findHistorico(String email) throws DaoException;
	
	void noDeseado(Integer codigo, String email, Integer codigoPedido) throws DaoException;
	
	void updateSuscripcion(String ip, Integer codigo) throws DaoException;

     void confirmSuscripcion(String email, String asoCod, String red) throws DaoException;

     String getPasswordAbierta(String email) throws DaoException;
}
