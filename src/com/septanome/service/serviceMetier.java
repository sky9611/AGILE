package com.septanome.service;

import java.io.File;

import com.septanome.model.*;

public class serviceMetier {
	private Plan plan;
	private Plan planLivraison;
	private Commande commande;
	private Tournee tournee;
	
	/**
	* Initialiser le plan total a partir d'un ficher XML
	* @nomFicherDePlan: nom du ficher xml a lire  
	*/
	public void initPlan(String nomFicherDePlan) {
		//TODO
	}
	
	/**
	 *Initialiser la commande a partir d'un ficher XML
	 */
	public void initCommande(String nomFicherDeCommande) {
		
	}
	
	/**
	 *Initialiser le plan avec que les points de livrson et les routes le plus court entre eux calcule par dijkstra 
	 */
	public void initPlanLivraison() {
		//TODO
	}
	
	/**
	 *Chercher dans le Plan total le longeur de chemin plus court de livraison origine vers destination
	 */ 
	public int calcLePlusCourtChemin(long destinationID, long origineID) {
		//TODO
		return 0;
	}
	
	/**
	 *Trouver le tournee final en utlisant le plan de livraison genere
	 */
	public void obtenirLeTournee() {
		//TODO
	}
}
