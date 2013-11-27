package com.espmail.utils.informes;

import com.espmail.modelo.maestras.Pais;
import com.espmail.utils.annotations.InformeAnnotation;

import jxl.CellFormat;
import jxl.DateCell;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import oracle.sql.TIMESTAMP;

import org.apache.commons.logging.LogFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Crea informes a partir de los datos obtenidos en la base de datos.
 * @author Luis Cubillo
 *
 */
public class   GeneradorInformes {

   public static String NUMBER="Number";
   public static String STRING="String";
   public static String TIMESTAMP="TimeStamp";
   public static String COLUMNERROR="ColumnNameError";
   
   public static final GeneradorInformes INSTANCE = new GeneradorInformes();

	private final Map cacheTemplate;
	
	private GeneradorInformes() {
		this.cacheTemplate = new HashMap();
	}

   public void generaExcel(Object [] lineas, OutputStream os)
        throws GeneradorInformesException{
      this.generaExcel(lineas, os, "");

   }
   public void generaExcel (Object [] lineas, OutputStream os, String tipoInforme)
      throws GeneradorInformesException{

	   if (lineas!=null)
		   LogFactory.getLog(this.getClass()).debug("Total filas informe "+lineas.length);
      WritableWorkbook libro = null ;
      WritableSheet hoja = null ;
      

      try {
         libro = Workbook.createWorkbook(os);
         hoja = libro.createSheet("Informe ",0);
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      Object l=null;
      boolean cabeceras = false;
      for (int fila = 0; fila < lineas.length ;
            fila++){
         l = lineas[fila];
         Method[] metodos = l.getClass().getMethods();
         LogFactory.getLog(this.getClass()).debug("Total metodos fila "+fila+ ": "+ metodos.length+ " Clase "+l.getClass().getName());


         /** Extraemos la linea de cabecera e invocamos los getters **/
        
         
         Vector<ValorOrdenInformes>  valores = new Vector();
//         valores.setSize(metodos.length);
       
         
         Method metodo = null;
         for(int columna = 0; columna < metodos.length ;
             columna++){
        	 ValorOrdenInformes voi = new ValorOrdenInformes();
        	 
        	 
            metodo = metodos[columna];
           LogFactory.getLog(this.getClass()).debug("Procesando fila "+fila+" metodo "+ metodo.getName());
            InformeAnnotation metaInfo = metodo.getAnnotation( InformeAnnotation.class );
            if(metaInfo == null){
              LogFactory.getLog(this.getClass()).debug("Annotation null");
               continue;
            }

            if(metaInfo.informe().indexOf(tipoInforme)<0)
            	continue;
           
            int orden = metaInfo.orden()>=0?metaInfo.orden():valores.size();
            
            if(!cabeceras && metaInfo.informe().indexOf(tipoInforme)>=0)
               try {

                  Label etiqueta = new Label(orden, 0, metaInfo.titulo());
                  hoja.addCell(etiqueta);

               } catch (WriteException e) {
                  try { hoja.addCell(new Label(columna,0,COLUMNERROR)); }
                  catch (WriteException e1) {}
               }

            try {   
               voi.setOrden(orden);
               voi.setParam1(metodo.invoke(l));
              valores.add(voi);          
               //valores.insertElementAt(metodo.invoke(l), orden );
            }  catch (IllegalAccessException e) {
                
            	LogFactory.getLog(this.getClass()).error("Error en el metodo "+ metodo.getName()+": "+e.getMessage());
               e.printStackTrace();
               //valores.insertElementAt( new String(), orden );
               valores.add(voi);
            } catch (InvocationTargetException e) {
               LogFactory.getLog(this.getClass()).error("Error en el metodo "+ metodo.getName()+": "+e.getMessage());
               e.printStackTrace();
               //valores.insertElementAt( new String(), orden );
               valores.add(voi);
            }
         }
         
        //valores.trimToSize();
         cabeceras = (!cabeceras) ? !cabeceras : cabeceras;
                           
        for(int columna = 0; columna < metodos.length;columna++){
            metodo = metodos[columna];
            InformeAnnotation metaInfo = metodo.getAnnotation( InformeAnnotation.class );
            if(metaInfo == null || metaInfo.equals(null))//todo a�adir la comprobacion de los parametros (solo valido para metodos get)
               continue;
            
            if(metaInfo.informe().indexOf(tipoInforme)<0)
            	continue;
            
            
            ValorOrdenInformes voi = new ValorOrdenInformes();
            
            
            WritableCell celda =  null;

            /** Comprobamos el tipo para la insercion **/
            
            String tipo = metaInfo.tipo();
            int orden = metaInfo.orden();
            //Obtengo el elemento de ese orden
 
            
            for (int z=0;z<valores.size();z++   ){
            	if (valores.get(z).getOrden()==orden){
            		//Es el elemento
            		   if(NUMBER.equals(tipo)){
                       	celda = new Number(orden, fila+1, (( Float )valores.get(z).getParam1()==null?0:(Float)valores.get(z).getParam1()));
                       }else if(TIMESTAMP.equals(tipo)){
	                       	try{
		                    	Date  dateTime = new Date();
		                    
		                    	DateFormat dform = new DateFormat("dd/MM/yyyy HH:mm:ss");		                       
		                        WritableCellFormat dformat = new WritableCellFormat(dform);
		                        dateTime.setTime((((Timestamp) valores.get(z).getParam1()).getTime()));

		                    	celda =  new DateTime( orden, fila+1,dateTime,dformat);
		                    	
		                    	
	                       	}catch(Exception ex){
	                       		
	                       	}
                       	
                      // 	celda = new DateTime( orden, fila+1,new Date( ((Timestamp) valores.get(z).getParam1()).getTime()));
                       }else{
                               
                       	//celda = new Label(orden, fila+1, ( String )(valores.get(0)==null?(valores.remove(0)==null?"":valores.remove(0)):valores.remove(0).toString()));            	
                       	celda = new Label(orden, fila+1, ( String )(valores.get(z).getParam1()==null?"":valores.get(z).getParam1().toString()));
                       }
            		
            	}
            }

            try {
               hoja.addCell( celda );

            } catch (WriteException e) {
               PrintWriter err = new PrintWriter(os);
               err.write("Error en el metodo "+metaInfo.titulo()+": "+e.getMessage());
               e.printStackTrace(err);
               err.flush();
               err.close();
               throw new GeneradorInformesException(e);
            }
         }

      }
      try {
         libro.write();
         libro.close();
      } catch (IOException e) {
         PrintWriter err = new PrintWriter(os);
         err.write("Error al cerrar el libro: "+e.getMessage());
         e.printStackTrace(err);
         err.flush();
         err.close();
         throw new GeneradorInformesException(e);
      }
   }

   public void generaExcel(ResultSet rs, OutputStream os, String [] types)
        throws GeneradorInformesException{
      try {
         WritableWorkbook libro = Workbook.createWorkbook(os);
         WritableSheet hoja = libro.createSheet("Informe", 0);
         ResultSetMetaData rsmd = rs.getMetaData();

         for(int i=1; i<= rsmd.getColumnCount();i++){
            Label etiqueta = new Label(i-1, 0, rsmd.getColumnName(i));
            hoja.addCell(etiqueta);
         }

         for (int fila = 1; rs.next() ; fila++ ){
            for(int i=0;i < types.length ;i++){
               WritableCell celda = null;
               if(types[i].equals("number")){
                  celda = new Number(i, fila, rs.getFloat(i+1));
               } else if(types[i].equals("timestamp")){
                  celda = new DateTime(i, fila, rs.getDate(i+1));
               } else
                  celda = new Label(i, fila, rs.getString(i+1));
               
               hoja.addCell(celda);
            }
         }
         libro.write();
         libro.close();
      } catch (Exception e) {
         e.printStackTrace();
         throw new GeneradorInformesException(e);
      }


   }
	/**
	 * Genera un excel con los datos obtenidos de una tabla
	 * @param rs ResultSet con los datos para generar el archivo Excel
	 * @param os datos de salida.
	 * @throws GeneradorInformesException En caso de error en la generaci�n del excel.
	 */
	public void generaExcel(ResultSet rs, OutputStream os)
			throws GeneradorInformesException {
		try {
			WritableWorkbook libro = Workbook.createWorkbook(os);
			WritableSheet hoja = libro.createSheet("Informe", 0);
			int length =  rs.getMetaData().getColumnCount();
	
			String[] types = new String[length];
	
			if(rs.next()) {
				for (int i = 1; i <= length; i++) {
					Label label = new Label(i - 1, 0, rs.getString(i));
			        hoja.addCell(label);
				}
			}
			
			if(rs.next()) {
				for (int i = 1; i <= length; i++) {
					types[i - 1] = rs.getString(i);
				}
			}

			for(int fila = 1; rs.next(); fila++) { 
				for (int i = 1; i <= length; i++) {
					String type = types[i - 1];
					WritableCell celda = null;
					String value = rs.getString(i);

					if (type.equals("number") && value != null) {
						celda = new Number(i - 1, fila, Float.parseFloat(value));
					} else {
						celda = new Label(i - 1, fila, value);
					}
					
					hoja.addCell(celda);
				}
			}
			libro.write();
			libro.close();
		} catch (Exception e) {
			throw new GeneradorInformesException(e);
		}
	}
	/**
	 * escribe en la salida los datos que salen de generar el xml con el resultset y luego aplicar la plantilla xslt con los par�metros. 
	 * @param xsltFile plantilla xslt
	 * @param rs resultset con los datos de la base de datos.
	 * @param os datos de salida
	 * @param params par�metros para la plantilla xsl
	 * @throws GeneradorInformesException 
	 */
	public void generaXML(String xsltFile, ResultSet rs, OutputStream os, Map params)
			throws GeneradorInformesException {
		try {
			StringBuffer sb = new StringBuffer("<result>");
			int length =  rs.getMetaData().getColumnCount();
			sb.append("<head>");
			String[] types = new String[length];

			if(rs.next()) {
				for (int i = 1; i <= length; i++) {
					sb.append("<value>");
					sb.append(rs.getString(i));
					sb.append("</value>");
				}
			}

			if(rs.next()) {
				for (int i = 1; i <= length; i++) {
					types[i - 1] = rs.getString(i);
				}
			}

			sb.append("</head>");

			while(rs.next()) {
				sb.append("<row>");
	
				for (int i = 1; i <= length; i++) {
					sb.append("<value type=\"").append(types[i - 1]).append("\">");
					String value = rs.getString(i);
					
					if (value != null) {
						sb.append(value);
					}

					sb.append("</value>");
				}
	
				sb.append("</row>");
			}
	
			sb.append("</result>");
			
			Transformer transformer = getTemplate(xsltFile);
			
			if (params != null) {
				Iterator it = params.entrySet().iterator();
				
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					transformer.setParameter( ( String ) entry.getKey(), entry.getValue() );
				}
			}
			
			StreamSource source = new StreamSource(new StringReader(sb.toString()));
			transformer.transform(source, new StreamResult(os));
		} catch (Exception e) {
			throw new GeneradorInformesException(e);                                                              
		}
	}
	
