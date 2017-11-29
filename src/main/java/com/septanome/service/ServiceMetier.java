package com.septanome.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import com.septanome.model.*;
import com.septanome.util.TSPTW;;
import com.septanome.util.UtilXML;
import tsp.TSP1;

public class ServiceMetier {
	private final int noPath = 9999;
	public Plan plan = new Plan();
	private PlanLivraison planLivraison = new PlanLivraison();
	public Commande commande = new Commande();
	private Tournee tournee = new Tournee();
	private int nombreDeLivraison;
	private UtilXML myUtil = new UtilXML();
	//private MyTSP tsp = new MyTSP();
	private TSP1 tsp = new TSP1();
	private final double vitesse = 15000/3600;
	/**
	* Initialiser le plan total a partir d'un ficher XML
	* @nomFicherDePlan: nom du ficher xml a lire  
	*/
	public void initPlan(String nomFicherDePlan) {
		//TODO
		plan.setPointMap(myUtil.loadPoint(nomFicherDePlan));
		plan.setTronconsMap(myUtil.loadTroncon(nomFicherDePlan));
		plan.getPointsMap().size();
	}
	
	/**
	 *Initialiser la commande(demande de livraisons) a partir d'un ficher XML
	 */
	public void initCommande(String nomFicherDeCommande) {
		commande = myUtil.loadCommande(nomFicherDeCommande, plan);
		nombreDeLivraison = commande.getListLivraison().size();
	}
	
	/**
	 *Initialiser le plan avec que les points de livraison et les routes les plus courts entre eux calcules par dijkstra 
	 */
	public void initPlanLivraison() {
        //l'entrepot est considere comme un objet Livraison dont l'attribut heureDeDepart devient heureDeDebut et heureDeFin est 9999 par defaut
        Livraison entrepot = new Livraison(commande.getEntrepot().getId(),commande.getEntrepot().getCoordX(),commande.getEntrepot().getCoordY(),0,commande.getHeureDeDepart(),9999);      
        
        HashMap<Long,Livraison> livraisonsMap = new HashMap<Long,Livraison>();
        livraisonsMap.put(entrepot.getId(), entrepot);
        HashMap<Long,HashMap<Long,Chemin>> cheminsMap = new HashMap<Long,HashMap<Long,Chemin>>();
        //HashMap<Long,Chemin> cm = new HashMap<Long,Chemin>();

        for (Livraison l:commande.getListLivraison()) {
            livraisonsMap.put(l.getId(),l);
            //cm.clear();
        }
        planLivraison.setLivraisonsMap(livraisonsMap);
        cheminsMap.putAll(calcLePlusCourtChemin(entrepot.getId()));
        for (Livraison l:commande.getListLivraison()) {
            cheminsMap.putAll(calcLePlusCourtChemin(l.getId()));
        }

        
        planLivraison.setCheminsMap(cheminsMap);
    }
	
