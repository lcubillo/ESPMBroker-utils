package com.espmail.modelo.maestras;


import com.espmail.utils.dao.DaoException;


public interface MaestrasDao {
	
	Pais[] damePaises();
	
	Pais[] damePaisesIdioma(String idioma);
	
	Categoria[] dameCategorias();
	
	Lista[] dameListas();
	
	Sector[] dameSectores();
	 
	Ocupacion[] dameOcupaciones();
	
	CategoriasRedes[] findCategoriasRedes();

}
