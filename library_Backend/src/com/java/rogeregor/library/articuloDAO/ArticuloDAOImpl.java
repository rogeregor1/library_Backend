package com.java.rogeregor.library.articuloDAO;

import java.util.Scanner;

import com.java.rogeregor.library.Principal;
import com.java.rogeregor.library.modelo.articulos.*;
import com.java.rogeregor.library.modelo.usuarios.Autor;
import com.java.rogeregor.library.util.Categoria;
import com.java.rogeregor.library.util.Valor;

public class ArticuloDAOImpl<T extends Ejemplar> implements ArticuloDAO<T> {
	Scanner sc = new Scanner(System.in);

	@Override
	public T createArticulo(Class<T> clazz, int cod) {
		System.out.println("Introducción de Artículo");
		if (cod == 0) {
			cod = Valor.getCod();
		}
		// Entrada de los datos comunes para Ejemplar
		int stock = solicitarStock();
		int yearPublicacion = solicitarAnioPublicacion();

		String isbn = null;
		Categoria categoria = null;
		String nombre = null;
		int numeroEdicion = 0;
        try {
            if (clazz.equals(Libro.class)) {
                isbn = solicitarTexto("ISBN del libro");
                categoria = solicitarCategoria();
                return clazz.getDeclaredConstructor(int.class, int.class, String.class, Categoria.class)
                        .newInstance(stock, yearPublicacion, isbn, categoria);

            } else if (clazz.equals(Revista.class)) {
                nombre = solicitarTexto("Nombre de la revista");
                numeroEdicion = solicitarNumeroEdicion();
                return clazz.getDeclaredConstructor(int.class, int.class, String.class, int.class)
                        .newInstance(stock, yearPublicacion, nombre, numeroEdicion);

            } else if (clazz.equals(DiscoCompacto.class)) {
                String tituloAlbum = solicitarTexto("Título del álbum");
                String artistaNombre = solicitarTexto("Nombre del artista");
                String artistaApellido = solicitarTexto("Apellido del artista");
                Autor artista = new Autor(artistaNombre, artistaApellido);
                int duracionMinutos = solicitarDuracion();
                return clazz.getDeclaredConstructor(int.class, int.class, String.class, Autor.class, int.class)
                        .newInstance(stock, yearPublicacion, tituloAlbum, artista, duracionMinutos);
            } else {
                return clazz.getDeclaredConstructor(int.class, int.class).newInstance(stock, yearPublicacion);
            }

        } catch (Exception e) {
            System.out.println("Error al crear la instancia de " + clazz.getName() + ": " + e.getMessage());
            return null;
        }
    }

	@Override
	public T updateArticulo(Class<T> clazz, int cod) {
		System.out.println("Actualizar datos de artículo");

		// Buscar el usuario por COD
		T articulo = seleccionarArticulo(clazz, cod);

		if (articulo == null) {
			System.out.println("Articulo no encontrado.");
			return null;
		}

		// Entrada de los datos comunes para Ejemplar
		int stock = solicitarStock();
		int yearPublicacion = solicitarAnioPublicacion();

		articulo.setStock(stock);
		articulo.setYearPublicacion(yearPublicacion);

		// Actualizar los atributos adicionales según la subclase
		if (articulo instanceof Libro) {
			Libro libro = (Libro) articulo;
			System.out.println("ISBN del libro: ");
			libro.setIsbn(sc.next());
			System.out.print("Categoría del libro (0-LITERATURA, 1-CIENCIA, 2-ARTE, 3-DEPORTE, 4-FILOSOFIA): ");
			int categoriaIndex = sc.nextInt();
			if (categoriaIndex >= 0 && categoriaIndex < Categoria.values().length) {
				libro.setCategoria(Categoria.values()[categoriaIndex]);
			} else {
				System.out.println("Categoría no válida. Se asignará valor por defecto.");
				libro.setCategoria(Categoria.LITERATURA); // Valor por defecto
			}
		} else if (articulo instanceof Revista) {
			Revista revista = (Revista) articulo;
			System.out.println("Nombre de la revista: ");
			revista.setNombre(sc.next());
			System.out.println("Numero de la Edicion de la revista: ");
			revista.setNumeroEdicion(sc.nextInt());
		} else if (articulo instanceof DiscoCompacto) {
		    DiscoCompacto disco = (DiscoCompacto) articulo;
		    System.out.println("Título del álbum: ");
		    disco.setTituloAlbum(sc.next());
		    System.out.println("Nombre del artista: ");
		    Autor artista = new Autor(sc.next(), sc.next()); // Suponiendo que Autor puede representar al artista
		    disco.setArtista(artista);
		    System.out.println("Duración en minutos: ");
		    disco.setDuracionMinutos(sc.nextInt());
		}

		return articulo;
	}

