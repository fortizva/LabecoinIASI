package es.uex.labecoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import es.uex.labecoin.util.Constantes;
import es.uex.labecoin.util.Utils;

public class Tablero {
	/*
	 * Empleamos dos variables para el tamaño del tablero para poder cargar mapas no
	 * cuadrados como extensión del proyecto.
	 */
	public final int MAX_WIDTH = 10;
	public final int MAX_HEIGHT = 10;
	public int price, wallet,nodos = 0;
	public Coordenada robot, salida;
	private int[][] tablero;
	public HashMap<Coordenada, Integer> monedas;

	public enum Movimiento {
		A, B, I, D, AI, BI, AD, BD
	};

	public Tablero() {
		tablero = new int[MAX_HEIGHT][MAX_WIDTH];
		monedas = new HashMap<>();
	}
	
	public boolean leerFichero(String path) {
		boolean error = false, robot_b = false, salida_b = false;
		try {
			System.out.print("Leyendo fichero ");
			File f = new File(path).getAbsoluteFile();
			System.out.println("\"" + f.getPath() + "\"");
			if (f.exists() && !f.isDirectory()) {
				FileReader fr = new FileReader(f);
				BufferedReader bf = new BufferedReader(fr);

				String tmp;
				tmp = bf.readLine();
				price = Integer.parseInt(tmp);
				for (int i = 0; i < MAX_HEIGHT; i++) {
					if (!error) {
						tmp = bf.readLine();
						String[] values = tmp.split(",");
						for (int j = 0; j < MAX_WIDTH; j++) {
							if (!error) {
								switch (Integer.parseInt(values[j])) {
								case Constantes.ROBOT:
									if (!robot_b) {
										robot_b = true;
										robot = new Coordenada(j, i);
									} else {
										error = true;
										System.out.println("Error cargando [" + j + ", " + i
												+ "] en tablero: Solo debe haber un robot.");
									}
									break;
								case Constantes.SALIDA:
									if (!salida_b) {
										salida_b = true;
										salida = new Coordenada(j, i);

									} else {
										error = true;
										System.out.println("Error cargando [" + j + ", " + i
												+ "] en tablero: Solo debe haber una salida.");
									}
									break;
								default:
									int currentValue = Integer.parseInt(values[j]);
									if (currentValue >= Constantes.CELDA_VACIA && currentValue <= Constantes.MURO) {
										if (currentValue >= 1 && currentValue <= Constantes.MONEDAS) {
											monedas.put(new Coordenada(j, i), currentValue);
										}
									} else {
										error = true;
										System.out.println("Error cargando [" + j + ", " + i + "] en tablero: Valor \""
												+ currentValue + "\" desconocido.");
									}
									break;
								}
								tablero[i][j] = Integer.parseInt(values[j]);
							} else
								break;
						}
					} else
						break;
				}
				bf.close();

				if (!error)
					System.out.println("Fichero cargado correctamente.");
			} else
				error = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return !error;
	}

	public void printTablero() {
		for (int i = 0; i < MAX_HEIGHT; i++) {
			for (int j = 0; j < MAX_WIDTH; j++) {
				System.out.print(tablero[i][j] + "  ");
			}
			System.out.println();
		}

	}

	public int getPrecio() {
		return price;
	}

	public int getCartera() {
		return wallet;
	}

	public boolean moverRobot(Movimiento mov) {

		Coordenada prueba;

		// Calculamos la coordenada objetivo
		prueba = Utils.calcularCoordenada(robot, mov);

		switch (tablero[prueba.getY()][prueba.getX()]) {
		case Constantes.MURO:
			return false;
		case Constantes.SALIDA:

		case Constantes.CELDA_VACIA:
			tablero[prueba.getY()][prueba.getX()] = Constantes.ROBOT;
			tablero[robot.getY()][robot.getX()] = Constantes.CELDA_VACIA;
			robot = prueba;
			return true;
		default:
			wallet = wallet + tablero[prueba.getY()][prueba.getX()];
			tablero[prueba.getY()][prueba.getX()] = Constantes.ROBOT;
			tablero[robot.getY()][robot.getX()] = Constantes.CELDA_VACIA;
			monedas.remove(prueba);
			robot = prueba;
			return true;
		}

	}

	public boolean comprobarMovimiento(Movimiento mov) {
		Coordenada comprobacion = Utils.calcularCoordenada(robot, mov);
		if (comprobacion.getY() >= 0 && comprobacion.getY() < MAX_HEIGHT && comprobacion.getX() >= 0
				&& comprobacion.getX() < MAX_WIDTH)
			return (tablero[comprobacion.getY()][comprobacion.getX()] != Constantes.MURO);
		return false;
	}

}
