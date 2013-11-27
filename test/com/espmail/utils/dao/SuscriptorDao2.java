package com.espmail.utils.dao;

public interface SuscriptorDao2 {

	
		void guardar(Suscriptor suscriptor);
				
		Suscriptor findById(Integer codigo);
		
		Suscriptor[] find(String lista, String pais);

	
	
}
