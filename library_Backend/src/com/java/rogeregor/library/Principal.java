package com.java.rogeregor.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.rogeregor.library.articuloDAO.ArticuloDAO;
import com.java.rogeregor.library.articuloDAO.ArticuloDAOImpl;
import com.java.rogeregor.library.modelo.articulos.*;
import com.java.rogeregor.library.modelo.prestamos.Prestamo;
import com.java.rogeregor.library.modelo.usuarios.*;
import com.java.rogeregor.library.servicios.*;
import com.java.rogeregor.library.usuarioDAO.UsuarioDAO;
import com.java.rogeregor.library.usuarioDAO.UsuarioDAOImpl;
import com.java.rogeregor.library.util.Categoria;
import com.java.rogeregor.library.util.EstadoPrestamo;

public class Principal {

	public static List<Persona> usuarios = new ArrayList<>();

	public static List<Ejemplar> articulos = new ArrayList<>();

	public static List<Prestamo> prestamos = new ArrayList<>();

	public static Map<Integer, Persona> personas = new HashMap<>();// personas, prestamosRealizados

	public static Map<Cliente, Integer> clientes = new HashMap<>();

	public static Map<Ejemplar, Integer> inventario = new HashMap<>();// articulo, stock

	public static Map<Ejemplar, Autor> ejemplarAutor = new HashMap<>();// articulo autor

	public static Map<String, Cliente> deudores = new HashMap<>();// dni, cliente deudor

	public static Map<Integer, Prestamo> enCurso = new HashMap<>();// prestamoId, prestamo.cliente

	public static Map<Ejemplar, List<Cliente>> reservas = new HashMap<>(); // Reservas pendientes

	static UsuarioDAO user = new UsuarioDAOImpl();
	static ArticuloDAO arti = new ArticuloDAOImpl();

	static ServUsuario servUser;
	static ServInventario servInve;
	static ServPrestamo servPres;

	static {
		// Inicialización en un bloque estático

		servInve = new ServInventario(arti);
		servUser = new ServUsuario(user);
		servPres = new ServPrestamo(user, arti);

	}

	public static void main(String[] args) {
		Principal app = new Principal();
		app.run();
	}

