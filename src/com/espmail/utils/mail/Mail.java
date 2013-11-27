package com.espmail.utils.mail;

import java.io.File;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espmail.utils.TextUtils;

/**
 * Clase que guarda los datos para un email y lo env�a.
 * 
 * @author Luis Cubillo
 * 
 */
public class Mail implements Runnable {

	private final static Log LOG = LogFactory.getLog(Mail.class);

   private final String bounce;

   private final String to;

	private final String cc;

	private final String bcc;

	private final String from;

   private final String subject;

	private final String mensaje;

	private final boolean html;

	private final String mensajeTexto;

	private final String replyTo;

	private final String host;

	private int error = 1;

	File[] adjuntos = null;

	File[] ficheros = null;

   HashMap cabeceras = null;
   /**
	 * Constructor.
	 * 
	 * @param host
	 *            Host
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
	public Mail(String host, String to, String cc, String bcc, String from,
			String subject, String mensaje, String mensajeTexto, String replyTo) {
		this(host, to, cc, bcc, from, null, subject, mensaje, mensajeTexto, replyTo,
				true, null);
	}

   /**
	 * Constructor.
	 *
	 * @param host
	 *            Host
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
	public Mail(String host, String to, String cc, String bcc, String from,
			String subject, String mensaje, String mensajeTexto, String replyTo, String bounce) {
		this(host, to, cc, bcc, from, bounce, subject, mensaje, mensajeTexto, replyTo,
				true, null);
	}
   /**
	 * Constructor.
	 * 
	 * @param host
	 *            Host
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
	public Mail(String host, String to, String from, String subject,
			String mensaje, boolean html) {
		this(host, to, null, null, from,null, subject, mensaje, null, null, true,
				null);
	}
   /**
	 * Constructor.
	 *
	 * @param host
	 *            Host
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
	public Mail(String host, String to, String from, String subject,
			String mensaje, boolean html, String bounce) {
		this(host, to, null, null, from, bounce, subject, mensaje, null, null, true,
				null);
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
	 */
	public Mail(String host, String to, String cc, String bcc, String from,
			String subject, String mensaje, String mensajeTexto,
			String replyTo, File[] ficheros) {
		this(host, to, cc, bcc, from, null, subject, mensaje, mensajeTexto, replyTo,
				true, ficheros);
	}

   /**
	 * Constructor privado
	 *
	 * @param host
	 *            Host
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
    * @param bounce
    *            direccion para el return path
    *
	 * @param subject
	 *            el tema del mensaje
	 * @param mensaje
	 *            Mensaje en html
	 * @param mensajeTexto
	 *            Texto
	 * @param replyTo
	 *            para responder a.
	 * @param html
	 *            Para indicar si mensaje tiene html o no.
	 */
   private Mail(String host, String to, String cc, String bcc, String from,String bounce,
			String subject, String mensaje, String mensajeTexto,
			String replyTo, boolean html, File[] ficheros){
      this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.from = from;
		this.subject = subject;
		this.mensaje = mensaje;
		this.mensajeTexto = mensajeTexto;
		this.replyTo = replyTo;
		this.html = html;
		this.host = host;
		this.ficheros = ficheros;
      this.bounce= bounce;      
   }

   /**
	 * Constructor privado
	 * 
	 * @param host
	 *            Host
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
	 * @param html
	 *            Para indicar si mensaje tiene html o no.
	 */
	private Mail(String host, String to, String cc, String bcc, String from,
			String subject, String mensaje, String mensajeTexto,
			String replyTo, boolean html, File[] ficheros) {
      this(host, to, cc, bcc, from, null, subject, mensaje,  mensajeTexto, replyTo, html, ficheros);
	}

	/**
	 * 
	 * @param ficheros
	 */
	public void adjunta(File[] ficheros, Multipart mp, MimeMessage msg) {

		adjuntos = ficheros;
		try {

			for (int i = 0; i < adjuntos.length; i++) {
				LogFactory.getLog(this.getClass()).debug(
						"Se va a enviar un fichero adjunto "
								+ adjuntos[i].getAbsolutePath());
				BodyPart mbp3 = new MimeBodyPart();
				DataSource ds = new FileDataSource(adjuntos[i]
						.getAbsolutePath());
				mbp3.setDataHandler(new DataHandler(ds));
				String nombre = adjuntos[i].getName();
				mbp3.setFileName(nombre);
				mp.addBodyPart(mbp3);
			}

			msg.setContent(mp);
		} catch (javax.mail.MessagingException me) {
			LogFactory.getLog(this.getClass()).error(
					"No se han podido adjuntar los archivos debido a "
							+ me.getMessage());
			me.printStackTrace();
		}

	}

