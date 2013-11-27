package com.espmail.utils.users;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

/**
 * 
 * @author Luis Cubillo
 *	Clase Usuario usada para aplicaciones con control de seguridad mediante TagLibs
 */
public class Usuario implements Serializable{

		String user;
		String pass;
		String rol;
		String id;//Se iguala al SessionID para comprobaciones de sesión
		
		
		public Usuario(){
			
		}
		
		public Usuario(String user,String pass){
			this.user = user;
			this.pass = pass;				
		}
						
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		
		public String getPass() {
			return pass;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		public String getRol() {
			return rol;
		}
		public void setRol(String rol) {
			this.rol = rol;
		}
		
}
