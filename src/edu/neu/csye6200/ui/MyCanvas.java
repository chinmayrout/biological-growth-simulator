/**
 * 
 */
package edu.neu.csye6200.ui;

import edu.neu.csye6200.simbg.*;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;



/**
 * @author ChinmayRout
 * NUID: 001520475
 * A Canvas Class to generate paint growth for each generation
 */
@SuppressWarnings("deprecation")
public class MyCanvas extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;		//Adding a default serial version ID to the selected type.
	private long ctr = 0L;	//counter of drawBG
	private Logger log = Logger.getLogger(MyCanvas.class.getName());	//logger


	/**
	 * Canvas constructor
	 */
	public MyCanvas() {
		this.setLayout(null);	//no layout manager
		this.setBackground(Color.decode("#CAF0F8"));		//setting color to the bgPanel to #CAF0F8
	}
	
	/**
	 * Switch Pause false on the thread
	 */
	synchronized void enableResume() {
		BGApp.isPaused = false;
		notifyAll();
	}
	

	/**
	 * Switch Pause true on the thread
	 */
	synchronized void enablePause() {
		BGApp.isPaused = true;
	}


	/**
	 * The UI thread calls this method when the screen changes, or in response to a
	 * user initiated call to repaint();
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawBG(g); 	//Updated drawing
	}


	/**
	 *  Get data from BGSimulation ArrayList(data about all the stems)
	 *  Pause the simulation if isPaused is true
	 *  After simulation is complete set isPause false, isSimComplete true
	 *  
	 * @param g2d
	 */
	private void drawTree(Graphics2D g2d) {
		try {
			if (BGApp.bgSimList.getBgGenList().isEmpty() == false) {	// Initially, Canvas is initialized without any stem info
				BGApp.messageTextArea.insert("Simulation in Process!...\n\n", 0);
				for (int i = 0; i < BGApp.bgSimList.getBgGenList().get(0).getBgStemList().size(); i++) {
					BGStem stem = BGApp.bgSimList.getBgGenList().get(0).getBgStemList().get(i); // get the BGStem according to the iteration
					paintStem(g2d, stem, BGApp.colr); //printing the BGStem on the canvas according to the iteration

					Thread.sleep(BGApp.progressRate);	//progressing(speed) according to the rate provided by the user

					synchronized(this) {
						while (BGApp.isPaused == true) {	//Pause if the user-defined isPause is set to be true
							wait();
						}
					}
				}
				/**
				 * After the simulation process is over
				 */
				BGApp.isRestart = true;	//New Simulations can be started now
				BGApp.isPaused = false;	
				BGApp.isSimulComplte = true;	//Simulation is complete
				BGApp.messageTextArea.insert("Simulation is done!\n\n", 0);	//Giving information on the textArea
			}
		}
		catch (InterruptedException e ) {
			e.printStackTrace();
		}
	}


	/**
	 * Draw the arrayList of all stems on bgPanel
	 * 
	 * @param g
	 */
	public void drawBG(Graphics g) {
		log.info("Starting the draw Process: " + ctr++);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,  RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/**
		 * using anonymous inner class to start a new thread
		 * using this thread to draw the simulation
		 * START-PAUSE-RESUME can control the thread
		 */
		if (BGApp.isSimulComplte == false) {
			new Thread(new Runnable() {
				@Override
				public void run() {drawTree(g2d);}}).start();
		}

		/**
		 * when drawing is complete, JPanel is repainted and we dont need thread anymore
		 */		
		if (BGApp.isSimulComplte == true) {
			for (int i = 0; i < BGApp.bgSimList.getBgGenList().get(0).getBgStemList().size(); i++) {
				BGStem stem = BGApp.bgSimList.getBgGenList().get(0).getBgStemList().get(i);
				paintStem(g2d, stem, BGApp.colr);
			}
		}
	}


	/**
	 * Method to draw the stem and give color according to the user
	 * @param g2d: Graphics2D content
	 * @param stem: BGStem stem - to be drawn
	 * @param color: color of the stem
	 */
	private void paintStem(Graphics2D g2d, BGStem stem, Color color) {
		Dimension size = getSize();
		Line2D line;
		g2d.setColor(BGApp.colr);		//setting color of the line according the user-defined input

		line = new Line2D.Double(stem.getxCoordinate() + size.getWidth() / 2,									// x1co-ordinate
				-stem.getyCoordinate() + size.getHeight(),													// y1 co-ordinate
				(stem.getxCoordinate() + stem.getLength() * Math.cos(stem.getRadians()) + size.getWidth() / 2),	// x2 co-ordinate
				-(stem.getyCoordinate() + stem.getLength() * Math.sin(stem.getRadians())) + size.getHeight());	// y2 co-ordinate
		g2d.draw(line);			//drawing stem based on x1,y1,x2,y2 coordinates
	}


	/**
	 * new updates from BGGeneration can be observed here
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
	}

}
