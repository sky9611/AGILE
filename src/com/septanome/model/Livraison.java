package com.septanome.model;

public class Livraison {
	private int heureDeDebut;
	private int heureDeFin;
	private Point point;
	
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
