package com.java.rogeregor.library.usuarioDAO;

import com.java.rogeregor.library.modelo.usuarios.Persona;

public interface UsuarioDAO<U extends Persona> {
	
	U createUsuario(Class<U> clazz, int id);
	U updateUsuario(Class<U> clazz, int id);
	boolean deleteUsuario(Class<U> clazz, int id);
	void viewUsuario(Class<U> clazz, int id);
	U seleccionarUsuario(Class<U> clazz, int id);
}

