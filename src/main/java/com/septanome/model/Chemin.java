package main.java.com.septanome.model;

import java.util.List;

public class Chemin {
	private long destinationPointID;
	private long originePointID;
	private List<Troncon> troncons;
	private double longeur;
	
	public Chemin(long destinationPointID, long originePointID,List<Troncon> troncons) {
		this.destinationPointID = destinationPointID;
		this.originePointID = originePointID;
		this.troncons = troncons;
		for(Troncon temp:troncons) {
			this.longeur = this.longeur + temp.getLongeur();
		}
	}
	
	public long getDestinationPointID(){
		return destinationPointID;
	}
	
	public void setDestinationPointID(long destinationPointID) {
		this.destinationPointID = destinationPointID;
	}
	
	public long getOriginePointID(){
		return originePointID;
	}
	
	public void setOriginePointID(long originePointID) {
		this.originePointID = originePointID;
	}
	
	public List<Troncon> getTroncons(){
		return troncons;
	}
	
	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}
	
}