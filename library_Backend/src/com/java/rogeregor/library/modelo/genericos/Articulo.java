package com.java.rogeregor.library.modelo.genericos;

import com.java.rogeregor.library.util.EstadoPrestamo;

public abstract class Articulo {
	
	private EstadoPrestamo estado;
	private int stock;
	
	// Constructor por defecto que inicializa el estado a DEVUELTO
	public Articulo() {
		this(EstadoPrestamo.DEVUELTO, 0); // Llama al constructor que recibe estado a DEVUElTO y stock
	}
	
	// Constructor que recibe solo el estado
	public Articulo(EstadoPrestamo estado) {
		this(estado, 0); // Llama al constructor que recibe estado y stock
	}
	
	// Constructor que recibe solo el stock
	public Articulo(int stock) {
		this(EstadoPrestamo.DEVUELTO, stock); // Estado por defecto DEVUELTO
	}
	
	// Constructor que recibe estado y stock
	public Articulo(EstadoPrestamo estado, int stock) {
		this.estado = estado != null ? estado : EstadoPrestamo.DEVUELTO; // Asegura que el estado nunca sea null
		this.stock = stock;
	}

	// Getters y setters
	public EstadoPrestamo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPrestamo estado) {
		this.estado = estado;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}