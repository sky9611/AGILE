//package main.java.com.septanome.util;
//
//public class test {
//	
//}

package com.septanome.util;

import com.septanome.service.ServiceMetier;

import java.io.IOException;

public class test {
	public static void main(String[] argc) throws IOException, ClassNotFoundException {
		ServiceMetier sm = new ServiceMetier();
		sm.initPlan("./src/test/resources/fichiersXML/planLyonGrand.xml");
		sm.initCommande("./src/test/resources/fichiersXML/DLGrand10.xml");
		sm.initPlanLivraison();
		sm.calculerTournee(false);
		System.out.println("Test output:");
		System.out.println(sm.getTournee());
	}
}
