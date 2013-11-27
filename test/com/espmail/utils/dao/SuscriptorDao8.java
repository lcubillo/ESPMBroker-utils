package com.espmail.utils.dao;

public interface SuscriptorDao8 {
		void guardar(Suscriptor suscriptor);
		
		void eliminar(Integer codigo);
		
		Suscriptor findByCodigo(Integer codigo);
		
		Suscriptor[] find(String lista, String pais);
		
		
	}

