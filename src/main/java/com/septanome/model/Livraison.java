package com.septanome.model;

public class Livraison extends Point {
    private int heureDeDebut;
    private int heureDeFin;
    private int duree;

    public Livraison(Livraison l){
        this.id=l.getId();
        this.coordX = l.getCoordX();
        this.coordY = l.getCoordY();
        this.heureDeDebut = l.getHeureDeDebut();
        this.heureDeFin = l.getHeureDeFin();
        this.duree = l.getDuree();
    }

    /**
     * Creer une livraison
     * @param id
     * @param coordX
     * @param coordY
     * @param duree
     * @param heureDeDebut
     * @param heureDeFin
     */
    public Livraison(long id, int coordX, int coordY, int duree, int heureDeDebut, int heureDeFin) {
        super(id, coordX, coordY);
        this.duree = duree;
        this.heureDeDebut = heureDeDebut;
        this.heureDeFin = heureDeFin;
    }

    /**
     * Creer une Livraison
     * @param id
     * @param coordX
     * @param coordY
     * @param duree
     */
    public Livraison(long id, int coordX, int coordY, int duree) {
        super(id, coordX, coordY);
        this.duree = duree;
        this.heureDeDebut = 0;
        this.heureDeFin = Integer.MAX_VALUE;
    }

    /**
     * Obtenir durée de la livraison
     * @return int durée
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Obtenir Heure de debut
     * @return int heureDeDebut
     */

    public int getHeureDeDebut() {
        return heureDeDebut;
    }

    /**
     * Obtenir Heure de fin
     * @return int heureDeDeFin
     */

    public int getHeureDeFin() {
        return heureDeFin;
    }

    /**
     * Modifier Heure de debut
     * @param heureDeDebut
     */
    public void setHeureDeDebut(int heureDeDebut) {
        this.heureDeDebut = heureDeDebut;
    }

    /**
     * Modifier Heure de fin
     * @param heureDeFin
     */
    public void setHeureDeFin(int heureDeFin) {
        this.heureDeFin = heureDeFin;
    }
    @Override




    public String toString() {
        return "("+id+", "+coordX+", "+coordY+", "+heureDeDebut+", "+heureDeFin+")";
    }
}
