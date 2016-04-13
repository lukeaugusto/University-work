import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 * Author: Lucas A Santos
 * Course:  CIS 5200, Spring 2016
 * Project: Homework 5 - 
 * 
 * The Java code should do the following: 

	Homework # 5
	
	Original Due Date: Wednesday, March 2, 2016
	
	Purpose: To perform string manipulations, sorting of data in Java using Power Ball winning numbers data and performing statistical calculations.  New features are added to the functionality of homework 4.
	
	Requirements:
	
	-Prompt the user for the input file name pb.txt
	
	-Prompt the user for the first output file pbo.txt
	
	-Prompt the user for the second output file dates.txt
	
	-Prompt the user for a third output file numbers.txt
	
	-Prompt the user for the fourth output file stats.txt
	
	-The Java code should do the following:
	
	            -Open the file pb.txt, read the string lines,
	
	            -Extract the 5 winning numbers and the PB number and save them as integers n1, n2, n3, n4, n5, and pb. 
	
	            -Sort the dates in ascending order, and assign an integer variable dateIndex for the date, which starts at 1 and is incremented by one for each date. 
	
	            -Output the first data file pbo.txt containing the date index and the winning numbers
	
	            -Output a second data file dates.txt which contain the date index and the actual dates
	
	            -Output a third file numbers.txt which contain all the integer numbers n1, n2, n3, n4, n5 and pb, one per line, and written in the same order as in file pbo.txt.
	
	            -Calculate the mean and the standard deviation of the numbers included in file numbers.txt, and output as floats to a fourth file stats.txt
	
	            -The file pbo.txt looks as follows:
	
	                        dateIndex  n1  n2  n3  n4  n5  pb
	
	                        -Note that spaces must separate the numbers, and you can use a format of your                                    choosing.  Numbers n1, n2, n3, n4 and pb must be output as integers.
	
	            -The file dates.txt looks as follows
	
	                        dateIndex   month    day   [two digit year]
	
	            -The file numbers.txt looks as follows
	
	               n1
	
	               n2
	
	               n3
	
	               n4
	
	n5
	
	               nb
	
	n1
	
	               n2
	
	               …
	
	               -The file stats.txt looks as follows
	
	MEAN = ….
	
	STANDARD DEVIATION = …
	
	-Example of output files
	
	Input file sample from pb.txt
	
	01/23/16  -22  32- 34- 40- 69  PB 19  X4  07/25/15  -27  29- 34- 41- 44  PB  2  X3 
	
	01/20/16  -5  39- 44- 47- 69  PB 24  X5  07/22/15  -12  31- 43- 44- 57  PB 11  X2 
	
	01/16/16  -3  51- 52- 61- 64  PB  6  X2  07/18/15  -6  37- 39- 45- 55  PB 33  X3 
	
	The first output filepbo.txt will look as follows:
	
	1     6  37  39  45   55  33 
	
	2   12  31  43  44   57  11   
	
	3   27  29  34  41   44    2  
	
	4    3   51  52  61   64    6 
	
	5    5   39  44  47   69  24
	
	6  22   32  34  40   69  19 
	
	The second output file sample dates.txt will look as follows:
	
	1   07  18  15 
	
	2   07  22  15 
	
	3   07  25  15 
	
	4   01  16  16 
	
	5   01  20  16 
	
	6   01  23  16 
	
	 
	
	The third output file numbers.txt will look as follows:
	
	6 
	
	37 
	
	39 
	
	45   
	
	55  
	
	33 
	
	12 
	
	31  
	
	43  
	
	44   
	
	57  
	
	11   
	
	27 
	
	29  
	
	34  
	
	41   
	
	44    
	
	2  
	
	3  
	
	51 
	
	52 
	
	61   
	
	64   
	
	6 
	
	5  
	
	39 
	
	44 
	
	47   
	
	69 
	
	24
	
	22  
	
	32 
	
	34 
	
	40   
	
	69  
	
	19 
	
	 
	
	The fourth output file stats.txt will look as follows:
	
	 
	
	MEAN = 35.3056
	
	STANDARD DEVIATION = 18.8070
	
	-When file reading and processing is completed, close the input and output files
	
	-Then output the message "Power Ball data processing completed" must be printed
	
	-Provide the Java file or files, the output files pbo.txt, dates.txt and numbers.txt and a flowchart or pseudocode in a compressed file folder, attached to the assignment
	
	-Note that the flowchart or pseudocode must show the code logic
	
	How it will be graded:
	
	-Include the problem statement at the beginning (you can cut and paste this entire document)
	
	-The Java code must produce correct results
	
	-The specifications in this document must be followed
	
	-The assignment must be submitted on time for full credit
	
	-Code style: Include comments in your code, use spaces to improve code readability
	
	-Efficiency: Make sure your code follows the logic shown in your flowchart or pseudocode    
	
	Additional notes:
	
	Any code that is not yours must be referenced.  For instance, provide the web link, author and so forth. 
	
	Academic integrity will be enforced.  For instance, students submitting identical codes, or copying code from the web without referencing them.


 * 
*/

public class Driver {
	public static void main(String[] args) throws IOException{
		
		// Instantiate the GUI
		GUI frame = new GUI();
        frame.setVisible(true);		
        
	}
}