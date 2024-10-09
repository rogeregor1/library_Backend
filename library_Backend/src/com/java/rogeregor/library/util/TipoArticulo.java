package com.java.rogeregor.library.util;

import com.java.rogeregor.library.modelo.articulos.DiscoCompacto;
import com.java.rogeregor.library.modelo.articulos.Ejemplar;
import com.java.rogeregor.library.modelo.articulos.Libro;
import com.java.rogeregor.library.modelo.articulos.Revista;

public enum TipoArticulo {
    LIBRO(1, Libro.class),
    REVISTA(2, Revista.class),
    COMPAC_DISC(3, DiscoCompacto.class);

    private final int codigo;
    private final Class<? extends Ejemplar> clase;

    TipoArticulo(int codigo, Class<? extends Ejemplar> clase) {
        this.codigo = codigo;
        this.clase = clase;
    }

    public int getCodigo() {
        return codigo;
    }

    public Class<? extends Ejemplar> getClase() {
        return clase;
    }

    public static TipoArticulo fromCodigo(int codigo) {
        for (TipoArticulo tipo : values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        return null; // Si no encuentra un tipo válido, retorna null
    }
}
