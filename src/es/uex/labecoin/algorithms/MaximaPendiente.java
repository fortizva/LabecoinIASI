package es.uex.labecoin.algorithms;

import java.util.ArrayList;

import es.uex.labecoin.Coordenada;
import es.uex.labecoin.Tablero;
import es.uex.labecoin.util.Utils;

public class MaximaPendiente {

	public static ArrayList<Tablero.Movimiento> camino = new ArrayList<>();

	public static void launch(Tablero t) {
		// TODO Implementar Máxima pendiente
		System.out.println("Labecoin: Maxima pendiente\n");
		boolean success = maximaPendiente(t);
		System.out.println();
		Utils.printCamino(camino, success, t);
	}

	public static boolean maximaPendiente(Tablero t) {
		System.out.println("USANDO MAXIMO: " + t.maxMovimientos);
		boolean success = false, end = false;
		Tablero.Movimiento mov = Tablero.Movimiento.B;
		Coordenada objetivo = new Coordenada(-1, -1);

		// Seleccionamos el primer objetivo
		objetivo = seleccionarObjetivo(t);
		System.out.println("Primer objetivo = " + objetivo);
		// Obtenemos las h' disponibles y escogemos la mejor
		Coordenada c, tmp;
		while (!end && t.maxMovimientos != 0) { // Usamos maxMovimientos!=0 para poder usar -1 para movimientos sin
												// límite
			end = true;
			c = t.robot;
			for (Tablero.Movimiento currentMov : Tablero.Movimiento.values()) {
				if (t.maxMovimientos == 0)
					break;
				else {
					tmp = Utils.calcularCoordenada(t.robot, currentMov);
					t.nodos++;
					if (Utils.getDistancia(tmp, objetivo) < Utils.getDistancia(c, objetivo)
							&& t.comprobarMovimiento(currentMov)) {
						c = tmp;
						System.out.println("Movimientos--: " + t.maxMovimientos);
						mov = currentMov;
						end = false;
					}
				}
			}
			t.maxMovimientos--;
			if (!end) {
				// Mover robot
				t.moverRobot(mov);
				camino.add(mov);
				if (t.robot.equals(objetivo)) {
					if (t.monedas.containsKey(objetivo)) {
						t.monedas.remove(objetivo);
						objetivo = seleccionarObjetivo(t);
						System.out.println("Nuevo objetivo = " + objetivo);
					} else {// Caso de ser la salida
						end = true;
						if (t.price <= t.wallet)
							success = true;
					}
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
