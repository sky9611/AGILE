package com.septanome.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.septanome.model.Commande;
import com.septanome.model.Livraison;
import com.septanome.model.Chemin;
import com.septanome.model.PlanLivraison;
import com.septanome.model.Tournee;

public class TSPTW {
	private PlanLivraison planLivraison = new PlanLivraison();
	private HashMap<Long,Livraison> livraisonsMap;
	private final double vitesse = 15000/3600;
	private Commande commande = new Commande();
	private long idEntrepot;
	private List<Long> idLivraisons = new ArrayList<>();
	private int levelMax = 2;
	
	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(src);  
	  
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    @SuppressWarnings("unchecked")  
	    List<T> dest = (List<T>) in.readObject();  
	    return dest;  
	}  
	
	public static <T> void printList(List<T> list){  
	    System.out.println("---begin---");  
	    for(T t : list){  
	        System.out.println(t);  
	    }  
	    System.out.println("---end---");  
	}  
	
	public TSPTW(PlanLivraison planLivraison, Commande commande) {
		this.planLivraison = planLivraison;
		this.commande = commande;
		for(Livraison l: commande.getListLivraison()) {
			idLivraisons.add(l.getId());
		}
		idEntrepot = commande.getEntrepot().getId();
		livraisonsMap = planLivraison.getLivraisonsMap();
	}

	public Tournee findSolution(int iterMax) throws ClassNotFoundException, IOException {
		int i = 0;
		List<Long> orderOfLivraison = null;
		List<Long> bestOrder = null;
		Tournee t = new Tournee();
		bestOrder = VNS();

		bestOrder = GVNS(bestOrder);
		while(i<iterMax) {
			orderOfLivraison = VNS();
			orderOfLivraison = GVNS(orderOfLivraison);
			if (TSPObjectiveFunction(orderOfLivraison)<TSPObjectiveFunction(bestOrder)) {
				bestOrder = orderOfLivraison;
			}
			i++;
		}
		List<Chemin> chemins = new ArrayList<>();
		for(int j=1;j<bestOrder.size();j++) {
			Chemin c = planLivraison.getCheminsMap().get(bestOrder.get(j-1)).get(bestOrder.get(j));
			chemins.add(c);
		}
		Chemin lastChemin = planLivraison.getCheminsMap().get(bestOrder.get(bestOrder.size()-1)).get(idEntrepot);
		chemins.add(lastChemin);
		t.setChemins(chemins);
		return t;
	}
	
	public List<Long> GVNS(List<Long> l) throws ClassNotFoundException, IOException {
		int level = 1;
		l = VND(l);
		while (level<=levelMax) {
			//System.out.println("level="+level);
			//System.out.println("Before perturbation");
			//printList(l);
			List<Long> l2 = perturbation(l,level);
			//System.out.println("After perturbation");
			//printList(l2);
			l2 = VND(l2);
			if (TSPObjectiveFunction(l2)<TSPObjectiveFunction(l)) {
				level=1;
				l = deepCopy(l2);
			} else {
				level++;
			}
		}
		return l;
	}
	
	public List<Long> VNS() throws ClassNotFoundException, IOException {
		List<Long> orderOfLivraison = null;
		int n = 0;
		do {
			orderOfLivraison = randomSolution();
			printList(orderOfLivraison);
			System.out.println(isFleasible(orderOfLivraison));
			orderOfLivraison = localOneOpt(orderOfLivraison);
			int level = 1;
			while (!isFleasible(orderOfLivraison)&&level<levelMax) {
				System.out.println("level="+level);
				List<Long> newOrderOfLivraison = perturbation(orderOfLivraison,level);
				newOrderOfLivraison = localOneOpt(newOrderOfLivraison);
				if (VNSObjectiveFunction(orderOfLivraison)<=VNSObjectiveFunction(newOrderOfLivraison)) {
					level++;
				} else {
					printList(orderOfLivraison);
					printList(newOrderOfLivraison);					
					orderOfLivraison = newOrderOfLivraison;
					level = 1;
				}
			}
		} while (!isFleasible(orderOfLivraison)&&n<10);

		if(!isFleasible(orderOfLivraison))
			System.out.println("Solution not found");
		return orderOfLivraison;
	}
	
	public List<Long> randomSolution() throws ClassNotFoundException, IOException {
		List<Long> listIdLivraisons = new ArrayList<>();
		listIdLivraisons.add(idEntrepot);
		List<Long> l = deepCopy(idLivraisons);
		Collections.shuffle(l);
		listIdLivraisons.addAll(l);

		return listIdLivraisons;
	}
	
	public List<Long> VND(List<Long> l) throws ClassNotFoundException, IOException{
		//System.out.println("Enter VND");
		//printList(l);
		List<Long> l2 = new ArrayList<>();
		do {
			l = localOneOpt(l);
			l2 = deepCopy(l);
			l = localTwoOpt(l);
		} while (!l2.equals(l));
		return l;
	}
	
	public boolean isFleasible(List<Long> l) {
		for (int i=1;i<l.size();i++) {
			if (isViolated(l,i))
				return false;
		}
		
		return true;
	}
	
	public boolean between(double v, double a, double b) {		 
		return v>a&&v<b;
	}
	
	public boolean isViolated(List<Long> l, int index) {
		//long idStart = l.get(index);
		long idDes = l.get(index);
		//double duree = planLivraison.getCheminsMap().get(idStart).get(idDes).getLongeur()/vitesse;
		double arrivalTime =  calculeArrivalTime(l)[index];
		double a = livraisonsMap.get(idDes).getHeureDeDebut();
		double b = livraisonsMap.get(idDes).getHeureDeFin()-livraisonsMap.get(idDes).getDuree();
		if (!between(arrivalTime,a,b))
			return true;
		return false;
	}
	
	public double[] calculeArrivalTime(List<Long> l) {
		//System.out.println("enter calculeArrivalTime");
		double[] arrivalTimes = new double[l.size()];
		arrivalTimes[0] = commande.getHeureDeDepart();
		for (int i=1;i<l.size();i++) {
			long idStart = l.get(i-1);
			double duree = livraisonsMap.get(idStart).getDuree();
			long idDes = l.get(i);
			//System.out.println(idStart+" "+idDes);
			double longeur = planLivraison.getCheminsMap().get(idStart).get(idDes).getLongeur();
			if (arrivalTimes[i-1]>livraisonsMap.get(idStart).getHeureDeDebut()) {
				arrivalTimes[i] = arrivalTimes[i-1] + duree + longeur/vitesse;
			} else {
				arrivalTimes[i] = livraisonsMap.get(idStart).getHeureDeDebut();
			}
		}
		return arrivalTimes;
	}
	
	public List<Long> localOneOpt(List<Long> l) throws ClassNotFoundException, IOException {
		//System.out.println("Enter localOneOpt");
		int length = l.size();
		//System.out.println("length = "+length);
		//printList(l);
		for (int i=length-1;i>0;i--) {
			//System.out.println("i:" + i);
			//printList(l);
			if(isViolated(l,i)) {
				for (int j=1;j<i;j++) {
					List<Long> l2 = deepCopy(l);
					l2.remove(i);
					l2.add(j, l.get(i));
					//System.out.println("l2: ");
					//printList(l2);
					if( VNSObjectiveFunction(l)>VNSObjectiveFunction(l2))
						return l2;
				}
			}
			if(!isViolated(l,i)) {
				for (int j=i+1;j<length;j++) {
					List<Long> l2 = deepCopy(l);
					l2.remove(i);
					l2.add(j, l.get(i));
					if( VNSObjectiveFunction(l)>VNSObjectiveFunction(l2))
						return l2;
				}
			}
			if(!isViolated(l,i)) {
				for (int j=1;j<i;j++) {
					List<Long> l2 = deepCopy(l);
					l2.remove(i);
					l2.add(j, l.get(i));
					if( VNSObjectiveFunction(l)>VNSObjectiveFunction(l2))
						return l2;
				}
			}
			if(isViolated(l,i)) {
				for (int j=i+1;j<length;j++) {
					List<Long> l2 = deepCopy(l);
					l2.remove(i);
					l2.add(j, l.get(i));
					if( VNSObjectiveFunction(l)>VNSObjectiveFunction(l2))
						return l2;
				}
			}
		}
		
		return l;
	}
	
	public List<Long> localTwoOpt(List<Long> l) throws ClassNotFoundException, IOException {
		int length = l.size();
		for (int i=1;i<length;i++) {
			for (int j=i+1;j<length;j++) {
				List<Long> l2 = deepCopy(l);
				List<Long> reverse = l2.subList(i, j+1);
				Collections.reverse(reverse);
				if( VNSObjectiveFunction(l)>VNSObjectiveFunction(l2))
					return l2;
			}
		}
		
		return l;
	}
	
	public double VNSObjectiveFunction(List<Long> l) {
		//System.out.println("enter VNSObjectiveFunction");
		double result = 0;
		double[] arrivalTimes = calculeArrivalTime(l);
		double[] dueTimes = new double[arrivalTimes.length];
		dueTimes[0] = commande.getHeureDeDepart();
		for (int i=1;i<arrivalTimes.length;i++) {
			dueTimes[i] = livraisonsMap.get(l.get(i)).getHeureDeFin();
			result += (arrivalTimes[i]-dueTimes[i]>0)?(arrivalTimes[i]-dueTimes[i]):0;
		}
		
		return result;
	}
	
	public double TSPObjectiveFunction(List<Long> l) {
		double[] arrivalTimes = calculeArrivalTime(l);
		double lastServiceTime = livraisonsMap.get(l.get(l.size()-1)).getDuree();
		return arrivalTimes[l.size()-1]+lastServiceTime;
	}
	
	public List<Long> perturbation(List<Long> l, int level) throws ClassNotFoundException, IOException{
		List<Long> result = deepCopy(l);
		int length = result.size();
		Random random = new Random();
		for(int i=0;i<level;i++) {
			List<Long> before = deepCopy(result);
			int index1=random.nextInt(length-1)+1;
			int index2=random.nextInt(length-1)+1;
			result.remove(index2);
			result.add(index1, before.get(index2));
		}
		
		return result;
	}
}
