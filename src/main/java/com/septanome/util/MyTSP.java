package main.java.com.septanome.util;

import java.util.ArrayList;
import java.util.Iterator;

import tsp.TemplateTSP;

public class MyTSP extends TemplateTSP{

	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		// TODO Auto-generated method stub
		return nonVus.iterator();
	}

}
