package com.septanome.model;

public class Point {
	private int coordX;
	private int coordY;
	private long id;
	
	public Point(int x,int y,long id){
		this.coordX=x;
		this.coordY=y;
		this.id=id;
	}
	public int getX(){
		return coordX;
	}
	public int getY(){
		return coordY;
	}
	public long getID(){
		return id;
	}
	public void setX(int X){
		coordX=X;
	}
	public void setY(int Y){
		coordY=Y;
	}
	public void setID(long i){
		id=i;
	}
}
