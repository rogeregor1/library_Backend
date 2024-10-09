package interfaces;

public interface CondicionEjemplar {
	 enum Condicion { ANTIGUO, NUEVO, REGULAR }
	  
	 // Método para evaluar la apariencia o condición del ejemplar
	 Condicion aparienciaEjemplar(int antiguedad);
}
