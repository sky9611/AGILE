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
	public int getCoordY() {
		return coordY;
	}
	public long getId() {
		return id;
	}
}
