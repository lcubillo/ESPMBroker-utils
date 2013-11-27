package com.espmail.utils.dao;
/**
 * prueba falta el parametro codigo de eliminar.
 * @author Luis Cubillo
 *
 */
public interface SuscriptorDao3 {

	void guardar(Suscriptor suscriptor);
	void guardar(Integer codigo);
	
	void eliminar(Integer codigo);
	
	Suscriptor findById(Integer codigo);
	
	Suscriptor[] find(String lista, String pais);
}
