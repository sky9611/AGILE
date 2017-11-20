package com.septanome.model;

import java.util.HashMap;

public class PlanLivraison {
	private HashMap<Long,Livraison> livraisonsMap;
	private HashMap<Long,HashMap<Long,Chemin>> cheminsMap;
	
	public PlanLivraison(HashMap<Long,Livraison> livraisonsMap,HashMap<Long,HashMap<Long,Chemin>> cheminsMap) {
		this.livraisonsMap = livraisonsMap;
		this.cheminsMap = cheminsMap;
	}
	
	public HashMap<Long,Livraison> getLivraisonsMap(){
		return livraisonsMap;
	}
	
	public HashMap<Long,HashMap<Long,Chemin>> getCheminsMap(){
		return cheminsMap;
	}
}
