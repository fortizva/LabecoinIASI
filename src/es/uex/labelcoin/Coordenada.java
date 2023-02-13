package es.uex.labelcoin;

public class Coordenada {

	private int x, y;

	// Almacenar dos valores y poder sacarlos con el get

	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int a) {
		x = a;
	}

	public void setY(int b) {
		y = b;
	}
}
