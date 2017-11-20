package com.septanome.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import com.septanome.model.*;
import com.septanome.util.*;
import com.septanome.util.utilXML;
public class serviceMetier {
	public static int noPath = 9999;
	private Plan plan;
	private PlanLivraison planLivraison;
	private Commande commande;
	private Tournee tournee;
	private int length; //nombre de points stocke dans plan
	private utilXML myUtil;
	
	/**
	* Initialiser le plan total a partir d'un ficher XML
	* @nomFicherDePlan: nom du ficher xml a lire  
	*/
	public void initPlan(String nomFicherDePlan) {
		//TODO
		
		plan.setPointMap(myUtil.loadPoint(nomFicherDePlan));
		plan.setTronconsMap(myUtil.loadTroncon(nomFicherDePlan));
		length = plan.getPointsMap().size();
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
	public Chemin calcLePlusCourtChemin(long destinationID, long origineID) {
		//Chemin chemin = new Chemin();
		List<Livraison> path = null;
		boolean[] s = new boolean[length];
		HashMap<Long, Integer> indexMap = new HashMap<Long, Integer>();
		HashMap<Long, Point> pointsMap = plan.getPointsMap();
		HashMap<Long, Livraison> livraisonsMap = planLivraison.getLivraisonsMap();
		HashMap<Long, HashMap<Long, Troncon>> tronconMap = plan.getTronconsMap();
        double min; 
        int curNode = 0;
        double[] dist = new double[length];
        long[] prev = new long[length];
        int v=0;
        int indexOrigine=0;
        for(Map.Entry<Long,Point> entry:pointsMap.entrySet()){
        	//System.out.println(entry.getKey()+"="+entry.getValue());
//        	if(entry.getKey()==origineID) {
//        		indexOrigine = v;
//        	}
        	indexMap.put(entry.getKey(), v);
        	s[v] = false;
        	Troncon t = tronconMap.get(entry.getKey()).get(origineID);
        	if (t!=null) {
        		dist[v] = t.getLongeur();
        		prev[v] = origineID;
        	} else {
        		dist[v] = noPath;
        		prev[v] = 0;
        	}
        	v++;
        }
        
        indexOrigine = indexMap.get(origineID);
        path.add(livraisonsMap.get(destinationID));
        dist[indexOrigine] = 0;
        s[indexOrigine] = true;
        
        for (int i = 1; i < length; i++){
           min = 9999;
           for (int w = 0; w < length; w++)
           {
              if (!s[w] && dist[w] < min)
              {
                 curNode = w;
                 min = dist[w];
              }
           }

           s[curNode] = true;

           for (int j = 0; j < length; j++) {
        	   Troncon tr = tronconMap.get(indexMap.get(curNode)).get(indexMap.get((long)j));
        	   double l = (tr!=null)?tr.getLongeur():noPath;
        	   if (!s[j] && min + l < dist[j])
        	   {
        		   dist[j] = min + l;
        		   prev[j] = curNode;
        	   }
           }
        }
     


		return null;
	}
	
	/**
	 *Trouver le tournee final en utlisant le plan de livraison genere
	 */
	public void obtenirLeTournee() {
		//TODO
	}
}
