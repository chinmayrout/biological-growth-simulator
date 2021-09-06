package edu.neu.csye6200.simbg;

/**
 * @author ChinmayRout
 * NUID: 001520475
 * A class to define the basic structure of a stem
 */
public class BGStem {

	private int stemID;					//generate a stemID(unique)
	private double length;				//length of each stem
	private double xCoordinate;			//Co-ordinate on X-axis
	private double yCoordinate;			//Co-ordinate on Y-axis
	private double radians;	
	private static int stemCTR = 0;		// Counter for each iteration

	/**
	 * Default Constructor
	 */
	public BGStem() {

	}

	/**
	 * BGStem - Constructor
	 * 
	 * @param length: length of each stem 
	 * @param xCoordinate: X-axis coordinate
	 * @param yCoordinate: Y-axis coordinate
	 * @param radians: Angle in radians of each stem
	 */
	public BGStem(double length, double xCoordinate, double yCoordinate, double radians) {
		setLength(length);
		setxCoordinate(xCoordinate);
		setyCoordinate(yCoordinate);
		setRadians(radians);
		this.stemID = stemCTR++;	//Increment counter for each stem
	}

	/*
	 * method overridden to perform pretty-print of Data
	 */
	@Override
	public String toString() {
		return String.format("%1$-16d %2$-16.2f %3$-16.2f %4$-16.2f %5$-16.2f", this.stemID, this.length, 
				this.xCoordinate,	this.yCoordinate, this.radians);
	}

	//get-set of locationX,Y
	public double getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public double getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/*
	 * Getter - Length
	 */
	public double getLength() {
		return length;
	}
	
	/*
	 * Setter - Length
	 */
	public void setLength(double length) {

		//the length should not less than 0
		if (length <= 0)
			length = 0;
		this.length = length;
	}
	
	/*
	 * Getter - Radians
	 */
	public double getRadians() {
		return radians;
	}
	
	/*
	 * Setter - Radians
	 */
	public void setRadians(double radians) {
		this.radians = radians;
	}

	/*
	 * getter of stemID
	 */
	public int getStemID() {
		return stemID;
	}

}
