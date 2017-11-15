package com.septanome.util;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.septanome.model.Point;
import com.septanome.model.Troncon;


public class utilXML {

	public Vector<Point> loadPoint(String folder) {
		Vector<Point> points = new Vector<Point>(); 
		Point point;
		
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
					points.add(point);					
				} 
			}
			return points;
		    } catch (Exception e) {
			e.printStackTrace();
			return null;
		    } 
	}
	
	
	public Vector<Troncon> loadTroncon(String folder) {
		Vector<Troncon> troncons = new Vector<Troncon>(); 
		Troncon troncon;
		
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
					troncons.add(troncon);	
					
				} 
			}
			return troncons;
		    } catch (Exception e) {
			e.printStackTrace();
			return null;
		    } 
	}
	
	
	
}
