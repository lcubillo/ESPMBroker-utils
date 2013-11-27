package com.espmail.utils.mail;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.HashMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.LogFactory;

//import components.mail.InternetAddress;

/**
 * Crea mails para un host y los env�a.
 * 
 * @author Luis Cubillo
 * 
 */
public class LanzadorMail {

   private HashMap cabeceras;
   private final String host;

	/**
	 * 
	 * @param host
	 */
	public LanzadorMail(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.host;
	}

	
	/**
	 * Crea un mail en un hilo y lo envia.
	 * 
	 * @param to
	 *            Persona a la que va dirigida el email.
	 * @param cc
	 *            define una o varias direcciones de email que van a recibir
	 *            copia exacta enviada al destinatario(s) original. Todos pueden
	 *            ver a quien se les envi� los emails.
	 * @param bcc
	 *            define una o varias direcciones de email que van a recibir
	 *            copia exacta enviada al destinatario(s) original. No pueden
	 *            ver a quien se les envi� los emails.
	 * @param from
	 *            el nombre y direcci�n de email del que env�a
	 * @param subject
	 *            el tema del mensaje
	 * @param mensaje
	 *            Mensaje en html
	 * @param mensajeTexto
	 *            Texto
	 * @param replyTo
	 *            para responder a.
	 */
	public void envia(String to, String cc, String bcc, String from,
			String subject, String mensaje, String mensajeTexto, String replyTo) {
		Mail mail = new Mail(this.host, to, cc, bcc, from, subject, mensaje,
				mensajeTexto, replyTo);
          mail.setCabeceras(cabeceras);
		new Thread(mail).start();
	}

   /**
	 * Crea un mail en un hilo y lo envia.
	 *
	 * @param to
	 *            Persona a la que va dirigida el email.
	 * @param cc
	 *            define una o varias direcciones de email que van a recibir
	 *            copia exacta enviada al destinatario(s) original. Todos pueden
	 *            ver a quien se les envi� los emails.
	 * @param bcc
	 *            define una o varias direcciones de email que van a recibir
	 *            copia exacta enviada al destinatario(s) original. No pueden
	 *            ver a quien se les envi� los emails.
	 * @param from
	 *            el nombre y direcci�n de email del que env�a
	 * @param subject
	 *            el tema del mensaje
	 * @param mensaje
	 *            Mensaje en html
	 * @param mensajeTexto
	 *            Texto
	 * @param replyTo
	 *            para responder a.
	 */
   public void envia(String to, String cc, String bcc, String from,String bounce,
			String subject, String mensaje, String mensajeTexto, String replyTo) {
      LogFactory.getLog(this.getClass()).debug("Bounce "+bounce);
      Mail mail = new Mail(this.host, to, cc, bcc, from,  subject, mensaje,
				mensajeTexto, replyTo,bounce);
      
      mail.setCabeceras(cabeceras);
      LogFactory.getLog(this.getClass()).debug("Arrancando Hilo");
      new Thread(mail).start();
	}

   /**
	 * Crea un mail en un hilo y lo envia.
	 * 
	 * @param to
	 *            Persona a la que va dirigida el email.
	 * @param from
	 *            el nombre y direcci�n de email del que env�a
	 * @param subject
	 *            el tema del mensaje
	 * @param mensaje
	 *            Mensaje en html
	 * @param html
	 *            si contiene html o no.
	 */
	public void envia(String to, String from, String subject, String mensaje,
			boolean html) {
		Mail mail = new Mail(this.host, to, from, subject, mensaje, html);
          mail.setCabeceras(cabeceras);
		new Thread(mail).start();
	}
   /**
	 * Crea un mail en un hilo y lo envia.
	 *
	 * @param to
	 *            Persona a la que va dirigida el email.
	 * @param from
	 *            el nombre y direcci�n de email del que env�a
	 * @param subject
	 *            el tema del mensaje
	 * @param mensaje
	 *            Mensaje en html
	 * @param html
	 *            si contiene html o no.
	 */
	public void envia(String to, String from, String bounce, String subject, String mensaje,
			boolean html) {
		Mail mail = new Mail(this.host, to, from, subject, mensaje, html, bounce);
          mail.setCabeceras(cabeceras);
		new Thread(mail).start();
	}

   /**
	 * 
	 * @param host
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param from
	 * @param subject
	 * @param mensaje
	 * @param mensajeTexto
	 * @param replyTo
	 * @param adjuntos ficheros adjuntos
	 */
	public void envia(String to, String cc, String bcc, String from,
			String subject, String mensaje, String mensajeTexto, String replyTo,File[] ficheros) {
		Mail mail = new Mail(this.host,to, cc, bcc, from, subject, mensaje, mensajeTexto, replyTo, ficheros);
          mail.setCabeceras(cabeceras);
      new Thread(mail).start();
			
	}

   public void setCabeceras(HashMap cabeceras){
      this.cabeceras = cabeceras;
   }



}
