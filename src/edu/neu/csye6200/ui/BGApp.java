package edu.neu.csye6200.ui;

import edu.neu.csye6200.simbg.BGSimulation;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.BorderLayout;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * An abstract class the defines basic application behavior for the BGApp application
 * @author ChinmayRout
 * NUID: 001520475
 *
 */
public abstract class BGApp implements ActionListener, WindowListener {

	/**
	 * All variables declared that would be used by MyAppUI and BGGeneration
	 */
	
	public static int genText = 0;	//default generation: only one base stem
	public static int progressRate = 0;	//default growth rate, 0 means do not have speed limitation - called in MyCanvas
	
	public static double sideGrowthFactor = 1.05;
	public static double midGrowthFactor = 1.2;	//default length
	public static double sideRadian = Math.PI /9;
	public static double midRadian = Math.PI/15;	//default radian
	
	public static String rule = "rule1";	//default rule
	
	public static boolean isPaused = false;	// judge if we need to stop the growth process
	public static boolean isSimulComplte = false; //if the simulation process is complete
	public static boolean isRestart = true;	//if the drawing process can be restarted
	
	public static Color colr = Color.white; 	//default color
	public static BGSimulation bgSimList  = BGSimulation.generationSet();//singleton pattern

	public static JFrame frame = null;		//base frame where everything will be; 
	public static JTextArea messageTextArea = null;	//setting a messagePanel for information to show
	protected JScrollPane messageScrollPane = null;	// scrollPanel to hold the JTextArea

	protected JPanel generalPanel = null;	//where everything would be stored
	protected JPanel menuPanel = null;		//all the buttons, ComboBox to be stores
	protected JPanel messagePanel = null;	//where the information would be shared
	
	protected MyCanvas bgCanvasPanel = null;
	
	protected MenuManager menuManagr = null;	//menu bar 
	
	protected static Logger log = Logger.getLogger(MyAppUI.class.getName());	//logger based on UI
	
	protected JButton startButton = null;	//start button
	protected JButton pauseButton = null;	//pause button
	protected JButton resumeButton = null;	//resume button
	protected JButton randomButton = null;	//random buttom
	
	protected JComboBox<String> ruleSelector = null;	//Rule select ComboBox
	protected JComboBox<String> colorSelector = null;	//Color select ComboBox
	protected JComboBox<String> speedSelector = null;	//Speed Select ComboBox
	protected JComboBox<String> sideLengthSelector = null;	//Length Select ComboBox
	protected JComboBox<String> sideRadianSelector = null;	//Radian Select ComboBox
	
	protected JTextField generationField = null; //input generation into this textField
	
	protected String logBase = "log/server.log";	//log file routine	
	protected String rules[] = {"rule1", "rule2", "rule3"};	//rule set
	protected String colors[] = {"black", "red", "blue", "green", "yellow"};	//color set
	protected String progressRates[] = {"Direct Output", "Fastest", "Normal", "Slow"}; 	//growth rate set
	protected String lengthRates[] = {"5%","10%","15%","20%"};	//length decrement factor
	protected String radianRates[] = {"Π/12 or 15°", "Π/6 or 30°", "Π/4 or 45°", "Π/3 or 60°", "Π/2 or 90°"};	//angle in factor
	
	
	


	/**
	 * The BGApp constructor. This methis initializes the GUI by performing calls to the
	 * extending class.
	 */
	public BGApp() {
		initGUI();
	}

	/**
	 * Initialize the Graphical User Interface
	 */
	public void initGUI() {
		frame = new JFrame();
		frame.setTitle("BGApp");

		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)

		// Permit the app to hear about the window opening
		frame.addWindowListener(this); 

		menuManagr = new MenuManager(this);

		frame.setJMenuBar(menuManagr.getMenuBar()); // Add a menu bar to this application

		frame.setLayout(new BorderLayout());

		frame.add(getGeneralPanel(), BorderLayout.CENTER);	//think about this

		messageTextArea.insert("App starts!\n\n", 0);
	}

	/**
	 * Override this method to provide the main content panel.
	 * @return a JPanel, which contains the main content of of your application
	 */
	public abstract JPanel getGeneralPanel();


	/**
	 * A convenience method that uses the Swing dispatch threat to show the UI.
	 * This prevents concurrency problems during component initialization.
	 */
	public void showUI() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.setVisible(true); // The UI is built, so display it;
			}
		});

	}
	
	/**
	 * Override this method to show an About Dialog
	 */
	public void showHelp() {
		// show read me file
		try {
			Desktop.getDesktop().open(new java.io.File("data/ABOUT.md"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Shut down the application
	 */
	public void exit() {
		frame.dispose();
		System.exit(0);
	}

	

}
