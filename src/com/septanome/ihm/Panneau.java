import javax.swing.JPanel;
import java.awt.*;
public class Panneau extends JPanel {
 
  Point[] p;
  int[][] r;
  Point[] tournee;
  int screenHeigth;
  
  public Panneau(Point[] tab,int[][] road,Point [] t,int h){
	  screenHeigth=h;
	  p=new Point[tab.length];
	  r=new int[road.length][2];
	  tournee=new Point[t.length];
	  for(int i=0;i<tab.length;i++){
		  p[i]=tab[i];
		  }
	  for(int i=0;i<road.length;i++){
		  r[i][0]=road[i][0];
		  r[i][1]=road[i][1];
		  }
	  for(int i=0;i<t.length;i++){
		  tournee[i]=t[i];
		  }
	}
  public void paintComponent(Graphics g){ 
	  int xmin=4000;
	  int xmax=-1;
	  int ymin=4000;
	  int ymax=-1;
	  for(int i=0;i<p.length;i++){ 
		if(p[i].x>xmax){
			xmax=p[i].x;
		}
		if(p[i].x<xmin){
			xmin=p[i].x;
		}
		if(p[i].y>ymax){
			ymax=p[i].y;   
		}
		if(p[i].y<ymin){
			ymin=p[i].y;   
		}
	   }
	   int scale = (ymax-ymin>xmax-xmin)? ymax-ymin:xmax-xmin;
	   for(int i=0;i<p.length;i++){             
			g.fillOval((int)((((double)p[i].x)-xmin)/scale*(screenHeigth-12)), (int)((((double)p[i].y)-ymin)/scale*(screenHeigth-37)), 10, 10);
	   }
	   g.drawLine(screenHeigth,0,screenHeigth,screenHeigth);
	   for(int i=0;i<r.length;i++){
		   int id1=-1;
		   int id2=-1;
		   for(int j=0;j<p.length;j++){
			   if(p[j].i==r[i][0]){
				  id1=j;
			   }
			   if(p[j].i==r[i][1]){
				  id2=j;
			   }
			   if(id1!=-1 && id2!=-1) {
					break;
			   }
		    }
			g.drawLine((int)((((double)p[id1].x)-xmin)/scale*(screenHeigth-12))+5,(int)((((double)p[id1].y)-ymin)/scale*(screenHeigth-37))+5,(int)((((double)p[id2].x)-xmin)/scale*(screenHeigth-12))+5,(int)((((double)p[id2].y)-ymin)/scale*(screenHeigth-37))+5);
	   }
	   g.setColor(Color.red);
	   for(int i=0;i<tournee.length;i++){
			int j=i+1;
			if(i==tournee.length-1)
				j=0;
		   g.drawLine((int)((((double)tournee[i].x)-xmin)/scale*(screenHeigth-12))+5,(int)((((double)tournee[i].y)-ymin)/scale*(screenHeigth-37))+5,(int)((((double)tournee[j].x)-xmin)/scale*(screenHeigth-12))+5,(int)((((double)tournee[j].y)-ymin)/scale*(screenHeigth-37))+5);
	   }
  }               
}
