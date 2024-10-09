package com.java.rogeregor.library.modelo.articulos;

import java.util.ArrayList;
import java.util.List;

import com.java.rogeregor.library.modelo.usuarios.Autor;
import com.java.rogeregor.library.util.EstadoPrestamo;

public class DiscoCompacto extends Ejemplar {

    private String tituloAlbum;        // Título del álbum
    private Autor artista;             // Artista del álbum (puede ser un "Autor" o crear una clase Artista si es necesario)
    private int duracionMinutos;       // Duración total del disco en minutos
    private List<String> pistas;  // Lista de pistas en el disco


    public DiscoCompacto(int yearPublicacion, EstadoPrestamo estado, int stock, String tituloAlbum,
			int duracionMinutos) {
		super(yearPublicacion, estado, stock);
		this.tituloAlbum = tituloAlbum;
		this.duracionMinutos = duracionMinutos;
	}
    //Constructor con parámetros sin pistas
    public DiscoCompacto(int yearPublicacion, int stock, String tituloAlbum, Autor artista, int duracionMinutos) {
        super(stock, yearPublicacion);
        this.tituloAlbum = tituloAlbum;
        this.artista = artista;
        this.duracionMinutos = duracionMinutos;
    }

    // Constructor con pistas con pistas
    public DiscoCompacto(int stock, int yearPublicacion, String tituloAlbum, Autor artista, int duracionMinutos, ArrayList<String> pistas) {
        super(stock, yearPublicacion);
        this.tituloAlbum = tituloAlbum;
        this.artista = artista;
        this.duracionMinutos = duracionMinutos;
        this.pistas = new ArrayList<>();
    }
   
	// Getters y Setters
    public String getTituloAlbum() {
        return tituloAlbum;
    }

    public void setTituloAlbum(String tituloAlbum) {
        this.tituloAlbum = tituloAlbum;
    }

    public Autor getArtista() {
        return artista;
    }

    public void setArtista(Autor artista) {
        this.artista = artista;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public List<String> getPistas() {
        return pistas;
    }

    public void setPistas(ArrayList<String> pistas) {
        this.pistas = pistas;
    }

    // Método toString para mostrar la información del disco compacto
    @Override
    public String toString() {
        return "CD [Título del Álbum=" + tituloAlbum +
                ", Artista=" + (artista != null ? artista.getNombre() + artista.getApellido() : "N/A") +
                ", Duración=" + duracionMinutos + " minutos" +
                ", Pistas=" + pistas +
                ", Cod=" + getCod() +
                ", YearPublicación=" + getYearPublicacion() + 
				", Estado=" + getEstado() +
				", Stock=" + getStock() + 
                "]";
    }
}
