package com.espmail.utils.generadores.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * aspmail
 * User: Luis Cubillo
 */
public class GeneradorPdf {

   Document pdf;
   AcroFields form;

   String pdfName;
   PdfWriter writer = null;
   PdfStamper stamp=null;
   StringBuffer content;

   public GeneradorPdf (){
      this("document.pdf");
   }

   public GeneradorPdf(String pdfName){
      PdfReader reader = null;
      try {
         reader = new PdfReader(pdfName);
         setPdfName(pdfName);
         stamp = new PdfStamper(reader, new FileOutputStream("cheque.pdf"));
         form = stamp.getAcroFields();

      } catch (IOException e) {
         e.printStackTrace();
      } catch (DocumentException e) {
         e.printStackTrace();
      }
   }

   public void generate(){
      stamp.setFormFlattening(true);
      try {
         stamp.close();
      } catch (DocumentException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void insertLink(String text, String url, int x, int y) throws DocumentException, IOException {

   }

   public void setCampo(String name, String valor){
      try {
         form.setField(name,valor);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (DocumentException e) {
         e.printStackTrace();
      }
   }

   public void insertBackGroundImage(File image) throws IOException, DocumentException {
      if(image.exists())
         insertBackGroundImage(image.getName());
   }

   private void insertBackGroundImage(String uri) throws IOException, DocumentException {
      insertBackGroundImage(uri, 0, 0);
   }

   private void insertBackGroundImage(String uri, int x, int y) throws IOException, DocumentException {
      if(!pdf.isOpen())
         pdf.open();
      Image img = Image.getInstance(uri);
      PdfContentByte pcb = writer.getDirectContent();
      pcb.addImage(img,img.getWidth(),0,0,img.getHeight(), pdf.getPageSize().getLeft()+40, pdf.getPageSize().getTop()-40-img.getHeight()-y);
      pcb.saveState();
   }

   public void insertText(StringBuffer texto, int x, int y) throws IOException, DocumentException {
      if(!pdf.isOpen())
         pdf.open();
      PdfContentByte pcb = writer.getDirectContent();
      BaseFont helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
      pcb.beginText();

      pcb.setFontAndSize(helv,10);
      pcb.setTextMatrix(pdf.left()+x, pdf.getPageSize().getTop()-y);
      pcb.showText(texto.toString());
      pcb.endText();
      pcb.saveState();
   }

   public File getFile(){
      return new File("cheque.pdf");
   }

   public static void main(String [] argv){


      GeneradorPdf pdf = new GeneradorPdf("existing.pdf");
      pdf.setCampo("localizador","12345678");
      pdf.setCampo("nombre","Luis Miguel Cubillo Serantes");

      pdf.generate();
      File fil = pdf.getFile();

   }

   public StringBuffer getContent() {
      return content;
   }

   public void setContent(StringBuffer content) {
      this.content = content;
   }

   public String getPdfName() {
      return pdfName;
   }

   public void setPdfName(String pdfName) {
      this.pdfName = pdfName;
   }

   private void setPdf(Document pdf) {
      this.pdf = pdf;
   }

   private void setWriter(PdfWriter writer) {
      this.writer = writer;
   }
}