	/**
	 * escribe en la salida los datos que salen de  aplicar la plantilla xslt con los par�metros a un xml dado. 
	 * @param xsltFile plantilla xslt
	 * @param xml xml a tratar con el xslt
	 * @param os datos de salida
	 * @param params par�metros para la plantilla xsl
	 * @throws GeneradorInformesException 
	 */
	public void generaXMLString(String xsltFile, String  xml, OutputStream os, Map params)
			throws GeneradorInformesException {
		try {
			StringBuffer sb = new StringBuffer(xml);
			
			
			Transformer transformer = getTemplate(xsltFile);
			
			if (params != null) {
				Iterator it = params.entrySet().iterator();
				
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					transformer.setParameter( ( String ) entry.getKey(), entry.getValue() );
				}
			}
			
			StreamSource source = new StreamSource(new StringReader(sb.toString()));
			transformer.transform(source, new StreamResult(os));
		} catch (Exception e) {
			throw new GeneradorInformesException(e);                                                              
		}
	}
	/**
	 * Obtiene un xml a partir de un array de objectos. 
	 * Inicialmente pensado para el uso en los informes de generacion en administracion
	 * @author Luis Cubillo
	 * @date 01/03/2010
	 * @param ob	Array de Objetos
	 * @return String xml 
	 */
	public String generarStringXMLInformes (Object[] lineas){
		String xml="";
		
		/**
		 * 
		 * 
		 */
		  Object l=null;
		  Vector<String> tipos =new Vector<String>();
		  
		  
	      boolean cabeceras = false;
	      if (lineas.length>0) xml =xml + "<result>";
	      
	      
	      
	      
	      try{
	      for (int fila = 0; fila < lineas.length ;fila++){
	         l = lineas[fila];
	         Method[] metodos = l.getClass().getMethods();
	         Vector<ValorOrdenInformes>  valores = new Vector();
	         Method metodo = null;

	       
	        //Miro los ordenes /valores de esta tanda
	         for (int columna = 0;columna< metodos.length;columna++){
	        	ValorOrdenInformes voi = new ValorOrdenInformes();
            	metodo = metodos[columna];	        	         
 	            InformeAnnotation metaInfo = metodo.getAnnotation( InformeAnnotation.class );
 	           
 		         if(metaInfo != null){	          		        	
 		        	 voi.setOrden(metaInfo.orden());
 	 	            voi.setParam1(metodo.invoke(l)==null?"":metodo.invoke(l));
 	 	            valores.add(voi);
 		            } 		         
	         }
	         
		     
	
	        //Si la fila es cero seran los titulos
	         if (fila ==0){
	        	 xml = xml +"<head>";
	        	 	for(int i = 0; i < valores.size()+1;i++){
	        	 		for(int j = 0; j < valores.size();j++){
	        	 			
		        	 		if (Integer.valueOf(valores.get(j).getOrden()).intValue()==i){
		        	 			xml= xml + "<value>"+String.valueOf(valores.get(j).getParam1())+"</value>";
		        	 			j=valores.size();
		        	 		}
	        	 		}
	        	 	}
	        	 	 xml = xml +"</head>";
	        	 }
	        	
	        	 
	         
	         //Si la fila es 1 seran los  tipos
	      	if (fila ==1){	      		
	      		for(int i = 0; i < valores.size();i++)
	      			tipos.add(String.valueOf(valores.get(i).getParam1()));	 
	      	
	         }
	      	
	         //Si la fila > 0 seran los valores
	         if (fila>1){
	        	 xml = xml +"<row>";
	        		for(int i = 0; i < valores.size()+1;i++){
	        	 		for(int j = 0; j < valores.size();j++){
		        	 		if (Integer.valueOf(valores.get(j).getOrden()).intValue()==i){
		        	 			//System.out.println(String.valueOf(valores.get(i).getParam1())+" - "+String.valueOf(valores.get(j).getParam1()));
		        	 			xml= xml + "<value type=\""+tipos.get(j)+"\">"+String.valueOf(valores.get(j).getParam1())+"</value>";
		        	 			j=valores.size();
		        	 		}
	        	 		}
	        	 	}
	        	
		        xml = xml +"</row>";
	         }

	      }
	      if (!xml.equals("")) xml = xml+"</result>";
	      //System.out.println("xml"+xml);
	      }catch(Exception ex){
	    	  ex.printStackTrace();
	    	  System.out.println("error: "+xml);
	    	  xml="";
	    	  
	      }
	    
		
		/**
		 */
		return xml;
	}
	/**
	 * Mira si el archivo ya existe en el mapa cacheTemplate y si no esta lo crea y devuelve una instancia de la plantilla. 
	 * @param fileName nombre del fichero
	 * @return una instancia de la plantilla.
	 * @throws TransformerException
	 */
	private Transformer getTemplate(String fileName) throws TransformerException {
		
		FicheroTemplate template = (FicheroTemplate) this.cacheTemplate.get(fileName);
		if (template == null) {
			try {
				template = new FicheroTemplate(fileName);
			} catch (IOException e) {
				throw new TransformerException(e);
			}

			this.cacheTemplate.put(fileName, template);
		}
		
		return template.newTransformer();
	}

   private Object invoke(String targetName, Class targetObject) throws NoSuchMethodException {
      return targetObject.getMethod(targetName,null);
   }
}