	public void run() {

		Persona p1 = new Persona("juan", "rico", LocalDate.of(2000, 9, 13));
		Persona p2 = new Persona("roger", "hurtado", LocalDate.of(1997, 2, 21));

		Persona cl1 = new Cliente("carlos", "barajas", "65434334H", 0);
		Persona cl2 = new Cliente("fabio", "luna", "76566554", 0);
		Persona cl3 = new Cliente("juan", "nefar", "76778289K", 0);
		Persona cl4 = new Cliente("shaila", "ivonne", "65488769W", 0);
		Persona cl5 = new Cliente("txeroki", "hurtado", "75444329W", 0);

		Persona e1 = new Empleado("mary", "sanchez", "contador", 2300.00);

		Persona a1 = new Autor("ramon", "garcia", "ecuador");
		Persona a2 = new Autor("juan", "perez", "austria");
		Persona a3 = new Autor("nino", "palma", "espania");
		Persona a6 = new Autor("jose", "borges", "argentina");
		Persona a7 = new Autor("mario", "vargas", "peru");
		Persona a9 = new Autor("miguel", "hernandez", "españa");
		Persona a10 = new Autor("mario", "jeque", "colombia");
		Persona a13 = new Autor("felipe", "samaniego", "mexico");
		Persona a14 = new Autor("los", "iracundos", "uruguay");

		usuarios.addAll(Arrays.asList(p1, p2, cl1, cl2, cl3, cl4, cl5, e1, a1, a2, a3, a6, a7, a9, a10, a13, a14));

		System.out.println("---------------------mapa de Usuarios----------------------------------------");

		personas.put(cl1.getId(), (Cliente) cl1);
		personas.put(cl2.getId(), (Cliente) cl2);
		personas.put(cl3.getId(), (Cliente) cl3);
		personas.put(cl4.getId(), (Cliente) cl4);
		personas.put(cl5.getId(), (Cliente) cl5);

		personas.put(a1.getId(), (Autor) a1);
		personas.put(a2.getId(), (Autor) a2);
		personas.put(a3.getId(), (Autor) a3);
		personas.put(a6.getId(), (Autor) a6);
		personas.put(a7.getId(), (Autor) a7);
		personas.put(a9.getId(), (Autor) a9);
		personas.put(a10.getId(), (Autor) a10);
		personas.put(a13.getId(), (Autor) a13);
		personas.put(a14.getId(), (Autor) a14);

		personas.put(p1.getId(), (Persona) p1);
		personas.put(p2.getId(), (Persona) p2);
		personas.put(e1.getId(), (Empleado) e1);

		// Imprimir mapa de personas
		for (Map.Entry<Integer, Persona> entry : personas.entrySet()) {
			Persona persona = entry.getValue();
			System.out.println("ID: " + entry.getKey() + ", Nombre: " + persona.getNombre() + ", Apellido: "
					+ persona.getApellido() + ", Clase: " + persona.getClass().getSimpleName());
		}

		clientes.put((Cliente) cl1, ((Cliente) cl1).getPrestamosRealizados());
		clientes.put((Cliente) cl2, ((Cliente) cl2).getPrestamosRealizados());
		clientes.put((Cliente) cl3, ((Cliente) cl3).getPrestamosRealizados());
		clientes.put((Cliente) cl4, ((Cliente) cl4).getPrestamosRealizados());
		clientes.put((Cliente) cl5, ((Cliente) cl5).getPrestamosRealizados());

		// Imprimir el mapa de clientes y préstamos realizados
		System.out.println("---------------------Mapa de Clientes----------------------------------------");

		for (Map.Entry<Cliente, Integer> entry : clientes.entrySet()) {
			Cliente cliente = entry.getKey();
			int prestamosRealizados = entry.getValue();
			System.out.println("Dni: " + cliente.getDni() + ", Nombre: " + cliente.getNombre() + ", Apellido: "
					+ cliente.getApellido() + " - Préstamos realizado: " + prestamosRealizados);
		}

		Ejemplar l1 = new Libro(1978, EstadoPrestamo.DEVUELTO, 1, "JPA23459", Categoria.LITERATURA, "asi hablo");

		Ejemplar l2 = new Libro(1945, EstadoPrestamo.DEVUELTO, 2, "JPA23458", Categoria.LITERATURA, "murio el flow");

		Ejemplar l3 = new Libro(2009, EstadoPrestamo.DEVUELTO, 2, "NPE28877", Categoria.LITERATURA, "milenials");

		Ejemplar l4 = new Libro(2000, EstadoPrestamo.DEVUELTO, 3, "NPE28876", Categoria.LITERATURA, "pasado fiesta");

		Ejemplar l5 = new Libro(1937, EstadoPrestamo.DEVUELTO, 4, "JBA12235", Categoria.LITERATURA, "pasado mas");

		Ejemplar l6 = new Libro(1945, EstadoPrestamo.DEVUELTO, 3, "MVP23434", Categoria.LITERATURA, "quantica");

		Ejemplar l7 = new Libro(2006, EstadoPrestamo.DEVUELTO, 2, "MVP23435", Categoria.LITERATURA, "sin futuro");

		Ejemplar l8 = new Libro(1977, EstadoPrestamo.DEVUELTO, 3, "MVP23436", Categoria.LITERATURA, "risas y salsa");

		Ejemplar l9 = new Libro(1932, EstadoPrestamo.DEVUELTO, 3, "HGT67676", Categoria.LITERATURA, "las fritos");

		Ejemplar l10 = new Libro(1896, EstadoPrestamo.DEVUELTO, 2, "MJC78765", Categoria.LITERATURA, "pasado fiesta");

		Ejemplar l13 = new Libro(2023, EstadoPrestamo.DEVUELTO, 3, "FTG54343", Categoria.DEPORTE, "Título del Libro");

		Ejemplar cd1 = new DiscoCompacto(1999, EstadoPrestamo.DEVUELTO, 1, "murio la flor", 38);
		
		Ejemplar cd2 = new DiscoCompacto(2000, EstadoPrestamo.DEVUELTO, 2, "los prisioneros", 58);

		Ejemplar rv1 = new Revista(2001, EstadoPrestamo.DEVUELTO, 1, "Anime", 2);

		Ejemplar rv3 = new Revista(2024, EstadoPrestamo.DEVUELTO, 2, "Revista XYZ", 12);

		// Crear lista de artículos y agregar instancias
		articulos.addAll(Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l13, rv1, rv3, cd1, cd2));

