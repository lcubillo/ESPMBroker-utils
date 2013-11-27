package com.espmail.utils;

import java.net.*;
import java.security .*;
import java.util.HashMap;
import java.util.Random;



public class PkGenerator
{
    
    private static SecureRandom seeder=new SecureRandom();
    
    
    /**
     * 
     * @param clase
     * @return
     */
    public synchronized static String getId(Object clase) 
    {
        int auxInt=seeder.nextInt();
        if (auxInt<0)
        {
            auxInt=auxInt*-1;
        }
        
        
        int auxCod=System.identityHashCode(clase);
        
        
        String auxString="";
        String auxCurrentTime="";
        
                
        
        
        auxString=""+auxInt+auxCod+System.currentTimeMillis();
        
        
        if (auxString.length()>14)
        {
            auxString=auxString.substring(0,14);
        }                
        
      
        
 
        //org.apache.commons.logging.LogFactory.getLog(PkGenerator.class).info("PkGenerator.getId() devuelve " + auxString);
        return auxString; 
        
        
        
    }
    
    /**
     * Genera una clave numerica de 12 digitos.
     * @param clase
     * @return
     */
    public synchronized static String getId12(Object clase) 
    {
        int auxInt=seeder.nextInt();
        if (auxInt<0)
        {
            auxInt=auxInt*-1;
        }
        int auxCod=System.identityHashCode(clase);
        if (auxCod<0)
        {
        	auxCod=auxCod*-1;
        }
        String auxString="";
       

        auxString=""+auxInt+auxCod;;
       
        
        if (auxString.length()>12)
        {
            auxString=auxString.substring(0,12);
        }
        
        if (auxString.length()<12){
        	 
        	//rellenar hasta 12.
        	int longitud = auxString.length();
        	int restante = 12 -longitud;
        	while (restante>0){
        		//genero un numero entre 1 y 9
        		Math.random();
        		int numeroAleatorio = (int) (Math.random()*8+1);
        		auxString = String.valueOf(numeroAleatorio)+auxString;
        		restante--;
        	}
        	
        	
        }
        
        return auxString; 
        
        
        
    }
    
    public static void main(String args[])
    {
        
        System.out.println("Inicio:"+System.currentTimeMillis());
        
        HashMap contenedor = new HashMap();
       
       
        
        String pks="";
        for (int i=0;i<200000;i++)
        {    
          PkGenerator pk=new PkGenerator();
           pks = pk.getId12((Object)pk);
         // contenedor.put(i,pks);
           if (pks.contains("-")){
          System.out.println(pks);
           }
        }
        
       /* int repetidos = 0;
        for (int j = 0;j<contenedor.size();j++){
        	Object aux = contenedor.get(j);
        	contenedor.remove(j);
        	if (j%1000==0) System.out.println(j+" Repetidos : "+repetidos);
        	if (contenedor.containsValue(aux)) repetidos++;
        		
        }
        System.out.println("REPETIDOs:"+repetidos);*/
        System.out.println("Final:"+System.currentTimeMillis());
    }
    
}
