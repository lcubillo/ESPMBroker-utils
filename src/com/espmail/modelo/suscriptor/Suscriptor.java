package com.espmail.modelo.suscriptor;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Timestamp;

import com.espmail.modelo.maestras.Idioma;
import com.espmail.modelo.maestras.Lista;
import com.espmail.modelo.maestras.Ocupacion;
import com.espmail.modelo.maestras.Pais;
import com.espmail.modelo.maestras.Sector;
import com.espmail.modelo.maestras.Sexo;

import com.espmail.utils.TextUtils;

import com.espmail.utils.set.SetUtils;

public class Suscriptor implements Serializable {

	private static final long serialVersionUID = 958613881977826246L;

	private Integer codigo;
	private String password;
	private String passwordAbierta;	
	private String nombre;	
	private String apellidos;
	private String email;
	private String movil;
	private Sexo sexo;
	private String cp;
	private Date fechaNacimiento;
	private Pais pais;
	private String provincia;
	private Ocupacion ocupacion;
	private Sector sector;
	private Lista[] listas;
	private Idioma idioma;
	private String nombreWeb;
	private String red;
	private Timestamp inscripcion;
	private Timestamp ultimoLogin;
	private String[] asociados;
	private String webmaster;
	private String ipConfir;
	private String ipRegistro;

	public Suscriptor() {
		//Nada
	}
	
	public Suscriptor(Number codigo, String password, String passwordAbierta, String nombre, String apellidos, String email,
			String movil, String sexo, String cp, Date fechaNacimiento,
			String pais, String provincia, Number ocupacion, Number sector,
			String listas, String idioma, String nombreWeb, String red,
			String webmaster, String asociados,	String inscripcion,
			String ultimoLogin, String ipConfir, String ipRegistro) {
		this.codigo = new Integer(codigo.intValue());
		this.password = password;		
		this.passwordAbierta = passwordAbierta;		
		this.nombre = nombre;	
		this.apellidos = apellidos;
		this.email = email;
		this.movil = movil;
		this.sexo = Sexo.get(sexo);
		this.cp = cp;
		this.fechaNacimiento = fechaNacimiento;
		this.pais = Pais.get(pais);
		this.provincia = provincia;
		this.ocupacion = ocupacion != null ? Ocupacion.get(ocupacion.toString()) : null;
		this.sector = sector != null? Sector.get(sector.toString()) : null;
		this.listas = (Lista[]) SetUtils.getAll(Lista.class, listas);
		this.idioma = Idioma.get(idioma);
		this.nombreWeb = nombreWeb;
		this.red = red;
		this.inscripcion = TextUtils.asTimestamp(inscripcion);
		this.ultimoLogin = TextUtils.asTimestamp(ultimoLogin);
		this.asociados = TextUtils.split(asociados);
		this.webmaster = webmaster;
		this.ipConfir = ipConfir;
		this.ipRegistro = ipRegistro;
	}

	public Integer getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getPassword() {
		return this.password;
	}	

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordAbierta() {
		return passwordAbierta;
	}

	public void setPasswordAbierta(String passwordAbierta) {
		this.passwordAbierta = passwordAbierta;
	}
	
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getCp() {
		return this.cp;
	}
	
	public void setCp(String cp) {
		this.cp = cp;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Lista[] getListas() {
		return this.listas;
	}
	
	public void setListas(Lista[] listas) {
		this.listas = listas;
	}
	
	public String getMovil() {
		return this.movil;
	}
	
	public void setMovil(String movil) {
		this.movil = movil;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Ocupacion getOcupacion() {
		return this.ocupacion;
	}
	
	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}
	
	public Pais getPais() {
		return this.pais;
	}
	
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public String getProvincia() {
		return this.provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public Sector getSector() {
		return this.sector;
	}
	
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
	public Sexo getSexo() {
		return this.sexo;
	}
	
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Idioma getIdioma() {
		return this.idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public String getNombreWeb() {
		return this.nombreWeb;
	}

	public String[] getAsociados() {
		return this.asociados;
	}

	public Timestamp getInscripcion() {
		return this.inscripcion;
	}

	public String getRed() {
		return this.red;
	}

	public Timestamp getUltimoLogin() {
		return this.ultimoLogin;
	}

	public String getWebmaster() {
		return this.webmaster;
	}

	public String getIpConfir() {
		return ipConfir;
	}

	public void setIpConfir(String ipConfir) {
		this.ipConfir = ipConfir;
	}

	public String getIpRegistro() {
		return ipRegistro;
	}

	public void setIpRegistro(String ipRegistro) {
		this.ipRegistro = ipRegistro;
	}
}