	/**
	 * Envia el mensaje.
	 */
	public void run() {
		if (empty(this.to) && empty(this.cc) && empty(this.bcc)) {
			return;
		}

		LOG.info("Eviando correo " + this.subject + " a " + this.to + " de "
				+ this.from);
          LOG.info("(Cabeceras == null)=>  "
				+ (cabeceras==null));
		boolean debug = false;
		Properties props = new Properties();
		String host = this.error == 1 ? this.host : "maildirecto" + this.error;
		props.put("mail.smtp.host", host);
      LOG.info("Insertamos bounce "+bounce);
      //Si se utiliza incluimos el return path para recuperar los bounces
      if(!TextUtils.isEmpty(bounce))
         props.put("mail.smtp.from", bounce);
      
      Session session = Session.getInstance(props, null);
		session.setDebug(debug);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(this.from));
			msg.setSubject(this.subject, "iso-8859-1");
			msg.setSentDate(new Date());

			if (!empty(this.to)) {
				InternetAddress[] address = InternetAddress.parse(this.to);
				msg.setRecipients(Message.RecipientType.TO, address);
			}

			if (!empty(this.cc)) {
				InternetAddress[] address = InternetAddress.parse(this.cc);
				msg.setRecipients(Message.RecipientType.CC, address);
			}
			if (!empty(this.bcc)) {
				InternetAddress[] address = InternetAddress.parse(this.bcc);
				msg.setRecipients(Message.RecipientType.BCC, address);
			}

			if (!empty(this.replyTo)) {
				InternetAddress[] address = { new InternetAddress(this.replyTo) };
				msg.setReplyTo(address);
			}

         if(cabeceras != null)
            procesaCabeceras(msg);

         if (!this.html) {
				msg.setText(this.mensaje, "iso-8859-1");
			} else {
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setContent(this.mensaje, "text/html");
				Multipart mp = null;

				if (empty(mensajeTexto)) {
					mp = new MimeMultipart();
				} else {
					mp = new MimeMultipart("alternative");
					MimeBodyPart mbp2 = new MimeBodyPart();
					mbp2.setContent(this.mensajeTexto, "text/plain");
					mp.addBodyPart(mbp2);
				}

				mp.addBodyPart(mbp1);
				msg.setContent(mp);
			}

			// Miro si tiene adjuntos
			if (ficheros != null) {
				Multipart mp = null;
				mp = new MimeMultipart("mixed");
				this.adjunta(ficheros, mp, msg);

				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setDescription("", "iso-8859-1");
				LogFactory.getLog(this.getClass()).debug("content");
				mbp.setContent(mensaje, "text/plain");
				LogFactory.getLog(this.getClass()).debug("bodypart");
				mp.addBodyPart(mbp);
				LogFactory.getLog(this.getClass()).debug("save");
				msg.saveChanges();
			}

			Transport.send(msg);
		} catch (MessagingException e) {
			LOG.error("Error enviando mensaje", e);
			Exception ex = e.getNextException();

			if (ex != null) {
				LOG.error("Error enviando mensaje 2", ex);
			}
			
			if (this.error < 4){
				this.error++;			
				run();
			}
		}

	}

   /**
    *  necesario para el wrap de MailDirecto
    *
    * @param cabeceras
    */

   public void setCabeceras(HashMap cabeceras){
      this.cabeceras=cabeceras;
   }

   /**
    * Necesario para el wrap de MailDirecto
    *
    * @param msg
    */
   public void procesaCabeceras(MimeMessage msg){
        if(cabeceras == null)
            return;

        Iterator it = cabeceras.keySet().iterator();
        while(it.hasNext()){
            String clave = ( String )it.next();
            try {
                msg.addHeader(clave, ( String )cabeceras.get(clave));
                LOG.info("Insertada cabecera "+clave+" con valor "+( String )cabeceras.get(clave));
            } catch (MessagingException e) {
                e.printStackTrace();
                LOG.error("No se inserto la cabecera para "+clave);
            }
        }
    }

   /**
	 * Indica si la cadena que se le pasa es nula o vacia.
	 * 
	 * @param value
	 *            String a comprobar
	 * @return True si es vacio, false en caso contrario.
	 */
	private boolean empty(String value) {
		return value == null || value.trim().length() == 0;
	}
}
