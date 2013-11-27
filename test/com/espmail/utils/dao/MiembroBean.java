package com.espmail.utils.dao;

import java.sql.Date;

public class MiembroBean {
	String EMAIL;
	Integer codigo;
	Date fecha;
	
	
	
	public MiembroBean(Number codigo,String email,Date fecha)
	{
		this.codigo=new Integer(codigo.intValue());
		this.EMAIL=email;
		this.fecha=fecha;
	}

	public MiembroBean(Integer codigo)
	{
		this.codigo=codigo;
	} 
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String email) {
		EMAIL = email;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String toString()
	{
	  return "Codigo: "+this.codigo+" Fecha: "+this.fecha+ " email: "+this.EMAIL;
	}
	
}
