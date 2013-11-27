/**
 * 
 */
package com.espmail.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;



import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;






/**
 * @author Luis Cubillo
 * @date 08/01/2010
 * 
 * Clase para hacer llamadas POST o GET a una URL
 */

public class PeticionesInternet {


	public static void main(String argv[])
	{
		
		BufferedReader test = PeticionesInternet.doPostCall("http://10.0.1.35/php/promocion_flash.php?","gon=es&es=esdos");
		String line="";
		String retorno="";
		int i =0;
				try {
        while((line = test.readLine()) != null) 
        {        	
            retorno+=line;                
        }	 
		}catch(IOException e){
			System.out.println("CASCO!");
		}		
        System.out.println(retorno);
         
	}
	
	
/**
 * 
 * @param url
 * @param parametros
 * @return
 * 
 */
	public static  BufferedReader doPostCall(String url,String parametros){
		String retorno="";
		
		 URLConnection conn;
		 DataOutputStream server;
		  try
	        {
	           
	            URL aURL = new URL(url);
	             conn = aURL.openConnection();
	            conn.setDoOutput(true);          
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            //conn.setAllowUserInteraction(false);     
	            conn.setRequestProperty( "Content-type",
	                                    "application/x-www-form-urlencoded");
	                  
	             server = new DataOutputStream(conn.getOutputStream());	              
	            server.writeBytes(parametros);	                
	            server.flush();	                
	            server.close();
	        	
	            
	            
	           
	        	BufferedReader stream=new BufferedReader( new InputStreamReader(conn.getInputStream()));
	           
	            
	            //stream.close();
	            return stream;
	            
	            
	            //si se quiere devolver el string sera con
	            /*String line="";
	            while((line = stream.readLine()) != null) 
	            {
	                retorno+=line;                
	            }	   
	              
	             */ 
	        }catch (Exception e){	            
	            e.printStackTrace();
	        }    
	        return null;
	}
	
/**
 * 
 * @param urlParams
 * @return 
 */
	public static BufferedReader doGetCall(String urlParams){
		String retorno="";
		System.out.println("Entro en GET");
		System.out.println(urlParams);
		// DataOutputStream server ;
		  try
	        {
	           
	            URL aURL = new URL(urlParams);
	            URLConnection conn = aURL.openConnection();
	            conn.setDoOutput(true);          
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            //conn.setAllowUserInteraction(false);     
	            conn.setRequestProperty( "Content-type",
	                                    "application/x-www-form-urlencoded");
	            
	        	//System.out.println("Obtengo get DataOutput");
	          // server= new DataOutputStream(conn.getOutputStream());
	          //server.writeBytes(urlParams);
	          //  server.flush();
	          //  server.close();
	            
	           
	            
	         
	            BufferedReader stream=new BufferedReader( new InputStreamReader(conn.getInputStream()));
	            
	            //stream.close();
	            return stream;
	          
	            //Si se quiere devolver el string sera
	            /*String line="";
	            while((line = stream.readLine()) != null) 
	            {
	                retorno+=line;                
	            }*/
	            
	           
	            
	        }catch (Exception e){	            	        	
	            e.printStackTrace();
	        }
	        return null;
	}
	
	/**
	 * Hace una peticion por get y obtiene el Document 
	 * @date 26/02/2010
	 * @param urlParams
	 * @return
	 */
	public static Document doGetCallDocument(String urlParams){
		String retorno="";
		System.out.println("Entro en GET");
		System.out.println(urlParams);
		// DataOutputStream server ;
		  try
	        {
	           
	            URL aURL = new URL(urlParams);
	            URLConnection conn = aURL.openConnection();
	            conn.setDoOutput(true);          
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            //conn.setAllowUserInteraction(false);     
	            conn.setRequestProperty( "Content-type",
	                                    "application/x-www-form-urlencoded");
	            
	            BufferedReader stream=new BufferedReader( new InputStreamReader(conn.getInputStream()));
	            
	            /**
	             * Obtengo el Document a partir de un BufferedReader
	             */
	            
	            SAXSource source = new SAXSource();
	            source.setInputSource(new InputSource(stream));
	     
	            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	            TransformerFactory.newInstance().newTransformer().transform(source,
	            	new DOMResult(doc));
	     
	            return doc;
	           
	            
	        }catch(ParserConfigurationException e1){
	        	e1.printStackTrace();
	        }catch(TransformerConfigurationException e2){
	        	e2.printStackTrace();
	        }catch(TransformerException e3){
	        	e3.printStackTrace();	
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return null;
	}
	
	/**
	 * Hace una llamada post y devuelve un document
	 * @date 26/02/2010
	 *
	 * @param url
	 * @param parametros
	 * @return
	 */
	
	public static  Document doPostCallDocument(String url,String parametros){
		
		
		 URLConnection conn;
		 DataOutputStream server;
		  try
	        {
	           
	            URL aURL = new URL(url);
	             conn = aURL.openConnection();
	            conn.setDoOutput(true);          
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            //conn.setAllowUserInteraction(false);     
	            conn.setRequestProperty( "Content-type",
	                                    "application/x-www-form-urlencoded");
	                  
	             server = new DataOutputStream(conn.getOutputStream());	              
	            server.writeBytes(parametros);	                
	            server.flush();	                
	            server.close();
	        	
	           
	        	BufferedReader stream=new BufferedReader( new InputStreamReader(conn.getInputStream()));
	         
	        	 /**
	             * Obtengo el Document a partir de un BufferedReader
	             */
	            
	            SAXSource source = new SAXSource();
	            source.setInputSource(new InputSource(stream));
	     
	            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	            TransformerFactory.newInstance().newTransformer().transform(source,
	            	new DOMResult(doc));
	     
	            return doc;
	           
	            
	        }catch(ParserConfigurationException e1){
	        	e1.printStackTrace();
	        }catch(TransformerConfigurationException e2){
	        	e2.printStackTrace();
	        }catch(TransformerException e3){
	        	e3.printStackTrace();	
	        
	        }catch (Exception e){	            
	            e.printStackTrace();
	        }    
	        return null;
	}
	
	
}
