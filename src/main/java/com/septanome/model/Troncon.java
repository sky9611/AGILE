package com.septanome.model;

public class Troncon {
    private long destinationID;
    private double longeur;
    private String nom;
    private long origineID;

    /**
     * Obtenir destination
     * @return long idDestination
     */
    public long getDestinationID() {
        return destinationID;
    }

    /**
     * Modifier destination
     * @param long destinationID
     */
    public void setDestinationID(long destinationID) {
        this.destinationID = destinationID;
    }

    /**
     * Obtenir longeur
     * @return double longeur
     */
    public double getLongeur() {
        return longeur;
    }

    /**
     * Modifier longeur
     * @param double longeur
     */
    public void setLongeur(double longeur) {
        this.longeur = longeur;
    }

    /**
     * Obtenir nom
     * @return String nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifier nom
     * @param String nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtenir Origine
     * @return long idOrigen
     */
    public long getOrigineID() {
        return origineID;
    }

    /**
     * Modifier Origine
     * @param long origineID
     */
    public void setOrigineID(long origineID) {
        this.origineID = origineID;
    }

    /**
     * cree un nouveau tronçon avec les parametres:
     * @param destination
     * @param longeur
     * @param nomDeRue
     * @param origine
     */

    public Troncon(long destination, double longeur, String nomDeRue, long origine) {
        this.setDestinationID(destination);
        this.setLongeur(longeur);
        this.setNom(nomDeRue);
        this.setOrigineID(origine);
    }

    @Override
    public String toString() {
        return origineID+"->"+destinationID+"("+nom+", "+longeur+")";
    }
}
