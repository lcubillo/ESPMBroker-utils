package com.espmail.modelo.maestras;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.espmail.utils.contexto.Contexto;
import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;
import com.espmail.utils.set.MironSet;
import com.espmail.utils.set.Set;

/**
 * 
 * @author 
 * @deprecated 
 */
public class Categoria implements Set, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Map INSTANCES;

	private final Integer codigo;

	private final String nombre;

	private final String nombreP;

	private Lista[] lista;

	public Categoria(Number codigo, String nombre, String nombreP) {

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

	public static Categoria get(String codigo) {
		return (Categoria) getInstances().get(codigo);
	}

	public Lista[] getListas()
	{
		if(this.lista==null)
		{
			List valor=new LinkedList();
			Iterator itr=Lista.values().iterator();	
			while(itr.hasNext())
			{
				Lista lista=(Lista) itr.next();
				if(lista.getCategoria()==this)
					valor.add(lista);
			}
			Lista[] listas=new Lista[valor.size()];
			this.lista=(Lista[])valor.toArray(listas);
		}
		return this.lista;
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
			MironSet.lanza(Categoria.class);
		}
	}
	public static synchronized void reload(){
		Map instancesAux = new LinkedHashMap();
		MaestrasDao dao;
		try {
			dao = (MaestrasDao) FactoriaDao.getInstance().getDao(
					MaestrasDao.class);
			Categoria[] categorias = dao.dameCategorias();
			for (int i = 0; i < categorias.length; i++) {
				Categoria categoria = categorias[i];
				instancesAux.put(categoria.getCodigo().toString(), categoria);
			}
			INSTANCES = instancesAux;
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
}
