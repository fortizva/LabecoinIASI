package es.uex.labelcoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Tablero {
	/*
	 * Empleamos dos variables para el tamaño del tablero para poder cargar mapas no
	 * cuadrados como extensión del proyecto.
	 */
	public final int MAX_WIDTH = 10;
	public final int MAX_HEIGHT = 10;
	public int price;
	private int[][] tablero;

	public Tablero() {
		tablero = new int[MAX_HEIGHT][MAX_WIDTH];
	}

	public boolean leerFichero(String path) {
		boolean success = false;
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
				tmp = bf.readLine();
				String[] values = tmp.split(",");
				for (int j = 0; j < MAX_WIDTH; j++) {
					tablero[i][j] = Integer.parseInt(values[j]);
				}
			}
			bf.close();
			success = true;
			System.out.println("Fichero cargado correctamente.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
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
