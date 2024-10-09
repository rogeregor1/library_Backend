package com.java.rogeregor.library.servicios;

import java.util.Scanner;

import com.java.rogeregor.library.Principal;
import com.java.rogeregor.library.articuloDAO.*;
import com.java.rogeregor.library.modelo.articulos.*;
import com.java.rogeregor.library.modelo.usuarios.*;
import com.java.rogeregor.library.util.*;

public class ServInventario<T extends Ejemplar> implements CondicionEjemplar {

	private final Scanner sc = new Scanner(System.in); // Se recomienda final si no cambia.
	private final ArticuloDAO<T> arti;

	public ServInventario(ArticuloDAO<T> arti) {
		this.arti = arti;
	}

	public void introEjemplar(Class<T> selectedClazz, int cod) {
		System.out.println("\n------- Nuevo Ejemplar -------");
		int nuevoCod = Valor.getCod(); // Generar código único
		Autor nuevoAutor = new Autor(); // Crear el nuevo autor

		T nuevoArticulo = arti.createArticulo(selectedClazz, nuevoCod);

		// Verificar si el artículo ya existe en el inventario
		if (Principal.inventario.containsKey(nuevoArticulo)) {
			System.out.println("El producto ya existe...");
		} else {
			System.out.println("Añadiendo nuevo Articulo al inventario...");
			Principal.inventario.put(nuevoArticulo, nuevoArticulo.getStock());
			Principal.ejemplarAutor.put(nuevoArticulo, nuevoAutor);
			Principal.articulos.add(nuevoArticulo);
			Principal.usuarios.add(nuevoAutor);
			System.out.println("Ejemplar: " + nuevoArticulo.getClass() + " con Código: " + nuevoArticulo.getCod()
					+ " fue añadido al inventario.");
		}
	}

	public void actualizarEjemplar() {
		String agregar;
		do {
			introEjemplarActualizable();
			System.out.println("¿Desea agregar otro ejemplar? (si/no)");
			agregar = sc.nextLine();
		} while (agregar.equalsIgnoreCase("si"));
		System.out.println("\n\n");
	}

	private void introEjemplarActualizable() {
		System.out.println("\n------- Actualizar Ejemplar -------");

		Class<T> clazz = solicitarTipoArticulo();
		if (clazz == null) {
			System.out.println("Tipo de artículo no válido.");
			return;
		}

		int cod = solicitarCodArticulo();

		T actualizadoArticulo = arti.updateArticulo((Class<T>) clazz, cod);

		if (actualizadoArticulo != null) {
			System.out.println("Artículo actualizado: " + actualizadoArticulo);
		} else {
			System.out.println("No se pudo actualizar el artículo.");
		}
	}

	public void eliminarEjemplarByCod(int cod) {
		Ejemplar delEjem = Principal.articulos.stream().filter(ejemplar -> ejemplar.getCod() == cod).findFirst()
				.orElse(null);

		if (delEjem != null && Principal.inventario.containsKey(delEjem)) {
			Principal.inventario.remove(delEjem);
			System.out.println("Producto eliminado.");
		} else {
			System.out.println("El producto no existe.");
		}
	}

	public void gestionStockByCod(int cod) {
		Ejemplar stockE = Principal.articulos.stream().filter(ejemplar -> ejemplar.getCod() == cod).findFirst()
				.orElse(null);

		if (stockE != null) {
			System.out.println("Stock actual: " + Principal.inventario.get(stockE));
			System.out.println("¿Desea aumentar (1) o reducir (2) el stock?");
			int eleccion = sc.nextInt();
			sc.nextLine(); // Limpiar el buffer
			switch (eleccion) {
			case 1:
				aumentarStock(stockE);
				break;
			case 2:
				reducirStock(stockE);
				break;
			default:
				System.out.println("Opción no válida.");
				break;
			}
		} else {
			System.out.println("El artículo no existe.");
		}
	}

	private void aumentarStock(Ejemplar stockE) {
		System.out.println("Ingresar cantidad de ejemplares a aumentar:");
		int stock = sc.nextInt();
		sc.nextLine(); // Limpiar buffer
		if (stock > 0) {
			int stockActual = Principal.inventario.get(stockE);
			Principal.inventario.replace(stockE, stockActual + stock);
			System.out.println("Stock aumentado.");
		} else {
			System.out.println("No se puede añadir un stock negativo.");
		}
	}

	private boolean reducirStock(Ejemplar stockE) {
		System.out.println("Ingresar cantidad de ejemplares a reducir:");
		int stock = sc.nextInt();
		int stockActual = Principal.inventario.get(stockE);
		if (stock > 0 && stock <= stockActual) {
			Principal.inventario.replace(stockE, stockActual - stock);
			System.out.println("Stock reducido.");
			return true;
		} else {
			System.out.println("Stock insuficiente o cantidad no válida.");
			return false;
		}
	}

	public void mostrarInventario() {
		for (Ejemplar ejemplar : Principal.inventario.keySet()) {
			int cod = Principal.inventario.get(ejemplar);
			System.out.println(ejemplar + " -- " + cod);
		}
	}

	public boolean containsEjemplar(Ejemplar ejemplar) {
		boolean contiene = Principal.inventario.containsKey(ejemplar);
		if (contiene) {
			System.out.println("Ejemplar encontrado en el inventario.");
		} else {
			System.out.println("Ejemplar no encontrado.");
		}
		return contiene;
	}

	public boolean containsStock(Integer stock) {
		boolean contiene = Principal.inventario.containsValue(stock);
		if (contiene) {
			System.out.println("El libro tiene al menos un ejemplar disponible para préstamo.");
		} else {
			System.out.println("El libro no tiene ejemplares disponibles.");
		}
		return contiene;
	}

	@Override
	public Condicion aparienciaEjemplar(int antiguedad) {
		if (antiguedad > 10) {
			return Condicion.ANTIGUO;
		} else if (antiguedad <= 3) {
			return Condicion.NUEVO;
		} else {
			return Condicion.REGULAR;
		}
	}

	private Class<T> solicitarTipoArticulo() {

		Class<T> clazz = null;
		System.out.println("¿Qué tipo de artículo es? (1-Libro, 2-Revista)");
		int tipoArticulo = sc.nextInt();
		sc.nextLine(); // Limpiar el buffer del Scanner

		// Validar elección
		while (tipoArticulo != 1 && tipoArticulo != 2) {
			System.out.println("Tipo de artículo no válido. Por favor, elija 1 (Libro) o 2 (Revista):");
			tipoArticulo = sc.nextInt();
			sc.nextLine(); // Limpiar el buffer
		}

		// Asignar la clase correspondiente
		if (tipoArticulo == 1) {
			clazz = (Class<T>) Libro.class;
		} else if (tipoArticulo == 2) {
			clazz = (Class<T>) Revista.class;
		}

		return clazz;
	}

	private Integer solicitarCodArticulo() {
		System.out.print("Código del artículo a actualizar: ");
		int cod = sc.nextInt();
		sc.nextLine(); // Limpiar buffer
		return cod;
	}
}
