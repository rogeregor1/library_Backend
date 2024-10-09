package com.java.rogeregor.library.articuloDAO;

import com.java.rogeregor.library.modelo.articulos.Ejemplar;

public interface ArticuloDAO<T extends Ejemplar> {
	
	T createArticulo(Class<T> clazz, int cod);
	T updateArticulo(Class<T> clazz, int cod);
	boolean deleteArticulo(Class<T> clazz, int cod);
	void viewArticulo(Class<T> clazz, int cod);
	T seleccionarArticulo(Class<T> clazz, int cod);
}

