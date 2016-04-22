## Synopsis

Perform string manipulations, sorting of data in Java using Power Ball winning numbers data and performing statistical calculations. 

## Motivation

Course: CIS 5200 - Advanced Programming in Java, Spring 2016, Florida Institute of Technology

## Installation

Source codes of a Java Project. 
Import the .java files in the src folder.
Compile and run.
The default dataset is pb.txt file, insert its name in the Power Ball File field and the program will work properly.

## Assignment 


Requirements:

-Prompt the user for the input file name pb.txt

-Generate output files pbo.txt, dates.txt, numbers.txt, stats.txt and probs.txt

-In addition to the files generated in homework # 5, a new file called probs.txt is generated.

-Each row in this file contain a number and its probability. 

-The number must be formatted as an integer.  The probability must be formatted as a float number with two decimal places.

-In this way, the first column contains numbers, and the second column contain probabilities. 

-The numbers must be sorted in ascending order.

-The numbers used are the ones contained in file numbers.txt.

-The probability using a percentage is defined as the number of times a number is repeated divided by the total number of entries in file numbers.txt, and multiplied by 100.  A way to check the validity of your probabilities is that their addition must equal 100. 

-When file reading and processing is completed, close the input and output files

-Then output the message "Power Ball data processing completed" must be printed

Example of a probability calculation.

Suppose you have numbers

2, 3, 4, 3, 2 and 3.

There are a total of 6 numbers.

2 is repeated twice, so its probability is (2/6)*100 = 33.33

3 is repeated three times, so its probability is (3/6)*100 = 50.00

4 is shown only one time, so its probability is (1/6)*100 = 16.67



The numbers and their probabilities are then printed as

2    33.33

3    50.00

4    16.67



To check this work we add the probabilities:

33.33 + 50.00 + 16.67 =100.00



If the addition is not 100, there is an error in the calculations.



-Example of output files

Input file sample from pb.txt.  Note that this is a sample data set which can be used to test your code, but the file that you must read and process is pb.txt which contains much more numbers.

01/23/16  -22  32- 34- 40- 69  PB 19  X4  07/25/15  -27  29- 34- 41- 44  PB  2  X3 

01/20/16  -5  39- 44- 47- 69  PB 24  X5  07/22/15  -12  31- 43- 44- 57  PB 11  X2 

01/16/16  -3  51- 52- 61- 64  PB  6  X2  07/18/15  -6  37- 39- 45- 55  PB 33  X3 

The first output file  pbo.txt will look as follows:

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



The fifth output file probs.txt will look as follows:

2

2.78

3

2.78

5

2.78

6

5.56

11

2.78

12

2.78

19

2.78

22

2.78

24

2.78

27

2.78

29

2.78

31

2.78

32

2.78

33

2.78

34

5.56

37

2.78

39

5.56

40

2.78

41

2.78

43

2.78

44

8.33

45

2.78

47

2.78

51

2.78

52

2.78

55

2.78

57

2.78

61

2.78

64

2.78

69

5.56
