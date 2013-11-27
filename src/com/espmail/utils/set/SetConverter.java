package com.espmail.utils.set;

import org.apache.commons.beanutils.Converter;
/**
 * Permite obtener el valor de un objeto que pertenece a una clase determinada.
 * @author Luis Cubillo
 *
 */
public class SetConverter implements Converter {

	
	/**
	 * Devuelve el valor de una clase.
	 */
	public Object convert(Class type, Object value) {
		if (value != null) {
         System.out.println("Invocamos SetUtils.get");
         return SetUtils.get(type, value.toString());
		}
		
		return null;
	}
}
