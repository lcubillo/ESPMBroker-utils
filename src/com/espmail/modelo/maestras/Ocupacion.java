package com.espmail.modelo.maestras;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.espmail.utils.contexto.Contexto;
import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;
import com.espmail.utils.set.MironSet;
import com.espmail.utils.set.Set;

public class Ocupacion implements Serializable, Set {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4882757538707297142L;
	
	private static Map INSTANCES;

	private final Integer codigo;

	private final String nombre;

	private final String nombreP;


	public Ocupacion(Number codigo, String nombre, String nombreP) {
		this.codigo = new Integer(codigo.intValue());
		this.nombre = nombre;
		this.nombreP = nombreP;
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public String getNombre() {
		Locale idioma = Contexto.getInstance().getIdioma();

		if(idioma == null || idioma.getLanguage().equals("es")) {
			return this.nombre;
		}

		return this.nombreP;
	}

	public Idioma getIdioma() {
		return "POR".equals(this.codigo)? Idioma.PORTUGUES : Idioma.ESPANHOL;
	}

	public String toString() {
		return this.codigo.toString();
	}

	public static Collection values() {
		return getInstances().values();
	}

	public static Ocupacion get(String codigo) {
		return (Ocupacion) getInstances().get(codigo);
	}

	private static Map getInstances() {
		if (INSTANCES == null) {
			init();
		}
		return INSTANCES;
	}

	private static synchronized void init() {
		if (INSTANCES == null){
			reload();
			MironSet.lanza(Ocupacion.class);
		}
	}
	public static synchronized void reload(){
		Map instancesAux = new LinkedHashMap();

		MaestrasDao dao;
		try {
			dao = (MaestrasDao) FactoriaDao.getInstance().getDao(
					MaestrasDao.class);
			Ocupacion[] Ocupaciones = dao.dameOcupaciones();

			for (int i = 0; i < Ocupaciones.length; i++) {
				Ocupacion ocupacion = Ocupaciones[i];
				instancesAux.put(ocupacion.getCodigo().toString(), ocupacion);
			}
			INSTANCES = instancesAux;
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
}
