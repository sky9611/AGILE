package com.septanome.model;

public class Point {
    protected long id;
    protected int coordX;
    protected int coordY;
    public Point(){

    }

    /**
     * Creer  un poiunt
     * @param id
     * @param coordX
     * @param coordY
     */
    public Point(long id, int coordX, int coordY) {
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * Creer un point
     * @param point
     */
    public Point(Point point){
        this.id = point.getId();
        this.coordX = point.getCoordX();
        this.coordY = point.getCoordY();
    }

    /**
     *Obtenir coord X
     * @return int X
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     *Modifier coord Y
     * @param coordX
     */
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    /**
     *Obtenir coord Y
     * @return int Y
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * Modifier coord Y
     * @param coordY
     */
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    /**
     * Obtenir le id
     * @return long id.
     */
    public long getId() {
        return id;
    }

    /**
     * Modifier id.
     * @param idPoint.
     */
    public void setId(long id) {
        this.id = id;
    }
}
