package com.septanome.model;

public class Livraison {
	private int heureDebut;
	private int heureFin;
	
	public Livraison(int d,int f){
		heureDebut=d;
		heureFin=f;
	}
	public void setHeureDebut(int d){
		heureDebut=d;
	}
	public void setHeureFin(int f){
		heureFin=f;
	}
	public int getHeureDebut(){
		return heureDebut;
	}
	public int getHeureFin(){
		return heureFin;
	}
}
