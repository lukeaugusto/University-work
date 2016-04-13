import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ElevGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JTextArea result;
	private JScrollPane paneResult;
	private JLabel passengers, floors;
	private JTextField passengersTF, floorsTF;
	private JButton runB, exitB;
	private JPanel pane, background, radioPane, submitPane;
	private ButtonGroup types;
	private JRadioButton simpleElev, basicElev, advancedElev;
	private RunButtonHandler runHandler;
	private ExitButtonHandler ebHandler;
	public ElevGUI()
	{	
		passengers = new JLabel("Enter the number of passengers: ", SwingConstants.CENTER);
		floors = new JLabel("Enter the number of floors: ", SwingConstants.CENTER);
		result = new JTextArea( 13, 37 );
		result.setEditable ( false );

		passengersTF = new JTextField();
		floorsTF = new JTextField();	

		types = new ButtonGroup();
		simpleElev = new JRadioButton("Simple Elevator", true);
		basicElev = new JRadioButton("Basic Elevator", false);
		advancedElev = new JRadioButton("Advanced Elevator", false);

		types.add(simpleElev);
		types.add(basicElev);
		types.add(advancedElev);

		//Specify handlers for each button and add (register) ActionListeners to each button.
		runB = new JButton("Run");
		runHandler = new RunButtonHandler();
		runB.addActionListener(runHandler);
		runB.setPreferredSize(new Dimension(200, 30));

		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		exitB.setPreferredSize(new Dimension(200,30));

		setTitle("Elevator to Heaven");

		// Fields Pane
		pane = new JPanel() ;
		pane.setLayout(new GridLayout(2, 2));
		pane.add(passengers);
		pane.add(passengersTF);
		pane.add(floors);
		pane.add(floorsTF);

		// Buttons Pane
		submitPane = new JPanel();
		submitPane.add(runB);
		submitPane.add(exitB);

		// Buttons Pane
		radioPane = new JPanel();
		radioPane.setLayout(new GridLayout(1, 3));
		radioPane.add(simpleElev);
		radioPane.add(basicElev);
		radioPane.add(advancedElev);

		// Results Pane
		paneResult = new JScrollPane(result);
		paneResult.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		paneResult.setVisible(true);

		// Background Panel
		background = new JPanel();
		background.add(pane);
		background.add(radioPane);
		background.add(submitPane);
		background.add(paneResult);
		add(background);

		// set sizes and layout
		setDefaultLookAndFeelDecorated(false);
		setSize(500,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Fill the result TextArea
	public void setEntry(String entry){
		result.append(entry + "\n");
	}

	private class RunButtonHandler implements ActionListener
	{
		// Check if the elements in a string are all int
		private boolean verifInt(String value){
			for(int i=0; i<value.length(); i++){
				char c = value.charAt(i);
				if(c < '0' || c > '9') return false;
			}
			return true;
		}

		public void actionPerformed(ActionEvent e)
		{
			// If the field's values are valid, run the algorithm
			if(passengersTF.getText().trim().length() != 0 && floorsTF.getText().trim().length() != 0  && verifInt(floorsTF.getText()) && verifInt(passengersTF.getText())){
				
				Random rnd= new Random(0);
				int maxWeight = 250;                   // max is 250 lbs
				int capacity = maxWeight*4;
				Time startingTime = new Time(8,0,0);   // 8 am
				int floors = Integer.parseInt(floorsTF.getText());;
				boolean verbose = true;
				int secondsPerFloor = 10;
				int timeOpenDoors = 15; // 15 seconds to open the door
				long currentTime = startingTime.getTime();
				int maxSeconds = 200;
				int numberOfPassengers = Integer.parseInt(passengersTF.getText()); //We use the getText & setText methods to manipulate
				// the data entered into those fields.
				Queue<PassengerRequest> elevatorQueue = new LinkedList<PassengerRequest>();
				final Elevator elevator;
				// Code for simpleElev
				if(simpleElev.isSelected()){
					elevator = new DumbElevatorV2(capacity,
							secondsPerFloor,floors, timeOpenDoors, verbose);
				} else if(basicElev.isSelected()){
					elevator = new BasicElevator(capacity,
							secondsPerFloor,floors, timeOpenDoors, verbose);
				} else if(advancedElev.isSelected()){
					elevator = new FastV5(capacity,
							secondsPerFloor,floors, timeOpenDoors, verbose);
				} else {
					elevator = null;
				}

				for (int i = 0; i < numberOfPassengers; i++) {
					int floor_from =rnd.nextInt(floors) + 1; // to generate numbers from [1, floords]
					int floor_to   = rnd.nextInt(floors) + 1; 

					int weight = rnd.nextInt(maxWeight);

					int seconds = rnd.nextInt(maxSeconds);
					currentTime+=seconds * 1000;
					Time time = new Time(currentTime);
					PassengerRequest request = new PassengerRequest(time, floor_from, 
							floor_to, weight);
					elevatorQueue.add(request);
				}
				elevator.initialize(elevatorQueue);
				// for the visualization to run correctly the operation of the elevator needs to be done in 
				// another thread.
				Thread elevatorThread = new Thread(new Runnable() {

					@Override
					public void run() {
						final ArrayList<PassengerReleased> output = elevator.operate();
						long cost = 0;

						for (int i = 0; i < output.size(); i++) {
							PassengerReleased passenger = output.get(i);
							Time timeRequested = passenger.getTimeArrived();
							Time timeLeft = passenger.getPassengerRequest().getTimePressedButton();

							cost+=Math.abs(timeLeft.getTime() - timeRequested.getTime());
						}

						// System.out.println("Total cost (in seconds): " + cost/ 1000);

						result.append("Total cost (in seconds): " + cost / 1000 + "\n\n");
					}
					
				});
				elevatorThread.start();
			}else
				JOptionPane.showMessageDialog(null,"Wrong parameters", "Error", JOptionPane.PLAIN_MESSAGE);

		}
	}

	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		    java.awt.EventQueue.invokeLater(new Runnable() {
		          public void run() {
		               ElevGUI frame = new ElevGUI();
		               frame.setVisible(true);
		          }
		    });
		}
	}
	
	
