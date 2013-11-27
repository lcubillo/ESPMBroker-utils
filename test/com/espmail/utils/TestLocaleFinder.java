package com.espmail.utils;

import com.espmail.utils.LocaleFinder;
import java.util.Locale;
import junit.framework.TestCase;

public class TestLocaleFinder extends TestCase {
	public void testLocaleFinder() {

		Locale local = new Locale("es", "ES");
		assertEquals(local, LocaleFinder.find("www.aspmail.com"));
		
		local = new Locale("es", "MX");
		assertEquals(local, LocaleFinder.find("www.aspmail.mx"));
		
		local = new Locale("es", "AR");
		assertEquals(local, LocaleFinder.find("www.aspmail.ar"));
		
		local = new Locale("es", "CL");
		assertEquals(local, LocaleFinder.find("www.aspmail.cl"));
		
		local = new Locale("pt", "PT");
		assertEquals(local, LocaleFinder.find("www.aspmail.pt"));
		
		assertEquals(null, LocaleFinder.find("www.aspmail.bb"));
	}

}
