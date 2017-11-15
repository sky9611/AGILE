package com.septanome.model;

public class Tournee {
	private Point[] ordreLivraison;
	
	public Tournee(	Point[] l){
		ordreLivraison=new Point[l.length];
		for(int i=0;i<l.length;i++){
			ordreLivraison[i]=l[i];
		}
	}
	public Point[] getOrdreLivraison(){
		return ordreLivraison;
	}
	public void setOrdreLivraison(Point[] l){
		ordreLivraison=new Point[l.length];
		for(int i=0;i<l.length;i++){
			ordreLivraison[i]=l[i];
		}
	}
}
