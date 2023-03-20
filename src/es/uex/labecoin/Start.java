package es.uex.labecoin;

import java.io.File;
import java.util.Scanner;

import es.uex.labecoin.algorithms.EscaladaSimple;
import es.uex.labecoin.algorithms.EscaladaSimpleEstocastico;
import es.uex.labecoin.algorithms.MaximaPendiente;

public class Start {

	public final static String default_path = "res/LABECOIN1.txt";

	public static void main(String[] args) {
		// Menu selector
		System.out.println("Labelcoin - IASI");
		System.out.println("Fichero del tablero: [" + default_path + "]");
		Scanner sc = new Scanner(System.in);
		String path = sc.nextLine();
		if (path == "")
			path = default_path;
		else {
			File f = new File(path).getAbsoluteFile();
			if (!f.exists() || f.isDirectory()) {
				System.out.println("Fichero invalido! Cargando fichero por defecto.");
				path = default_path;
			}
		}
		Tablero t = new Tablero();
		boolean read = t.leerFichero(path);
		if (read) {
			System.out.println("\nAlgoritmo:");
			boolean valid = false;
			while (!valid) {
				System.out.println("\t1)\tEscalada simple");
				System.out.println("\t2)\tMaxima pendiente");
				System.out.println("\t3)\tEscalada simple estocástico");
				int selection;
				try {
					selection = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					// Si hay un error al leer la opción introducida establecemos selection a -1
					// para que salte el error
					selection = -1;
				}

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
					int mejora = 1;
					while (!validMejora) {
						System.out.println("Cantidad de mejora:");
						try {
							mejora = Integer.parseInt(sc.nextLine());

							if (mejora >= 0)
								validMejora = true;
							else
								throw new Exception();
						} catch (Exception e) {
							System.out.println(
									"Seleccion invalida! La cantidad de mejora debe ser un número entero positivo:");
						}
					}

					EscaladaSimpleEstocastico.launch(t, mejora);
					break;
				default:
					System.out.println("Seleccion invalida! Pruebe con una de las siguientes opciones:");
					valid = false;
					break;
				}
			}
		} else {
			System.out.println("No se pudo cargar el fichero! Finalizando...");
		}
	}

}
