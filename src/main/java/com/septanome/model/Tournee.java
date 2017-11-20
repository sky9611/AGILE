package main.java.com.septanome.model;

import java.util.List;

public class Tournee {
	private List<Chemin> chemins;
	
	public Tournee(List<Chemin> chemins) {
		this.chemins = chemins;
	}
	
	public List<Chemin> getChemins(){
		return chemins;
	}
	
}
