package com.espmail.utils.tags;

import com.espmail.utils.TextUtils;
import org.apache.struts.util.MessageResources;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.jstl.fmt.LocaleSupport;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;


public class FieldTag extends BodyTagSupport {

	private static final long serialVersionUID = 6828817145887367473L;
   private String bundle;
	private boolean required = false;
	private String value;
	private String key;
	private boolean valueIsKey = false;
    private boolean tienePermiso = false;
    private String rol="";

    public boolean isTienePermiso() {
        return tienePermiso;
    }

    public void setTienePermiso(boolean tienePermiso) {
        this.tienePermiso = tienePermiso;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isValueIsKey() {
		return this.valueIsKey;
	}

	public void setValueIsKey(boolean valueIsKey) {
		this.valueIsKey = valueIsKey;
	}

   public String getBundle() {
      return bundle;
   }

   public void setBundle(String bundle) {
      this.bundle = bundle;
   }

   public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
        if (tienePermiso){
            try {
                out.write("</div>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }

        return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
        boolean hayRol = !TextUtils.isEmpty(rol);
		tienePermiso = !hayRol;

		if (hayRol) {
			String rolArray[] = rol.split(",");
			int i = 0;
			while (!tienePermiso && i < rolArray.length) {
				tienePermiso = ((HttpServletRequest) this.pageContext
						.getRequest()).isUserInRole(rolArray[i]);
				i++;
			}
		}

        if(tienePermiso){
            try {
                out.write("<div class=\"field");

                if (this.required) {
                    out.write(" required");
                }

               System.out.println("mensaje ");
               System.out.println(LocaleSupport.getLocalizedMessage(this.pageContext,this.key));
                out.write("\"><label>");
                out.write( LocaleSupport.getLocalizedMessage(this.pageContext,this.key));
                out.write("</label>");

                if (this.value != null) {
                    out.write("<span>");

                    if (this.value == null || (this.value instanceof String
                            && ((String) this.value).trim().length() == 0)) {
                        out.write("&nbsp;");
                    } else if (this.valueIsKey) {
                        out.write(LocaleSupport.getLocalizedMessage(this.pageContext,
                                this.value.toString()));
                    } else {
                        out.write(this.value);
                    }

                    out.write("</span>");
                    return SKIP_BODY;
                }
            } catch (IOException e) {
                throw new JspException(e);
            }
            return EVAL_BODY_INCLUDE;
        }else
            return SKIP_BODY;


   }
}
