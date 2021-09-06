package edu.neu.csye6200.simbg;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author ChinmayRout
 * NUID: 001520475
 * 
 * A class to grow the tree
 * Also gives out information in the messagePanel
 */

@SuppressWarnings("deprecation")
public class BGSimulation extends Observable {	//implements Runnable

	/*
	 * Declaring class Variables
	 */
	private BGGeneration bgGeneration;	//object of BGGeneration
	private ArrayList<BGGeneration> bgGenList = new ArrayList<BGGeneration>();	//list to collect bgGeneration
	private static BGSimulation generationSet = null;
	private static Logger log = Logger.getLogger(BGSimulation.class.getName());	

	/**
	 *  singleton pattern
	 */
	private BGSimulation() {		//private constructor
	}

	public static BGSimulation generationSet() {	//one instance only
		if(generationSet == null) {
			log.info("An instance of BGGenrationSet is created");	//logging info
			generationSet = new BGSimulation();
		}			
		return generationSet;
	}


	/**
	 * Generates stemdata based on rule
	 * @param rule: has rule1, rule2 and rule3. 
	 * growTree depends on rules
	 */
	public void genrationSet(String rule) {
		bgGeneration = new BGGeneration();
		
		switch (rule) {

		case "rule1":
			bgGeneration.growTree("rule1");		//rule1 - grow two child-stems - 2 side-ways
				break;

		case "rule2":
			bgGeneration.growTree("rule2"); 	//rule2 - grow three child-stems - 2 side-ways and 1 in middle
				break;

		case "rule3":
			bgGeneration.growTree("rule3");		//rule3 - grow four child-stems - 2 side-ways and 2 in middle
				break;		
		}
		
		bgGenList.add(0,bgGeneration);	
		setChanged();	//Observable object as having been changed
		notifyObservers(new String("A message"));	//notify all of its observers
	}
	
	/**
	 * Getter of ArrayList of BGGeneration
	 * @return ArrayList of BGGeneration
	 */
	public ArrayList<BGGeneration> getBgGenList() {
		return bgGenList;
	}

}
