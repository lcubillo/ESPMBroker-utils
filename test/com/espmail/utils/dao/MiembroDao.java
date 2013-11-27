package com.espmail.utils.dao;

import java.sql.Date;


public interface MiembroDao {

	MiembroBean codigos(Integer suscriptor);

	int codigo(Integer codigo);
	
	int dameCodigoBean(MiembroBean codigo);
	
	int codigoSinParametros();
	
	String dameEmail(Integer codigo);
	
	MiembroBean[] dameEmails(String cadena);
	
	String[] dameArraySimple(String cadena);
	
	Date[] dameFecha(String cadena);
	
	Integer[] dameCodigos(String cadena);
}