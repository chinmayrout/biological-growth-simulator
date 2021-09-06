package edu.neu.csye6200.simbg;

import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * @author ChinmayRout
 * NUID: 001520475
 * 
 * A class to define the rules of growth for the Biological Tree
 * 
 *  RULES - RULES for generation of child stems
 *  rule1 - grow two child-stems - 2 side-ways
 */
public class BGRule {
	
	private static Logger log = Logger.getLogger(BGRule.class.getName());	//logger to log all activities
	private BGStem stem;	//Making an object of BGStem which will be used to store stem-data

	/**
	 * 	variables to be used in growStem on the basis of Rules
	 */
	private double length;		//length of stem
	private double xCoordinate;	//x-coordinate of stem
	private double yCoordinate;	//y-coordinate of stem
	private double radians;		//angle in radians
	private double sideReduce; 	//stem length reduction factor (side-ways)
	private double midReduce;	//stem-length reduction factor	(mid-ways) for rule 2 and 3

	/**
	 * Constructor of BGRule
	 * give out information while logging
	 */
	public BGRule() {
		log.info("Constructor of BGRule called!");
	}


	/** 
	 * rule1 - grow two child-stems - 2 side-ways
	 * Generate 2 - child stems from the initial stem
	 * child stems length based on sideReduce factor
	 * @param bgSimList: ArrayList containing all Stems
	 * @param itr: index of element in bgSimList
	 * @param sideGrowthFactor: side-child-length manipulation
	 * @param sideRadian: angle manipulation based on every itr
	 * 
	 * return BGStem - child stem based on rule 1
	 */
	public BGStem growStem(ArrayList<BGStem> bgSimList, int itr, double sideGrowthFactor, double sideRadian) {

		/**
		 * Generating sideReduce by using pow() on sideGrowthFactor on logarithmic itr and reducing it in every iteration.
		 * This is done to get a reduced length of child stems
		 */ 
		sideReduce = Math.pow(sideGrowthFactor, ((int) (Math.log(itr + 2) / Math.log(3))));		//used to reduce length of child stems 
		// RULE1: Every Stem Generates 2 Child STEMS!
		// stems generate on the left side first
		if (itr % 2 == 1) {
			updateStemData(bgSimList, (itr - 1) / 2);	// generating length, x,y-coordinates and the radian for the new stem

			stem = new BGStem(
					(length / sideReduce),						// to calculate the child-stem length
					(xCoordinate + length * Math.cos(radians)),	// child-stem's x-coordinate
					(yCoordinate + length * Math.sin(radians)),	// child-stem's y-coordinate
					radians + sideRadian);						// new radian	-	left side
		}
		// stems generates on the right side next
		else if (itr % 2 == 0) {
			updateStemData(bgSimList, (itr - 2) / 2);
			stem = new BGStem(
					(length / sideReduce), 						// to calculate the child-stem length
					(xCoordinate + length * Math.cos(radians)), // child-stem's x-coordinate
					(yCoordinate + length * Math.sin(radians)),	// child-stem's y-coordinate
					radians - sideRadian);						// new radian	-	right side
		}
		return stem;			//return the new child-stem based on rule1
	}


