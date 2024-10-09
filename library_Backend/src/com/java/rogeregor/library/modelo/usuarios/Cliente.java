
package com.java.rogeregor.library.modelo.usuarios;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente extends Persona{

	    private String dni;
	    private int prestamosRealizados = 0;
	    private LocalDate fechaFinPenalizacion;
	    
	    public Cliente() {super(); }
	    
	    public Cliente(String nombre, String apellido, String dni) {
	        super(nombre, apellido);
	        this.dni = dni;
	    }
	    
	    public Cliente(String nombre, String apellido, String dni, int prestamosRealizados) {
	        super(nombre, apellido);
	        this.dni = dni;
	        this.prestamosRealizados = prestamosRealizados;
	    }
	    
		public String getDni() {
	        return dni;
	    }

	    public int getPrestamosRealizados() {
	        return prestamosRealizados;
	    }

	    public void setDni(String dni) {
	        this.dni = dni;
	    }

	    public void setPrestamosRealizados(int prestamosRealizados) {
			this.prestamosRealizados = prestamosRealizados;
		}
	    
	    public LocalDate getFechaFinPenalizacion() {
	        return fechaFinPenalizacion;
	    }

	    public void setFechaFinPenalizacion(LocalDate fechaFinPenalizacion) {
	        this.fechaFinPenalizacion = fechaFinPenalizacion;
	    }

		@Override
	    public String toString() {
	        return "Cliente [ id= " + getId() + 
	        		", nombre: " + getNombre() + 
	        		", apellido: " + getApellido() + 
	        		", dni = " + getDni() + 
	        		", prestamos Realizados #: " + getPrestamosRealizados() + 
	        		"]";
	    }

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cliente other = (Cliente) obj;
			return Objects.equals(dni, other.dni);
		}

	    @Override
	    public int hashCode() {
	        return dni != null ? dni.hashCode() : 0;
	    }

	}

