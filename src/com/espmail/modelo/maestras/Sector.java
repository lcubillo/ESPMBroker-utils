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

public class Sector implements Set, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5790942408935084419L;

	private static Map INSTANCES;
	
	private final Integer codigo;

	private final String nombre;

	private final String nombreP;
	
	
	public Sector(Number codigo, String nombre, String nombreP) {
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

	public static Sector get(String codigo) {
		return (Sector) getInstances().get(codigo);
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
			MironSet.lanza(Sector.class);
		}
	}
	public static synchronized void reload(){
		Map instancesAux = new LinkedHashMap();

		MaestrasDao dao;
		try {
			dao = (MaestrasDao) FactoriaDao.getInstance().getDao(
					MaestrasDao.class);
			Sector[] Sectores = dao.dameSectores();
			for (int i = 0; i < Sectores.length; i++){
				Sector sector = Sectores[i];
				instancesAux.put(sector.getCodigo().toString(), sector);
			}
			INSTANCES = instancesAux;
		}catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
}
