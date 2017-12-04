package com.septanome.test;

import com.septanome.exception.BadLinkException;
import com.septanome.exception.EmptyListException;
import com.septanome.model.Chemin;
import com.septanome.service.ServiceMetier;

import java.io.IOException;


public class Test {

    public static void main(String[] argc) throws ClassNotFoundException, IOException, BadLinkException, EmptyListException {

        long startTime = System.currentTimeMillis();

        ServiceMetier sm = new ServiceMetier();
        sm.initPlan("./src/test/resources/fichiersXML/planLyonGrand.xml");
        sm.initCommande("./src/test/resources/fichiersXML/DLgrand10.xml");
        sm.initPlanLivraison();
        sm.calculerTournee(true);
        System.out.println("Test output:");
        System.out.println(sm.getTournee());
        System.out.println();

        int l = 0;
        for (Chemin c : sm.getTournee().getChemins()) {
            l += c.getLongeur();
        }
        System.out.println(l);
        //System.out.println(sm.getTournee().getChemins().get(9).getLongeur());

        long endTime = System.currentTimeMillis();
        System.out.println("execution time： " + (endTime - startTime)/1000 + "s");
    }
}
