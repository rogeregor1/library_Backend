package com.java.rogeregor.library.modelo.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.java.rogeregor.library.modelo.articulos.Ejemplar;

public class Autor extends Persona {
	
	    private List<? extends Ejemplar> librosDeAutor;
	    private String paisNacimiento;
	    
	    public Autor() {
	    }

		public Autor(String nombre, String apellido) {
	        super(nombre, apellido);
	    }
	    
	    public Autor(String nombre, String apellido, String paisNacimiento) {
			super(nombre, apellido);
			this.paisNacimiento = paisNacimiento;
		}

		public Autor(String nombre, String apellido, String paisNacimiento, List<? extends Ejemplar> librosDeAutor) {
			super(nombre, apellido);
			this.librosDeAutor = new ArrayList<>();
			this.paisNacimiento = paisNacimiento;
		}

		public String getPaisNacimiento() {
			return paisNacimiento;
		}

		public void setPaisNacimiento(String paisNacimiento) {
			this.paisNacimiento = paisNacimiento;
		}
	   
		public List<? extends Ejemplar> getLibrosDeAutor() {
			return librosDeAutor;
		}

		public void setLibrosDeAutor(List<? extends Ejemplar> librosDeAutor) {
			this.librosDeAutor = librosDeAutor;
		}

		@Override
		public String toString() {
			return "Autor [Id=" + getId() + 
					", Nombre=" + getNombre() + 
					", Apellido=" + getApellido() + 
					", PaisNacimiento=" + getPaisNacimiento() + 
					", LibrosDeAutor=" + getLibrosDeAutor() + 
					"]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(paisNacimiento);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Autor other = (Autor) obj;
			return Objects.equals(paisNacimiento, other.paisNacimiento);
		}

}

