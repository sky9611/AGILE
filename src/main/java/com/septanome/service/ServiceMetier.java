package com.septanome.service;

import com.septanome.exception.BadLinkException;
import com.septanome.exception.EmptyListException;
import com.septanome.model.*;
import com.septanome.util.GATSPTW;
import com.septanome.util.UtilXML;
import tsp.TSP1;

import java.io.IOException;
import java.util.*;

public class ServiceMetier implements Cloneable{

    public Commande getCommande() {
        return commande;
    }

    public Plan getPlan() {

        return plan;
    }

    private Plan plan = new Plan();
    private PlanLivraison planLivraison = new PlanLivraison();
    private Commande commande = new Commande();
    private Tournee tournee = new Tournee();
    private int nombreDeLivraison;
    private UtilXML myUtil = new UtilXML();
    private TSP1 tsp = new TSP1();
    private static final double vitesse = 60000 / 3600;

    public void init(String nomFicherDePlan, String nomFicherDeCommande) throws BadLinkException, EmptyListException {
        initPlan(nomFicherDePlan);
        initCommande(nomFicherDeCommande);
        initPlanLivraison();
    }


    /**
     * Initialiser le plan total a partir d'un ficher XML
     * @param nomFicherDePlan nom du ficher xml a lire
     */
    public void initPlan(String nomFicherDePlan) {
        plan.setPointMap(myUtil.loadPoint(nomFicherDePlan));
        plan.setTronconsMap(myUtil.loadTroncon(nomFicherDePlan));
        plan.getPointsMap().size();
    }

    /**
     * Initialiser la commande a partir d'un ficher XML
     * @param nomFicherDeCommande nom du ficher xml a lire
     */
    public void initCommande(String nomFicherDeCommande) {
        commande = myUtil.loadCommande(nomFicherDeCommande, plan);
        nombreDeLivraison = commande.getListLivraison().size();
    }

    /**
     * Initialiser le plan avec que les points de livraison et les routes les plus courts entre eux calcules par dijkstra
     */
    public void initPlanLivraison() throws BadLinkException, EmptyListException {
        //l'entrepot est considere comme un objet Livraison dont l'attribut heureDeDepart devient heureDeDebut et heureDeFin est 9999 par defaut
        Livraison entrepot = new Livraison(commande.getEntrepot().getId(), commande.getEntrepot().getCoordX(), commande.getEntrepot().getCoordY(), 0, commande.getHeureDeDepart(), 9999);

        HashMap<Long, Livraison> livraisonsMap = new HashMap<>();
        livraisonsMap.put(entrepot.getId(), entrepot);
        HashMap<Long, HashMap<Long, Chemin>> cheminsMap = new HashMap<>();


        for (Livraison l : commande.getListLivraison()) {
            livraisonsMap.put(l.getId(), l);
        }


        planLivraison.setLivraisonsMap(livraisonsMap);
        cheminsMap.putAll(calcLePlusCourtChemin(entrepot.getId()));
        for (Livraison l : commande.getListLivraison()) {
            cheminsMap.putAll(calcLePlusCourtChemin(l.getId()));
        }
        planLivraison.setCheminsMap(cheminsMap);
    }


    /**
     * Chercher dans le Plan total la longueur de chemin plus courte de livraison origine vers destination
     *
     * @param origineID id de point d'origine
     * @return plan du chemin
     */
    private HashMap<Long, HashMap<Long, Chemin>> calcLePlusCourtChemin(long origineID) throws BadLinkException, EmptyListException {
        class dist implements Comparable<dist> {
            private long index;
            private double value;

            private dist(long index, double value) {
                this.index = index;
                this.value = value;
            }

            @Override
            public int compareTo(dist o) {
                return Double.compare(this.value, o.value);
            }
        }
        HashMap<Long, Point> pointsMap = plan.getPointsMap();
        HashMap<Long, Long> prev = new HashMap<>();
        HashMap<Long, Livraison> livraisonsMap = planLivraison.getLivraisonsMap();
        HashMap<Long, HashMap<Long, Troncon>> tronconMap = plan.getTronconsMap();
        List<Long> neighbourList = new ArrayList<>();

        PriorityQueue<dist> queue = new PriorityQueue<>(pointsMap.size());


        queue.add(new dist(origineID, 0.0));
        Map<Long, Double> visited = new HashMap<>();

        List<Long> destinationList = new ArrayList<>();
        for (Map.Entry<Long, Livraison> entry : livraisonsMap.entrySet()) {
            long key = entry.getKey();
            if (key != origineID)
                destinationList.add(key);
        }

        while (!queue.isEmpty()) {
            dist d = queue.poll();
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
                //System.out.println(d.index+" "+i);
                prev.put(i, d.index);
                dist d2 = new dist(i,newlength);
                queue.add(d2);
            }
        }
        HashMap<Long, HashMap<Long, Chemin>> cheminMap = new HashMap<>();
        HashMap<Long, Chemin> origineCheminMap = new HashMap<>();

