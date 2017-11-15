package objet;

public class Route {
	int longueur;
	long id;
	long origineID;
	long destinationID;
	String nom;

	public Route(int l,long i,long o,long d,String n){
		longueur=l;
		id=i;
		origineID=o;
		destinationID=d;
		nom=n;
	}
	public int getLongueur(){
		return longueur;
	}
	public long getID(){
		return id;
	}
	public long getOrigineID(){
		return origineID;
	}
	public long getDestinationID(){
		return destinationID;
	}
	public String getNom(){
		return nom;
	}
	public void setLongueur(int l){
		longueur=l;
	}
	public void setID(long l){
		id=l;
	}
	public void setOrigineID(long l){
		origineID=l;
	}
	public void setDestinationID(long l){
		destinationID=l;
	}
	public void setNom(String n){
		nom=n;
	}
}