package com.espmail.utils.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.fmt.LocaleSupport;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.espmail.utils.TextUtils;

public class TabItemTag extends BodyTagSupport {

	private static final long serialVersionUID = 3044494369085282597L;

	private String key;

	private String url;

	private String rol;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		boolean hayRol = !TextUtils.isEmpty(rol);
		boolean tienePermiso = !hayRol;
		
		if (hayRol) {
			String rolArray[] = rol.split(",");
			int i = 0;
			while (!tienePermiso && i < rolArray.length) {
				tienePermiso = ((HttpServletRequest) this.pageContext
						.getRequest()).isUserInRole(rolArray[i]);
				i++;
			}
		}
		
		if (tienePermiso) {
			try {
				out.write("<li>\n");
				out.write("<a ");
				
				if (this.url != null) {
					out.write(" href=\"");
					
					if (url.charAt(0) == '/') {
						out.write(((HttpServletRequest) this.pageContext
								.getRequest()).getContextPath());
					}
					
					out.write(url);
					out.write("\"");
	
					String path = (String) this.pageContext.getRequest().getAttribute("path");
					
					if (this.url.indexOf(path) == 0) {
						out.write(" class=\"selected\"");
					}
				}

				out.write(">");
				out.write(LocaleSupport.getLocalizedMessage(this.pageContext,
						this.key));
				out.write("</a>\n");
			} catch (IOException e) {
				throw new JspException(e);
			}
		}

		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
}
