package com.espmail.utils.set;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.espmail.utils.TextUtils;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Permite acceder a los metodos get y values de las clases.
 * @author Luis Cubillo
 *
 */
public class SetUtils {

	private static final Map INSTANCES = new HashMap();
// tiene el nombre de la clase a la que pertenece el objeto.
	private final Method metodoGet;

	private final Method metodoValues;
	
	private Method metodoReload;
	/**
	 * Constructor privado.
	 * @param type Clase.
	 */
	private SetUtils(Class type) {
		// TODO hacer comprobaciones de los métodos y si extiende a Set
		try {
			this.metodoGet = type.getMethod("get", new Class[] { String.class });
			this.metodoValues = type.getMethod("values", new Class[] {});
			
			try{
				this.metodoReload = type.getMethod("reload", new Class[] {});
			} catch (NoSuchMethodException e) {
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Devuelve un Set con el codigo que se le pasa.
	 * 
	 * @param type Clase a la que pertenece el objeto que se quiere obtener.
	 * @param codigo Código del objeto a recuperar.
	 * @return Set con el tipo.
	 */
	public static final Set get(Class type, String codigo) {
      System.out.println("SetUtils.get class string");
      SetUtils utils = get(type);

		try {
			return (Set) utils.metodoGet.invoke(null, new Object[] { codigo });
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	/**
	 * Devuelve todos los valores que tiene el set.
	 * 
	 * @param type
	 *            tipo de la clase.
	 * @return Collection con todos los valores.
	 */
	public static final Collection values(Class type) {
		SetUtils utils = get(type);

		try {
			return (Collection) utils.metodoValues
					.invoke(null, new Object[] {});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public static final void reload(Class type) {
		SetUtils utils = get(type);

		try {
			utils.metodoReload.invoke(null, new Object[] {});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}
	/**
	 * Devuelve todos los objetos. Para un conjunto de codigos.
	 * 
	 * @param type
	 *            La clase a la que pertenencen los elementos
	 * @param values
	 *            Valores que se van a recuperar.
	 * @return array de Set que contiene todos los datos null si no hay nada
	 */
	public static final Set[] getAll(Class type, String values) {
		String[] codigos = TextUtils.split(values);
		Set[] result = null;

		if (codigos != null) {
			int length = codigos.length;
			result = (Set[]) Array.newInstance(type, length);

			for (int i = 0; i < length; i++) {
				result[i] = get(type, codigos[i]);
			}
		}

		return result;
	}

	/**
	 * Devuelve una instancia de SetUtils si no hay una ya. (primera validación para el init).
	 * 
	 * @param type
	 *            Class
	 * @return
	 */
	private static final SetUtils get(Class type) {
      System.out.println("Setutils.get class");
      SetUtils utils = (SetUtils) INSTANCES.get(type);

		if (utils == null) {
			utils = init(type);
		}

		return utils;
	}

	/**
	 * Crea una instancia de SetUtils si no esta creada. (sólo permite la validación y creacion de un elemento a la vez (synchronized) ). 
	 * 
	 * @param type
	 *            Clase.
	 * @return Devuelve una instancia de SetUtils.
	 */
	private static final synchronized SetUtils init(Class type) {
		SetUtils utils = (SetUtils) INSTANCES.get(type);

		if (utils == null) {
			utils = new SetUtils(type);
			INSTANCES.put(type, utils);
		}
      System.out.println("SetUtils.init");
		return utils;
	}
}
