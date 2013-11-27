/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espmail.utils.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Luis Cubillo
 */
public class Conexion {


   public static void main (String argv []){

      BufferedReader isr = null;
      
      URL direccion = null;
      try {
         direccion = new URL(argv[0]);
      } catch (MalformedURLException ex) {
         LogFactory.getLog(Conexion.class).error("Fallo al abrir la direccion " + ex.getMessage());
         ex.printStackTrace();
         System.exit(-1);
      }
      URLConnection url = null;
      try {
         url = direccion.openConnection();
      } catch (IOException ex) {
         LogFactory.getLog(Conexion.class).error("Error en la apertura " + ex.getMessage());
         ex.printStackTrace();
         System.exit(-1);
      }
      url.setDoInput(true);
      try {
         isr = new BufferedReader(new InputStreamReader(url.getInputStream()));
      } catch (IOException ex) {
         LogFactory.getLog(Conexion.class).error("Error inesperado: " + ex.getMessage());
      }

      LogFactory.getLog(Conexion.class).info("Establecida conexion");

      SAXSource source = new SAXSource();
      source.setInputSource(new InputSource(isr));

      Document doc = null;
      try {
         doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
         doc.setStrictErrorChecking(false);
      } catch (ParserConfigurationException ex) {
         LogFactory.getLog(Conexion.class).error("Error inesperado abriendo documento: " + ex.getMessage());
      }
      NodeList elementos = doc.getElementsByTagName("A");
      try {
         TransformerFactory.newInstance().newTransformer().transform(source, new DOMResult(doc));
      } catch (TransformerException ex) {
         LogFactory.getLog(Conexion.class).error("Error inesperado en el parseo: " + ex.getMessage());
         ex.printStackTrace();
      }

      try {
         isr.close();
      } catch (IOException e) {
         LogFactory.getLog(Conexion.class).error("Error inesperado al cerrar el stream: " + e.getMessage());
      }

      for (int i = 0; i < elementos.getLength(); i++){
         Element enlace = ( Element )elementos.item(i);
         LogFactory.getLog(Conexion.class).info("Enlace encontrado hacia-> "+enlace.getAttribute("src"));
      }

   }

}
