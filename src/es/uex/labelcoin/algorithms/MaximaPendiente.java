package es.uex.labelcoin.algorithms;

import java.util.ArrayList;

import es.uex.labelcoin.Coordenada;
import es.uex.labelcoin.Tablero;
import es.uex.labelcoin.util.Utils;

public class MaximaPendiente {

	public static ArrayList<Tablero.Movimiento> camino = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Implementar Máxima pendiente
		System.out.println("Labelcoin: Máxima pendiente");
		System.out.println(Utils.getDistancia(new Coordenada(4, 1), new Coordenada(3, 1)));
		// Obtener fichero de argumento (si es que hay)
		String path = (args.length == 1) ? args[0] : "res/LABECOIN1.txt";
		// Carga del tablero
		Tablero t = new Tablero();
		if (t.leerFichero(path)) {
			t.printTablero();
			System.out.println("\nPrecio: " + t.getPrecio());
		} else
			System.exit(1);

		boolean success = maximaPendiente(t);
		System.out.println();
		Utils.printCamino(camino, success);
	}

	public static boolean maximaPendiente(Tablero t) {
		boolean success = false, end = false;
		Tablero.Movimiento mov = Tablero.Movimiento.Abajo;
		Coordenada objetivo = new Coordenada(-1, -1);

		// Seleccionamos el primer objetivo
		objetivo = seleccionarObjetivo(t);
		System.out.println("DEBUG: Primer objetivo = " + objetivo);
		// Obtenemos las h' disponibles y escogemos la mejor
		Coordenada c, tmp;
		while (!end) {
			end = true;
			c = t.robot;
			for (Tablero.Movimiento currentMov : Tablero.Movimiento.values()) {
				tmp = Utils.calcularCoordenada(t.robot, currentMov);
				if (Utils.getDistancia(tmp, objetivo) < Utils.getDistancia(c, objetivo) && t.comprobarMovimiento(currentMov)) {
					c = tmp;
					mov = currentMov;
					end = false;
				}
			}
			if (!end) {
				// Mover robot
				t.moverRobot(mov);
				camino.add(mov);
				if (t.robot.equals(objetivo)) {
					if (t.monedas.containsKey(objetivo)) {
						t.monedas.remove(objetivo);
						objetivo = seleccionarObjetivo(t);
						System.out.println("DEBUG: Nuevo objetivo = "+objetivo);
					} else // Caso de ser la salida
						end = true;
				}
			}
		}
		return success;
	}

	public static Coordenada seleccionarObjetivo(Tablero t) {
		Coordenada objetivo = null;
		double dist = -1; // Empleamos la distancia al objetivo como h'
		if (t.getPrecio() > t.getCartera()) {
			double tmp;
			for (int i = 0; i < t.monedas.size(); i++) {
				tmp = Utils.getDistancia(t.robot, (Coordenada) t.monedas.keySet().toArray()[i]);
				// TODO: Elegir la moneda de más valor a misma distancia.
				if (dist == -1 || tmp <= dist) {
					dist = tmp;
					objetivo = (Coordenada) t.monedas.keySet().toArray()[i];
				}
			}
		} else {
			objetivo = t.salida;
			dist = Utils.getDistancia(t.robot, t.salida);
		}

		return objetivo;
	}
}
