//package main.java.com.septanome.util;
//
//public class test {
//	
//}

package main.java.com.septanome.util;

import main.java.com.septanome.service.ServiceMetier;

public class test {
	public static void main(String[] argc) {
		ServiceMetier sm = new ServiceMetier();
		sm.initPlan("D:\\documents\\INSA\\4IF\\PLD AGILE\\AGILE\\src\\test\\resources\\fichiersXML\\testPlan.xml");
		sm.initCommande("D:\\documents\\INSA\\4IF\\PLD AGILE\\AGILE\\src\\test\\resources\\fichiersXML\\testLivraisons.xml");
		sm.initPlanLivraison();
		sm.obtenirLeTournee(false);
	}
}