		// Create books for each author
		List<Ejemplar> librosA1 = Arrays.asList(l1, l13); // libros de autor a1
		List<Ejemplar> librosA2 = Arrays.asList(l1, l2);
		List<Ejemplar> librosA3 = Arrays.asList(l3, l4);
		List<Ejemplar> librosA6 = Arrays.asList(l1, l5);
		List<Ejemplar> librosA7 = Arrays.asList(l6, l7, l8);
		List<Ejemplar> librosA9 = Arrays.asList(l1, l9);
		List<Ejemplar> librosA10 = Arrays.asList(l1, l10);
		List<Ejemplar> librosA13 = Arrays.asList(l1, l13);

		((Autor) a1).setLibrosDeAutor(librosA1);
		((Autor) a2).setLibrosDeAutor(librosA2);
		((Autor) a2).setLibrosDeAutor(librosA3);
		((Autor) a2).setLibrosDeAutor(librosA6);
		((Autor) a2).setLibrosDeAutor(librosA7);
		((Autor) a2).setLibrosDeAutor(librosA9);
		((Autor) a2).setLibrosDeAutor(librosA10);
		((Autor) a2).setLibrosDeAutor(librosA13);

		// System.out.println(a1);
		// System.out.println(a2);

		System.out.println(
				"------------------------------------Mapa (ejemplar-stock)---------------------------------------");

		inventario.put((Libro) l1, l1.getStock());
		inventario.put((Libro) l2, l2.getStock());
		inventario.put((Libro) l3, l3.getStock());
		inventario.put((Libro) l4, l4.getStock());
		inventario.put((Libro) l5, l5.getStock());
		inventario.put((Libro) l6, l6.getStock());
		inventario.put((Libro) l7, l7.getStock());
		inventario.put((Libro) l8, l8.getStock());
		inventario.put((Libro) l9, l9.getStock());
		inventario.put((Libro) l10, l10.getStock());
		inventario.put((Libro) l13, l13.getStock());
		inventario.put((DiscoCompacto) cd1, cd1.getStock());
		inventario.put((DiscoCompacto) cd2, cd2.getStock());
		inventario.put((Revista) rv1, rv1.getStock());
		inventario.put((Revista) rv3, rv3.getStock());

		for (Map.Entry<Ejemplar, Integer> entry : inventario.entrySet()) {
			Ejemplar ejemplar = entry.getKey();
			// int stock = entry.getValue();

			// Imprimir detalles del ejemplar
			System.out.println("Cod: " + ejemplar.getCod() + ", Año: " + ejemplar.getYearPublicacion() + ", Clase: "
					+ ejemplar.getClass().getSimpleName() + ", Stock: " + entry.getValue());
		}

		System.out.println("--------------Mapa (ejemplar-autor)-----------------");

		ejemplarAutor.put((Libro) l1, (Autor) a2);
		ejemplarAutor.put((Libro) l2, (Autor) a2);
		ejemplarAutor.put((Libro) l6, (Autor) a7);
		ejemplarAutor.put((Libro) l7, (Autor) a7);
		ejemplarAutor.put((Libro) l8, (Autor) a7);
		ejemplarAutor.put((Libro) l3, (Autor) a3);
		ejemplarAutor.put((Libro) l4, (Autor) a3);
		ejemplarAutor.put((Libro) l5, (Autor) a6);
		ejemplarAutor.put((Libro) l9, (Autor) a9);
		ejemplarAutor.put((Libro) l10, (Autor) a10);
		ejemplarAutor.put((Libro) l13, (Autor) a13);

