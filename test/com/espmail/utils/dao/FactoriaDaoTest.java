package com.espmail.utils.dao;

import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;
import java.util.List;
import java.util.Map;

import com.espmail.utils.contexto.Contexto;
import com.espmail.utils.contexto.ContextoPrueba;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test que prueba el correcto funcionamiento de los métodos executeUpdate y
 * executeQuery de la clase FactoriaDao.
 * 
 * @author Luis Cubillo
 */
public class FactoriaDaoTest extends TestCase {

	protected void setUp() {
		Contexto.TYPE = ContextoPrueba.class;
	}

	public void testFactoriaDaoTest() throws DaoException {

		FactoriaDao dao = FactoriaDao.getInstance();
		dao
				.executeUpdate("insert into registros_automaticos (ped_cod,e_subject) values (-1,'prueba')");
		List lista = dao
				.executeQuery("select * from registros_automaticos where ped_cod = -1");
		String eSub = (String) ((Map) lista.get(0)).get("E_SUBJECT");
		Assert.assertEquals(eSub, "prueba");
		dao
				.executeUpdate("update registros_automaticos set e_subject = 'prueba para FactoriaDaoTest.java' where ped_cod = -1");
		lista = dao
				.executeQuery("select * from registros_automaticos where ped_cod = -1");
		eSub = (String) ((Map) lista.get(0)).get("E_SUBJECT");
		Assert.assertEquals(eSub, "prueba para FactoriaDaoTest.java");
		dao
				.executeUpdate("delete from registros_automaticos where ped_cod =-1");

	}

}
