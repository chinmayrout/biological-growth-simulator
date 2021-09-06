/**
 * The main program to start execution
 */
package edu.neu.csye6200.ui;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


/**
 * @author ChinmayRout
 * NUID: 001520475
 *
 *
 *	Assignment 5: Biological Growth
 */
public class MyAppUI extends BGApp {

	/**
	 * Miscellaneous UI adjustments are implemented here
	 */
	private void customizeGUI() {
		frame.setSize(1400, 1000); // starting JFrame resolution	-	could be changed according the user's requirement
		frame.setTitle("Biological Growth");	//title of the Java Application
	}

	/**
	 * MyAppUI constructor
	 */
	public MyAppUI() {
		menuManagr.createDefaultActions(); // Create and start default Menu-Bar things
		customizeGUI();
		showUI(); // Make the Swing start thread to display the JFrame
	}

	/**
	 * Generate a JPanel which will contain everything in the Java Application
	 * @return a JPanel with content
	 */
	@SuppressWarnings("deprecation")
	@Override
	public JPanel getGeneralPanel() {
		if(generalPanel != null) return generalPanel;	//Only build the panel Once
		generalPanel = new JPanel();
		generalPanel.setLayout(new BorderLayout());
		generalPanel.add(BorderLayout.NORTH, getMenuPanel());		//set the menuPanel to the top 

		bgCanvasPanel = new MyCanvas();
		generalPanel.add(bgCanvasPanel);	// set menuPanel to the left and mainPanel to the right
		bgSimList.addObserver(bgCanvasPanel); /// add observer
		generalPanel.add(BorderLayout.WEST, getMessagePanel());
		return generalPanel;
	}


