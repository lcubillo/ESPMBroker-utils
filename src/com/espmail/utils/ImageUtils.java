package com.espmail.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Luis Cubillo
 * @date: 11/05/2010
 * 
 */

public  class  ImageUtils {

	
	
/**
 * Crea una imagen con un texto determinado , de un tama�o determinado, y con una fuente determinada
 * @param text
 * @param width
 * @param height
 * @param font por defecto SansSerif
 * @return BufferedImage 
 */
	 public static BufferedImage generateImage(String text,int width,int height,String font){

		 try{
			 Color background = new Color(255,255,255);
			 Color fbl = new Color(63,63,63);

			 if (font.equals("")) font = "SansSerif";

                Font fnt=new Font(font,1,17);
                //Calculamos las dimensiones que tendrá la imagen a partir del texto
                FontRenderContext frc = new FontRenderContext(null, false, false);                
                width = (int) fnt.getStringBounds(text, frc).getWidth()+30;
                height = (int)fnt.getStringBounds(text, frc).getHeight()+10;
                
			 BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			 
                Graphics g = cpimg.createGraphics();

			 g.setColor(background);
                g.setFont(fnt);
			 g.fillRect(0,0,width,height);
			 g.setColor(fbl);			 
			 g.drawString(text,15,20);
			 g.setColor(background);

			//Dibujaria unas lineas, para distorsionar un poco la imagen
			 // g.drawLine(50,17,80,17);
			// g.drawLine(50,22,80,22);
			 
			 return cpimg;
			 
			
			 }catch (Exception ex){
				 ex.printStackTrace();
			 }
		return null;
	    }
	 
	 
	 /**
	  * Crea una imagen con un texto determinado y de un tama�o determinado y la imprime por pantalla
	  * @param req
	  * @param response
	  * @param text
	  * @param width
	  * @param height
	  * @return
	  */
	 public static void generateImageScreen(HttpServletRequest req,HttpServletResponse response, String text,int width,int height,String font){

		 try{
			 Color background = new Color(255,255,255);
			 Color fbl = new Color(63,63,63);
			 if (font.equals("")) font = "SansSerif";
			 Font fnt=new Font(font,1,17);
			 BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			 Graphics g = cpimg.createGraphics();
			 g.setColor(background);
			 g.fillRect(0,0,width,height);
			 g.setColor(fbl);
			 g.setFont(fnt);
			 g.drawString(text,15,20);
			 g.setColor(background);

				//Dibujaria unas lineas, para distorsionar un poco la imagen
			// g.drawLine(50,17,80,17);
			 //g.drawLine(50,22,80,22);
			 
			 response.setContentType("image/gif");
			 ServletOutputStream strm = response.getOutputStream();
			 ImageIO.write(cpimg,"gif",strm);
			 cpimg.flush();
			 strm.flush();
			 strm.close();
			 }catch (Exception ex){
				 ex.printStackTrace();
			 }
	    }
	 
	 
	 
	 
	 
}
