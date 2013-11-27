package com.espmail.modelo.maestras;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.espmail.utils.set.Set;

public class Sexo implements Set, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894713142547671542L;


	private final static Map INSTANCES = new HashMap();

	public final static Sexo Hombre = new Sexo("H");

	public final static Sexo Mujer = new Sexo("M");

	private final String codigo;

	private Sexo(String codigo) {
		this.codigo = codigo;
		INSTANCES.put(codigo, this);
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String toString() {
		return this.codigo;
	}

   public boolean equals(Object obj) {
      if(obj instanceof com.espmail.modelo.maestras.Sexo)
         return this.getCodigo().equals(((Sexo)obj).getCodigo());
      return false;
   }

   public static Collection values() {
		return INSTANCES.values();
	}

	public static Sexo get(String codigo) {
		return (Sexo) INSTANCES.get(codigo);
	}

   public boolean isTarget(){
      return false;
   }
}
