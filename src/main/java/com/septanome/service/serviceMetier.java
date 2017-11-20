package main.java.com.septanome.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import main.java.com.septanome.model.*;
import main.java.com.septanome.util.*;
import main.java.com.septanome.util.utilXML;
public class serviceMetier {
	public static int noPath = 9999;
	private Plan plan;
	private PlanLivraison planLivraison;
	private Commande commande;
	private Tournee tournee;
	private int length; //nombre de points stocke dans plan
	private int nombreDeLivraison;
	private utilXML myUtil;
	private TemplateTSP tsp;
	private final double vitesse = 15000/3600;
	/**
	* Initialiser le plan total a partir d'un ficher XML
	* @nomFicherDePlan: nom du ficher xml a lire  
	*/
	public void initPlan(String nomFicherDePlan) {
		//TODO
		length = plan.getPointsMap().size();
	}
	
	/**
	 *Initialiser la commande a partir d'un fichier XML
	 */
	public void initCommande(String nomFicherDeCommande) {
		commande = myUtil.loadCommande(nomFicherDeCommande, plan);
		nombreDeLivraison = commande.getListLivraison().size();
	}
	
	/**
	 *Initialiser le plan avec que les points de livrson et les routes le plus court entre eux calcule par dijkstra 
	 */
	public void initPlanLivraison() {
		//l'entrepot est considere comme un objet Livraison dont l'attribut heureDeDepart devient heureDeDebut et heureDeFin est 9999 par defaut
		Livraison livraison = new Livraison(commande.getEntrepot().getId(),commande.getEntrepot().getCoordX(),commande.getEntrepot().getCoordY(),commande.getHeureDeDepart(),9999);
		
		HashMap<Long,Livraison> livraisonsMap = new HashMap<Long,Livraison>();
		HashMap<Long,HashMap<Long,Chemin>> cheminsMap = new HashMap<Long,HashMap<Long,Chemin>>();
		HashMap<Long,Chemin> cm = new HashMap<Long,Chemin>();
		for (Livraison l:commande.getListLivraison()) {
			livraisonsMap.put(l.getId(),l);
			cm.clear();
			for(Livraison l2:commande.getListLivraison()) {
				if(!l.equals(l2)) {
					cm.put(l2.getId(), calcLePlusCourtChemin(l.getId(), l2.getId()));
				}
				
			}
			cm.put(commande.getEntrepot().getId(), calcLePlusCourtChemin(l.getId(),commande.getEntrepot().getId()));
			cheminsMap.put(l.getId(),cm);
		}
		planLivraison.setLivraisonsMap(livraisonsMap);
		planLivraison.setCheminsMap(cheminsMap);
	}
	
	/**
	 *Chercher dans le Plan total la longueur de chemin plus courte de livraison origine vers destination
	 */ 
	public Chemin calcLePlusCourtChemin(long destinationID, long origineID) {
		//Chemin chemin = new Chemin();
		List<Livraison> path = null;
		boolean[] s = new boolean[length];
		HashMap<Long, Integer> indexMap = new HashMap<Long, Integer>();
		HashMap<Long, Point> pointsMap = plan.getPointsMap();
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
    		for(Entry<Long, Troncon> entry:tronconMap.get(d.index).entrySet()){
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
        	v++;
        }
        HashMap<Long,HashMap<Long,Chemin>> cheminMap = new HashMap<Long,HashMap<Long,Chemin>>();
        HashMap<Long,Chemin> origineCheminMap = new HashMap<Long,Chemin>();
        List<Long> destinationList = new ArrayList<>();
        for(Entry<Long, Livraison> entry:livraisonsMap.entrySet()){
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
     
        

		return null;
	}
	
	/**
	 *Trouver le tournee final en utilisant le plan de livraison genere
	 *@param b consider or not the time interval
	 */
	public void obtenirLeTournee(boolean b) {
		if (b) {
			
		} else {
			int tpsLimite = 1000;
			HashMap<Long,HashMap<Long,Chemin>> cheminsMap = planLivraison.getCheminsMap();
			int[][] cout = new int[cheminsMap.size()+1][cheminsMap.size()+1];
			for(int i=0;i<nombreDeLivraison+1;i++) {
				long startID = 0;
				long desID = 0;
				if(i==0) {
					startID = commande.getEntrepot().getId();
				} else {
					startID = commande.getListLivraison().get(i-1).getId();
				}
				for(int j=0;j<nombreDeLivraison+1;j++) {
					if(j==0) {
						desID = commande.getEntrepot().getId();
					} else {
						desID = commande.getListLivraison().get(j-1).getId();
					}
					if(startID == desID) {
						cout[i][j] = 0;
					} else {
						cout[i][j] = (int)(cheminsMap.get(startID).get(desID).getLongeur()/vitesse);
					}
				}
			}
			int[] duree = new int[cheminsMap.size()+1];
			tsp.chercheSolution(tpsLimite, length, cout, duree);
		}
		//TODO
	}
}