	/**
	 *Chercher dans le Plan total la longueur de chemin plus courte de livraison origine vers destination
	 */ 
	public HashMap<Long,HashMap<Long,Chemin>> calcLePlusCourtChemin(long origineID) {
		//Chemin chemin = new Chemin();
		//System.out.println("origineID="+origineID);
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
		//HashMap<Long, Integer> indexMap = new HashMap<Long, Integer>();
		HashMap<Long, Point> pointsMap = plan.getPointsMap();
		HashMap<Long, Double> distMap = new HashMap<Long, Double>();
		HashMap<Long, Long> prev = new HashMap<Long,Long>();
		HashMap<Long, Livraison> livraisonsMap = planLivraison.getLivraisonsMap();
		HashMap<Long, HashMap<Long, Troncon>> tronconMap = plan.getTronconsMap();
		List<Long> neighbourList = new ArrayList<>();
		
		PriorityQueue<dist> queue=new PriorityQueue<dist>(pointsMap.size());
		//Map<Long, Double> map=new HashMap<Long, Double>();
        //map.put(origineID, 0.0);
		for(Map.Entry<Long, Point> entry:pointsMap.entrySet()){
        	distMap.put(entry.getKey(),(double)noPath);
		}
        queue.add(new dist(origineID,0.0));
        Map<Long, Double> visited=new HashMap<Long, Double>();
        
        //System.out.println(distMap);	
        //System.out.println(plan.getTronconsMap());	
        List<Long> destinationList = new ArrayList<>();
        //System.out.println(livraisonsMap);
        for(Map.Entry<Long, Livraison> entry:livraisonsMap.entrySet()){
        	long key = entry.getKey();
        	if(key!=origineID)
        		destinationList.add(key);
		}
        while(!queue.isEmpty()) {
        	dist d = queue.poll();
        	//System.out.println(d.index+" "+d.value);	
        	if(visited.get(d.index)==null) {
        		visited.put(d.index, d.value);
        	}
        	if(tronconMap.get(d.index)==null) {
        		continue;
        	}
        	neighbourList.clear();
        	//System.out.println(tronconMap.get((long)0).entrySet());
    		for(Map.Entry<Long, Troncon> entry:tronconMap.get(d.index).entrySet()){
    			//System.out.println(entry.getKey()+" "+entry.getValue());	
    			neighbourList.add(entry.getKey());
    		}
        	for(long i:neighbourList) {
        		if(visited.get(i)!=null) {
        			continue;
        		}
        		double newlength = d.value+tronconMap.get(d.index).get(i).getLongeur();
        		//System.out.println(d.index+" "+i);	
        		if(newlength<distMap.get(i)) {
        			prev.put(i, d.index);
        		}
        		dist d2 = new dist(tronconMap.get(d.index).get(i).getDestinationID(),newlength);
        		queue.add(d2);
        	}
        }
        HashMap<Long,HashMap<Long,Chemin>> cheminMap = new HashMap<Long,HashMap<Long,Chemin>>();
        HashMap<Long,Chemin> origineCheminMap = new HashMap<Long,Chemin>();

        
//        for(Map.Entry<Long, Long> entry:prev.entrySet()){
//        	System.out.println(entry.getKey()+" "+entry.getValue());
//		}
        //System.out.println(destinationList);
        //System.out.println(tronconMap);
        for (long id : destinationList) {
        	List<Troncon> tronconList = new ArrayList<>();
        	long tempDes = id;
        	long tempSta = 0;
        	while(prev.get(tempDes)!=null) {
        		tempSta = prev.get(tempDes);
        		//System.out.println(tronconMap.get(tempSta).get(tempDes));
        		tronconList.add(tronconMap.get(tempSta).get(tempDes));
        		tempDes = tempSta;
        	}
        	Collections.reverse(tronconList);
        	Chemin chemin = new Chemin(id,origineID,tronconList);
        	System.out.println(chemin);
        	origineCheminMap.put(id, chemin);
        }
        cheminMap.put(origineID, origineCheminMap); 
		return cheminMap;
	}
	