	/**
	 * Panel to hold buttons, ComboBox, TextFields
	 * These swing items will perform the operation and generate the Biological Tree based on User-Input
	 * 
	 * @return a JPanel with content
	 */
	public JPanel getMenuPanel() {
		menuPanel = new JPanel();							//creating a new JPanel to hold all buttons, ComboBoxes and JTextFields
		menuPanel.setLayout(new FlowLayout());				 //provides a very simple layout manager that is used by the JPanel objects 
		menuPanel.setPreferredSize(new Dimension(1000, 50)); //the size of menu panel is 250*800

		//colorSelector and it's action listener
		colorSelector = new JComboBox<String>();	//using JComboBox, which lets the user choose one of several choices in Color
		colorSelector.setModel(new DefaultComboBoxModel<String>(colors));	//selecting from an array of String of all colors
		colorSelector.addActionListener(e -> {colorBoxAction();});			//adding actionListner as the event handler
		menuPanel.add(new JLabel("COLOR:"));		//adding JLabel to the menuPanel
		menuPanel.add(colorSelector);				//adding the JComboBox

		//jTextField: input the number of generation
		generationField = new JTextField();			//using JTextField, which lets user select the generation number 0 to 9
		generationField.setText("0-9");				//setting default text 0-9
		generationField.setHorizontalAlignment(SwingConstants.CENTER);	//aligning the text to the center
		menuPanel.add(new JLabel("GENERATION:"));	//adding GENERATION - JLabel to the menuPanel
		menuPanel.add(generationField);				//adding generationField - JTextField to the menuPanel

		// ruleSelector and action listener
		ruleSelector = new JComboBox<String>(rules);	//using JComboBox, which lets the user choose one of several choices in Rules
		ruleSelector.setModel(new DefaultComboBoxModel<String>(rules));	//selecting from an array of String of all Rules
		ruleSelector.addActionListener(e -> {ruleBoxAction();});	//adding actionListner as the event handler 
		menuPanel.add(new JLabel("RULE:"));				//adding Rule - JLabel to the menuPanel
		menuPanel.add(ruleSelector);					//adding the ruleSelector JComboBox to the menuPanel

		//speedSelector and action listener
		speedSelector = new JComboBox<String>();		//using JComboBox, which lets the user choose one of several choices in Speed
		speedSelector.setModel(new DefaultComboBoxModel<String>(progressRates));	//selecting from an array of String of all Colors
		speedSelector.addActionListener(e->{speedBoxAction();});	//adding actionListner as the event handler and generating progressRate which will be used in BGGeneration
		menuPanel.add(new JLabel("SPEED:"));			//adding Speed - JLabel to the menuPanel
		menuPanel.add(speedSelector);					//adding the speedSelector JComboBox to the menuPanel


		//adding sideRadianSelector for radian change in different Rules
		sideRadianSelector = new JComboBox<String>();			//using JComboBox, which lets the user choose one of several choices in Radian-Angles
		sideRadianSelector.setModel(new DefaultComboBoxModel<String>(radianRates));			//selecting from an array of String of all Radians
		sideRadianSelector.addActionListener(e->{getRadian();});
		/*
		 * adding actionListner as the event handler and generating sideRadian from getRadian method and this be used in BGGeneration.
		 * @sideRadian: used as new radian to change angle between various stems
		 */
		menuPanel.add(new JLabel("Radian:"));					//adding Radian - JLabel to the menuPanel
		menuPanel.add(sideRadianSelector);						//adding the sideRadianSelector JComboBox to the menuPanel


		//adding sideLengthSelector for % length change in different Rules
		sideLengthSelector = new JComboBox<String>();			//using JComboBox, which lets the user choose one of several choices in Length Factor
		sideLengthSelector.setModel(new DefaultComboBoxModel<String>(lengthRates));			//selecting from an array of String of all Length-Factors
		sideLengthSelector.addActionListener(e->{growLength();});
		/*
		 * adding actionListner as the event handler and generating sideGrowthFactor from growLength method and this be used in BGGeneration.
		 * @sideGrowthFactor: used as a percentage decrease in the upcoming stems
		 */
		menuPanel.add(new JLabel("LENGTH FACTOR:"));			//adding Length Factor - JLabel to the menuPanel
		menuPanel.add(sideLengthSelector);						//adding the sideLengthSelector JComboBox to the menuPanel


		// adding a start button to perform operations
		startButton = new JButton("START!"); 					//adding START - JButton to the menuPanel
		startButton.addActionListener(e -> {startButtonAction();});
		/*
		 * adding actionListener as the event handler on startButton to start the operation
		 * using method startButtonAction
		 */
		menuPanel.add(startButton);				//adding the startButton JButton to the menuPanel

		// adding a Pause button to stop the simulation for a while
		pauseButton = new JButton("PAUSE!");					//adding PAUSE - JButton to the menuPanel
		pauseButton.addActionListener(e -> {bgCanvasPanel.enablePause();	// alter the status of PAUSE button -  PAUSE to CONTINUE and CONTINUE to PAUSE
		if(isRestart == false)								//check if iRestart variable is false
			messageTextArea.insert("Simulation process is paused.\n\n", 0);			//if false; give out information in JTextArea
		});
		menuPanel.add(pauseButton);								//adding the pauseButton JButton to the menuPanel

		// adding a Resume button to resume the simulation after it has been paused
		resumeButton = new JButton("RESUME!");					//adding PAUSE - JButton to the menuPanel
		resumeButton.addActionListener(e -> {bgCanvasPanel.enableResume();	// alter the status of PAUSE button -  PAUSE to CONTINUE and CONTINUE to PAUSE
		if(isRestart == false)								//check if iRestart variable is false
			messageTextArea.insert("Simulation process continues.\n\n", 0);		//if false; give out information in JTextArea
		});
		menuPanel.add(resumeButton);							//adding the resumeButton JButton to the menuPanel

		// adding a Random button to randomize the Input values
		randomButton = new JButton("RANDOM!");					//adding RANDOM - JButton to the menuPanel
		randomButton.addActionListener(e -> {randomBtnAction();});
		/*
		 * adding actionListener as the event handler on randomButton to start the operation
		 * using method resumeButtonAction
		 */

		menuPanel.add(randomButton);							//adding the randomButton JButton to the menuPanel
		menuPanel.setBackground(Color.WHITE);					//setting the background color of menuPanel
		return menuPanel;										//giving out menuPanel
	}


	/**
	 * Generate a basic panel which will contain a JTextArea to display messages and information about simulation activities.
	 * Also holds information about the author and other details
	 * 
	 * @return JPanel
	 */
	private JPanel getMessagePanel() {
		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(250, 1000));
		messagePanel.setLayout(null);

