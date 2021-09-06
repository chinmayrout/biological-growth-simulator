/**
 * 
 */
package edu.neu.csye6200.simbg;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import edu.neu.csye6200.ui.*;

/**
 * @author ChinmayRout
 * NUID: 001520475
 * 
 * A class to generate stems based on the rules from BGRule.
 * 
 */
public class BGGeneration extends BGStem {

	private BGStem bgStem;			//bgStem from BGStem
	private ArrayList<BGStem> bgStemList = new ArrayList<BGStem>();	//arrayList of BGStem

	private BGRule bgRule = new BGRule();		//using BGRule to generate child stems based on rules

	//logger to provide information about activities
	private static Logger log = Logger.getLogger(BGGeneration.class.getName());

	/**
	 * declaring variables to store stem data
	 * @length: length of stem
	 * @xCoordinate: location in X-axis
	 * @yCoordinate: location in Y-axis
	 * @radians: angle in terms of radians
	 */
	double length, xCoordinate, yCoordinate, radians;

	/**
	 * Constructor
	 * logging information
	 */
	public BGGeneration() {
		log.info("Instance of BGGenration is generated!");
	}
	
	/**
	 * A getter to retrieve ArrayList of BGStem
	 * @return: ArrayList of BGStem
	 */
	public ArrayList<BGStem> getBgStemList() {
		return bgStemList;
	}
	
	/**
	 * Storing Stem information in log/stemData.txt
	 */
	private void saveTreeData() {
		try  {		//adding try-catch on file-operations
			File myObj = new File("data/stemData.txt");	//creating new file and adding all stem-data in it.
			 if (myObj.createNewFile()) {
				 log.warning("File created: " + myObj.getName());
			      } else {
			    	  log.warning("File already exists.");	//if file already exists
			      }
			FileWriter newWriter = new FileWriter("data/stemData.txt", false);	//Replace all existing content with new content.
			BufferedWriter bufferedWriter = new BufferedWriter(newWriter);
			bufferedWriter.write(String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s", "stemID", "length", "xCoordinate",
					"yCoordinate", "radians"));
			bufferedWriter.write('\n');
			for (BGStem stem : bgStemList) {		//iterating over arraylist to write into the file
				bufferedWriter.write(stem.toString());
				System.out.println(stem.toString());
				bufferedWriter.write('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warning("Something happened! " + e);
		}
	}
	

	/**
	 * growTree creates all the stems
	 * 
	 * @param rule: rule selected by end user
	 */
	public void growTree(String rule) {

		/*
		 * creating base stem with 120 length, π/2 radians and (0,0) co-ordinates
		 */
		bgStemList.add(new BGStem(120, 0, 0, Math.PI / 2));

		
		if (rule.equals("rule1")) {		// rule1 - 2 child stems
			
			for (int i = 1; i < (Math.pow(2, BGApp.genText) * 2 - 1); i++) {
				bgStem = bgRule.growStem(bgStemList, i,	BGApp.sideGrowthFactor, BGApp.sideRadian);	//applying rule-1 using method overloading
				bgStemList.add(bgStem);
			}
		}
		
		else if (rule.equals("rule2")) {		// rule2 - 3 child stems; 1 in middle
			for (int i = 1; i < ((Math.pow(3, BGApp.genText) * 3 / 2) - 1); i++) {
				bgStem = bgRule.growStem(bgStemList, i, BGApp.sideGrowthFactor, BGApp.midGrowthFactor, BGApp.sideRadian);	//applying rule-2 using method overloading
				bgStemList.add(bgStem);
			}
		}

		// rule3 - grow to four side
		else if (rule.equals("rule3")) {		// rule3 - 4 child stems; 2 child stems in middle
			for (int i = 1; i < ((Math.pow(4, BGApp.genText) * 4 / 3) - 1); i++) {
				bgStem = bgRule.growStem(bgStemList, i,	BGApp.sideGrowthFactor, BGApp.midGrowthFactor, BGApp.sideRadian, BGApp.midRadian);	//applying rule-3 using method overloading
				bgStemList.add(bgStem);
			}
		}
		saveTreeData();	//saving the data in stemData.txt
	}
}