	/**	rule2 - grow three child-stems - 2 side-ways and 1 in middle
	 * Generate 3 - child stems from the initial stem
	 * child stems length based on sideReduce factor
	 * angle based on sideRadian
	 * 
	 * @param bgSimList: ArrayList containing all Stems
	 * @param itr: index of element in bgSimList
	 * @param sideLengthGrow: side-child-length manipulation
	 * @param midLengthGrow: mid-length manipulation 
	 * @param sideRadian: angle manipulation based on itr
	 * 
	 * return BGStem - child stem based on rule 2
	 */
	public BGStem growStem(ArrayList<BGStem> bgSimList, int itr, double sideLengthGrow, double midLengthGrow, double sideRadian) {
		/**
		 * Generating sideReduce by using pow() on sideGrowthFactor on logarithmic 2 times itr and reducing it in every iteration.
		 * This is done to get a reduced length of child stems
		 * Generating midReduce to add middle child: (rule-2)specific
		 */ 
		sideReduce = Math.pow(sideLengthGrow, ((int) (Math.log(2 * itr + 1) / Math.log(3))));	//2 times to generate more space for more child stems
		midReduce = Math.pow(midLengthGrow, ((int) (Math.log(2 * itr + 1) / Math.log(3))));		//

		// RULE2: Every Stem Generates 3 Child STEMS!

		if (itr % 3 == 1) {			// child stem generate on the left side 
			updateStemData(bgSimList, (itr - 1) / 3);
			stem = new BGStem((length / sideReduce),			// to calculate the child-stem length
					(xCoordinate + length * Math.cos(radians)), // child-stem's x-coordinate
					(yCoordinate + length * Math.sin(radians)),	// child-stem's y-coordinate
					(radians + sideRadian));					// new radian	-	left side
		}

		else if (itr % 3 == 2) {	//	child stem grow in the middle

			updateStemData(bgSimList, (itr - 2) / 3);			// to calculate the child-stem length - middle stem
			stem = new BGStem((length / midReduce),				// child-stem's x-coordinate
					(xCoordinate + length * Math.cos(radians)), // child-stem's y-coordinate
					(yCoordinate + length * Math.sin(radians)), //same radian as it is the middle one
					radians);
		}

		else if (itr % 3 == 0) {	//	child stem grows in the right
			updateStemData(bgSimList, (itr - 3) / 3);
			stem = new BGStem((length / sideReduce),			// to calculate the child-stem length - middle stem
					(xCoordinate + length * Math.cos(radians)), // child-stem's x-coordinate
					(yCoordinate + length * Math.sin(radians)),	// child-stem's y-coordinate
					(radians - sideRadian));					// new radian	-	right side
		}
		return stem;				//return the new child-stem based on rule2
	}



	/** rule3 - grow four child-stems - 2 side-ways and 2 in middle
	 * 
	 * 
	 * @param bgSimList: ArrayList containing all Stems
	 * @param itr: index of element in bgSimList
	 * @param sideLengthGrow: side-child-length manipulation
	 * @param midLengthGrow: mid-length manipulation 
	 * @param sideRadian: angle manipulation based on itr
	 * @param midRadian: angle manipulation for inner child stems based on itr
	 * 
	 * return BGStem - - child stem based on rule 3
	 */
	public BGStem growStem(ArrayList<BGStem> bgs, int i, double sideLengthGrow, double midLengthGrow, double sideRadian, double midRadian) {
		sideReduce = Math.pow(sideLengthGrow, (int) (Math.log(3 * i + 1) / Math.log(4)));
		midReduce = Math.pow(midLengthGrow, (int) (Math.log(3 * i + 1) / Math.log(4)));

		// RULE2: Every Stem Generates 3 Child STEMS!

		if (i % 4 == 1) {			// child stem generate on the left side
			updateStemData(bgs, (i - 1) / 4);
			stem = new BGStem((length / sideReduce),
					(xCoordinate + length * Math.cos(radians)), 
					(yCoordinate + length * Math.sin(radians)),
					(radians + sideRadian));
		}

		else if (i % 4 == 2) {		// child stem generate on the mid-left side
			updateStemData(bgs, (i - 2) / 4);
			stem = new BGStem((length / midReduce),
					(xCoordinate + length * Math.cos(radians)), 
					(yCoordinate + length * Math.sin(radians)),
					(radians + midRadian));
		}

		else if (i % 4 == 3) {		// child stem generate on the mid-right side
			updateStemData(bgs, (i - 3) / 4);
			stem = new BGStem((length / midReduce),
					(xCoordinate + length * Math.cos(radians)), 
					(yCoordinate + length * Math.sin(radians)), 
					(radians - midRadian));
		}
		else if (i % 4 == 0) {		// child stem generate on the right side
			updateStemData(bgs, (i - 4) / 4);
			stem = new BGStem((length / sideReduce),
					(xCoordinate + length * Math.cos(radians)),
					(yCoordinate + length * Math.sin(radians)),
					(radians - sideRadian));
		}
		return stem;		//return the new child-stem based on rule2
	}

	/**
	 * the current stem data based on the previous stem data
	 * 
	 * @param lastStemID: index of stem in the arrayList
	 * @param bgStemList: ArrayList of stems which contains the whole Tree
	 */
	private void updateStemData(ArrayList<BGStem> bgStemList, int lastStemID) {
		length = bgStemList.get(lastStemID).getLength();
		xCoordinate = bgStemList.get(lastStemID).getxCoordinate();
		yCoordinate = bgStemList.get(lastStemID).getyCoordinate();
		radians = bgStemList.get(lastStemID).getRadians();
	}
}
