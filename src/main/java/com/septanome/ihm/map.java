package main.java.com.septanome.ihm;

import javax.swing.JPanel;

import main.java.com.septanome.model.Commande;
import main.java.com.septanome.model.Troncon;
import main.java.com.septanome.model.Point;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
public class map extends JPanel {
 
  HashMap<Long,Point> p=new HashMap<Long,Point>();
  HashMap<Long,HashMap<Long,Troncon>> r=new HashMap<Long,HashMap<Long,Troncon>>();;
  Commande tournee;
  int screenHeigth;
  
	public map(HashMap<Long,Point> tab,HashMap<Long,HashMap<Long,Troncon>> road,Commande t,int h){
		screenHeigth=h;
		p=tab;
		r=road;
		tournee=t;
	}
	
	public void paintComponent(Graphics g){ 
	  int xmin=((Point)p.values().toArray()[0]).getCoordX();
	  int xmax=((Point)p.values().toArray()[0]).getCoordX();
	  int ymin=((Point)p.values().toArray()[0]).getCoordY();
	  int ymax=((Point)p.values().toArray()[0]).getCoordY();
	  for(Map.Entry<Long,Point> entry:p.entrySet()){ 
		if(entry.getValue().getCoordX()>xmax){
			xmax=entry.getValue().getCoordX();
		}
		if(entry.getValue().getCoordX()<xmin){
			xmin=entry.getValue().getCoordX();
		}
		if(entry.getValue().getCoordY()>ymax){
			ymax=entry.getValue().getCoordY();   
		}
		if(entry.getValue().getCoordY()<ymin){
			ymin=entry.getValue().getCoordY();   
		}
	   }
	   int scale = (ymax-ymin>xmax-xmin)? ymax-ymin:xmax-xmin;
	   for(Map.Entry<Long,Point> entry:p.entrySet()){ 	           
			g.fillOval((int)((((double)entry.getValue().getCoordX())-xmin)/scale*(screenHeigth-12)), (int)((((double)entry.getValue().getCoordY())-ymin)/scale*(screenHeigth-37)), 10, 10);
	   }
	   
	   g.drawLine(screenHeigth,0,screenHeigth,screenHeigth);
	   
	   for(Map.Entry<Long,HashMap<Long,Troncon>> entry:r.entrySet()){
		   Long idOri=entry.getKey();
		   HashMap<Long,Troncon> h= entry.getValue();
		   for(Map.Entry<Long,Troncon> e:h.entrySet()){
		   Long idDest=entry.getKey(); 
		   g.drawLine((int)((((double)p.get(idOri).getCoordX())-xmin)/scale*(screenHeigth-12))+5,(int)((((double)p.get(idOri).getCoordY())-ymin)/scale*(screenHeigth-37))+5,(int)((((double)p.get(idDest).getCoordX())-xmin)/scale*(screenHeigth-12))+5,(int)((((double)p.get(idDest).getCoordX())-ymin)/scale*(screenHeigth-37))+5);
	   } }
	   
	   g.setColor(Color.green);
	   g.fillOval((int)((((double)tournee.getEntrepot().getCoordX())-xmin)/scale*(screenHeigth-12)), (int)((((double)tournee.getEntrepot().getCoordY())-ymin)/scale*(screenHeigth-37)), 10, 10);
		g.setColor(Color.red);
		int l=tournee.getListLivraison().size();
	   for(int i=0;i<l;i++){
		   if(i==0 || i==l-1){
			   g.drawLine((int)((((double)tournee.getListLivraison().get(i).getCoordX())-xmin)/scale*(screenHeigth-12))+5,(int)((((double)tournee.getListLivraison().get(i).getCoordY())-ymin)/scale*(screenHeigth-37))+5,(int)((((double)tournee.getEntrepot().getCoordX())-xmin)/scale*(screenHeigth-12))+5,(int)((((double)tournee.getEntrepot().getCoordY())-ymin)/scale*(screenHeigth-37))+5);
			   }
		   else{
		   g.drawLine((int)((((double)tournee.getListLivraison().get(i).getCoordX())-xmin)/scale*(screenHeigth-12))+5,(int)((((double)tournee.getListLivraison().get(i).getCoordY())-ymin)/scale*(screenHeigth-37))+5,(int)((((double)tournee.getListLivraison().get(i+1).getCoordX())-xmin)/scale*(screenHeigth-12))+5,(int)((((double)tournee.getListLivraison().get(i+1).getCoordY())-ymin)/scale*(screenHeigth-37))+5);
		}
	   }
  }               
}