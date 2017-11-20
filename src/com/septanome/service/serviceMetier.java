package com.septanome.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
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
		commande = myUtil.loadCommande(nomFicherDeCommande, plan);
	}
	
	/**
	 *Initialiser le plan avec que les points de livraison et les routes les plus courts entre eux calcules par dijkstra 
	 */
	public void initPlanLivraison() {
        //l'entrepot est considere comme un objet Livraison dont l'attribut heureDeDepart devient heureDeDebut et heureDeFin est 9999 par defaut
        Livraison entrepot = new Livraison(commande.getEntrepot().getId(),commande.getEntrepot().getCoordX(),commande.getEntrepot().getCoordY(),commande.getHeureDeDepart(),9999);      
        
        HashMap<Long,Livraison> livraisonsMap = new HashMap<Long,Livraison>();
        livraisonsMap.put(entrepot.getId(), entrepot);
        HashMap<Long,HashMap<Long,Chemin>> cheminsMap = new HashMap<Long,HashMap<Long,Chemin>>();
        HashMap<Long,Chemin> cm = new HashMap<Long,Chemin>();
        for (Livraison l:commande.getListLivraison()) {
            livraisonsMap.put(l.getId(),l);
            cm.clear();
            cheminsMap.putAll(calcLePlusCourtChemin(l.getId()));
        }
        planLivraison.setLivraisonsMap(livraisonsMap);
        planLivraison.setCheminsMap(cheminsMap);
    }
	
	/**
	 *Chercher dans le Plan total la longueur de chemin plus courte de livraison origine vers destination
	 */ 
	public HashMap<Long,HashMap<Long,Chemin>> calcLePlusCourtChemin(long origineID) {
		//Chemin chemin = new Chemin();
		class dist implements Comparable<dist> {
			long index;
			double value;
			public dist(long index, double value) {
				this.index = index;
				this.value = value;
			}
			@Override
			public int compareTo(dist o) {
				if(o.value<this.value) {
					return 1;
				} else if(o.value>this.value) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		boolean[] s = new boolean[length];
		//HashMap<Long, Integer> indexMap = new HashMap<Long, Integer>();
		HashMap<Long, Point> pointsMap = plan.getPointsMap();
		HashMap<Long, Double> distMap = new HashMap<Long, Double>();
		HashMap<Long, Long> prev = new HashMap<Long,Long>();
		HashMap<Long, Livraison> livraisonsMap = planLivraison.getLivraisonsMap();
		HashMap<Long, HashMap<Long, Troncon>> tronconMap = plan.getTronconsMap();
		List<Long> neighbourList = new ArrayList<>();
		
		PriorityQueue<dist> queue=new PriorityQueue<dist>(pointsMap.size());
		Map<Long, Double> map=new HashMap<Long, Double>();
        map.put(origineID, 0.0);
        queue.add(new dist(map.entrySet().iterator().next().getKey(),map.entrySet().iterator().next().getValue()));
        Map<Long, Double> visited=new HashMap<Long, Double>();
        while(!queue.isEmpty()) {
        	dist d = queue.poll();
        	distMap.put(d.index, d.value);
        	if(visited.get(d.index)==null) {
        		visited.put(d.index, d.value);
        	}
        	if(tronconMap.get(d.index)==null) {
        		continue;
        	}
        	neighbourList.clear();
    		for(Map.Entry<Long, Troncon> entry:tronconMap.get(d.index).entrySet()){
    			neighbourList.add(entry.getKey());
    		}
        	for(long i:neighbourList) {
        		if(visited.get(i)!=null) {
        			continue;
        		}
        		double newlength = d.value+tronconMap.get(d.index).get(i).getLongeur();
        		if(newlength<distMap.get(i)) {
        			prev.put(i, d.index);
        		}
        		dist d2 = new dist(tronconMap.get(d.index).get(i).getDestinationID(),newlength);
        		queue.add(d2);
        	}
        }
        HashMap<Long,HashMap<Long,Chemin>> cheminMap = new HashMap<Long,HashMap<Long,Chemin>>();
        HashMap<Long,Chemin> origineCheminMap = new HashMap<Long,Chemin>();
        List<Long> destinationList = new ArrayList<>();
        for(Map.Entry<Long, Livraison> entry:livraisonsMap.entrySet()){
        	long key = entry.getKey();
        	if(key!=origineID)
        		destinationList.add(entry.getKey());
		}
        for (long id : destinationList) {
        	List<Troncon> tronconList = new ArrayList<>();
        	long tempDes = id;
        	long tempSta = 0;
        	while(prev.get(tempDes)!=null) {
        		tempSta = prev.get(tempDes);
        		tronconList.add(tronconMap.get(tempSta).get(tempDes));
        	}
        	Chemin chemin = new Chemin(id,origineID,tronconList);
        	origineCheminMap.put(id, chemin);
        }
        cheminMap.put(origineID, origineCheminMap);
		return cheminMap;
	}
	
	/**
	 *Trouver le tournee final en utilisant le plan de livraison genere
	 */
	public void obtenirLeTournee() {
		//TODO
		
		
	}
}
