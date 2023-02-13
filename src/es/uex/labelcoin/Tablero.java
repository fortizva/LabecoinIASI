package es.uex.labelcoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Tablero {
	/*
	 * Empleamos dos variables para el tamaño del tablero para poder cargar mapas no
	 * cuadrados como extensión del proyecto.
	 */
	public final int MAX_WIDTH = 10;
	public final int MAX_HEIGHT = 10;
	public int price;
	public Coordenada robot, salida;
	private int[][] tablero;
	private HashMap<Coordenada, Integer> monedas;

	enum ElementoTablero {
		CELDA_VACIA(0), MONEDAS(6), SALIDA(7), ROBOT(8), MURO(9);

		public final int value;

		private ElementoTablero(int value) {
			this.value = value;
		}
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
							case ElementoTablero.ROBOT.value:
								if (!robot_b) {
									robot_b = true;
									robot = new Coordenada(i, j);
								} else {
									error = true;
									System.out.println("Error cargando tablero: Solo debe haber un robot.");
								}
								break;
							case ElementoTablero.SALIDA.value:
								if (!salida_b) {
									salida_b = true;
									salida = new Coordenada(i, j);

								} else {
									error = true;
									System.out.println("Error cargando tablero: Solo debe haber una salida.");
								}
								break;
							default:
								int currentValue = Integer.parseInt(values[j]);
								if (currentValue > ElementoTablero.CELDA_VACIA.value
										&& currentValue < ElementoTablero.MURO.value) {
									if (currentValue >= 1 && currentValue <= ElementoTablero.MONEDAS.value) {
										monedas.put(new Coordenada(i, j), currentValue);
									}
								} else {
									error = true;
									System.out.println(
											"Error cargando tablero: Valor \"" + currentValue + "\" desconocido.");
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
}
