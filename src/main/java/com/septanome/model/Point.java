package com.septanome.model;

public class Point {
	protected long id;
	protected int coordX;
	protected int coordY;
	
	public Point(long id, int coordX, int coordY) {
		this.id = id;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
