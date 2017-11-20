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
	
	public void setHeureDeDepart(int heureDeDepart) {
		this.heureDeDepart = heureDeDepart;
	}
	
	public Point getEntrepot() {
		return entrepot;
	}
	
	public void setEntrepot(Point entrepot) {
		this.entrepot = entrepot;
	}
	
	public List<Livraison> getListLivraison(){
		return livraisons;
	}
<<<<<<< HEAD
	public void addLivraison(Livraison l) {
		this.livraisons.add(l);
=======
	
	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
>>>>>>> 126bf821ca53c74ae4f2c356522ccdfefbf676b0
	}
}
