package main.java.com.septanome.util;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	
	public void writeTourneeToFile(String folder) {
		try {  
	           
		     DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();  
		     DocumentBuilder builder = factory.newDocumentBuilder();  
		           
		         Document document = builder.newDocument();   
		         //创建属性名、赋值  
		         Element root = document.createElement("Languages");  
		         root.setAttribute("cat", "it");  
		  
		                 //创建第一个根节点、赋值  
		         Element lan = document.createElement("lan");  
		         lan.setAttribute("id", "1");  
		         Element name = document.createElement("name");  
		         name.setTextContent("java");  
		         Element ide = document.createElement("IDE");  
		         ide.setTextContent("Eclipse");  
		         lan.appendChild(name);  
		         lan.appendChild(ide);  
		             
		         //创建第二个根节点、赋值  
		         Element lan2 = document.createElement("lan");  
		         lan2.setAttribute("id", "2");  
		         Element name2 = document.createElement("name");  
		         name2.setTextContent("Swift");  
		         Element ide2 = document.createElement("ide");  
		         ide2.setTextContent("XCode");  
		         lan2.appendChild(name2);  
		         lan2.appendChild(ide2);  
		           
		                 //添加到属性中、  
		         root.appendChild(lan);  
		                 root.appendChild(lan2);      
		                 document.appendChild(root);  
		           
		                //定义了用于处理转换指令，以及执行从源到结果的转换的  
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();  
		        Transformer transformer = transformerFactory.newTransformer();  
		        transformer.setOutputProperty("encoding", "UTF-8");  
		              
		        StringWriter writer = new StringWriter();  
		        transformer.transform(new DOMSource(document), new StreamResult(writer));  
		        System.out.println(writer.toString());  
		              
		        transformer.transform(new DOMSource(document), new StreamResult(new File(folder)));     
		          
		          
		    } catch (ParserConfigurationException | TransformerException e) {  
		        e.printStackTrace();  
		    }
	}
	
	public void bubbleSort(int a[]) {
		int n = a.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				if (a[j] > a[j + 1]) {
					int temp = a[j]; 
					a[j] = a[j + 1];
					a[j + 1] = temp;  
				}  
			}
		}
	}   
	
	public int getIndex(int a[], int value) {
		int n = a.length;
		for (int i = 0; i < n - 1; i++) {
			if (a[i]==value) {
				return i;
			}
		}
		return -1;
	}   
	
	
	
	
}
