package com.septanome.model;

import java.util.*;

public class Commande {
	private int heureDeDepart;
	private Point entrepot;
	private List<Livraison> livraisons;

	public Commande(int heureDeDepart, Point entrepot, List<Livraison> livraisons) {
		this.heureDeDepart = heureDeDepart;
		this.entrepot = entrepot;
		this.livraisons = livraisons;
	}

	public int getHeureDeDepart() {
		return heureDeDepart;
	}
	public Point getEntrepot() {
		return entrepot;
	}
	public List<Livraison> getListLivraison(){
		return livraisons;
	}
}
