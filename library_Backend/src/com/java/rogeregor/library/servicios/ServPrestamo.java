package com.java.rogeregor.library.servicios;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.java.rogeregor.library.modelo.articulos.*;
import com.java.rogeregor.library.modelo.prestamos.Prestamo;
import com.java.rogeregor.library.modelo.prestamos.TicketPrestamo;
import com.java.rogeregor.library.modelo.usuarios.*;
import com.java.rogeregor.library.usuarioDAO.UsuarioDAO;

import interfaces.DeudaPrestamo;
import com.java.rogeregor.library.Principal;
import com.java.rogeregor.library.articuloDAO.ArticuloDAO;
import com.java.rogeregor.library.util.*;

public class ServPrestamo<U extends Persona, T extends Ejemplar> implements DeudaPrestamo{

	private Scanner sc = new Scanner(System.in);

	private final UsuarioDAO<U> user;
	private final ArticuloDAO<T> arti;

	public ServPrestamo(UsuarioDAO<U> user, ArticuloDAO<T> arti) {
		this.user = user;
		this.arti = arti;
	}

	@Override
	public boolean test(LocalDate fechaFin) {
		long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaFin);
		return diasRestantes < 0; // Hay deuda si la fecha de fin ya pasó
	}

	public void agregarDeudores() {
		U cliente = user.createUsuario((Class<U>) Cliente.class, Valor.getDeudorId());

		if (cliente == null) {
			System.out.println("No es cliente");
			return; // Salir del método si el cliente es null
		}
		// Verificar si es una instancia de Cliente
		if (!(cliente instanceof Cliente)) {
			System.out.println("El usuario creado no es un cliente.");
			return; // Salir si no es una instancia de Cliente
		}

		Cliente clienteValido = (Cliente) cliente;

		// Verificar si el cliente ya está en la lista de morosos
		if (Principal.deudores.containsKey(clienteValido.getDni())) {
			System.out.println("El cliente ya está en la lista de deudores.");
		} else {
			String dni = solicitarTexto("DNI del Cliente");
			Principal.deudores.put(dni, clienteValido);
			System.out.println("Cliente agregado a la lista de deudores.");
		}

		System.out.println("Lista de deudores: " + Principal.deudores);

	}

	public void agregarDeudores(Cliente cliente) {
		if (Principal.deudores.containsKey(cliente.getDni())) {
			System.out.println("El cliente ya está en la lista de deudores.");
		} else {
			Principal.deudores.put(cliente.getDni(), cliente);
			System.out.println("Cliente agregado a la lista de deudores.");
		}
	}

	private boolean tieneDeudasPendientes(Cliente cliente) {
		return obtenerPrestamosCliente(cliente).stream().anyMatch(prestamo -> test(prestamo.getFechaFin()));
	}

	private List<Prestamo> obtenerPrestamosCliente(Cliente cliente) {
		return Principal.prestamos.stream().filter(prestamo -> prestamo.getCliente().equals(cliente))
				.collect(Collectors.toList());
	}

	public void devolverPrestamo() {
		System.out.println("--------devolución de artículo----------------");
		int clienteId = solicitarIdCliente();
		Cliente cliente = (Cliente) user.seleccionarUsuario((Class<U>) Cliente.class, clienteId);

		if (cliente != null && Principal.usuarios.contains(cliente)) {
			String devolver;
			do {
				// Comprobar si tiene deudas antes de proceder con la devolución
				if (tieneDeudasPendientes(cliente)) {
					agregarDeudores(cliente);
				}

				// Proceder con la devolución
				if (devolucion(cliente) == EstadoPrestamo.DEVUELTO) {
					System.out.println("¿Desea devolver otro préstamo? (si/no)");
					devolver = sc.next();
					sc.nextLine(); // Limpiar buffer
					System.out.println("Préstamo devuelto");
				} else {
					System.out.println("No se pudo realizar la devolución.");
					break;
				}
			} while (devolver.equalsIgnoreCase("si"));
			// Verificar si el cliente aún tiene deudas y eliminarlo del mapa de deudores si
			// no
			if (!tieneDeudasPendientes(cliente)) {
				Principal.deudores.remove(cliente.getDni());
				System.out.println("Cliente retirado de la lista de deudores.");
			}
		} else {
			System.out.println("Usuario no encontrado.");
		}
	}

	private EstadoPrestamo devolucion(Cliente cliente) {
		// Preguntar al usuario qué tipo de artículo quiere (Libro o Revista)
		T ejemplar = seleccionarEjemplar();

		if (ejemplar == null) {
			System.out.println("El ejemplar no se encuentra.");
			return EstadoPrestamo.ANY_STATE;
		}
		// Verificar si el artículo está en EN_CURSO o EN_DEUDA
		if (ejemplar.getEstado() == EstadoPrestamo.EN_CURSO) {
			ejemplar.setEstado(EstadoPrestamo.DEVUELTO);
			aumentarStock(ejemplar);

			// Verificar si hay reservas pendientes
			if (Principal.reservas.containsKey(ejemplar) && !Principal.reservas.get(ejemplar).isEmpty()) {
				Cliente siguienteCliente = Principal.reservas.get(ejemplar).remove(0); // Obtener el primer cliente en
																						// la cola
				System.out.println("El ejemplar ha sido devuelto. El siguiente cliente en la reserva es: "
						+ siguienteCliente.getNombre());
			}

			// Actualizar la lista de préstamos
			for (int i = 0; i < Principal.prestamos.size(); i++) {
				Prestamo prestamo = Principal.prestamos.get(i);
				if (prestamo.getEjemplar().equals(ejemplar) && prestamo.getCliente().equals(cliente)) {
					long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaFin(), LocalDate.now());

					// Si la devolución se excedió en más de 15 días, aplicar penalización
					if (diasRetraso > 15) {
						System.out
								.println("El cliente ha excedido el plazo de devolución en " + diasRetraso + " días.");
						LocalDate fechaFinPenalizacion = LocalDate.now().plusDays(30);
						cliente.setFechaFinPenalizacion(fechaFinPenalizacion);
						System.out.println("El cliente no podrá pedir préstamos hasta: " + fechaFinPenalizacion);
					}

					// Actualizar el préstamo a DEVUELTO
					int stockActual = Principal.inventario.getOrDefault(ejemplar, 0);
					Principal.prestamos.set(i, new Prestamo(prestamo.getCliente(), prestamo.getEjemplar(),
							prestamo.getFechaInicio(), LocalDate.now(), EstadoPrestamo.DEVUELTO, stockActual));
					break;
				}
			}
			System.out.println("Artículo devuelto correctamente.");
			return EstadoPrestamo.DEVUELTO;
		} else {
			System.out.println("El artículo no estaba prestado.");
			return ejemplar.getEstado();
		}
	}

	public void pedirPrestamo() {
		System.out.println("-------- Petición de Artículo --------");

		int clienteId = solicitarIdCliente();
		Cliente cliente = (Cliente) user.seleccionarUsuario((Class<U>) Cliente.class, clienteId);

		if (cliente != null) {

			if (cliente.getFechaFinPenalizacion() != null
					&& cliente.getFechaFinPenalizacion().isAfter(LocalDate.now())) {
				System.out.println("El cliente está penalizado hasta " + cliente.getFechaFinPenalizacion());
				return;
			}

			if (tieneDeudasPendientes(cliente)) {
				System.out.println("El cliente tiene deudas pendientes y no puede solicitar préstamos.");
				agregarDeudores(cliente);
				return;
			}

			String agregar;
			do {
				if (peticion(cliente) == EstadoPrestamo.EN_CURSO) {
					System.out.println("¿Desea añadir otro préstamo? (si/no)");
					agregar = sc.next();
					sc.nextLine();
					aumentarContadorPrestamos(cliente);
					System.out.println("Préstamo añadido");
					System.out.println("---------- Ticket del Préstamo ----------");
					imprimirTicket(cliente);
				} else {
					System.out.println("No se pudo realizar el préstamo.");
					break;
				}
			} while (agregar.equalsIgnoreCase("si"));

		} else {
			System.out.println("Usuario no encontrado.");
		}
	}

	private EstadoPrestamo peticion(Cliente cliente) {
		// Preguntar al usuario qué tipo de artículo quiere (Libro o Revista)

		T ejemplarSolicitado = seleccionarEjemplar();

		if (ejemplarSolicitado != null) {
			EstadoPrestamo estado = ejemplarSolicitado.getEstado();
			// Reducir el stock del ejemplar
			reducirStock(ejemplarSolicitado);
			int stockActual = Principal.inventario.getOrDefault(ejemplarSolicitado, 0);

			if (stockActual == 0) {
				System.out.println("No hay ejemplares disponibles. ¿Desea reservarlo? (si/no)");
				String respuesta = sc.next();
				if (respuesta.equalsIgnoreCase("si")) {
					reservarEjemplar(cliente);
				} else {
					System.out.println("No se realizó la reserva.");
				}
				return EstadoPrestamo.ANY_STATE;
			} else {
				ejemplarSolicitado.setEstado(EstadoPrestamo.EN_CURSO);
				LocalDate fechaInicio = LocalDate.now();
				LocalDate fechaFin = fechaInicio.plusDays(15); // Ajustar el período del préstamo según sea necesario

				// Crear un nuevo objeto Prestamo usando el constructor adecuado
				Prestamo nuevoPrestamo = new Prestamo(cliente, ejemplarSolicitado, fechaInicio, fechaFin,
						EstadoPrestamo.EN_CURSO, stockActual);

				// Agregar el nuevo préstamo a la lista de préstamos
				Principal.prestamos.add(nuevoPrestamo);
				// Agregar el nuevo prestamo al mapa de enCurso
				Principal.enCurso.put(nuevoPrestamo.getPrestamoId(), nuevoPrestamo);
				return EstadoPrestamo.EN_CURSO;
			}
		} else {
			System.out.println("El artículo no se encuentra en la biblioteca.");
			return EstadoPrestamo.ANY_STATE;
		}
	}

	public void reservarEjemplar(Cliente cliente) {
		T ejemplar = seleccionarEjemplar();

		if (ejemplar == null) {
			System.out.println("El ejemplar no se encuentra.");
			return;
		}

		int stockActual = Principal.inventario.getOrDefault(ejemplar, 0);

		// Si no hay ejemplares disponibles, proceder con la reserva
		if (stockActual == 0) {
			Principal.reservas.computeIfAbsent(ejemplar, k -> new ArrayList<>()).add(cliente);
			System.out.println("El ejemplar ha sido reservado. Se le notificará cuando esté disponible.");
		} else {
			System.out.println("El ejemplar está disponible. No es necesario reservarlo.");
		}
	}

	public void mostrarReservas(Cliente cliente) {
		Principal.reservas.forEach((ejemplar, listaClientes) -> {
			if (listaClientes.contains(cliente)) {
				System.out.println("El cliente tiene una reserva para el ejemplar: " + ejemplar.getCod());
			}
		});
	}

	public void mostrarTodosLosPrestamos() {
		if (Principal.enCurso.isEmpty()) {
			System.out.println("No hay préstamos registrados.");
			return;
		}

		System.out.printf("%-10s %-20s %-20s %-15s %-15s%n", "ID", "Usuario", "Artículo", "Fecha Préstamo",
				"Fecha Devolución");
		System.out.println("---------------------------------------------------------------");

		Principal.enCurso.values().forEach(prestamo -> {
			System.out.printf("%-10d %-20s %-20s %-15s %-15s%n", prestamo.getPrestamoId(),
					prestamo.getCliente().getDni(), prestamo.getEjemplar().getCod(), prestamo.getFechaInicio(),
					prestamo.getFechaFin() != null ? prestamo.getFechaFin() : "Pendiente");
		});
	}

	public T seleccionarArticulo(Class<T> clazz, int cod) {
		return Principal.articulos.stream().filter(ejemplar -> clazz.isInstance(ejemplar) && ejemplar.getCod() == cod)
				.map(clazz::cast).findFirst().orElse(null);
	}

	public T validarCliente(Class<T> clazz, int id) {
		return Principal.usuarios.stream().filter(persona -> clazz.isInstance(persona) && persona.getId() == id)
				.map(clazz::cast).findFirst().orElse(null);
	}

	// Métodos auxiliares para solicitar datos

	private String solicitarTexto(String campo) {
		System.out.println(campo + ": ");
		return sc.next();
	}

	private Integer solicitarIdCliente() {
		System.out.print("Código del Cliente solicitado: ");
		int id = sc.nextInt();
		sc.nextLine(); // Limpiar buffer
		return id;
	}

	private void imprimirTicket(Cliente cliente) {
		// Buscar el último préstamo del cliente usando streams
		Principal.enCurso.values().stream().filter(prestamo -> prestamo.getCliente().equals(cliente)) // Filtrar por
																										// cliente
				.max(Comparator.comparing(Prestamo::getFechaInicio)) // Obtener el último préstamo
				.ifPresentOrElse(prestamo -> { // Si se encuentra el préstamo
					TicketPrestamo ticket = new TicketPrestamo(Valor.getTk(), cliente, prestamo.getEjemplar(),
							prestamo.getFechaInicio(), prestamo.getFechaFin());
					System.out.println(ticket); // Imprimir el ticket
				}, () -> System.out.println("No se encontró ningún préstamo para el cliente.") // Si no hay préstamo
				);
	}

	public void aumentarContadorPrestamos(Cliente cliente) {
		int conteoActual = Principal.clientes.getOrDefault(cliente, cliente.getPrestamosRealizados());
		conteoActual++;
		// Actualiza el stock en inventario y articulos
		actualizarContador(cliente, conteoActual);
		System.out.println("Préstamos realizados actualizados: " + conteoActual);
	}

	public void reducirContadorPrestamos(Cliente cliente) {
		// Obtiene el stock actual del inventario
		int conteoActual = Principal.clientes.getOrDefault(cliente, cliente.getPrestamosRealizados());

		if (conteoActual > 0) {
			conteoActual--; // Reduce el stock en 1
			// Actualiza el stock en inventario y articulos
			actualizarContador(cliente, conteoActual);
		} else {
			System.out.println("No se pueden reducir los préstamos, ya que el cliente no tiene préstamos activos.");
		}

		System.out.println("Préstamos realizados actualizados: " + conteoActual);
	}

	private void actualizarContador(Cliente cliente, int conteoActual) {
		// Actualiza el contador de préstamos en el mapa
		Principal.clientes.put(cliente, conteoActual);
		// También actualiza el contador de préstamos dentro del objeto cliente
		cliente.setPrestamosRealizados(conteoActual);

		System.out.println("Contador de préstamos actualizado para el cliente: " + cliente.getNombre());
	}

	public void aumentarStock(Ejemplar ejemplar) {
		Principal.inventario.merge(ejemplar, 1, Integer::sum); // Incrementa el stock
		actualizarStockEnLista(ejemplar);
	}

	public void reducirStock(Ejemplar ejemplar) {
		Principal.inventario.computeIfPresent(ejemplar, (key, stock) -> stock > 0 ? stock - 1 : 0);
		actualizarStockEnLista(ejemplar);
	}

	private void actualizarStockEnLista(Ejemplar ejemplar) {
		Principal.articulos.stream().filter(e -> e.equals(ejemplar)).findFirst()
				.ifPresent(e -> e.setStock(Principal.inventario.get(ejemplar)));
	}

	private T seleccionarEjemplar() {
		// Preguntar al usuario qué tipo de artículo quiere (Libro o Revista)
		System.out.println("¿Qué tipo de artículo prefiere? (1-Libro, 2-Revista, 3-DiscoCompacto, 4-Otro)");
		int tipoArticulo = sc.nextInt();
		sc.nextLine(); // Limpiar el buffer del Scanner

		Class<T> clazz;
		if (tipoArticulo == 1) {
			clazz = (Class<T>) Libro.class;
		} else if (tipoArticulo == 2) {
			clazz = (Class<T>) Revista.class;
		} else if (tipoArticulo == 3) {
			clazz = (Class<T>) DiscoCompacto.class;
		} else {
			System.out.println("Tipo de artículo no válido.");
			return null;
		}

		System.out.println("Introduce el código del artículo: ");
		int codSolicitado = sc.nextInt();
		sc.nextLine(); // Limpiar el buffer

		return seleccionarArticulo(clazz, codSolicitado);
	}

}
