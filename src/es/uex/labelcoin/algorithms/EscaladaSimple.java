package es.uex.labelcoin.algorithms;

import es.uex.labelcoin.Tablero;

public class EscaladaSimple {

	public static void main(String[] args) {
		// TODO Implementar Escalada simple
		// Obtener fichero de argumento (si es que hay)
		String path = (args.length == 1) ? args[0] : "res/LABECOIN1.txt";
		// Carga del tablero
		Tablero t = new Tablero();
		if (t.leerFichero(path)) {
			t.printTablero();
			System.out.println("\nPrecio: " + t.getPrecio());
		}
	}

}
