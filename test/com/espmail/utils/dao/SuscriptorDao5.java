package com.espmail.utils.dao;
/**
 * prueba un elemento de más
 * @author Luis Cubillo
 *
 */
public interface SuscriptorDao5 {
	
		void guardar(Suscriptor suscriptor);
		
		void eliminar(Integer codigo);
		
		Suscriptor findByCodigo(Integer codigo);
		
		Suscriptor[] find(String lista, String pais,String pais3);
	}
