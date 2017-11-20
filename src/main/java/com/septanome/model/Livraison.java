package main.java.com.septanome.model;

public class Livraison extends Point {
	private int heureDeDebut;
	private int heureDeFin;

	public Livraison(long id, int coordX, int coordY, int heureDeDebut, int heureDeFin) {
		super(id, coordX, coordY);
		this.heureDeDebut = heureDeDebut;
		this.heureDeFin = heureDeFin;
	}

	public int getHeureDeDebut() {
		return heureDeDebut;
	}

	public int getHeureDeFin() {
		return heureDeFin;
	}

	public void setHeureDeDebut(int heureDeDebut) {
		this.heureDeDebut = heureDeDebut;
	}
	public void setHeureDeFin(int heureDeFin) {
		this.heureDeFin = heureDeFin;
	}
	@Override
	public String toString() {
		return "("+id+", "+coordX+", "+coordY+", "+heureDeDebut+", "+heureDeFin+")";
	}
}
