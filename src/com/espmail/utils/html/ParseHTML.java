/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.espmail.utils.html;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;

/**
 *
 * @author Luis Cubillo
 */
public class ParseHTML {



    public static Document getDocument( BufferedReader br ) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        //todo sacamos del html todos los elementos de ese tipo
        SAXSource source = new SAXSource();
        source.setInputSource(new InputSource(br));

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        TransformerFactory.newInstance().newTransformer().transform(source,
				new DOMResult(doc));
       

        //InputSource is = new InputSource(url.openStream());
        //SAXSource source = new SAXSource(new Parser(), is);

        return doc;
    }

    public static NodeList getElements(String type,Document doc){            
        return doc.getElementsByTagName(type);

    }
}
