package com.septanome.model;

import com.septanome.exception.BadLinkException;
import com.septanome.exception.EmptyListException;

import java.util.List;

public class Chemin {
    private long destinationPointID;
    private long originePointID;
    private List<Troncon> troncons;
    private double longeur;

    public Chemin(long destinationPointID, long originePointID,List<Troncon> troncons) throws BadLinkException, EmptyListException {
        this.destinationPointID = destinationPointID;
        this.originePointID = originePointID;
        this.troncons = troncons;
        long lastID = originePointID;
        boolean first = true;
        if(troncons.isEmpty()){
            if(destinationPointID!=originePointID){
                throw new EmptyListException("Troncon list is empty");
            }
        }else{
            for(Troncon temp:troncons) {
                if(temp.getOrigineID()!=lastID){
                    if(first){
                        throw new BadLinkException("originePointID does not match the first Troncon origineID");
                    }
                    throw new BadLinkException("troncons are not linked");
                }
                first=false;
                lastID= temp.getDestinationID();
                this.longeur = this.longeur + temp.getLongeur();
            }
            if(lastID!=destinationPointID){
                throw new BadLinkException("destinationPointID does not match the last Troncon destinationID\n Expected:"
                        +destinationPointID+ "\nFound:"+lastID);
            }
        }
    }

    /**
     * Obtenir le point destination
     * @return id destination.
     */
    public long getDestinationPointID(){
        return destinationPointID;
    }

    /**
     * modifier le point destination
     * @param destinationPointID
     */

    public void setDestinationPointID(long destinationPointID) {
        this.destinationPointID = destinationPointID;
    }

    /**
     * Obtenir le point origine
     * @return id origine.
     */

    public long getOriginePointID(){
        return originePointID;
    }

    /**
     * modifier le point origine
     * @param originePointID
     */

    public void setOriginePointID(long originePointID) {
        this.originePointID = originePointID;
    }

    /**
     * Obtenir liste des tronçon du chemin
     * @return List<Troncon>
     */
    public List<Troncon> getTroncons(){
        return troncons;
    }

    /**
     * remplacer la Liste des tronçon du chemin avec une nouvelle liste
     * @param liste troncons
     */

    public void setTroncons(List<Troncon> troncons) {
        this.troncons = troncons;
    }

    /**
     * Obtenir la longeur du chemin.
     * @return double longeur.
     */
    public double getLongeur() {
        return longeur;
    }

    @Override
    public String toString() {
        String s = originePointID+"";
        for(Troncon tr : troncons) {
            s += "->"+tr.getDestinationID();
        }
        return s;
    }

}