package com.java.rogeregor.library.modelo.articulos;

import java.util.Objects;

import com.java.rogeregor.library.modelo.usuarios.Autor;
import com.java.rogeregor.library.util.Categoria;
import com.java.rogeregor.library.util.EstadoPrestamo;

public class Libro extends Ejemplar {

    private Autor autor;
    private String isbn;
    private String titulo;
    private Categoria categoria = Categoria.LITERATURA;

    
    public Libro(int yearPublicacion, EstadoPrestamo estado, int stock, String isbn, Categoria categoria, String titulo) {
		super(yearPublicacion, estado, stock);
		this.isbn = isbn;
		this.titulo = titulo;
		this.categoria = categoria;
	}


    public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

    @Override
	public String toString() {
		return "Libro [Autor= " + (autor != null ? autor.getNombre() : "Sin Autor") +
				", Isbn=" + getIsbn() + 
				", Titulo=" + getTitulo() +
				", Categoria=" + getCategoria() +  
				", Cod=" + getCod() + 
				", Stock=" + getStock() + 
				", YearPublicacion=" + getYearPublicacion() +
				", Estado=" + getEstado() +
				"]";
	}

	@Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Libro other = (Libro) obj;
        return Objects.equals(isbn, other.isbn);
    }
    
}

