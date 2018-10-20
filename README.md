# InvestmentPortfolio

Ryan Gundu
rgundu@uoguelph.ca

Additional Information

- All ".java" files contained in folder named "a3"
- The custom exceptions are located in investment.java

Summary

- Overall in this assignment the problem is to compute total gain after purchasing and selling many investments at the users request 
-The user interation is through a GUI

The ASSUMPTIONS made are that:
------------------------------
- A file must be provided to run the program
- Only when searching an invalid price will be ignored and considered empty field (other menus will promt an error)
- if there are no investments, no file will be created or written to
- You will not be asked twice before quiting if there is no investments
- price is user defined (does not change in real time)
- all symbols are unique


- To build such a program requires a separate class for both mutualFund and Stock. A super class is used, as both classes hold similar properties. The amount of information stored is unknown so an arraylist is used storing objects related to the Parent class (Investment). The user is prompted to buy, sell, update, getGain, search or quit. These commands are provided in a switch statement contained in a While loop. Each statement branches to different functions to proceed accordingly. Each sell option updates its price and quantity and then the bookValue. The buy command for an existing investment does the same and also updates the bookValue. These options occur in a loop so when the user wants the total gain each arraylist is iterated through calculating the total gain. 

- Correctness is tested through different cases, for example, attempting to enter an existing symbol. Also if you try to sell more quantity then you own, giving an invalid range. Also correctness is shown when the program outputs appropriate messages after the user fails to follow instructions. The program does not crash at any point and only ends when the user specifies.

Test plan
----------
- Attempt to run the program without a file name in command line
- After selling all quantity of an investment, check if the HashshMap is 
adjusted to the new indexes of the arrayList
- Attempt to sell more then then the quantity value
- Attempt to sell a negative quantity
- Add investments and exit program, see if file is updated with new data
- Remove investments and quit program, see if file is updated
- Try to create an investment with an existing symbol
- When prompted for price input characters/ negative value
- When prompted for quantity enter characters/ negative value
- When searching for investments give an invalid range
- Request for total gain at any time when option is available
- while in the update menu try to itterate past the array size

Limitations
------------
- Only a specific formatting of a file can be read by the program
- There are limited error responses (only to the errors expected)


To run on terminal

- move to the appropriate directory 
- run the line "javac a3/*.java" to compile
- run the line "java a3.A3 fileName.txt" to run the program,
where "fileName" can be of your choice
