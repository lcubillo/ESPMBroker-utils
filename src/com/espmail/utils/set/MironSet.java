package com.espmail.utils.set;

public class MironSet implements Runnable {
	
	// clase que clase que instancia el objeto
	private final Class setType;

	// tiempo en el que se despierta.
	private static final long TIME = 3600000;

	/**
	 * Crea el hilo con el fichero y el periodo de tiempo en el que
	 * comprobará si el fichero ha cambiado
	 * 
	 * @param prop
	 *            Fichero a comprobar.
	 * @param time
	 *            El tiempo que pasa entre las comprobaciones.
	 */
	public MironSet(Class setType) {
		this.setType= setType;
	}

	/**
	 * Duerme el hilo un periodo de tiempo y al despertar comprueba si ha
	 * cambiado el fichero y si ha cambiado lo recarga.
	 */
	public void run() {
		boolean parar = false;

		while (!parar) {
			try {
				Thread.sleep(TIME);
			} catch (InterruptedException e) {

				parar = true;
			}

			SetUtils.reload(setType);
		}
	}
	public static void lanza(Class setType){
		new Thread(new MironSet(setType)).start();
	}
}