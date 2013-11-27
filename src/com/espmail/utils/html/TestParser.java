/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espmail.utils.html;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Luis Cubillo
 */
public class TestParser {

   public static void main(){
         try {
            BufferedReader br = new BufferedReader(new FileReader("archivo.html"));
            NodeList links = ParseHTML.getElements("A", ParseHTML.getDocument(br));

            for(int i=0; i < links.getLength();i++)
               ((Element)links.item(i)).getAttributeNode("a");
         } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
         } catch (TransformerConfigurationException ex) {
            Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
         } catch (TransformerException ex) {
            Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
         }catch (FileNotFoundException ex) {
         Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
      }
      


   }


}