		// Author data and other information
		JLabel dataLabel = new JLabel("Author: Chinmay Rout");	//JLabel to add author information
		dataLabel.setBounds(0, 0, 200, 50);						//setBounds to resize and to conform to the new bounding rectangle
		dataLabel.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 18));	//adding fonts, italicize and bold the text
		messagePanel.add(dataLabel);							//adding to the messagePanel
		dataLabel = new JLabel("Northeastern University");
		dataLabel.setBounds(0, 0, 200, 90);
		dataLabel.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 18));
		messagePanel.add(dataLabel);
		dataLabel = new JLabel("NUID: 001520475");
		dataLabel.setBounds(0, 0, 200, 130);
		dataLabel.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 18));
		messagePanel.add(dataLabel);

		// add a scrollPane to hold messageTextArea
		dataLabel = new JLabel("MESSAGE & INFO TEXT-AREA");		//adding heading to text-area using JLabel
		dataLabel.setBounds(20, 0, 200, 380);
		dataLabel.setFont(new Font("Serif", Font.BOLD, 13));
		messagePanel.add(dataLabel);
		messageScrollPane = new JScrollPane();					//adding a scrollPane to scroll through the JTextArea
		messageScrollPane.setBounds(20, 200, 200, 400);
		messagePanel.add(messageScrollPane);

		// add a textArea to output info
		messageTextArea = new JTextArea();						//adding  a JTextArea to show messages and information
		messageTextArea.setBounds(15, 25, 220, 400);
		messageScrollPane.setViewportView(messageTextArea);		//to make the JTextArea scrollable

		return messagePanel;									//giving out messagePanel
	}



	/**
	 * A method used to set values to different variables which would be used in BGGeneration
	 * used in the actionListener of startButton
	 * 	 
	 */
	private void startButtonAction() {
		if(isRestart == true) {	// to check if the simulation is done and there is no ongoing simulation
			try {
				int genCheck = Integer.parseInt(generationField.getText());
				System.out.print("Number of Generations: "+generationField.getText().trim() + "\n");
				if (genCheck >= 0 && genCheck <= 9) { 								// To check if the generation is between 0 and 9.
					frame.setResizable(true);		// set frame resizable true; 
					genText = Integer.parseInt(generationField.getText());	//getting generation number
					messageTextArea.insert("Simulation Started! \n\r", 0);	//putting information in the messageTextArea 
					isSimulComplte = false;
					isPaused = false; 										// setting it false to not pause the simulation
					isRestart = false;	
					bgSimList.genrationSet(rule); 							// Start the Tree Generation according to the rule
					bgCanvasPanel.paint(bgCanvasPanel.getGraphics());		// Printing the graph on the bgCanvasPanel
				}
				else {	
					log.warning("Invalid Data Input!");	//if generation is invalid (not in 0-9)
					JOptionPane.showMessageDialog(null, " Enter Valid data for Generations (0-9) ", " Wrong Input Data", JOptionPane.ERROR_MESSAGE); 	//showing error in JOptionPane
				}
			}catch(NumberFormatException ex) {	
				log.warning("Invalid Data Input!");		//if any of input is wrong
				JOptionPane.showMessageDialog(null, " Enter Valid data for Generations (0-9) ", " Wrong Input Data", JOptionPane.ERROR_MESSAGE); 		//showing error in JOptionPane
			}
		}
		else {	
			JOptionPane.showMessageDialog(null, "Wait until this simulation is Complete! "		//if isRestart is true, it means execution of previous is happening 
					+ "\nOR \nChange the Speed-ComboBox to Direct Input to complete the simulation ASAP! ",	//need to wait until the simulation is done
					" Cannot Restart!", JOptionPane.ERROR_MESSAGE); 
		}
	}



	/**
	 * A method to randomize the input values according the boundary conditions.
	 * used in actionListener of Random Button
	 */
	private void randomBtnAction() {
		messageTextArea.insert("Set parameters randomly\n\n", 0);

		colorSelector.setSelectedIndex((int) (Math.random() * 5));					// A random index for colorSelector (between 5 different colors)
		generationField.setText(String.valueOf((int) (Math.random() * 9)));		// A random text for generationField (0 to 9)
		ruleSelector.setSelectedIndex((int) (Math.random() * 3));					// A random index for ruleSelector (1 to 3)
		speedSelector.setSelectedIndex((int) (Math.random() * 4));					// A random index for speedSelector	(Slow to Fast)
		sideRadianSelector.setSelectedIndex((int) (Math.random() * 5));				// A random index for sideRadianSelector (5 different angles)
		sideLengthSelector.setSelectedIndex((int) (Math.random() * 4));				// A random index for sideLengthSelector (4 different factors)

	}



	/**
	 * A method used to set colr which would be used to select the color of the tree
	 * used in the actionListener of speedSelector
	 */
	private void colorBoxAction() {
		switch (colorSelector.getSelectedIndex()) {
		case 0:
			colr = Color.black; 	//black color
			break;	
		case 1:
			colr = Color.red; 		//red color
			break;	
		case 2:
			colr = Color.blue; 		//blue color
			break;	
		case 3:
			colr = Color.green;		//green color
			break;	
		case 4:
			colr = Color.yellow;	//yellow color
			break;	
		}
	}	



	/**
	 * A method used to set rule which would be used select the rule for the simulation process
	 * used in the actionListener of ruleSelector
	 */
	private void ruleBoxAction() {
		switch (ruleSelector.getSelectedIndex()) {
		case 0:
			rule = "rule1"; 	//rule1 selected
			break;
		case 1:
			rule = "rule2"; 	//rule2 selected
			break;
		case 2:
			rule = "rule3"; 	//rule3 selected
			break;
		}
	}
	


	/**
	 * A method used to set progressRate which would be used speed up/down the simulation process
	 * used in the actionListener of speedSelector
	 */
	private void speedBoxAction() {
		switch (speedSelector.getSelectedIndex()) {
		case 0:
			progressRate = 0; 	//direct output - 0ms delay
			break;		
		case 1:
			progressRate = 1; 	//Fastest - 1ms delay
			break;
		case 2:
			progressRate = 10; 	//Normal	- 10ms delay
			break;
		case 3:
			progressRate = 50; 	//Slow	- 50ms delay
			break;
		}
	}

	

	/**
	 * A method used to set radian which would be used to bend the stem from the root stem
	 * used in the actionListener of sideRadianSelector
	 */
	private void getRadian() {
		switch(sideRadianSelector.getSelectedIndex()) {
		case 0:
			sideRadian = Math.PI / 12;		//selecting PI/12 or 15°
			break;			

		case 1:
			sideRadian = Math.PI / 6;		//selecting PI/6 or 30°
			break;		

		case 2:
			sideRadian = Math.PI / 4;		//selecting PI/4 or 45°
			break;

		case 3:
			sideRadian = Math.PI / 3;		//selecting PI/3 or 60°
			break;

		case 4:
			sideRadian = Math.PI / 2;		//selecting PI/2 or 90°
			break;			
		}
	}
	
	

	/**
	 * A method used to set value of sidegrowthFactor which would be used while tree generation
	 * used in actionListener of sideLengthSelector
	 */
	private void growLength() {
		switch(sideLengthSelector.getSelectedIndex()) {
		case 0:
			sideGrowthFactor = 1.05; break;			//a decrease in growth by 5% 

		case 1:
			sideGrowthFactor = 1.10; break;			//a decrease in growth by 10% 

		case 2:
			sideGrowthFactor = 1.15; break;			//a decrease in growth by 15% 

		case 3:
			sideGrowthFactor = 1.20; break;			//a decrease in growth by 20% 
		}
	}


	/**
	 * Starting the execution 
	 * @param args
	 */
	public static void main(String[] args) {
		new MyAppUI();
		System.out.println("App is Starting!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		log.info("Window deactivated");		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		log.info("Window Closing!");		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		log.info("Window Opened!");		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		log.info("Window Closed!");		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		log.info("Window Iconified!");		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		log.info("Window De-Iconified!");		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		log.info("Window Activated!");		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		log.info("Window De-Activated!");		
	}
}
