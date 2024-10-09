package com.java.rogeregor.library.servicios;

import com.java.rogeregor.library.modelo.genericos.GenericoPersona;
import com.java.rogeregor.library.modelo.usuarios.*;
import com.java.rogeregor.library.usuarioDAO.*;
import com.java.rogeregor.library.Principal;

public class ServUsuario<U extends Persona> {

	private final UsuarioDAO<U> user;

	public ServUsuario(UsuarioDAO<U> user) {
		this.user = user;
	}

	// Método para registrar un nuevo usuario
	public void introPersona(Class<U> clazz, int id) {
		System.out.println("\n------- Nueva Persona -------");

		U nuevoUsuario = user.createUsuario((Class<U>) clazz, id);
		if (nuevoUsuario != null) {
			Principal.personas.put(nuevoUsuario.getId(), nuevoUsuario);
			Principal.usuarios.add(nuevoUsuario);
			System.out.println(
					"Usuario registrado exitosamente: " + nuevoUsuario.getNombre() + " " + nuevoUsuario.getApellido());

			// GenericoPersona<U> genericoPersona = new GenericoPersona<>(usuario);
			// genericoPersona.mostrarClase();
		} else {
			System.out.println("No se pudo registrar el usuario.");
		}
	}

	public void actualizarPersona(Class<U> clazz, int id) {
		U usuario = user.seleccionarUsuario(clazz, id);

		if (usuario == null) {
			System.out.println("Usuario no encontrado.");
			return;
		}

		U usuarioActualizado = user.updateUsuario(clazz, id);

		if (usuarioActualizado != null) {
			System.out.println("Usuario actualizado exitosamente: " + usuarioActualizado.getNombre() + " "
					+ usuarioActualizado.getApellido());

			GenericoPersona<U> genericoPersona = new GenericoPersona<>(usuarioActualizado);
			// genericoPersona.mostrarClase();
		} else {
			System.out.println("No se pudo actualizar el usuario.");
		}
	}

	// Método para eliminar un usuario por ID
	public void eliminarPersona(Class<U> clazz, int id) {
		boolean eliminado = user.deleteUsuario(clazz, id);
		if (eliminado) {
			System.out.println("Usuario eliminado exitosamente.");
		} else {
			System.out.println("No se encontró un usuario con el ID proporcionado.");
		}
	}

	// Método para mostrar todos los usuarios registrados
	public void mostrarPersonas() {
		System.out.println("Lista de Usuarios Registrados:");
		for (Persona usuario : Principal.usuarios) {
			System.out.println(usuario.getId() + ": " + usuario.getNombre() + " " + usuario.getApellido() + " ("
					+ usuario.getClass().getSimpleName() + ")");
		}
	}
}
