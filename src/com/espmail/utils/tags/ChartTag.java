package com.espmail.utils.tags;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;


public class ChartTag extends BodyTagSupport{

	/**
	 * @author Luis Cubillo
	 * 
	 *	Clase que genera una URL para el API de google que devolverá una imagen de una gráfica.
	 * 
	 *	Los parámetros title, titleColorSize, color, type, legend, grid, size son opcionales
	 *	y responden a la sintaxis creada por el API de google y expresada en la página:
	 *	http://code.google.com/apis/chart/
	 *
	 */

	private static final long serialVersionUID = -4383338107918338056L;
	private Collection data;
	private String columnLabel;
	private String columnValue;
	private String title;
	private String titleColorSize;
	private String color;
	private String type;
	private String legend;
	private String grid;
	private String size;
	private String colValScp = ",";

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the columnLabel
	 */
	public String getColumnLabel() {
		return columnLabel;
	}
	/**
	 * @param columnLabel the columnLabel to set
	 */
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	/**
	 * @return the columnValue
	 */
	public String getColumnValue() {
		return columnValue;
	}
	/**
	 * @param columnValue the columnValue to set
	 */
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the grid
	 */
	public String getGrid() {
		return grid;
	}
	/**
	 * @param grid the grid to set
	 */
	public void setGrid(String grid) {
		this.grid = grid;
	}
	/**
	 * @return the legend
	 */
	public String getLegend() {
		return legend;
	}
	/**
	 * @param legend the legend to set
	 */
	public void setLegend(String legend) {
		this.legend = legend;
	}
	/**
	 * @return the titleColorSize
	 */
	public String getTitleColorSize() {
		return titleColorSize;
	}
	/**
	 * @param titleColorSize the titleColorSize to set
	 */
	public void setTitleColorSize(String titleColorSize) {
		this.titleColorSize = titleColorSize;
	}	
	public int doEndTag() throws JspException {
		
		JspWriter out = this.pageContext.getOut();
		
		try {
			out.write("/>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return EVAL_PAGE;
	}
	
	private String getDataStringPie(){
		
		Iterator iterator = this.data.iterator();			
		Map item;
		String cadLabel="",cadValue="";
		
		
		while(iterator.hasNext()) {
			item = (Map)iterator.next();
			cadLabel += "|"+item.get(columnLabel);
			cadValue += ","+item.get(columnValue); 
		}
		return "&chd=t:" + cadValue.substring(1) + "&chl=" + cadLabel.substring(1);
	}
	
	private String getDataStringLxy() throws Exception{
		

		Iterator iterator = this.data.iterator();			
		Map item;
		int dataSize = this.data.size();
		String columnValueArray[] = this.columnValue.split(",");
		String cadLabel="", cadValue = "", cadValueArray[] = new String[columnValueArray.length];
		String strXLabel = "";
		int i=0;
		
		try{
		for (int j = 0; j < cadValueArray.length ; j++){
			cadValueArray[j] = "";
			System.out.println("Estoy inicializando cadValueArray->" + Integer.toString(j)+cadValueArray[j]);
		}
				
		while(iterator.hasNext()) {
			item = (Map)iterator.next();
			cadLabel += "|"+item.get(columnLabel);
			for (int j=0;j<columnValueArray.length;j++){
				cadValueArray[j] += ","+item.get(columnValueArray[j]);
				System.out.println("El valor que hay en columnValueArray->"+ columnValueArray[j]);
				System.out.println("cadValueArray dentro del bucle->" + cadValueArray[j]);
			}
			System.out.println("Las etiquetas X!!");			
			strXLabel += "," + Float.toString((float) (1.0*i*(100.0/(dataSize-1))));
			System.out.println("Las etiquetas X->"+ strXLabel);
			i++;
		}
		
		for (int j=0;j<cadValueArray.length;j++){
			cadValue += "|" + strXLabel.substring(1)+ "|" + cadValueArray[j].substring(1);
			System.out.println("Esto hay en cadValue"+cadValue);
			System.out.println("|" + strXLabel.substring(1)+ "|" + cadValueArray[j].substring(1) + "|");			
		}
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
		return "&chd=t:" + cadValue.substring(1) + "&chl=" + cadLabel.substring(1);

	}
	
	private String getDataLineFormat(String column, String exp){
		
		Iterator iterator = this.data.iterator();			
		Map item;
		String strValue = "";		
		while(iterator.hasNext()) {
			item = (Map)iterator.next();
			strValue += exp +item.get(column);
		}
		return strValue.substring(1);
	}
	
	private String getLavelLineFormat(String column, String exp, int max){
		
		Object[] arrayMap = this.data.toArray();			
		Map item = null;
		String strValue = "";
		float fl = (float) (((float)data.size())/((float)max));
		if (fl<1){
			fl=1;
		}
		for(int i=0;i<data.size();i++) {
			if ((i)%Math.round(fl)==0){
				item = (Map)arrayMap[i];
				strValue += exp +item.get(column);
			}
		}
		return strValue.substring(1);
	}
	
	private String getDataStringLc() throws Exception {

		String columnValueArray[] = this.columnValue.split(colValScp);
		String cadValue = "";
		for (int j=0;j<columnValueArray.length;j++){
			cadValue += "|" + getDataLineFormat(columnValueArray[j],",");
		}
		return "&chd=t:" + cadValue.substring(1) + "&chl=" + getLavelLineFormat(this.columnLabel,"|",10);
	}
	
	private String getDataString() throws Exception{
		
		if (this.type.toLowerCase().equals("p3")){
			return this.getDataStringPie();
		}else if(this.type.toLowerCase().equals("lxy")){
			return this.getDataStringLxy();
		}else if(this.type.toLowerCase().equals("lc")){
			return this.getDataStringLc();
		}
		return "";
	}

	public int doStartTag() throws JspException {
		
		JspWriter out = this.pageContext.getOut();
		
		try {
			out.write("<img src=\"http://chart.apis.google.com/chart?cht="+type);
			out.write(this.getDataString());
			
			if (title!=null){
				out.write("&chtt="+title);
				if (titleColorSize!=null){
					out.write("&chtl="+titleColorSize);
				}				
			}			
			if (legend!=null){
				out.write("&chtl="+legend);
			}
			if (color!=null){
				out.write("&chtc="+color);
			}
			if(grid!=null){
				out.write("&chg="+grid);
			}
			if(size!=null){
				out.write("&chs="+size+"\"");
			}else{
				out.write("&chs=800x300"+"\"");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}
	/**
	 * @return the data
	 */
	public Collection getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Collection data) {
		this.data = data;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the colValScp
	 */
	public String getColValScp() {
		return colValScp;
	}
	/**
	 * @param colValScp the colValScp to set
	 */
	public void setColValScp(String colValScp) {
		this.colValScp = colValScp;
	}

}