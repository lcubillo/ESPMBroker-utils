package com.espmail.utils.dao;

import java.sql.Date;

public class Suscriptor {
	private Integer codigo;
	private String nombre;
	private String password;
	private String mail;
	private Date incripcion;
	private String[] listas;
	private String pais;
	
	public Suscriptor() {

	}

	public Suscriptor(Number codigo, String nombre, String password,
			String mail, Date inscripcion, String listas, String pais) {
		this.codigo = new Integer(codigo.intValue());
		this.nombre = nombre;
		this.password = password;
		this.mail = mail;
		this.incripcion = inscripcion;
		this.listas = listas.split(",");
		this.pais = pais;
	}

	public Date getIncripcion() {
		return incripcion;
	}

	public void setIncripcion(Date incripcion) {
		this.incripcion = incripcion;
	}

	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String[] getListas() {
		return listas;
	}

	public void setListas(String[] listas) {
		this.listas = listas;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
}
