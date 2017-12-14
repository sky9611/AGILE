package com.septanome.model;

import java.util.ArrayList;
import java.util.List;

public class Tournee {
    private List<Chemin> chemins;

    /**
     * Creer une tournee avec parametre:
     * @param chemins
     */
    public Tournee(List<Chemin> chemins) {
        this.chemins = chemins;
    }

    /**
     * Creer une tournee sans parametre
     */
    public Tournee() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Obtenir la liste des chemins de la tournée
     * @return List<Chemin> chemins.
     */
    public List<Chemin> getChemins(){
        return chemins;
    }

    /**
     * Remplacer la liste des chemins
     * @param nouvelleCheminList
     */

    public void setChemins(List<Chemin> cheminList) {
        chemins = new ArrayList<Chemin>(cheminList);
    }

    @Override
    public String toString() {
        String s ="";
        for (Chemin chemin:chemins) {
            s += chemin + "\r\n";
        }
        return s;

    }
}