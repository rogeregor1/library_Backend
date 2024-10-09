package com.java.rogeregor.library.modelo.articulos;

import java.util.Objects;

import com.java.rogeregor.library.modelo.genericos.Articulo;
import com.java.rogeregor.library.util.EstadoPrestamo;
import com.java.rogeregor.library.util.Valor;

public class Ejemplar extends Articulo {

    private int cod = Valor.getCod();
    int yearPublicacion;
    boolean deuda = false;
   
//para todos constructor
	public Ejemplar(int yearPublicacion, EstadoPrestamo estado, int stock) {
		super(estado, stock);
		this.yearPublicacion = yearPublicacion;
	}
	//revista y discoCompacto: constructor todos los parametros sin autores, sin estado
		public Ejemplar(int stock, int yearPublicacion) {
			super(stock);
			this.yearPublicacion = yearPublicacion;
		}
		

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public int getYearPublicacion() {
		return yearPublicacion;
	}

	public void setYearPublicacion(int yearPublicacion) {
		this.yearPublicacion = yearPublicacion;
	}

	public boolean isDeuda() {
		return deuda;
	}

	public void setDeuda(boolean deuda) {
		this.deuda = deuda;
	}

	
	@Override
	public String toString() {
		return "Ejemplar [Cod=" + getCod() + 
				", YearPublicacion=" + getYearPublicacion() + 
				", Estado=" + getEstado() + 
				", Stock=" + getStock() + 
				"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejemplar other = (Ejemplar) obj;
		return cod == other.cod;
	}
    
    
}
