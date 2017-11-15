package com.septanome.model;

import java.util.*;

public class Commande {
	private int heureDeDepart;
	private Point entrepot;
	private List<Livraison> livraisons;
	
	public Commande(int point, Point heureDeDebut, List<Livraison> livra) {
		this.point = point;
		this.heureDeDebut = heureDeDebut;
		this.heureDeFin = heureDeFin;
	}
}
