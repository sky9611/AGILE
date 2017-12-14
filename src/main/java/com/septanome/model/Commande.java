package com.septanome.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int heureDeDepart = 0;
    private Point entrepot;
    private List<Livraison> livraisons;

    public Commande(int heureDeDepart, Point entrepot, List<Livraison> livraisons) {
        this.heureDeDepart = heureDeDepart;
        this.entrepot = entrepot;
        this.livraisons = livraisons;
    }

    public Commande(Commande c) throws IOException, ClassNotFoundException {
        this.heureDeDepart = c.getHeureDeDepart();
        this.entrepot = new Point(c.getEntrepot());
        List<Livraison> ll= new ArrayList<>();
        for(Livraison l:c.getListLivraison()){
            Livraison l2=new Livraison(l);
            ll.add(l2);
        }
        this.livraisons = ll;
    }


    public Commande() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Obtenir heure de depart.
     * @return int heure.
     */

    public int getHeureDeDepart() {
            return heureDeDepart;
    }

    /**
     * Modifier l'heure de depart.
     * @param heureDeDepart
     */

    public void setHeureDeDepart(int heureDeDepart) {
        this.heureDeDepart = heureDeDepart;
    }

    /**
     * Pour obtenir l'entrepot.
     * @return Point entrepot
     */

    public Point getEntrepot() {
        return entrepot;
    }

    /**
     * modifier l'entrepot
     * @param nouveau entrepot
     */

    public void setEntrepot(Point entrepot) {
        this.entrepot = entrepot;
    }

    /**
     * obtenir la liste des livraisons de la commande.
     * @return List<Livraison>
     */

    public List<Livraison> getListLivraison(){
        return livraisons;
    }

    /**
     * Ajouter une nouvelle livraison a la liste des livraisons.
     * @param Livraison l
     */

    public void addLivraison(Livraison l) {
        this.livraisons.add(l);
    }


    /**
     * Remplacer la liste des livraisons actuelle pour une nouvelle.
     * @param List<Livraison> livraisons
     */

    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }
}

