package com.espmail.modelo.maestras;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;
import com.espmail.utils.set.MironSet;
import com.espmail.utils.set.Set;

/**
 * 
 * @author Luis Cubillo
 *
 */
public class CategoriasRedes implements I18N, Set, Serializable {

	
	
	public String getI18n() {
		return i18n;
	}


	private static Map INSTANCES;

	//private final Number id;
	private final String etiqueta;
	private final String tags;
	private final String i18n;
	
/*	public Number getId() {
		return id;
	}*/



	public String getEtiqueta() {
		return etiqueta;
	}



	public String getTags() {
		return tags;
	}
        /**
         *
         * @param codigo
         * @param padre
         */
        public CategoriasRedes(String etiqueta, String tags,String i18n) {
		//this.id = id;
		this.etiqueta= etiqueta;
		this.tags = tags;
		this.i18n = i18n;
	}
	
       

    @Override
	public String toString() {
		return this.etiqueta;
	}
    
    /**
     * Gonzalo
     * @return
     */
    public String toI18N(String et){
    	return this.get(et).getI18n();
    }
	
        /**
         *
         * @return
         */
        public static Collection values() {
		return getInstances().values();
	}
	
        /**
         *
         * @param etiqueta
         * @return
         */
        public static CategoriasRedes get(String etiqueta) {
        	return (CategoriasRedes) getInstances().get(etiqueta);
	}
	
        /**
         * 
         * @return
         */
	private static Map getInstances() {
		if (INSTANCES == null) {
			init();
		}
		
		return INSTANCES;
	}
	
	/**
	 * 
	 */
	private static synchronized void init() {
		if (INSTANCES == null) {
			reload();
			MironSet.lanza(CategoriasRedes.class);
		}
	}
	
	
        /**
         *
         */
    public static synchronized void reload(){
		Map instancesAux = new LinkedHashMap();
		try {
			MaestrasDao dao = (MaestrasDao) FactoriaDao.getInstance()
				.getDao(MaestrasDao.class);
			
			CategoriasRedes[] categoriasRedes= dao.findCategoriasRedes();
			
			for (int i = 0; i < categoriasRedes.length; i++) {
				CategoriasRedes categoriaRed = categoriasRedes[i];
				instancesAux.put(categoriaRed.getEtiqueta(), categoriaRed);
			}
			INSTANCES = instancesAux;	
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

}
