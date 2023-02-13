package es.uex.labelcoin.algorithms;

import es.uex.labelcoin.Coordenada;
import es.uex.labelcoin.Tablero;
import es.uex.labelcoin.util.Utils;

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
		} else
			System.exit(1);

		/*
		 * Demostración de la utilidad para medir distancia entre dos puntos del tablero
		 * Lo usaremos para la heurística
		 */
		Coordenada a = new Coordenada(1, 2);
		Coordenada b = new Coordenada(3, 1);
		System.out.println("DEBUG: Distancia entre dos puntos " + a.toString() + " y " + b.toString() + ": "
				+ Utils.getDistancia(a, b));
	}

}
