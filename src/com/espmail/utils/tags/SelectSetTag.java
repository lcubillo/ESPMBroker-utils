package com.espmail.utils.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.beanutils.BeanUtils;
import com.espmail.utils.set.SetUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;


public class SelectSetTag extends BodyTagSupport{
	
	private static final long serialVersionUID = 2991442354331678012L;
	//property del tag
	private String property;
	//indica el numero de filas que se veran
	private Integer size;
	//para indicar si el select va a estar deshabilitado
	private boolean disabled;
	//para indicar si podrá tener varios option seleccionados
	private boolean multiple;
	//por si tiene un valor de seleccione o algo así
	private String emptyKey;
	//para indicar la clase de la que se quiere mostrar los datos
	private String set;
	//para mostrar un valor de los properties
	private String labelKey;
	//para mostrar un valor de una clase
	private String labelProp;
	//para el styleId
	private String id;
	//para el onclick de javascript
	private String onclick;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getEmptyKey() {
		return emptyKey;
	}
	public void setEmptyKey(String emptyKey) {
		this.emptyKey = emptyKey;
	}
	public String getLabelKey() {
		return labelKey;
	}
	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}
	public String getLabelProp() {
		return labelProp;
	}
	public void setLabelProp(String labelProp) {
		this.labelProp = labelProp;
	}
	public boolean getMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSet() {
		return set;
	}
	public void setSet(String set) {
		this.set = set;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		try {
			out.write("</select>");
		} catch (IOException e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}
	public int doStartTag() throws JspException {
		
		JspWriter out = this.pageContext.getOut();
		try {
			out.write("<select ");
			if(property!=null)
				out.write("name=\""+this.getProperty()+"\" ");
			if(onclick!=null)
				out.write("onclick=\""+this.getOnclick()+"\" ");
			if(size!=null)
				out.write("size="+this.getSize()+"\" ");
			if(disabled)
				out.write("disabled=\"true\" ");
			if(multiple)
				out.write("multiple=\"true\" ");
			if(id!=null)
				out.write("id=\""+this.getId()+"\"");
			out.write(">");
			String name=Constants.BEAN_KEY;
			
            Object bean = TagUtils.getInstance().lookup(pageContext, name, null);
            String[] match;
            if (bean == null) {
                JspException e =
                    new JspException("el formulario es nulo "+ name);

                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }

            try {
                match = BeanUtils.getArrayProperty(bean, property);
                if (match == null) {
                    match = new String[0];
                }
            } catch (IllegalAccessException e) {
                TagUtils.getInstance().saveException(pageContext, e);
                throw new JspException("error en el getter.access"+property+" "+ name);
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();

                TagUtils.getInstance().saveException(pageContext, t);
                throw new JspException("error en el getter.result "+property+t.toString());
            } catch (NoSuchMethodException e) {
                TagUtils.getInstance().saveException(pageContext, e);
                throw new JspException("error en el getter.method "+property+ name);
            }

			if(emptyKey!=null)
			{
				out.write("<option value=\"\">"+LocaleSupport.getLocalizedMessage(this.pageContext, this.emptyKey)+"</option>");
				
			}
			Iterator itr;
			try {
				itr = SetUtils.values(Class.forName(this.set)).iterator();
			} catch (ClassNotFoundException e) {		
				throw new JspException("error en la asignacion de la clase."+property);
			}
			if(labelKey!=null)
			{
				while(itr.hasNext())
				{
					out.write("<option value=\"");
					String valor= itr.next().toString();
					out.write(valor+"\"");
					if(isSelected(valor,match))
						out.write(" selected=\"selected\"");
					out.write(">"+LocaleSupport.getLocalizedMessage(this.pageContext, labelKey+valor));
					out.write("</option>\n");
				}
			}
			else if(labelProp!=null)
			{
				while(itr.hasNext())
				{
					out.write("<option value=\"");
					Object instancia=itr.next();
					out.write(instancia+"\"");
					if(isSelected(instancia.toString(),match))
						out.write(" selected=\"selected\"");
					out.write(">"+BeanUtils.getProperty(instancia, labelProp));
					out.write("</option>\n");
				}
			}else
			{
				while(itr.hasNext())
				{
					out.write("<option value=\"");
					Object instancia=itr.next();
					out.write("\"");
					if(isSelected(instancia.toString(),match))
						out.write(" selected=\"selected\"");
					out.write(">"+instancia);
					out.write("</option>\n");
				}
			}
			
		} catch (IOException e) {
			throw new JspException(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new JspException(e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new JspException(e);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	private boolean isSelected(String valor,String[] match)
	{
		int i=0;
		boolean encontrado=false;
		while(i<=match.length-1&&!encontrado)
		{
			if(match[i].equals(valor))
			{
				encontrado=true;
			}
			i++;
		}
		return encontrado;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
}