		for (Map.Entry<Ejemplar, Autor> entry : ejemplarAutor.entrySet()) {
			Autor autor = entry.getValue();
			Ejemplar ejemplar = entry.getKey();
			System.out.println("ID: " + ejemplar.getCod() + ", Clase: " + ejemplar.getClass().getSimpleName()
					+ ", Autor: " + autor.getNombre() + " " + autor.getApellido());
		}

		System.out.println("---------------pruebas----------------");

		// Inicialización de prestamos

		l1.setEstado(EstadoPrestamo.EN_DEUDA);
		servPres.reducirStock((Libro) l1);
		servPres.aumentarContadorPrestamos((Cliente) cl4);
		Prestamo prestamo1 = new Prestamo((Cliente) cl4, (Libro) l1, LocalDate.of(2024, 9, 3),
				LocalDate.of(2024, 9, 18), EstadoPrestamo.EN_DEUDA, 0);

		rv3.setEstado(EstadoPrestamo.EN_CURSO);
		servPres.reducirStock((Revista) rv3);
		servPres.aumentarContadorPrestamos((Cliente) cl5);
		Prestamo prestamo2 = new Prestamo((Cliente) cl5, (Revista) rv3, LocalDate.of(2024, 9, 13),
				LocalDate.of(2024, 9, 28), EstadoPrestamo.EN_CURSO, 1);

		// Crear lista de prestamos y agregar instancias
		prestamos.addAll(Arrays.asList(prestamo1, prestamo2));

		System.out.println("-------------------lista de prestamos------------------");
		for (Prestamo valor : prestamos) {
			System.out.println(valor);
		}

		// Agregar a mapa de prestamos en curso
		enCurso.put(prestamo2.getPrestamoId(), prestamo2);
		System.out.println("--------------Mapa de prestamos en curso-----------------");
		// enCurso.forEach((prestamoId, prestamo) -> System.out.println(prestamo));

		for (Map.Entry<Integer, Prestamo> entry : enCurso.entrySet()) {
			Prestamo prestamo = entry.getValue();
			System.out.println("prestamoID: " + entry.getKey() + ", Cliente: " + prestamo.getCliente().getNombre() + " "
					+ prestamo.getCliente().getApellido());
		}

		System.out.println("-----------------------------");
		// Agregar a mapa deudores
		deudores.put("65488769W", (Cliente) cl4);

		System.out.println("--------------Mapa de clientes deudores-----------------");
		// Imprimir la lista de deudores
		for (Map.Entry<String, Cliente> entry : deudores.entrySet()) {
			System.out.println("DNI: " + entry.getKey() + ", Cliente: " + entry.getValue().getNombre() + " "
					+ entry.getValue().getApellido());
		}

		System.out.println("-----------------------------");
		servPres.mostrarTodosLosPrestamos();

		servPres.pedirPrestamo();// peticion de prestamo ejemplar

		System.out.println("-------------------lista de prestamos------------------");
		for (Prestamo valor : prestamos) {
			System.out.println(valor);
		}

		// usuario 13 pedir prestado un libro 101 stock=0
		servPres.pedirPrestamo(); // Si no hay ejemplares disponibles, se le ofrece
									// la posibilidad de reservar

		System.out.println("-------------------mapa de reservas------------------");
		for (Map.Entry<Ejemplar, List<Cliente>> entry : reservas.entrySet()) {
			Ejemplar ejemplar = entry.getKey();
			List<Cliente> clientes = entry.getValue();
			for (Cliente cli : clientes) {
				System.out.println("ID: " + ejemplar.getCod() + ", Clase: " + ejemplar.getClass().getSimpleName()
						+ ", Cliente: " + cli.getNombre() + " " + cli.getApellido());
			}
		}

		servPres.devolverPrestamo(); // devolucion de prestamo

		System.out.println("-------------------lista de prestamos------------------");
		for (Prestamo valor : prestamos) {
			System.out.println(valor);
		}

	}
}
