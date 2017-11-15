package com.septanome.model;

import java.util.*;
import java.io.File;

public class Plan {
	private HashMap<Long,Point> pointsMap;
	private HashMap<Long,HashMap<Long,Troncon>> tronconsMap;
	
	public Plan(HashMap<Long,Point> pointsMap,HashMap<Long,HashMap<Long,Troncon>> tronconsMap) {
		this.pointsMap = pointsMap;
		this.tronconsMap = tronconsMap;
	}
	
	public HashMap<Long,Point> getPointsMap(){
		return pointsMap;
	}
	
	public HashMap<Long,HashMap<Long,Troncon>> getTronconsMap(){
		return tronconsMap;
	}
}
