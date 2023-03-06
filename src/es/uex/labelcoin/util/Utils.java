package es.uex.labelcoin.util;

import java.util.ArrayList;

import es.uex.labelcoin.Coordenada;
import es.uex.labelcoin.Tablero;
import es.uex.labelcoin.Tablero.Movimiento;

public class Utils {

	public static double getDistancia(Coordenada s, Coordenada t) {
		double x = Math.pow(t.getX() - s.getX(), 2);
		double y = Math.pow(t.getY() - s.getY(), 2);

		return Math.abs(Math.sqrt(x + y));
	}

	public static Coordenada calcularCoordenada(Coordenada s, Tablero.Movimiento m) {
		Coordenada target;

		switch (m) {
		case Arriba:
			target = new Coordenada(s.getX(), s.getY() - 1);
			break;
		case Abajo:
			target = new Coordenada(s.getX(), s.getY() + 1);
			break;
		case Izquierda:
			target = new Coordenada(s.getX() - 1, s.getY());
			break;
		case Derecha:
			target = new Coordenada(s.getX() + 1, s.getY());
			break;
		case AbajoIzquierda:
			target = new Coordenada(s.getX() - 1, s.getY() + 1);
			break;
		case ArribaIzquierda:
			target = new Coordenada(s.getX() - 1, s.getY() - 1);
			break;
		case AbajoDerecha:
			target = new Coordenada(s.getX() + 1, s.getY() + 1);
			break;
		case ArribaDerecha:
			target = new Coordenada(s.getX() + 1, s.getY() - 1);
			break;
		default:
			target = new Coordenada(-1, -1);
			break;
		}

		return target;
	}

	public static void printCamino(ArrayList<Movimiento> camino) {
		for(Movimiento mov : camino) {
			System.out.print(mov+": ");
		}
	}
}
