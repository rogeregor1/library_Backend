package com.java.rogeregor.library.modelo.prestamos;

import java.time.LocalDate;
import java.util.Objects;

import com.java.rogeregor.library.modelo.articulos.Ejemplar;
import com.java.rogeregor.library.modelo.genericos.Articulo;
import com.java.rogeregor.library.modelo.usuarios.Cliente;
import com.java.rogeregor.library.util.EstadoPrestamo;
import com.java.rogeregor.library.util.Valor;

public class Prestamo extends Articulo{

    private int prestamoId = Valor.getPrestamoId();
    private Ejemplar ejemplar;
    private Cliente cliente;
    private LocalDate fechaInicio = LocalDate.now();
    private LocalDate fechaFin = LocalDate.now().plusDays(15);
 

	public Prestamo() {
		super();
	}

	public Prestamo(Cliente cliente, Ejemplar ejemplar, LocalDate fechaInicio, LocalDate fechaFin, EstadoPrestamo estado, int stock) {
		super(estado, stock);
		this.ejemplar = ejemplar;
		this.cliente = cliente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Prestamo(Cliente cliente, Ejemplar ejemplar, LocalDate fechaInicio, LocalDate fechaFin, EstadoPrestamo estado) {
		super(estado);
		this.ejemplar = ejemplar;
		this.cliente = cliente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Prestamo(EstadoPrestamo estado, int stock, int prestamoId, Ejemplar ejemplar,
			Cliente cliente) {
		super(estado, stock);
		this.prestamoId = prestamoId;
		this.ejemplar = ejemplar;
		this.cliente = cliente;
	}

   
    public Prestamo(EstadoPrestamo estado, int stock, int prestamoId, Ejemplar ejemplar,
			Cliente cliente, LocalDate fechaFin) {
		super(estado, stock);
		this.prestamoId = prestamoId;
		this.ejemplar = ejemplar;
		this.cliente = cliente;
		this.fechaFin = fechaFin;
	}



	public Prestamo(Cliente cliente, Ejemplar ejemplar, LocalDate fechaInicio,
			LocalDate fechaFin) {
		this.ejemplar = ejemplar;
		this.cliente = cliente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public int getPrestamoId() {
		return prestamoId;
	}

	public void setPrestamoId(int prestamoId) {
		this.prestamoId = prestamoId;
	}

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	
    @Override
	public String toString() {
		return "Prestamo [Id=" + getPrestamoId() + 
				", Cliente=" + getCliente() + 
				", Ejemplar=" + getEjemplar() + 
				", FechaInicio=" + getFechaInicio() + 
				", FechaFin=" + getFechaFin() +
				", Estado=" + getEstado() + 
				", Stock=" + getStock() + 
				"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(prestamoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		return prestamoId == other.prestamoId;
	}

}