	@Override
	public boolean deleteArticulo(Class<T> clazz, int cod) {
		T articuloAEliminar = seleccionarArticulo(clazz, cod);
		if (articuloAEliminar == null) {
			System.out.println("Articulo no encontrado para eliminar.");
			return false;
		}
		// Confirmar si el articulo se ha encontrado y mostrar su información antes de
		// eliminar
		System.out.println(
				"Eliminando articulo: " + articuloAEliminar.getCod() + " " + articuloAEliminar.getYearPublicacion());

		Principal.articulos.remove(articuloAEliminar);
		return true;
	}

	@Override
	public T seleccionarArticulo(Class<T> clazz, int cod) {
		// Buscar articulo por COD
		return (T) Principal.articulos.stream()
				.filter(articulo -> articulo.getCod() == cod && clazz.isAssignableFrom(articulo.getClass())).findFirst()
				.orElse(null);
	}

	@Override
	public void viewArticulo(Class<T> clazz, int cod) {
		T articulo = seleccionarArticulo(clazz, cod);

		// Si el usuario no se encuentra, mostrar un mensaje
		if (articulo == null) {
			System.out.println("Articulo con COD " + cod + " no encontrado.");
			return;
		}

		// Mostrar atributos comunes
		System.out.println("COD: " + articulo.getCod());
		System.out.println("Stock: " + articulo.getStock());
		System.out.println("Año de Publicacion: " + articulo.getYearPublicacion());

		// Mostrar atributos adicionales según la clase
		if (articulo instanceof Libro) {
			Libro libro = (Libro) articulo;
			System.out.println("ISBN: " + libro.getIsbn());
			System.out.println("Categoría: " + libro.getCategoria());
		} else if (articulo instanceof Revista) {
			Revista revista = (Revista) articulo;
			System.out.println("Nombre: " + revista.getNombre());
			System.out.println("Número de Edición: " + revista.getNumeroEdicion());
		}else if (articulo instanceof DiscoCompacto) {
		    DiscoCompacto disco = (DiscoCompacto) articulo;
		    System.out.println("Título del álbum: " + disco.getTituloAlbum());
		    System.out.println("Artista: " + disco.getArtista().getNombre());
		    System.out.println("Duración en minutos: " + disco.getDuracionMinutos());
		    System.out.println("Pistas: " + disco.getPistas());
		}
	}

	// metodos auxiliares

	private int solicitarStock() {
		System.out.print("Stock del artículo: ");
		int stock = sc.nextInt();
		return Math.max(stock, 1); // Asegurar que el stock sea al menos 1
	}

	private int solicitarAnioPublicacion() {
		System.out.print("Año de publicación: ");
		return sc.nextInt();
	}

	private Categoria solicitarCategoria() {
		System.out.print("Categoría del libro (0-LITERATURA, 1-CIENCIA, 2-ARTE, 3-DEPORTE, 4-FILOSOFIA): ");
		int categoriaIndex = sc.nextInt();
		if (categoriaIndex >= 0 && categoriaIndex < Categoria.values().length) {
			return Categoria.values()[categoriaIndex];
		} else {
			System.out.println("Categoría no válida. Se asignará valor por defecto.");
			return Categoria.LITERATURA; // Valor por defecto
		}
	}

	private int solicitarNumeroEdicion() {
		System.out.print("Número de edición de la revista: ");
		return sc.nextInt();
	}

	private String solicitarTexto(String campo) {
		System.out.println(campo + ": ");
		return sc.next();
	}
	
	private int solicitarDuracion() {
	    System.out.print("Duración en minutos: ");
	    return sc.nextInt();
	}
}
