package com.espmail.utils.dao;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.Attributes;

/**
 * Clase que que parsea el documento xml y guarda en un mapa los
 * métodos y las querys.
 * 
 * @author Luis Cubillo
 * 
 */
class DaoXmlHandler extends DefaultHandler {
	
	//stringbuffer que contendrá las querys del metodo.
	private StringBuffer sb = new StringBuffer();
	
	//nombre del metodo
	private String name;
	
	//parametros que lee
	private String param;
	
	//Mapa con los métodos y las querys
	private Map metodos = new HashMap();
	
	//Para el nombre del interfaz 
	private String className;
	
	//guarda el nombre del interfaz en className
	DaoXmlHandler(Class type) {
		this.className = type.getName();
	}

	/**
	 * Lee los caracteres que hay entre las etiquetas.
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		this.sb.append(ch, start, length);

	}

	/**
	 * Se invoca cuando empieza una etiqueta y guarda en name el contenido del
	 * atributo nombre de la etiqueta metodo
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.name = attributes.getValue("nombre");
		this.param = attributes.getValue("params");
	}

	/**
	 * Se invoca cuando se cierra una etiqueta y almacena en un mapa el nombre y
	 * las querys (name y sb)
	 */
	public void endElement(String uri, String localName, String qName)throws SAXException {
		if (localName.toLowerCase().equals("metodo")) {
			if (this.metodos != null) {
				Metodo metodo = new Metodo(className + "." + this.name, this.sb.toString(), this.param);
				this.metodos.put(this.name, metodo);
			}
		}

		// creamos de nuevo el buffer para la siguiente etiqueta.
		this.sb = new StringBuffer();
	}

	/**
	 * Devuelve el mapa con los nombres y querys asociadas
	 * 
	 * @return Map con los datos.
	 */
	public Map getMetodos() {
		return metodos;
	}

}
