package com.septanome.model;

import java.util.List;

public class Chemin {
	private long destinationPointID;
	private long originePointID;
	private List<Troncon> troncons;
	
	public Chemin(long destinationPointID, long originePointID,List<Troncon> troncons) {
		this.destinationPointID = destinationPointID;
		this.originePointID = originePointID;
		this.troncons = troncons;
	}
	
	public long getDestinationPointID(){
		return destinationPointID;
	}
	public long getOriginePointID(){
		return originePointID;
	}
	public List<Troncon> getTroncons(){
		return troncons;
	}
	
}

