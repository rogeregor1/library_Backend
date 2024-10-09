package com.java.rogeregor.library.modelo.articulos;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.java.rogeregor.library.modelo.usuarios.Persona;
import com.java.rogeregor.library.util.EstadoPrestamo;
import com.java.rogeregor.library.modelo.usuarios.Autor;

public class Revista extends Ejemplar{

    private String nombre;
    private int numeroEdicion;      // Número de la edición de la revista
    private Month mesPublicacion;   // Mes de publicación
    private List<? extends Persona> autoresRevista;

   
    public Revista(int yearPublicacion, EstadoPrestamo estado, int stock, String nombre, int numeroEdicion) {
    	super(yearPublicacion, estado, stock);
		this.nombre = nombre;
		this.numeroEdicion = numeroEdicion;
	}

	// Constructor con todos los parámetros sin autores
    public Revista(int stock, int yearPublicacion, String nombre, int numeroEdicion, Month mesPublicacion) {
        super(stock, yearPublicacion);
        this.nombre = nombre;
        this.numeroEdicion = numeroEdicion;
        this.mesPublicacion = mesPublicacion;
    }

    // Constructor con autores
    public Revista(int stock, int yearPublicacion, String nombre, int numeroEdicion, Month mesPublicacion, ArrayList<Autor> autoresRevista) {
        super(stock, yearPublicacion);
        this.nombre = nombre;
        this.numeroEdicion = numeroEdicion;
        this.mesPublicacion = mesPublicacion;
        this.autoresRevista = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public Month getMesPublicacion() {
        return mesPublicacion;
    }

    public void setMesPublicacion(Month mesPublicacion) {
        this.mesPublicacion = mesPublicacion;
    }

	public List<? extends Persona> getAutoresRevista() {
		return autoresRevista;
	}

	public void setAutoresRevista(List<? extends Persona> autoresRevista) {
		this.autoresRevista = autoresRevista;
	}

	// Método toString para mostrar la información de la revista
    @Override
    public String toString() {
        return "Revista [Cod=" + getCod() +
                ", Nombre=" + getNombre() +
                ", Número Edición=" + getNumeroEdicion() +
                ", Mes Publicación=" + getMesPublicacion() +
                ", Autores=" + getAutoresRevista() +
        		", Stock=" + getStock() +
                ", YearPublicación=" + getYearPublicacion() +
                ", Estado=" + getEstado() +
                "]";
    }

	@Override
	public int hashCode() {
		return Objects.hash(numeroEdicion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Revista other = (Revista) obj;
		return numeroEdicion == other.numeroEdicion;
	}


}