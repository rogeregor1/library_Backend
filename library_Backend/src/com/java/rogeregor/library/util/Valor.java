package com.java.rogeregor.library.util;


public class Valor {
    
    private static int id = 10;
    private static int cod = 100;
    private static int tk = 1000;
    private static int prestamosRealizados = 0;
    private static int indice = 1;
    private static int prestamoId = 1000;
	private static int deudorId = 0;
    
    // Synchronized para evitar problemas de concurrencia
    public static synchronized int getId(){
        return id++;
    }

    public static synchronized int getCod(){
        return cod++;
    }

    public static synchronized int getTk() {
        return tk++;
    }

    public static synchronized int getPrestamosRealizados() {
        return prestamosRealizados++;
    }

    public static synchronized int getIndice(){
        return indice++;
    }

    public static synchronized int getPrestamoId() {
		return prestamoId++;
	}
    
    public static synchronized int getDeudorId(){
        return deudorId++;
    }

	// Método para inicializar valores (útil en aplicaciones que mantienen estado)
    public static void setId(int newId) {
        id = newId;
    }

    public static void setCod(int newCod) {
        cod = newCod;
    }

    public static void setTk(int newTk) {
        tk = newTk;
    }

    public static void setPrestamosRealizados(int newPrestamosRealizados) {
        prestamosRealizados = newPrestamosRealizados;
    }

    public static void setIndice(int newIndice) {
        indice = newIndice;
    }

	public static void setPrestamoId(int newPrestamoId) {
		prestamoId = newPrestamoId;
	}

	public static void setDeudorId(int newDeudorId) {
		deudorId = newDeudorId;
	}
	
	
    
}