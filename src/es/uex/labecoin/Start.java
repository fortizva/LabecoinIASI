package es.uex.labecoin;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import es.uex.labecoin.algorithms.EscaladaSimple;
import es.uex.labecoin.algorithms.EscaladaSimpleEstocastico;
import es.uex.labecoin.algorithms.MaximaPendiente;
import es.uex.labecoin.util.Utils;

public class Start {

	public final static String default_path = "res/LABECOIN1.txt";

	public static void main(String[] args) {
		// Extraer argumentos de la consola
		HashMap<String, String> map = Utils.argumentParser(args);

		long time;

		// Menu selector
		System.out.println("Labecoin - IASI");
		String path;
		Scanner sc = new Scanner(System.in);

		if (!map.containsKey("Fichero")) {
			System.out.println("Fichero del tablero: [" + default_path + "]");
			path = sc.nextLine();
			if (path.isEmpty())
				path = default_path;
			else {
				File f = new File(path).getAbsoluteFile();
				if (!f.exists() || f.isDirectory()) {
					System.out.println("Fichero invalido! Cargando fichero por defecto.");
					path = default_path;
				}
			}
		} else {
			path = map.get("Fichero");
		}
		Tablero t = new Tablero();
		
		// Cambiar el ancho/alto del tablero
		if(map.containsKey("Width"))
			t.setMaxWidth(Integer.parseInt(map.get("Width")));
		if(map.containsKey("Height"))
			t.setMaxHeight(Integer.parseInt(map.get("Height")));
		
		boolean read = t.leerFichero(path);
		if (read) {
			int maxMovs = -1;
			String str;
			while (maxMovs < 0) {
				System.out.println("Seleccione el numero máximo de movimientos [Sin límite: 0]");
				str = sc.nextLine();
				if (str.isEmpty())
					maxMovs = 0;
				else {
					try {
						maxMovs = Integer.parseInt(str);
					} catch (NumberFormatException e) {
						// Si hay un error al leer la opción introducida establecemos selection a -1
						// para que salte el error
						maxMovs = -1;
					}

					if (maxMovs < 0) {
						System.out.println("Número inválido, el número máximo debe tener un valor entero positivo!");
					}
				}
			}
			maxMovs = (maxMovs == 0) ? -1 : maxMovs; // Cambiamos el valor 0 a -1 para definir un número ilimitado de movimientos
			t.setMovimientos(maxMovs);
			
			boolean valid = false;
			while (!valid) {
				int selection;
				if (!map.containsKey("Algoritmo")) {
					System.out.println("\nAlgoritmo:");
					System.out.println("\t1)\tEscalada simple");
					System.out.println("\t2)\tMaxima pendiente");
					System.out.println("\t3)\tEscalada simple estocástico");
					try {
						selection = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						// Si hay un error al leer la opción introducida establecemos selection a -1
						// para que salte el error
						selection = -1;
					}
				} else {
					selection = Integer.parseInt(map.get("Algoritmo"));
					valid = true;
				}

				time = System.nanoTime();
				switch (selection) {
				case 1:
					valid = true;
					EscaladaSimple.launch(t);
					break;
				case 2:
					valid = true;
					MaximaPendiente.launch(t);
					break;
				case 3:
					valid = true;
					boolean validMejora = false;
					double mejora = 1.0;
					if (!map.containsKey("IndiceEstocastico")) {
						while (!validMejora) {
							System.out.println("Cantidad de mejora:");
							try {
								mejora = Double.parseDouble(sc.nextLine());

								if (mejora >= 0)
									validMejora = true;
								else
									throw new Exception();
							} catch (Exception e) {
								System.out.println(
										"Seleccion invalida! La cantidad de mejora debe ser un número entero positivo:");
							}
						}
					} else {
						mejora = Double.parseDouble(map.get("IndiceEstocastico"));
					}
					time = System.nanoTime();
					EscaladaSimpleEstocastico.launch(t, mejora);
					break;
				default:
					System.out.println("Seleccion invalida! Pruebe con una de las siguientes opciones:");
					valid = false;
					break;
				}
				time = System.nanoTime() - time;
				System.out.println("\nTiempo del proceso: " + time + "ns (" + time / 1000000.0 + "ms)");

			}
		} else {
			System.out.println("No se pudo cargar el fichero! Finalizando...");
		}
		sc.close();
	}

}
