package com.java.rogeregor.library.modelo.genericos;

import com.java.rogeregor.library.modelo.usuarios.Persona;

//Clase genérica para Persona
public class GenericoPersona<U extends Persona> {

	private U tipo;

	// Constructor que recibe un tipo genérico que extiende de Persona
	public GenericoPersona(U tipo) {
		this.tipo = tipo;
	}

	// Método para mostrar la clase del tipo genérico
	public void mostrarClase() {
		if (tipo != null) {
			System.out.println("La clase genérica es: " + tipo.getClass().getName());
		} else {
			System.out.println("No se ha especificado una instancia de tipo.");
		}
	}

	// Método getter para obtener la instancia del tipo
	public U getTipo() {
		return tipo;
	}

	// Método setter para cambiar la instancia del tipo
	public void setTipo(U tipo) {
		this.tipo = tipo;
	}
}

