package es.uex.labecoin.util;

import java.util.ArrayList;

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
		for(Movimiento mov : camino) {
			System.out.print(mov+", ");
		}
		System.out.println("\n\nEl algoritmo ha generado " +  t.nodos + " nodos en total");
		System.out.print("El algoritmo ");
		if(!success)
			System.out.print("no ");
		System.out.println("ha encontrado una solucion.");
	}
}
