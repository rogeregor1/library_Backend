package com.java.rogeregor.library.modelo.prestamos;

import java.time.LocalDate;

import com.java.rogeregor.library.modelo.articulos.Ejemplar;
import com.java.rogeregor.library.modelo.usuarios.Cliente;
import com.java.rogeregor.library.util.Valor;

public class TicketPrestamo extends Prestamo{

    private int tk = Valor.getTk();
    

	public TicketPrestamo( int tk, Cliente cliente, Ejemplar ejemplar, LocalDate fechaInicio, LocalDate fechaFin) {
		super(cliente, ejemplar, fechaInicio, fechaFin);
		this.tk = tk;
	}

	public int getTk() {
		return tk;
	}

	public void setTk(int tk) {
		this.tk = tk;
	}

	@Override
	public String toString() {
		return "[Ticket=" + getTk() + 
				", Dni Usuario=" + getCliente().getDni() + 
				", Ejemplar=" + getEjemplar().getCod() + 
				", FechaInicio=" + getFechaInicio() + 
				", FechaFin=" + getFechaFin() + 
				"]";
	}

}
