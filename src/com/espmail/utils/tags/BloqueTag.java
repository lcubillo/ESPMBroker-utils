package com.espmail.utils.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.jstl.fmt.LocaleSupport;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class BloqueTag extends BodyTagSupport{

	private static final long serialVersionUID = -5690452465985082716L;

	private String key;
	private String id;
	private String styleClass;
	
	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		try {
			out.write("</div>");
		} catch (IOException e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		try {
			out.write("<div class=\"Bloque\"");
			if(this.getId()!=null)
			{
					out.write(" id=\""+this.getId()+"\"");
			}
			if(this.getStyleClass()!=null)
			{
				out.write(" style=\""+this.getStyleClass()+"\"");
			}
			out.write(">");
			if (this.getKey()!=null) {
				out.write("<div class=\"Titulo\">"+LocaleSupport.getLocalizedMessage(this.pageContext, this.key)+"</div>");
			}

			
		} catch (IOException e) {
			throw new JspException(e);
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
