package objet;

public class Plan {
	Route[] routes;
	Point[] pointsLivraison;
	
	public Plan(Route[] r,Point[] p){
		routes=new Route[r.length];
		for(int i=0;i<r.length;i++){
			routes[i]=r[i];
		}
		pointsLivraison=new Point[p.length];
		for(int i=0;i<p.length;i++){
			pointsLivraison[i]=p[i];
		}
	}
	public Route[] getRoutes(){
		return routes;
	}
	public Point[] getPointsLivraison(){
		return pointsLivraison;
	}
	public void setRoutes(Route[] r){
		routes=new Route[r.length];
		for(int i=0;i<r.length;i++){
			routes[i]=r[i];
		}
	}
	public void setPointLivraison(Point[] p){
		pointsLivraison=new Point[p.length];
		for(int i=0;i<p.length;i++){
			pointsLivraison[i]=p[i];
		}
	}
}

