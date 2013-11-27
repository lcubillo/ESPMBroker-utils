package com.espmail.utils.dao;

import java.sql.Date;

import org.apache.commons.beanutils.ConvertUtils;

import com.espmail.modelo.maestras.MaestrasDao;
import com.espmail.modelo.maestras.Pais;
import com.espmail.utils.contexto.Contexto;
import com.espmail.utils.contexto.ContextoPrueba;
import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;
import com.espmail.utils.set.SetConverter;

import junit.framework.TestCase;
import junit.framework.Assert;

public class DaoTest extends TestCase {

	protected void setUp() {
		// Si hay que inicializar algo para hacer los tests
		SetConverter converter=new SetConverter();
		ConvertUtils.register(converter, Pais.class);
	}

	

	public void testDaoCorrecto() throws DaoException {
		SuscriptorDao dao;
		long time = System.currentTimeMillis();
			Contexto.TYPE = ContextoPrueba.class;
			dao = (SuscriptorDao) FactoriaDao.getInstance().getDao(SuscriptorDao.class);
			dao.findByCodigo(new Integer(20130506));
		System.out.println("---> " + (System.currentTimeMillis() - time));
	}

	public void testDaoMal2() {
		try {
			FactoriaDao.getInstance().getDao(SuscriptorDao2.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORINTERFACE);
		}
	}
	
	public void testDaoMal3() {
		try {
			FactoriaDao.getInstance().getDao(SuscriptorDao3.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORPARAMETROS);
		}
	}
	public void testDaoMal4() {
		try {
			FactoriaDao.getInstance().getDao(SuscriptorDao4.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORQUERYFILAS);
		}
	}
	public void testDaoMal5() {
		try {
			FactoriaDao.getInstance().getDao(
					SuscriptorDao5.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORNUMEROPARAMETROS);
		}
	}

	public void testDaoMal6() {
		try {
			FactoriaDao.getInstance().getDao(
					SuscriptorDao6.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORVALORQUERY);
		}
	}
	
	public void testDaoMal7() {
		
		try {
			FactoriaDao.getInstance().getDao(
					SuscriptorDao7.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORNUMEROPARAMETROS);
		}
	}
	public void testDaoMal8() {
		try {
			// dao = (SuscriptorDao)
			// FactoriaDao.getInstance().getDao(SuscriptorDao.class);
			FactoriaDao.getInstance().getDao(
					SuscriptorDao8.class);

			// Suscriptor suscriptor = dao.findById(new Integer(20130506));
			// Suscriptor suscriptor = dao.findByCodigo(new Integer(20130506));
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORQUERYSELECT);
		}
	}

	public void testDaoMal9() {
	try {
	FactoriaDao.getInstance().getDao(
					SuscriptorDao9.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORQUERYVACIA);
		}
	}
	
	public void testDaoMal10() {

		try {
			FactoriaDao.getInstance().getDao(
					SuscriptorDao10.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORPARAMETROSQUERY);
		}
	}

	public void testDaoMal11() {

		try {
			FactoriaDao.getInstance().getDao(SuscriptorDao11.class);
		} catch (DaoException e) {
			Assert.assertEquals(e.getCodigoError(), DaoException.ERRORXMLNOEXISTE);
		}
	}
	
	public void testCorrectoSelect() throws DaoException  {
		MiembroDao dao;
		Contexto.TYPE = ContextoPrueba.class;	
		dao = (MiembroDao) FactoriaDao.getInstance().getDao(MiembroDao.class);
		MiembroBean miembro = dao.codigos(new Integer(20130956));
		Assert.assertEquals(miembro.getCodigo(),new Integer(20130956) );
		int valor = dao.codigo(new Integer(20130956));
		Assert.assertEquals("Se esperaba que el valor fuera 20130956.",valor,20130956 );
		valor=dao.codigoSinParametros();
		Assert.assertEquals("Se esperaba que el valor fuera 20130956.",valor,20130956 );
		valor=dao.dameCodigoBean(miembro);
		Assert.assertEquals("Se esperaba que el valor fuera 20130956.",valor,20130956 );
		MiembroBean[] amiembro = dao.dameEmails("za5");
		Assert.assertEquals("Se esperaba que el email empezara por za5.",amiembro[0].getEMAIL().startsWith("za5"), true);
		String[] cadenas = dao.dameArraySimple("za5");
		Assert.assertEquals("Se esperaba que el email empezara por za5.",cadenas[0].startsWith("za5") ,true);
		//Asigno un array de fechas.
		Date[] fecha = dao.dameFecha("za5");
		//Asigno un array de integer
		Integer[] enteros = dao.dameCodigos("za5");
	}

}
