package com.espmail.modelo.maestras;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.espmail.utils.set.Set;

public class Idioma implements Set, Serializable {

	private static final long serialVersionUID = 8095759053717648892L;

	private final static Map INSTANCES = new HashMap();

	public final static Idioma ESPANHOL = new Idioma("E", "es");

	public final static Idioma PORTUGUES = new Idioma("P", "pt");

	private final String codigoInternacional;

	private final String codigo;

	private Idioma(String codigo, String codigoInternacional) {
		this.codigo = codigo;
		this.codigoInternacional = codigoInternacional;
		INSTANCES.put(codigo, this);
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getCodigoInternacional() {
		return this.codigoInternacional;
	}

	public String toString() {
		return this.codigo;
	}

	public static Collection values() {
		return INSTANCES.values();
	}

	public static Idioma get(String codigo) {
		return (Idioma) INSTANCES.get(codigo);
	}
}
