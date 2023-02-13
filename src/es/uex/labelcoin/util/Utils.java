package es.uex.labelcoin.util;

import es.uex.labelcoin.Coordenada;

public class Utils {

	public static double getDistancia(Coordenada s, Coordenada t) {
		double x = Math.pow(t.getX() - s.getX(), 2);
		double y = Math.pow(t.getY() - s.getY(), 2);

		return Math.abs(Math.sqrt(x + y));
	}
}
