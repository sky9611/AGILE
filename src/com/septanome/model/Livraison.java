package com.septanome.model;

public class Livraison extends Point{
	private int heureDeDebut;
	private int heureDeFin;
	
	public Livraison(Point point, int heureDeDebut, int heureDeFin) {
		this.point = point;
		this.heureDeDebut = heureDeDebut;
		this.heureDeFin = heureDeFin;
	}
	
	public Point getPoint() {
		return point;
	}
	public int getHeureDeDebut() {
		return heureDeDebut;
	}
	public int getHeureDeFin() {
		return heureDeFin;
	}
}
