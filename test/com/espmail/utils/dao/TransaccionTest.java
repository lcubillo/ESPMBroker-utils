package com.espmail.utils.dao;

import com.espmail.utils.dao.DaoException;
import com.espmail.utils.dao.FactoriaDao;
import com.espmail.utils.dao.Transaccion;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.espmail.utils.contexto.Contexto;
import com.espmail.utils.contexto.ContextoPrueba;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test que prueba el correcto funcionamiento de la clase Transaccion.
 * 
 * @author Luis Cubillo
 * 
 */
public class TransaccionTest extends TestCase {
	/**
	 * en el setUp() se instancia un contexto que utilizaran los demás métodos.
	 */
	protected void setUp() {
		Contexto.TYPE = ContextoPrueba.class;
	}

	/**
	 * éste método hace que falle intencionadamente una transacción insertando
	 * dos veces la misma tupla en la base de datos, de éste modo se produce un
	 * error que será traducido en un rollback. La comprobación del Assert será
	 * que ésta tupla insertada no éste en la base de datos ya que de ser así
	 * estaría infringiedo la transaccionalidad.
	 * 
	 * @throws DaoException
	 */
	public void testTransaccion() throws DaoException {

		Transaccion transaccion = new Transaccion();
		SuscriptorDao dao = (SuscriptorDao) FactoriaDao.getInstance().getDao(
				SuscriptorDao.class);
		Integer num = new Integer(6994032);

		try {
			Suscriptor suscriptor = new Suscriptor(num, "Prueba", "1234",
					"prueba2@prueba.com", new Date(13123113), "lista", "ESP");
			dao.insert(suscriptor);
			dao.insert(suscriptor);
		} catch (DaoException e) {
			transaccion.rollback();
		} finally {
			transaccion.close();
		}

		Assert.assertNull(dao.findByCodigo(num));
	}

	/**
	 * Éste método hace una llamadas a los métodos executeQuery y executeUpdate
	 * de la clase FactoriaDao dentro de una misma transacción. Al ejecutar la
	 * segunda sentencia update se ha sustituido el valor de la columna
	 * "e_subject" por "e_subje", así se producirá una excepción en la
	 * transacción haciendo un rollback. La sentencia assert comprueba que el
	 * valor de la tupla sea el mismo que el existente antes de la transacción.
	 * 
	 * @throws DaoException
	 */
	public void testTransaccion02() throws DaoException {

		FactoriaDao dao = FactoriaDao.getInstance();
		List lista = dao
				.executeQuery("select * from registros_automaticos where ped_cod = 722");
		String eSub1 = (String) ((Map) lista.get(0)).get("e_subject");
		Transaccion transaccion = new Transaccion();

		try {
			dao
					.executeUpdate("update registros_automaticos set e_subject = 'prueba 1 para TransaccionTest.java' where ped_cod = 722");
			dao
					.executeUpdate("update registros_automaticos set e_subje = 'prueba 2 para TransaccionTest.java' where ped_cod = 722");
		} catch (DaoException e) {
			transaccion.rollback();
		} finally {
			transaccion.close();
		}
		lista = dao
				.executeQuery("select * from registros_automaticos where ped_cod = 722");
		String eSub2 = (String) ((Map) lista.get(0)).get("e_subject");
		Assert.assertEquals(eSub1, eSub2);

	}

	public static void main(String args[]) {
		junit.textui.TestRunner.run(TransaccionTest.class);
	}
}
