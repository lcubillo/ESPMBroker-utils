package com.espmail.utils.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espmail.utils.TextUtils;
import com.espmail.utils.users.Usuario;

/**
 * 
 * @author Luis Cubillo
 */

public class Seguridad extends BodyTagSupport {

	/**
	 * Creates new instance of tag handler
	 */

	private String key;
	private String password;
	private String rol;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Seguridad() {
		super();
	}

	// //////////////////////////////////////////////////////////////
	// / ///
	// / User methods. ///
	// / ///
	// / Modify these methods to customize your tag handler. ///
	// / ///
	// //////////////////////////////////////////////////////////////

	/**
	 * Method called from doStartTag(). Fill in this method to perform other
	 * operations from doStartTag().
	 * 
	 */
	private void otherDoStartTagOperations() {
		//
		// TODO: code that performs other operations in doStartTag
		// should be placed here.
		// It will be called after initializing variables,
		// finding the parent, setting IDREFs, etc, and
		// before calling theBodyShouldBeEvaluated().
		//
		// For example, to print something out to the JSP, use the following:
		//
		// try {
		// JspWriter out = pageContext.getOut();
		// out.println("something");
		// } catch (IOException ex) {
		// // do something
		// }
		//
		//
	}

	/**
	 * Method called from doEndTag() Fill in this method to perform other
	 * operations from doEndTag().
	 * 
	 */
	private void otherDoEndTagOperations() {
		//
		// TODO: code that performs other operations in doEndTag
		// should be placed here.
		// It will be called after initializing variables,
		// finding the parent, setting IDREFs, etc, and
		// before calling shouldEvaluateRestOfPageAfterEndTag().
		//
	}

	/**
	 * Fill in this method to process the body content of the tag. You only need
	 * to do this if the tag's BodyContent property is set to "JSP" or
	 * "tagdependent." If the tag's bodyContent is set to "empty," then this
	 * method will not be called.
	 */
	private void writeTagBodyContent(JspWriter out, BodyContent bodyContent)
			throws IOException {
		//
		// TODO: insert code to write html before writing the body content.
		// e.g.:
		//
		// out.println("<strong>" + attribute_1 + "</strong>");
		// out.println("   <blockquote>");

		//
		// write the body content (after processing by the JSP engine) on the
		// output Writer
		//
		bodyContent.writeOut(out);

		//
		// Or else get the body content as a string and process it, e.g.:
		// String bodyStr = bodyContent.getString();
		// String result = yourProcessingMethod(bodyStr);
		// out.println(result);
		//

		// TODO: insert code to write html after writing the body content.
		// e.g.:
		//
		// out.println("   </blockquote>");

		// clear the body content for the next time through.
		bodyContent.clearBody();
	}

	// //////////////////////////////////////////////////////////////
	// / ///
	// / Tag Handler interface methods. ///
	// / ///
	// / Do not modify these methods; instead, modify the ///
	// / methods that they call. ///
	// / ///
	// //////////////////////////////////////////////////////////////

	/**
	 * This method is called when the JSP engine encounters the start tag, after
	 * the attributes are processed. Scripting variables (if any) have their
	 * values set here.
	 * 
	 * @return EVAL_BODY_BUFFERED if the JSP engine should evaluate the tag
	 *         body, otherwise return SKIP_BODY. This method is automatically
	 *         generated. Do not modify this method. Instead, modify the methods
	 *         that this method calls.
	 */

	public int doStartTag() throws JspException, JspException {
		otherDoStartTagOperations();

		if (theBodyShouldBeEvaluated()) {
			return EVAL_BODY_BUFFERED;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * This method is called after the JSP engine finished processing the tag.
	 * 
	 * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP
	 *         page, otherwise return SKIP_PAGE. This method is automatically
	 *         generated. Do not modify this method. Instead, modify the methods
	 *         that this method calls.
	 */
	public int doEndTag() throws JspException, JspException {
		otherDoEndTagOperations();

		if (shouldEvaluateRestOfPageAfterEndTag()) {
			return EVAL_PAGE;
		} else {
			return SKIP_PAGE;
		}
	}

	/**
	 * This method is called after the JSP engine processes the body content of
	 * the tag.
	 * 
	 * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body
	 *         again, otherwise return SKIP_BODY. This method is automatically
	 *         generated. Do not modify this method. Instead, modify the methods
	 *         that this method calls.
	 */
	public int doAfterBody() throws JspException {
		try {
			//
			// This code is generated for tags whose bodyContent is "JSP"
			//
			BodyContent bodyContent = getBodyContent();
			JspWriter out = bodyContent.getEnclosingWriter();

			writeTagBodyContent(out, bodyContent);
		} catch (Exception ex) {
			handleBodyContentException(ex);
		}

		if (theBodyShouldBeEvaluatedAgain()) {
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * Handles exception from processing the body content.
	 */
	private void handleBodyContentException(Exception ex) throws JspException {
		// Since the doAfterBody method is guarded, place exception handing code
		// here.
		throw new JspException("error in NewTag: " + ex);
	}

	/**
	 * Fill in this method to determine if the rest of the JSP page should be
	 * generated after this tag is finished. Called from doEndTag().
	 */
	private boolean shouldEvaluateRestOfPageAfterEndTag() {
		//
		// TODO: code that determines whether the rest of the page
		// should be evaluated after the tag is processed
		// should be placed here.
		// Called from the doEndTag() method.
		//
		return true;
	}

	/**
	 * Fill in this method to determine if the tag body should be evaluated
	 * again after evaluating the body. Use this method to create an iterating
	 * tag. Called from doAfterBody().
	 */
	private boolean theBodyShouldBeEvaluatedAgain() {
		//
		// TODO: code that determines whether the tag body should be
		// evaluated again after processing the tag
		// should be placed here.
		// You can use this method to create iterating tags.
		// Called from the doAfterBody() method.
		//
		return false;
	}

	private boolean theBodyShouldBeEvaluated() {
		HttpSession sesion = ((HttpServletRequest) this.pageContext
				.getRequest()).getSession();
		// Obtengo el usuario que esta en sesion
		boolean hayUsuario = true;
		boolean hayRol = true;
		Usuario usuario = null;

		try {
			try {
				usuario = (Usuario) sesion.getAttribute("usuario");
			} catch (Exception e) {
				usuario = new Usuario();
				usuario.setRol("");
				usuario.setUser("");
			}
			if (usuario.getUser().equals(""))
				hayUsuario = false;
			if (usuario.getRol().equals(""))
				hayRol = false;
		} catch (Exception ex) {
			hayUsuario = false;
			hayRol = false;
			ex.printStackTrace();
		}
		try {
			if (hayUsuario && hayRol) {

				String rolArray[] = this.rol.split(",");
				int i = 0;
				while (i < rolArray.length) {

					if (usuario.getRol().equals(rolArray[i].toString())) {
						// Hay usuario y Rol
						return true;
					}
					i++;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

}
