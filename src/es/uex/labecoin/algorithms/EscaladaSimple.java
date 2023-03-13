package es.uex.labecoin.algorithms;

import java.util.ArrayList;

import es.uex.labecoin.Coordenada;
import es.uex.labecoin.Tablero;
import es.uex.labecoin.util.Utils;

public class EscaladaSimple {

	public static ArrayList<Tablero.Movimiento> camino = new ArrayList<>();

	public static void launch(Tablero t) {
		// TODO Implementar Escalada simple
		System.out.println("Labecoin: Escalada simple");

		boolean success = escaladaSimple(t);
		System.out.println();
		Utils.printCamino(camino, success);
	}

	public static boolean escaladaSimple(Tablero t) {
		boolean success = false, end = false, found = false;
		Tablero.Movimiento mov = Tablero.Movimiento.Abajo;
		Coordenada objetivo = new Coordenada(-1, -1);
		int i = 0;
		Tablero.Movimiento currentMov;
		// Seleccionamos el primer objetivo
		objetivo = seleccionarObjetivo(t);
		System.out.println("DEBUG: Primer objetivo = " + objetivo);
		// Obtenemos las h' disponibles y escogemos la mejor
		Coordenada c, tmp;
		while (!end) {
			end = true;
			c = t.robot;
			found = false;
			i = 0;
			while (!found && i < Tablero.Movimiento.values().length) {
				currentMov = Tablero.Movimiento.values()[i];
				tmp = Utils.calcularCoordenada(t.robot, currentMov);
				if (Utils.getDistancia(tmp, objetivo) < Utils.getDistancia(c, objetivo)
						&& t.comprobarMovimiento(currentMov)) {
					c = tmp;
					mov = currentMov;
					end = false;
					found = true;
				}
				i++;
			}

			if (!end) {
				// Mover robot
				t.moverRobot(mov);
				camino.add(mov);
				if (t.robot.equals(objetivo)) {
					if (t.monedas.containsKey(objetivo)) {
						t.monedas.remove(objetivo);
						objetivo = seleccionarObjetivo(t);
						System.out.println("DEBUG: Nuevo objetivo = " + objetivo);
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
