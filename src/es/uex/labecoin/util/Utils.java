package es.uex.labecoin.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import es.uex.labecoin.Coordenada;
import es.uex.labecoin.Tablero;
import es.uex.labecoin.Tablero.Movimiento;

public class Utils {

	public static double getDistancia(Coordenada s, Coordenada t) {
		double x = Math.pow(t.getX() - s.getX(), 2);
		double y = Math.pow(t.getY() - s.getY(), 2);

		return Math.abs(Math.sqrt(x + y));
	}

	public static Coordenada calcularCoordenada(Coordenada s, Tablero.Movimiento m) {
		Coordenada target;

		switch (m) {
		case A:
			target = new Coordenada(s.getX(), s.getY() - 1);
			break;
		case B:
			target = new Coordenada(s.getX(), s.getY() + 1);
			break;
		case I:
			target = new Coordenada(s.getX() - 1, s.getY());
			break;
		case D:
			target = new Coordenada(s.getX() + 1, s.getY());
			break;
		case BI:
			target = new Coordenada(s.getX() - 1, s.getY() + 1);
			break;
		case AI:
			target = new Coordenada(s.getX() - 1, s.getY() - 1);
			break;
		case BD:
			target = new Coordenada(s.getX() + 1, s.getY() + 1);
			break;
		case AD:
			target = new Coordenada(s.getX() + 1, s.getY() - 1);
			break;
		default:
			target = new Coordenada(-1, -1);
			break;
		}

		return target;
	}

	public static void printCamino(ArrayList<Movimiento> camino, boolean success, Tablero t) {
		System.out.print("Camino: ");
		for (Movimiento mov : camino) {
			System.out.print(mov + ", ");
		}
		System.out.println("\n\nEl algoritmo ha generado " + t.nodos + " nodos en total");
		System.out.print("El algoritmo ");
		if (!success)
			System.out.print("no ");
		System.out.println("ha encontrado una solucion.");
	}

	// Extractor de argumentos de la terminal (Para enviar parámetros desde consola)
	public static HashMap<String, String> argumentParser(String[] args) {
		HashMap<String, String> map = new HashMap<>();
		if (args.length >= 2) {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-a": // Algoritmo
					if ((i + 1) < args.length) {
						int alg = 0;
						switch (args[i + 1]) {
						case "Estocastica": // Opcion 3
							alg++;
						case "MaximaPendiente": // Opcion 2
							alg++;
						case "EscaladaSimple": // Opcion 1
							alg++;
							i++; // Aumentamos i para no volver a comprobar el indice
							map.put("Algoritmo", Integer.toString(alg));
							break;
						}
					}
					break;
				case "-e": // Indice estocastico
					if ((i + 1) < args.length) {
						try {
							Double.parseDouble(args[i + 1]);
							if (Double.parseDouble(args[i + 1]) >= 0) {
								map.put("IndiceEstocastico", args[i + 1]);
							}
							i++; // Aumentamos i para no volver a comprobar el indice
						} catch (NumberFormatException e) {
							System.out.println(
									"Indice estocastico inválido! El indice estocastico debe ser un numero con decimales válido positivo");
						}
					}
					break;
				case "-f": // Fichero de tablero
					if ((i + 1) < args.length) {
						File f = new File(args[i + 1]);
						if (f.exists() && !f.isDirectory()) {
							map.put("Fichero", args[i + 1]);
							i++; // Aumentamos i para no volver a comprobar el indice
						}
					}
					break;

				}
			}
		}

		return map;
	}
}