        for (long id : destinationList) {
            List<Troncon> tronconList = new ArrayList<>();
            long tempDes = id;
            long tempSta;
            while (prev.get(tempDes) != null) {
                tempSta = prev.get(tempDes);
                tronconList.add(tronconMap.get(tempSta).get(tempDes));
                tempDes = tempSta;
            }

            Collections.reverse(tronconList);
            Chemin chemin = new Chemin(id, origineID, tronconList);
            origineCheminMap.put(id, chemin);
        }
        cheminMap.put(origineID, origineCheminMap);
        return cheminMap;
    }

    /**
     * Trouver le tournee final en utilisant le plan de livraison genere
     *
     * @param b consider or not the time interval
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void calculerTournee(boolean b) throws ClassNotFoundException, IOException {
        if (b) {
            GATSPTW ga = new GATSPTW(planLivraison, commande);
            if(ga.findSolution(1000))tournee = ga.getTournee();
            else tournee = null;
        } else {
            int tpsLimite = 1000;
            HashMap<Long, HashMap<Long, Chemin>> cheminsMap = planLivraison.getCheminsMap();
            List<Livraison> listLivraisons = commande.getListLivraison();
            int[][] cout = new int[nombreDeLivraison + 1][nombreDeLivraison + 1];
            long idEntrepot = commande.getEntrepot().getId();
            int[] duree = new int[cheminsMap.size()];
            for (int i = 0; i < cheminsMap.size(); i++) {
                long startID;
                long desID;
                if (i == 0) {
                    startID = idEntrepot;
                    duree[i] = 0;
                } else {
                    startID = listLivraisons.get(i - 1).getId();
                    duree[i] = listLivraisons.get(i - 1).getDuree();
                }


                for (int j = 0; j < cheminsMap.size(); j++) {
                    if (j == 0) desID = idEntrepot;
                    else desID = listLivraisons.get(j - 1).getId();
                    if (startID == desID) {
                        cout[i][j] = 0;
                    } else {
                        cout[i][j] = (int) (cheminsMap.get(startID).get(desID).getLongeur() / vitesse);
                    }
                }
            }
//

            tsp.chercheSolution(tpsLimite, cheminsMap.size(), cout, duree);
            List<Chemin> cheminList = new ArrayList<>();
            for (int k = 0; k < cheminsMap.size(); k++) {
                if (k == 0) {
                    Chemin chemin = cheminsMap.get(idEntrepot).get(listLivraisons.get(tsp.getMeilleureSolution(k + 1) - 1).getId());
                    cheminList.add(chemin);
                } else if (k < cheminsMap.size() - 1) {
                    Chemin chemin = cheminsMap.get(listLivraisons.get(tsp.getMeilleureSolution(k) - 1).getId()).get(listLivraisons.get(tsp.getMeilleureSolution(k + 1) - 1).getId());
                    cheminList.add(chemin);
                } else {
                    Chemin chemin = cheminsMap.get(listLivraisons.get(tsp.getMeilleureSolution(k) - 1).getId()).get(idEntrepot);
                    cheminList.add(chemin);
                }
            }

            tournee.setChemins(cheminList);
        }

    }

    /**
     * Get tournee
     */
    public Tournee getTournee() {
        return tournee;
    }

    public void setCommande(Commande c) throws IOException, ClassNotFoundException {
        this.commande = new Commande(c);
    }

    /**
     * ajouter un nouveau livraison
     * @param livraison livaison a ajouter
     */
    public void ajouterNouveauLivraison(Livraison livraison){
        List <Chemin> listChemin = tournee.getChemins();
        commande.getListLivraison().clear();
        List<Livraison> newListLivraison = new ArrayList();
        double [] arrivalTime = calculerArrivalTime();
        HashMap<Long,Livraison> pl = planLivraison.getLivraisonsMap();
        for(int i =0;i<listChemin.size()-1;i++){
            Long destinationID = listChemin.get(i).getDestinationPointID();
            Livraison l = pl.get(destinationID);
            if(l.getHeureDeDebut()==0){
                int hd = (int)arrivalTime[i];
                int hf = (int)arrivalTime[i]+14400;//plage horaire = [t, t+1h]
                Livraison temp = new Livraison(l.getId(),l.getCoordX(),l.getCoordY(),l.getDuree(),hd,hf);
                newListLivraison.add(temp);
            } else {
                newListLivraison.add(l);
            }
        }
        commande.setLivraisons(newListLivraison);
        commande.addLivraison(livraison);
    }

    /**
     * calculer le temps d'arrive pour chaque point
     * @return le temps d'arrive
     */
    public double[] calculerArrivalTime(){
        List<Chemin> chemins = tournee.getChemins();
        List<Long> l = new ArrayList<Long>();
        for(int i=0;i<chemins.size()-1;i++){
            l.add(chemins.get(i).getDestinationPointID());
        }
        return calculeArrivalTime(l);
    }
    public double[] calculeArrivalTime(List<Long> l) {
        double[] arrivalTimes = new double[l.size()+1];
        double[] leaveTimes = new double[l.size()+1];
        arrivalTimes[0] = commande.getHeureDeDepart();
        leaveTimes[0] = commande.getHeureDeDepart();
        HashMap<Long,Livraison> pl = planLivraison.getLivraisonsMap();
        for (int i=1;i<=l.size();i++) {
            long idStart = l.get(i-1);
            double duree = pl.get(idStart).getDuree();
            long idDes = i<l.size()?l.get(i):commande.getEntrepot().getId();
            double longeur = planLivraison.getCheminsMap().get(idStart).get(idDes).getLongeur();
            arrivalTimes[i] = leaveTimes[i-1] + duree + longeur/vitesse;
            if (arrivalTimes[i]>pl.get(idDes).getHeureDeDebut()) {
                leaveTimes[i] = arrivalTimes[i] + duree;
            } else {
                leaveTimes[i] = pl.get(idDes).getHeureDeDebut()+duree;
            }
        }
        return arrivalTimes;
    }
    public ServiceMetier clone(){
        try {
            return (ServiceMetier) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

