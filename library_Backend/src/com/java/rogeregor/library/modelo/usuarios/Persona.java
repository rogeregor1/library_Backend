package com.java.rogeregor.library.modelo.usuarios;

import java.time.LocalDate;
import java.util.Objects;

import com.java.rogeregor.library.util.Valor;


public class Persona {

		   	private String nombre;
		    private String apellido;
		    private LocalDate fn;
		    private int id = Valor.getId();
		    
		    public Persona() {
		    }

		    public Persona(String nombre, String apellido) {
		        this.nombre = nombre;
		        this.apellido = apellido;
		    }

		    public Persona(String nombre, String apellido, LocalDate fn) {
				this.nombre = nombre;
				this.apellido = apellido;
				this.fn = fn;
			}

			public int getId() {
		        return id;
		    }

		    public void setId(int id) {
				this.id = id;
			}

			public String getNombre() {
		        return nombre;
		    }

		    public void setNombre(String nombre) {
		        this.nombre = nombre;
		    }

		    public String getApellido() {
		        return apellido;
		    }

			public void setApellido(String apellido) {
		        this.apellido = apellido;
		    }

		    public LocalDate getFn() {
		        return fn;
		    }
		    
			public void setFn(LocalDate fn) {
				this.fn = fn;
			}

			@Override
			public String toString() {
				return "Persona [ id= " + getId() + 
						", nombre=" + getNombre() + 
						", apellido=" + getApellido()+
				        ", fn=" + (getFn() != null ? getFn() : "No disponible") + 
				        "]";
			}

			@Override
			public int hashCode() {
				return Objects.hash(apellido, fn, id);
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Persona other = (Persona) obj;
				return Objects.equals(apellido, other.apellido) && Objects.equals(fn, other.fn) && id == other.id;
			}



}
