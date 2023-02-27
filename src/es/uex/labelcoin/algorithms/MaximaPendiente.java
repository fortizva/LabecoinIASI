package es.uex.labelcoin.algorithms;

import java.util.ArrayList;

import es.uex.labelcoin.Coordenada;
import es.uex.labelcoin.Tablero;
import es.uex.labelcoin.util.Utils;

public class MaximaPendiente {

	public ArrayList<Tablero.Movimientos> camino = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Implementar Máxima pendiente
		System.out.println("Labelcoin: Máxima pendiente");
		// Obtener fichero de argumento (si es que hay)
		String path = (args.length == 1) ? args[0] : "res/LABECOIN1.txt";
		// Carga del tablero
		Tablero t = new Tablero();
		if (t.leerFichero(path)) {
			t.printTablero();
			System.out.println("\nPrecio: " + t.getPrecio());
		} else
			System.exit(1);

		maximaPendiente(t);
	}

	public static boolean maximaPendiente(Tablero t) {
		boolean success = false, end = false;
		Tablero.Movimiento mov;
		Coordenada objetivo;
		double dist = -1; // Empleamos la distancia al objetivo como h'
		// Seleccionamos el primer objetivo
		if (t.getPrecio() > t.getCartera()) {
			double tmp;
			for (int i = 0; i < t.monedas.size(); i++) {
				tmp = Utils.getDistancia(t.robot, (Coordenada) t.monedas.keySet().toArray()[i]);
				if (dist == -1 || tmp < dist || (tmp == dist && t.monedas.get((Coordenada) t.monedas.keySet().toArray()[i]) > t.monedas.get(objetivo))) {
						dist = tmp;
						objetivo = (Coordenada) t.monedas.keySet().toArray()[i];
				}
			}
		} else {
			objetivo = t.salida;
			dist = Utils.getDistancia(t.robot, t.salida);
		}
		// Obtenemos las h' disponibles y escogemos la mejor
		Coordenada c, tmp;
		Tablero.Movimiento mov;
		while (!end) {
			end = true;
			for (Tablero.Movimiento currentMov : Tablero.Movimiento.values()) {
				tmp = Utils.calcularCoordenada(t.robot, currentMov);
				if(Utils.getDistancia(tmp, objetivo) < Utils.getDistancia(c, objetivo)) {
					c = tmp;
					mov = currentMov;
					end = false;
				}
			}
			if(!end) {
				// Mover robot
			}
		}

	return success;
}}
