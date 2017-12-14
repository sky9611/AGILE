package com.septanome.model;

import java.util.HashMap;

public class PlanLivraison {
    private HashMap<Long,Livraison> livraisonsMap;
    private HashMap<Long,HashMap<Long,Chemin>> cheminsMap;

    /**
     * Creer un plan de livraison:
     * @param livraisonsMap
     * @param cheminsMap
     */
    public PlanLivraison(HashMap<Long,Livraison> livraisonsMap, HashMap<Long,HashMap<Long,Chemin>> cheminsMap) {
        this.livraisonsMap = livraisonsMap;
        this.cheminsMap = cheminsMap;
    }

    public PlanLivraison() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Obtenir les livraisons
     * @return HashMap<Long,Livraison> livraisonsMap
     */
    public HashMap<Long,Livraison> getLivraisonsMap(){
        return livraisonsMap;
    }

    /**
     * Remplacer les livraisons
     * @param HashMap<Long,Livraison> nouvelleLivraisonsMap
     */

    public void setLivraisonsMap(HashMap<Long,Livraison> livraisonsMap) {
        this.livraisonsMap = livraisonsMap;
    }

    /**
     * Obtenir les chemin,
     * @return HashMap<Long,HashMap<Long,Chemin>>  cheminsMap
     */
    public HashMap<Long,HashMap<Long,Chemin>> getCheminsMap(){
        return cheminsMap;
    }

    /**
     * Remplacer les chemin
     * @param HashMap<Long,HashMap<Long,Chemin>> nouvelleCheminsMap.
     */
    public void setCheminsMap(HashMap<Long,HashMap<Long,Chemin>> cheminsMap) {
        this.cheminsMap = cheminsMap;
    }
}
