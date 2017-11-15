package com.septanome.model;

import java.util.List;

public class Tournee {
	private List<Point> ordresDeLivraisons;
	
	public Tournee(List<Point> ordresDeLivraisons) {
		this.ordresDeLivraisons = ordresDeLivraisons;
	}
	
	public List<Point> getListOrdreLivraison(){
		return ordresDeLivraisons;
	}
}
