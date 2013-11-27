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
import com.espmail.utils.TextUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class Pais implements Set, Serializable{

   private static final Log log =LogFactory.getLog(Pais.class); 

   private static final long serialVersionUID = -5088290974283619900L;

	private static Map INSTANCES;

	private final String codigo;

	private final String nombre;

	private final String nombreP;
	
	private final String idioma;

	// indica si el pais tiene provincias
	private final boolean provincias;
	
	
	
	public Pais(String codigo, String nombre, String nombreP,String provincia,String idioma) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.nombreP = nombreP;
		this.provincias = provincia.toLowerCase().equals("true");
		this.idioma = idioma;
	}
	
	public Pais(String codigo, String nombre, String nombreP,String provincia) {
		this(codigo,nombre,nombreP,provincia,"es");

	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getNombre() {
		Locale idioma = Contexto.getInstance().getIdioma();

		if(idioma == null || idioma.getLanguage().equals("es")) {
			return this.nombre;
		}

		return this.nombreP;
	}

	public boolean isProvincias() {
		return this.provincias;
	}
	
	public Idioma getIdioma() {
		
		return ("POR".equals(this.codigo) || "BRA".equals(this.codigo))? Idioma.PORTUGUES : Idioma.ESPANHOL;
	}

	public String toString() {
		return this.codigo;
	}
	
	public static Collection values() {
		
		return getInstances().values();
	}

	public static Pais get(String codigo) {
		return (Pais) getInstances().get(codigo);
	}

	private static Map getInstances() {
		if (INSTANCES == null) {
			init();
		}
		return INSTANCES;
	}

   public boolean equals(Object obj) {
      if(obj == null)
         return false;
      if(! (obj instanceof Pais))
         return false;
      if( ((Pais)obj).getCodigo().equalsIgnoreCase(this.getCodigo()) &&
           ((Pais)obj).getIdioma().getCodigoInternacional().equalsIgnoreCase(this.getIdioma().getCodigoInternacional()) &&
           ((Pais)obj).getNombre().equalsIgnoreCase(this.getNombre()))
         return true;

      return false;
   }

   private static synchronized void init() {
		if (INSTANCES == null){ 
			reload();
			MironSet.lanza(Pais.class);
		}
	}
	public static synchronized void reload(){
		Map instancesAux = new LinkedHashMap();
		MaestrasDao dao;
      log.info("Instanciamos Factoria");
      try {
			dao = (MaestrasDao) FactoriaDao.getInstance().getDao(
					MaestrasDao.class);
         log.info("Descargamos paises");
         Pais[] paises = dao.damePaises();

			for (int i = 0; i < paises.length; i++) {
				Pais pais = paises[i];
				instancesAux.put(pais.getCodigo(), pais);
			}
			INSTANCES = instancesAux;
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
      log.info("saliendo reload");
   }
}