	/**
	 *Trouver le tournee final en utilisant le plan de livraison genere
	 *@param b consider or not the time interval
	 */
	public void calculerTournee(boolean b) throws ClassNotFoundException, IOException{
		if (b) {
			TSPTW tsptw = new TSPTW(planLivraison, commande);
			tournee = (tsptw.findSolution(10));
		} else {
			int tpsLimite = 1000;
			HashMap<Long,HashMap<Long,Chemin>> cheminsMap = planLivraison.getCheminsMap();
			List<Livraison> listLivraisons = commande.getListLivraison();
			int[][] cout = new int[nombreDeLivraison+1][nombreDeLivraison+1];
			long idEntrepot = commande.getEntrepot().getId();
			int[] duree = new int[cheminsMap.size()];
			for(int i=0;i<cheminsMap.size();i++) {
				long startID = 0;
				long desID = 0;
				if(i==0) {
					startID = idEntrepot;
					//duree[(int) startID]=24*3600;
					duree[i] = 0;
				} else {
					startID = listLivraisons.get(i-1).getId();
					//duree[(int) startID]=(listLivraisons.get(i-1).getHeureDeFin()-listLivraisons.get(i-1).getHeureDeDebut())*3600;
					duree[i] = listLivraisons.get(i-1).getDuree();
				}
				
				
				for(int j=0;j<cheminsMap.size();j++) {
					if(j==0) {
						desID = idEntrepot;
					} else {
						desID = listLivraisons.get(j-1).getId();
					}
					if(startID == desID) {
						cout[i][j] = 0;
					} else {
						cout[i][j] = (int)(cheminsMap.get(startID).get(desID).getLongeur()/vitesse);
					}
				}
			}
			for(int temp:duree) {
				System.out.println(temp);
			}
			for(int i=0;i<cheminsMap.size();i++) {
				for(int j=0;j<cheminsMap.size();j++) {
					long startID = (i==0)?idEntrepot:listLivraisons.get(i-1).getId();
					long desID = (j==0)?idEntrepot:listLivraisons.get(j-1).getId();
					System.out.println("cout("+startID+","+desID+"): "+cout[i][j]);
						
				}
					
			}
			
			tsp.chercheSolution(tpsLimite, cheminsMap.size(), cout, duree);
			System.out.println(tsp.getMeilleureSolution(0));
			System.out.println(tsp.getMeilleureSolution(1));
			System.out.println(tsp.getMeilleureSolution(2));
			List<Chemin> cheminList = new ArrayList<>();
			for(int k=0;k<cheminsMap.size();k++) {
				if(k==0) {
					Chemin chemin = cheminsMap.get(idEntrepot).get(listLivraisons.get(tsp.getMeilleureSolution(k+1)-1).getId());
					cheminList.add(chemin);
				} else if (k<cheminsMap.size()-1) {
					//System.out.println(listLivraisons.get(tsp.getMeilleureSolution(k)-1).getId());
					//System.out.println(listLivraisons.get(tsp.getMeilleureSolution(k+1)-1).getId());
					Chemin chemin = cheminsMap.get(listLivraisons.get(tsp.getMeilleureSolution(k)-1).getId()).get(listLivraisons.get(tsp.getMeilleureSolution(k+1)-1).getId());
					cheminList.add(chemin);
				} else {
					Chemin chemin = cheminsMap.get(listLivraisons.get(tsp.getMeilleureSolution(k)-1).getId()).get(idEntrepot);
					cheminList.add(chemin);
				}
			}
			//System.out.println(cheminList);
//			int[] dureeOrigin = duree; 
//			List<Chemin> cheminList = new ArrayList<>();
//			myUtil.bubbleSort(duree);
//			for(int k=0;k<duree.length;k++) {
//				int indexStart;
//				int indexDes;
//				if (k!=duree.length-1) {
//					indexStart = myUtil.getIndex(dureeOrigin, duree[k]);
//					indexDes = myUtil.getIndex(dureeOrigin, duree[k+1]);
//				} else {
//					indexStart = myUtil.getIndex(dureeOrigin, duree[k]);
//					indexDes = myUtil.getIndex(dureeOrigin, duree[0]);
//				}
//
//				if (indexStart != 0) {
//					Chemin chemin = cheminsMap.get(listLivraisons.get(indexStart-1).getId()).get(listLivraisons.get(indexDes-1).getId());
//					cheminList.add(chemin);
//				} else {
//					Chemin chemin = cheminsMap.get(idEntrepot).get(listLivraisons.get(indexDes-1).getId());
//					cheminList.add(chemin);
//				}
//			}
			tournee.setChemins(cheminList);
			//TODO remove System.out.println
			System.out.println(tournee.getChemins());
		}

	}	

	/**
	 * Get tournee
	 */
	public Tournee getTournee() {
		//TODO remove System.out.println
		System.out.println(tournee.getChemins());
		return tournee;
	}
}

