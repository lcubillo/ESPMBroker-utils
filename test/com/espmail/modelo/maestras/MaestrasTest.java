package com.espmail.modelo.maestras;

import com.espmail.modelo.maestras.Sector;
import com.espmail.modelo.maestras.Ocupacion;
import com.espmail.modelo.maestras.Categoria;
import com.espmail.modelo.maestras.Lista;
import com.espmail.modelo.maestras.Sexo;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

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

public class MaestrasTest extends TestCase {

	protected void setUp() {
		// Si hay que inicializar algo para hacer los tests
		SetConverter converter=new SetConverter();
		ConvertUtils.register(converter, Pais.class);
	}

	
	public void testPais(){
		MaestrasDao dao;
		
		Contexto.TYPE=ContextoPrueba.class;
		try {
			dao = (MaestrasDao) FactoriaDao.getInstance().getDao(MaestrasDao.class);
			Pais[] pais=dao.damePaises();
			
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	
	public void testCategoriasListas(){
		//MaestrasDao dao;
		
		Contexto.TYPE=ContextoPrueba.class;
		try {
			
			Collection col=Lista.values();
			Lista lista=(Lista)col.iterator().next();
			assertNotNull(col);
			assertEquals("Administrativo", lista.getNombre());
			Collection col2=Categoria.values();
			assertNotNull(col2);
			Categoria cat=(Categoria) col2.iterator().next();
			assertEquals("Artículos de Caballero",cat.getNombre() );
			Lista[] listas=cat.getListas();
			assertNotNull(listas);
			
			for(int i=0;i<listas.length;i++)
				if(listas[i].getCodigo().intValue()==124)
				{
					assertEquals("Calzado Masculino", listas[i].getNombre());
				}else
				{
					assertNotSame("Calzado Masculino", listas[i].getNombre());
				}
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void testSector()
	{
		try{
		Contexto.TYPE=ContextoPrueba.class;
		Collection col=Sector.values();
		assertNotNull(col);
		Sector sector=(Sector) col.iterator().next();
		assertEquals("Arquitectura/Ingeniería/Contrucción", sector.getNombre());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void testOcupacion()
	{
		try{
			Contexto.TYPE=ContextoPrueba.class;
			Collection col=Ocupacion.values();
			assertNotNull(col);
			Ocupacion ocupacion=(Ocupacion) col.iterator().next();
			assertEquals("Ama de Casa", ocupacion.getNombre());
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	public void testSexo()
	{
		try{
			Contexto.TYPE=ContextoPrueba.class;
			assertNotNull(Sexo.values());
			
		}catch(Exception e){
			e.printStackTrace();
		
		}
	}
}
