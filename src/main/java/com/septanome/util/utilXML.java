package main.java.com.septanome.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import main.java.com.septanome.model.Commande;
import main.java.com.septanome.model.Livraison;
import main.java.com.septanome.model.Plan;
import main.java.com.septanome.model.Point;
import main.java.com.septanome.model.Troncon;

/**
 * Some tools to load XML files or create an XML file
*/

public class utilXML {

	/** @param folder Path to XML file */
	public HashMap<Long, Point> loadPoint(String folder) {
		Point point;
		HashMap<Long, Point> points = new HashMap<Long, Point>(); 
		
		try {
			File fXmlFile = new File(folder);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			doc.getDocumentElement().getNodeName();
			NodeList nList = doc.getElementsByTagName("noeud");			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Long id = Long.parseLong(eElement.getAttribute("id"));
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					point = new Point(id, x, y);
					points.put(id, point);					
				} 
			}
			return points;
		    } catch (Exception e) {
			e.printStackTrace();
			return null;
		    } 
	}
	
	/** @param folder Path to XML file*/
	public HashMap<Long, HashMap<Long, Troncon>> loadTroncon(String folder) {
		Troncon troncon;
		HashMap<Long, Troncon> h = new HashMap<Long, Troncon>(); 
		HashMap<Long, HashMap<Long, Troncon>> troncons = new HashMap<Long, HashMap<Long, Troncon>>();
		
		try {
			File fXmlFile = new File(folder);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			doc.getDocumentElement().getNodeName();
			NodeList nList = doc.getElementsByTagName("troncon");			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Long dest = Long.parseLong(eElement.getAttribute("destination"));
					double length = Double.parseDouble(eElement.getAttribute("longueur"));
					String street = eElement.getAttribute("nomRue");
					Long origine = Long.parseLong(eElement.getAttribute("origine"));
					troncon = new Troncon(dest, length, street, origine);
					h.put(origine,troncon);
					troncons.put(dest, h);						
				} 
			}
			return troncons;
		    } catch (Exception e) {
			e.printStackTrace();
			return null;
		    } 
	}
	
	/** 
	 * Load a command from an XML file
	 * @param folder Path to XML file
	 * @param hash list of existing points
	 * @return Return a @Commande
	 */
	public Commande loadCommande(String folder, Plan plan) {
		HashMap<Long, Point> hash = plan.getPointsMap();
		Commande commande;
		Livraison livraison;
		List<Livraison> liste = new Vector<Livraison>();
		Point entrepot = null;
		int heure = -1;
	
		try {
			File fXmlFile = new File(folder);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			doc.getDocumentElement().getNodeName();
			
			//Get entrepot attributes
			NodeList nList = doc.getElementsByTagName("Entrepot");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					heure = Integer.parseInt(eElement.getAttribute("h"));
					entrepot = findPointbyCoords(x,y, hash);
				}
			}
			
			if(entrepot == null) {
				System.out.println("Entrepot localisation not found");
				return null;
			}
			
			if(heure < 0 || heure > 24) {
				System.out.println("Hour format incorrect");
				return null;
			}
			
			nList = doc.getElementsByTagName("Livraison");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					//long d = Long.parseLong(eElement.getAttribute("d"));
					Point p = findPointbyCoords(x,y, hash);
					
					if(p != null) {
						int hd = Integer.parseInt(eElement.getAttribute("hd"));
						int hf = Integer.parseInt(eElement.getAttribute("hf"));
						livraison = new Livraison(p.getId(), p.getCoordX(), p.getCoordY(), hd, hf);
						liste.add(livraison);
					} else {
						System.out.println("Ce noeud n'existe pas : " + x + " " + y);
					}				
				}
			}
			commande = new Commande(heure, entrepot, liste);
			return commande;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		  } 	
		}
	
	/**
	 * Find a point into the HashMap from coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param hash List of existing @Point
	 * @return @Point if it exists, null otherwise
	 */
	
	public Point findPointbyCoords(int x, int y, HashMap<Long, Point> hash) {
		Point point = null;
		for(Map.Entry<Long,Point> entry:hash.entrySet()) {
			Point p = entry.getValue();
			if(p.getCoordX()==x && p.getCoordY()==y) {
				point = p; 
			}
		}		
		return point;		
	}
	
	public void writeTourneeToFile() {
		
	}
	
}
