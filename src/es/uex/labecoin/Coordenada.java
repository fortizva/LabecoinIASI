package es.uex.labecoin;

public class Coordenada {

	private int x, y;

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

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public boolean equals(Object o) {
		return (((Coordenada) o).getX() == this.getX() & ((Coordenada) o).getY() == this.getY());
	}
}
