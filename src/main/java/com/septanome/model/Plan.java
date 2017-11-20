package main.java.com.septanome.model;

import java.util.*;
import java.io.File;

public class Plan {
	private HashMap<Long,Point> pointsMap;
	private HashMap<Long,HashMap<Long,Troncon>> tronconsMap;
	
	
	//1ere Long est le debut, 2eme Long est la fin
	public Plan(HashMap<Long,Point> pointsMap,HashMap<Long,HashMap<Long,Troncon>> tronconsMap) {
		this.pointsMap = pointsMap;
		this.tronconsMap = tronconsMap;
	}
	
	public HashMap<Long,Point> getPointsMap(){
		return pointsMap;
	}
	
	public void setPointMap(HashMap<Long,Point> pointsMap){
		this.pointsMap = pointsMap;
	}
	
	public HashMap<Long,HashMap<Long,Troncon>> getTronconsMap(){
		return tronconsMap;
	}
	
	public void setTronconsMap(HashMap<Long,HashMap<Long,Troncon>> tronconsMap) {
		this.tronconsMap = tronconsMap;
	}
}
