package com.espmail.modelo.parametros;

import java.io.Serializable;

import java.sql.Date;

import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

import com.espmail.utils.contexto.Contexto;

import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;

public class Parametro implements Serializable {

	private static final long serialVersionUID = -1034939139643477986L;
	
	private static final Map INSTANCES = new HashMap();
	private static final long VIDA = 10 * 60 * 1000; // 10 minutos

	private final String codigo;
	private final String valorString;
	private final String valorStringP;
	private final Float valorFloat;
	private final Date valorFecha;
	private long actualizado;
	
	public Parametro(String codigo, Number valorFloat, Date valorFecha,
			String valorString, String valorStringP) {
		this.codigo = codigo;
		this.valorString = valorString;
		this.valorStringP = valorStringP;
		this.valorFloat = valorFloat == null? null : new Float(valorFloat.floatValue());
		this.valorFecha = valorFecha;
		this.actualizado = System.currentTimeMillis();
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getValorString() {
		if (this.valorString == null) {
			return null;
		}

		Locale idioma = Contexto.getInstance().getIdioma();

		return idioma == null || idioma.getLanguage().equals("es")? 
				this.valorString : this.valorStringP;
	}

	public Date getValorFecha() {
		return this.valorFecha;
	}

	public Float getValorFloat() {
		return this.valorFloat;
	}
	
	public static Parametro get(String codigo) {
		Parametro parametro = (Parametro) INSTANCES.get(codigo);
		
		if (parametro == null || (System.currentTimeMillis() - parametro.actualizado > VIDA)) {
			try {
				ParametroDao dao = (ParametroDao) FactoriaDao.getInstance()
						.getDao(ParametroDao.class);
				parametro = dao.get(codigo);
				INSTANCES.put(codigo, parametro);
			} catch (DaoException e) {
				throw new RuntimeException(e);
			}
		}

		return parametro;
	}
}
