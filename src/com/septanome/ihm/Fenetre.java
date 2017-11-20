import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


 
public class Fenetre extends JFrame implements ActionListener {
	
	
	JButton valider=new JButton("valider");
	JButton b1=new JButton("          action 1            ");
	JButton b2=new JButton("          action 2            ");
    JButton b3=new JButton("          action 3            ");
    JButton b1o=new JButton("          action 4            ");
    JButton b2o=new JButton("          action 5            ");
    JButton b3o=new JButton("          action 6            ");
    JTextField loadPlan=new JTextField("entrez le chemin");
    
  public Fenetre(Point[] tab,int[][] road,Point[] tournee){             
    super("Fullscreen");
    this.setTitle("Map");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Toolkit toolkit =  Toolkit.getDefaultToolkit ();
	Dimension dim = toolkit.getScreenSize();
	getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
	pack();
	setResizable(false);
	show();
	this.setLocationRelativeTo(null);
              
    //Instanciation content
    Panneau pan = new Panneau(tab,road,tournee,dim.height); 
    pan.setLayout(new BorderLayout());
    
    //création menu(side bar)
    JPanel menu=new JPanel();
    menu.setPreferredSize(new Dimension(dim.width-dim.height, dim.height));
    menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
    
    //création panel de bouton
    JPanel butPan=new JPanel();
    butPan.setLayout(new BoxLayout(butPan,BoxLayout.Y_AXIS));
    
    JPanel text=new JPanel();
    text.setLayout(new BoxLayout(text,BoxLayout.X_AXIS));
    JPanel but1=new JPanel();
    but1.setLayout(new BoxLayout(but1,BoxLayout.X_AXIS));
    
    JPanel but2=new JPanel();
    but2.setLayout(new BoxLayout(but2,BoxLayout.X_AXIS));
    
    JPanel tour= new JPanel();
    tour.setLayout(new BoxLayout(tour,BoxLayout.Y_AXIS));
    JPanel tourTitle=new JPanel();
    
    
    tourTitle.setPreferredSize(new Dimension(300, 70));
	tourTitle.setMaximumSize(new Dimension(300, 70));
    JLabel j=new JLabel("ordre de la tournee",SwingConstants.CENTER);
    j.setFont(j.getFont().deriveFont(32f));

    
    tourTitle.add(j);
    for(int i=0;i<tournee.length;i++){
		JLabel l=new JLabel(Integer.toString(tournee[i].i));
		tour.add(l);
	}
    
 
    b1.addActionListener(this);

    
    valider.addActionListener(this);
    

	loadPlan.setPreferredSize(new Dimension(150, 25));
	loadPlan.setMaximumSize(new Dimension(150, 25));
	text.add(loadPlan);
      text.add(Box.createRigidArea(new Dimension(30,0)));
      text.add(valider);
      
      but1.add(b1);
      but1.add(Box.createRigidArea(new Dimension(30,0)));
      but1.add(b2);
       but1.add(Box.createRigidArea(new Dimension(30,0)));
      but1.add(b3);
          
      but2.add(b1o);
      but2.add(Box.createRigidArea(new Dimension(30,0)));
      but2.add(b2o);
       but2.add(Box.createRigidArea(new Dimension(30,0)));
      but2.add(b3o);  
      
      butPan.add(Box.createRigidArea(new Dimension(0,20)));
	  butPan.add(but1);
      butPan.add(Box.createRigidArea(new Dimension(0,20)));
	  butPan.add(but2);
      
	  menu.add(Box.createRigidArea(new Dimension(0,20)));
      menu.add(text);
      menu.add(Box.createRigidArea(new Dimension(0,50)));
	  menu.add(butPan);
	  menu.add(Box.createRigidArea(new Dimension(0,50)));
	  menu.add(tourTitle);
	  menu.add(tour);
	  
      pan.add(menu, BorderLayout.EAST); 

    this.getContentPane().add(pan); 
 
                 
    this.setVisible(true);
  }
     public void actionPerformed(ActionEvent arg0){
		   if(arg0.getSource() == valider){
				   Point[] tab=new Point[6];
				   Point a=new Point(100,100,1453);
                   Point b=new Point(210,100,2);
                   Point c=new Point(100,200,3);
                   Point d=new Point(135,135,4);
                   Point e=new Point(100,150,3);
                   Point f=new Point(150,100,4);
                   tab[0]=a;
                   tab[1]=b; 
    tab[2]=c;
    tab[3]=d;
    tab[4]=e;
    tab[5]=f;
    
    Point [] tournee=new Point[3];
    tournee[0]=a;
    tournee[1]=d;
    tournee[2]=b;
    
    int[][] road= new int[4][2];
    road[0][0]=1453;
    road[0][1]=3;
    road[1][0]=1453;
    road[1][1]=2;
    road[2][0]=3;
    road[2][1]=4;
    road[3][0]=2;
    road[3][1]=4;
    Fenetre fenetre = new Fenetre(tab,road,tournee);
			}
			
     } 
       
public static void main(String[] args){
    Fenetre fenetre = new Fenetre(new Point[0],new int[0][0],new Point[0]);
  }       
}  


