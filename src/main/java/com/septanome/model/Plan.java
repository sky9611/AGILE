package com.septanome.model;

import java.util.HashMap;

public class Plan {
    private HashMap<Long,Point> pointsMap;
    private HashMap<Long,HashMap<Long,Troncon>> tronconsMap;


    //1ere Long est le debut, 2eme Long est la fin

    /**
     * Creer un plan avec parametre:
     * @param pointsMap
     * @param tronconsMap
     */
    public Plan(HashMap<Long,Point> pointsMap, HashMap<Long,HashMap<Long,Troncon>> tronconsMap) {
        this.pointsMap = pointsMap;
        this.tronconsMap = tronconsMap;
    }

    /**
     * Creer un plan sans parametres
     */
    public Plan() {
        // TODO Auto-generated constructor stub
        this.pointsMap = new HashMap<Long,Point>();
        this.tronconsMap =  new HashMap<Long,HashMap<Long,Troncon>>();
    }

    /**
     * Obtenir tous les points du plan
     * @return HashMap<Long,Point> Points
     */
    public HashMap<Long,Point> getPointsMap(){
        return pointsMap;
    }

    /**
     * Modifier les points du plan.
     * @param pointsMap
     */
    public void setPointMap(HashMap<Long,Point> pointsMap){
        this.pointsMap = pointsMap;
    }

    /**
     * Obtenir les tronçon du plan
      * @return HashMap<Long,HashMap<Long,Troncon>> Tronçons
     */
    public HashMap<Long,HashMap<Long,Troncon>> getTronconsMap(){
        return tronconsMap;
    }

    /**
     * Remplacer les tronçon du plan
     * @param  NouvelletronconsMap
     */
    public void setTronconsMap(HashMap<Long,HashMap<Long,Troncon>> tronconsMap) {
        this.tronconsMap = tronconsMap;
    }

}