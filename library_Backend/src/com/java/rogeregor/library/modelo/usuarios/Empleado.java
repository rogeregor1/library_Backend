package com.java.rogeregor.library.modelo.usuarios;

import java.util.Objects;

public class Empleado extends Persona{
	
	private String puesto;
	private double sueldo;
	
	public Empleado() {
		super();
	}
	public Empleado(String nombre, String apellido, String puesto) {
		super(nombre, apellido);
		this.puesto = puesto;
	}
	
	public Empleado(String nombre, String apellido, String puesto, double sueldo) {
		super(nombre, apellido);
		this.puesto = puesto;
		this.sueldo = sueldo;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public double getSueldo() {
		return sueldo;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}

	
	@Override
	public String toString() {
		return "Empleado [ Id= " + getId() +  
				", Nombre= " + getNombre() +  
				", Apellido= " + getApellido() +
				", Puesto=" + getPuesto() + 
				", Sueldo=" + getSueldo() + 
				"]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(puesto);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(puesto, other.puesto);
	}


}
