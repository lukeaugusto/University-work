import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;

	private JLabel inputLabel, firstOutputLabel, secondOutputLabel, thirdOutputLabel, fourthOutputLabel, fifthOutputLabel;
	private JTextField inputTF, firstOutputTF, secondOutputTF, thirdOutputTF, fourthOutputTF, fifthOutputTF;
	private JPanel panel;
	private JButton runB, exitB;
	private RunButtonHandler runHandler;
	private ExitButtonHandler ebHandler;
	
	public GUI(){
		
		// Create the labels and TextFields
		inputLabel = new JLabel("Input file: ");
		inputTF = new JTextField();
		
		firstOutputLabel = new JLabel("First output file: ");
		firstOutputTF = new JTextField();
		

		secondOutputLabel = new JLabel("Second output file: ");
		secondOutputTF = new JTextField();
		

		thirdOutputLabel = new JLabel("Third output file: ");
		thirdOutputTF = new JTextField();


		fourthOutputLabel = new JLabel("Fourth output file: ");
		fourthOutputTF = new JTextField();
		
		fifthOutputLabel = new JLabel("Fifth output file: ");
		fifthOutputTF = new JTextField();
		
		panel = new JPanel();
		
		// Create the buttons and handlers
		runB = new JButton("Run");
		runHandler = new RunButtonHandler();
		runB.addActionListener(runHandler);
		runB.setPreferredSize(new Dimension(200, 30));
	
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		exitB.setPreferredSize(new Dimension(200,30));
		
		// Add the elements in the panel
		panel.add(inputLabel);
		panel.add(inputTF);
		panel.add(firstOutputLabel);
		panel.add(firstOutputTF);
		panel.add(secondOutputLabel);
		panel.add(secondOutputTF);
		panel.add(thirdOutputLabel);
		panel.add(thirdOutputTF);
		panel.add(fourthOutputLabel);
		panel.add(fourthOutputTF);
		panel.add(fifthOutputLabel);
		panel.add(fifthOutputTF);
		panel.add(runB);
		panel.add(exitB);
		
		// Configure the panel
		panel.setLayout(new GridLayout(7, 2));
		setTitle("Files, Strings and GUI assignment");
		
		// Configure the frame
		add(panel);
		setSize(400,200);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private class RunButtonHandler implements ActionListener{
		
		private Scanner input;
		private File temp;
		private Formatter output, output2, output3, output4, output5;
		
		// Check if a string contains line
		private boolean validString(String line, String regex){
			Matcher m = Pattern.compile(regex).matcher(line);
			return m.find();
		}
		
		// Check if the line is valid
		private boolean validLine(String line){
			return validString(line, "\\d{2}/\\d{2}/\\d{2}");
		}
		
		// Create the numbers in the str
		private ArrayList<Integer> createNumbers(String str){
			// numbers[0] = n1
			// numbers[1] = n2
			// numbers[2] = n3
			// numbers[3] = n4
			// numbers[4] = n5
			// numbers[5] = pb
			ArrayList<Integer> numbers = new ArrayList<Integer>();
			String[] aux;
			
			aux = str.split("((-)|( ))");
			
			for(String number : aux)
				if(number.matches("[0-9]+")) numbers.add(Integer.parseInt(number));

			return numbers;
		}
		
		public void actionPerformed(ActionEvent e){
			if(inputTF.getText().trim().length() != 0 && 
				firstOutputTF.getText().trim().length() != 0 && 
				secondOutputTF.getText().trim().length() != 0 && 
				thirdOutputTF.getText().trim().length() != 0 && 
				fourthOutputTF.getText().trim().length() != 0 &&
				fifthOutputTF.getText().trim().length() != 0
				){
				
				// Open the input file
				try{	
					temp = new File(Paths.get(inputTF.getText().trim()).toString());
					input = new Scanner(temp);
				}catch (IOException IOException){
					JOptionPane.showMessageDialog(null,"No input file found. Terminating.", "Error", JOptionPane.PLAIN_MESSAGE);
					System.exit(1);
				}

				int i, j;
				
				String line;
				String[] winners, auxDays;
				
				ArrayList<ArrayList<Integer>> baseNumbers = new ArrayList<ArrayList<Integer>>();
				ArrayList<String> dates = new ArrayList<String>();
				ArrayList<Integer> days = new ArrayList<Integer>();
				
				StringBuilder result = new StringBuilder();
				StringBuilder result2 = new StringBuilder();	
				StringBuilder result3 = new StringBuilder();	
				StringBuilder result4 = new StringBuilder();	
				StringBuilder result5 = new StringBuilder();				
				
				while(input.hasNextLine()){
					line = input.nextLine();
					if(validLine(line)){
						
						// winners[0] = First column date
						// winners[1] = First column numbers
						// winners[2] = Second column date
						// winners[3] = Second column numbers
						winners = line.split("(?=(?!^)\\d{2}/\\d{2}/\\d{2})|(?<=\\d{2}/\\d{2}/\\d{2})");
						
						// Add the numbers in the ArrayList
						baseNumbers.add(createNumbers(winners[1]));
						baseNumbers.add(createNumbers(winners[3]));

						// Add the date in the ArrayList
						dates.add(winners[0]);
						dates.add(winners[2]);
						
						// Transform the date in days
						auxDays = winners[0].split("/");
						days.add(Integer.parseInt(auxDays[0]) * 30 +
							     Integer.parseInt(auxDays[1]) +
							     Integer.parseInt(auxDays[2]) * 360);
						
						auxDays = winners[2].split("/");
						days.add(Integer.parseInt(auxDays[0]) * 30 +
							     Integer.parseInt(auxDays[1]) +
							     Integer.parseInt(auxDays[2]) * 360);
					}
				}
				
				// Sort the data (Selection Sort)
				for (i = 0; i < days.size()-1; i++){
		            int index = i;
		            for (j = i + 1; j < days.size(); j++)
		                if (days.get(j) < days.get(index))
		                    index = j;
		            
		            // Switch values
		            Integer auxSort = days.get(index); 
		            days.set(index, days.get(i));
		            days.set(i, auxSort);
		            
		            String auxSort2 = dates.get(index);
		            dates.set(index, dates.get(i));
		            dates.set(i, auxSort2);
		            
		            ArrayList<Integer> auxSort3 = baseNumbers.get(index);
		            baseNumbers.set(index, baseNumbers.get(i));
		            baseNumbers.set(i, auxSort3);
		        }

				// Create the content of the first file
				for(i=0;i<baseNumbers.size(); i++){
					result.append((i+1) + "  ");
					for(Integer number : baseNumbers.get(i)){
						result.append(number + "  ");
					}
					result.append("\n");
				}
				
				// Create the content of the second file
				for(i=0; i<dates.size(); i++)
					result2.append((i+1) + " " + dates.get(i).replace("/"," ") + "\n");
				
				// Create the content of the third file
				for(i=0;i<baseNumbers.size(); i++)
					for(Integer number : baseNumbers.get(i))
						result3.append(number + "\n");
				
				// Create the content of the fourth file
				double mean = 0, stdDeviation = 0;
				double count = 0;
				
				// Calculate mean
				for(i=0;i<baseNumbers.size(); i++){
					for(Integer number : baseNumbers.get(i)){
						mean += number;
						count++;
					}
				}
				mean = mean / count; 
				
				// Calculate Standard Deviation
				for(i=0;i<baseNumbers.size(); i++){
					for(Integer number : baseNumbers.get(i)){
						stdDeviation += (mean-number) * (mean-number);
					}
				}
				
				stdDeviation = Math.sqrt(stdDeviation/count);
				
				result4.append("MEAN = " + new DecimalFormat("#0.0000").format(mean) + "\n");
				result4.append("STANDARD DEVIATION = " + new DecimalFormat("#0.0000").format(stdDeviation) + "\n");
				
				// Create the content of the fifth file
				// Set the appearance of the numbers to 0
				int appearance[] = new int[100];
				int qtdNumbers = 0;
				for(int qtd : appearance){
					qtd = 0;
				}
				
				// Iterate over the array and count the appearance of the numbers.
				for(i=0; i<baseNumbers.size(); i++){
					for(Integer number : baseNumbers.get(i)){
						appearance[number]++;
						qtdNumbers++;
					}
				}
				
				// Convert the appearance count to percentage and create the result5 string
				for(i=0; i<appearance.length; i++){
					if(appearance[i]>0){
						float percentage = ((float)appearance[i] / qtdNumbers) * 100;
						result5.append(i + "\t" + new DecimalFormat("#0.00").format(percentage) + "\n");
					}
				}
				
				// Create the output files
				try{
					output = new Formatter(Paths.get(firstOutputTF.getText().trim()).toString());
					output2 = new Formatter(Paths.get(secondOutputTF.getText().trim()).toString());
					output3 = new Formatter(Paths.get(thirdOutputTF.getText().trim()).toString());
					output4 = new Formatter(Paths.get(fourthOutputTF.getText().trim()).toString());
					output5 = new Formatter(Paths.get(fifthOutputTF.getText().trim()).toString());
				}catch(SecurityException securityException){
					JOptionPane.showMessageDialog(null,"Write permission denied. Terminating.", "Error", JOptionPane.PLAIN_MESSAGE);
					System.exit(1);
				}catch(FileNotFoundException fileNotFoundException){
					JOptionPane.showMessageDialog(null,"Error opening file. Terminating.", "Error", JOptionPane.PLAIN_MESSAGE);
					System.exit(1);
				}
				
				// Save the output files
				output.format("%s", result.toString());
				output2.format("%s", result2.toString());
				output3.format("%s", result3.toString());
				output4.format("%s", result4.toString());
				output5.format("%s", result5.toString());
				
				// Close the files
				input.close();
				output.close();
				output2.close();
				output3.close();
				output4.close();
				output5.close();
				
				// Show the success message and terminate
				JOptionPane.showMessageDialog(null, "Power Ball data processing completed", "Error", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);

			}else
				JOptionPane.showMessageDialog(null,"Enter the file names", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	private class ExitButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
}

