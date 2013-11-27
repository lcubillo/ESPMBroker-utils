package com.espmail.utils;

import java.util.Random;

/**
 * Genera un String de 8 caracteres aleatorio.
 * 
 * @author Luis Cubillo
 * 
 */
public class PasswordGenerator {
	/**
	 * Genera un String de ocho caracteres aleatorios.
	 * 
	 * @return String con ocho letras.
	 */
	public static String generate() {
		String letras = "abcdefghijklmnopqrstuvwxyz1234567890";
		Random rng = new Random();
		StringBuffer password = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			password.append(letras.charAt(rng.nextInt(36)));
		}
		return password.toString();
	}

}
