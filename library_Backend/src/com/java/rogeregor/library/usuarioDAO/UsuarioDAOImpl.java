package com.java.rogeregor.library.usuarioDAO;

import java.time.LocalDate;
import java.util.Scanner;

import com.java.rogeregor.library.Principal;
import com.java.rogeregor.library.modelo.usuarios.*;
import com.java.rogeregor.library.util.Valor;

public class UsuarioDAOImpl<U extends Persona> implements UsuarioDAO<U> {
	private Scanner sc = new Scanner(System.in);

	@Override
	public U createUsuario(Class<U> clazz, int id) {
		System.out.println("Introducción de Usuario");
		if (id == 0) {
			id = Valor.getId();
		}
		// Entrada de los datos comunes
		String nombre = solicitarTexto("Nombre");
		String apellido = solicitarTexto("Apellido");

		try {
			if (clazz.equals(Autor.class)) {
				String paisNacimiento = solicitarTexto("País de Nacimiento");
				return clazz.getDeclaredConstructor(String.class, String.class, String.class).newInstance(nombre,
						apellido, paisNacimiento);
			} else if (clazz.equals(Cliente.class)) {
				String dni = solicitarTexto("DNI del Cliente");
				return clazz.getDeclaredConstructor(String.class, String.class, String.class).newInstance(nombre,
						apellido, dni);
			} else if (clazz.equals(Empleado.class)) {
				String puesto = solicitarTexto("Puesto del Empleado");
				return clazz.getDeclaredConstructor(String.class, String.class, String.class).newInstance(nombre,
						apellido, puesto);
			} else {
				LocalDate fn = solicitarFechaNacimiento();
				return clazz.getDeclaredConstructor(String.class, String.class, LocalDate.class).newInstance(nombre,
						apellido, fn);
			}
		} catch (Exception e) {
			System.out.println("Error al crear la instancia: " + e.getMessage());
			return null;
		}
	}

	@Override
	public U updateUsuario(Class<U> clazz, int id) {
		System.out.println("Actualizar datos de usuario");

		// Buscar el usuario por ID
		U usuario = seleccionarUsuario(clazz, id);

		if (usuario == null) {
			System.out.println("Usuario no encontrado.");
			return null;
		}

		// Entrada de los datos comunes
		String nombre = solicitarTexto("Nombre");
		String apellido = solicitarTexto("Apellido");

		usuario.setNombre(nombre);
		usuario.setApellido(apellido);

		// Actualizar los atributos específicos
		if (usuario instanceof Autor) {
			System.out.println("País de nacimiento: ");
			((Autor) usuario).setPaisNacimiento(sc.next());
		} else if (usuario instanceof Cliente) {
			System.out.println("DNI del cliente: ");
			((Cliente) usuario).setDni(sc.next());
		} else if (usuario instanceof Empleado) {
			System.out.println("Puesto del empleado: ");
			((Empleado) usuario).setPuesto(sc.next());
		} else if (usuario instanceof Persona) {
			System.out.println("Fecha de nacimiento (año, mes, día): ");
			LocalDate fn = LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
			usuario.setFn(fn);
		}

		return usuario;
	}

	@Override
	public boolean deleteUsuario(Class<U> clazz, int id) {
		U usuarioAEliminar = seleccionarUsuario(clazz, id);
		// GenericoPersona<U> genericoPersona = new GenericoPersona<>(usuarioAEliminar);
		if (usuarioAEliminar == null) {
			System.out.println("Usuario no encontrado para eliminar.");
			return false;
		}
		// Confirmar si el usuario se ha encontrado y mostrar su información antes de
		// eliminar
		System.out
				.println("Eliminando usuario: " + usuarioAEliminar.getNombre() + " " + usuarioAEliminar.getApellido());

		Principal.usuarios.remove(usuarioAEliminar);
		return true;
	}

	@Override
	public U seleccionarUsuario(Class<U> clazz, int id) {
		// Buscar usuario por ID
		return (U) Principal.usuarios.stream()
				.filter(usuario -> usuario.getId() == id && clazz.isAssignableFrom(usuario.getClass())).findFirst()
				.orElse(null);
	}

	@Override
	public void viewUsuario(Class<U> clazz, int id) {
		U usuario = seleccionarUsuario(clazz, id);

		// Si el usuario no se encuentra, mostrar un mensaje
		if (usuario == null) {
			System.out.println("Usuario con ID " + id + " no encontrado.");
			return;
		}

		// Mostrar atributos comunes
		System.out.println("ID: " + usuario.getId());
		System.out.println("Nombre: " + usuario.getNombre());
		System.out.println("Apellido: " + usuario.getApellido());

		/// Mostrar atributos adicionales según la clase
		if (usuario instanceof Autor) {
			System.out.println("País de Nacimiento: " + ((Autor) usuario).getPaisNacimiento());
		} else if (usuario instanceof Cliente) {
			System.out.println("DNI del Cliente: " + ((Cliente) usuario).getDni());
		} else if (usuario instanceof Empleado) {
			System.out.println("Puesto del Empleado: " + ((Empleado) usuario).getPuesto());
		} else if (usuario instanceof Persona) {
			System.out.println("Fecha de Nacimiento: " + usuario.getFn());
		}
	}

	// Métodos auxiliares para solicitar datos

	private String solicitarTexto(String campo) {
		System.out.println(campo + ": ");
		return sc.next();
	}

	private LocalDate solicitarFechaNacimiento() {
		System.out.println("Fecha de nacimiento (año, mes, día): ");
		return LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
	}

}
