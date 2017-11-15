package objet;

public class Commande {
	Livraison[] livraisons;
	Point entrepot;
	int heureDepart;
	
	public Commande(Livraison[] l,Point p,int h){
		livraisons=new Livraison[l.length];
		for(int i=0;i<l.length;i++){
			livraisons[i]=l[i];
		}
		entrepot=p;
		heureDepart=h;
	}
	public Livraison[] getLivraisons(){
		return livraisons;
	}
	public Point getEntrepot(){
		return entrepot;
	}
	public int getHeure(){
		return heureDepart;
	}
	public void setLivraisons(Livraison[] l){
		livraisons=new Livraison[l.length];
		for(int i=0;i<l.length;i++){
			livraisons[i]=l[i];
		}
	}
	public void setEntrepot(Point p){
		entrepot=p;
	}
	public void setHeureDepart(int h){
		heureDepart=h;
	}
}
