package a3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Contains majority of the functionality used by the GUI
 * @author ryangundu
 */
public class Portfolio {
    
    //Constans for commision fees
    public static final double MF_COST = 45.00;
    public static final double STOCK_COST = 9.99;
    
    //Investment option values selected (buy/sell)
    public static final int BUY_COMMAND = 1;
    public static final int SELL_COMMAND = 2;
    
    
    //Specifies the type (stock/mutualfund)
    public static final int TYPE_STOCK = 0;
    public static final int TYPE_MUTUALFUND = 1;
    public static final int SELL_OPTION = 2;
    
    
    //Return value if symbol exists in another investment type
    public static final int NOT_FOUND = 0;
    public static final int MF_EXIST = 1;
    public static final int STOCK_EXIST = 2;
    public static final int UPDATED = 3;
    public static final int INVALID_NAME = 4;
    
    //Update options
    public static final int NEXT = 0;
    public static final int PREV = 1;
    
    
    //Specific range input identifier
    public static final int EMPTY = -1;
    
    static ArrayList <Investment> investments = new ArrayList<Investment>();
    static Scanner scan = new Scanner(System.in);
    static HashMap <String, ArrayList<Integer>> index = new HashMap <String, ArrayList<Integer>>();
    static int counter = -1;
    
    /**
     * Calculates the number of investments stored
     * @return 
     */
    public static int getDataSize() {
        return investments.size();
    }
    
    /**
     * resets the iteration counter to 0
     */
    public static void resetCounter() {
        counter = 0;
    }

    /**
     * Identifies the specific investment and command then proceeds accordingly
     * @param investment
     * @param command (buy or sell)
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     * @return 
     * @throws a3.Investment.EmptySymbol 
     * @throws a3.Investment.InvalidQuantity 
     * @throws a3.Investment.EmptyName 
     * @throws a3.Investment.InvalidPrice 
     */
    public static String check(int investment, int command, String symbol, String name, int quantity, double price) throws Investment.EmptySymbol, Investment.InvalidQuantity, Investment.EmptyName, Investment.InvalidPrice {
        Investment check = new Investment(symbol, name, quantity, price, quantity * price + STOCK_COST); // To check validity of variables
        if (investment == TYPE_STOCK && command == BUY_COMMAND) {
            return AnalyseArray(TYPE_STOCK,symbol, name, quantity, price);
        }
        else if (investment == TYPE_MUTUALFUND && command == BUY_COMMAND) {
            return AnalyseArray(TYPE_MUTUALFUND, symbol, name, quantity, price);
        }
        else if (investment == SELL_OPTION && command == SELL_COMMAND) {
            return searchListSymbols(symbol, quantity, price);   
        }
        
        return "Oops, something went wrong!";
    }
    
    /**
     * After buy command, AnalyseArray creates an investment or modifies one if it already exists 
     * @param type
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     * @return 
     * @throws a3.Investment.EmptySymbol 
     * @throws a3.Investment.InvalidQuantity 
     * @throws a3.Investment.EmptyName 
     * @throws a3.Investment.InvalidPrice 
     */
    public static String AnalyseArray(int type, String symbol, String name, int quantity, double price) throws Investment.EmptySymbol, Investment.InvalidQuantity, Investment.EmptyName, Investment.InvalidPrice {
       
       int addOrChange;
       double bookValue;
       
        addOrChange =  Portfolio.searchLists(type, symbol, name, quantity, price);
        
        if (name.equals("") && addOrChange == NOT_FOUND) {
            
            return "The Investment does not exist";
            
        } else if (addOrChange == INVALID_NAME){
            
                return "The symbol "+symbol+" was found, but not with with the corresponding name \"" +name+ "\".\nTo update the stock"
                        + " with this symbol, match the name.\nTo create a new stock with this name, consider changing the symbol.";
            
        } else if (addOrChange == NOT_FOUND) { //if it does not exist, create it
            
            bookValue = quantity * price + STOCK_COST;
            if (type == TYPE_STOCK) { //if Stock
                Stock s = new Stock(symbol, name, quantity, price, bookValue);
                Investment inv = (Investment) s;
                investments.add(inv);
            }
            else if (type == TYPE_MUTUALFUND) { //if MutualFund
                MutualFund m = new MutualFund(symbol, name, quantity, price, bookValue-STOCK_COST);
                Investment inv = (Investment) m;
                investments.add(inv);
            }
            return "The Investment has been added!";
        } 
        else if (addOrChange == MF_EXIST) {
            return "Symbol already exists in the MutualFund investment";
        }
        else if (addOrChange == STOCK_EXIST) {
            return "Symbol already exists in the Stock investment";
        } 
        else if (type == TYPE_STOCK) {
            return quantity +" shares of the Stock have been purchased";
        }
        else if (type == TYPE_MUTUALFUND) {
            return quantity +" shares of the MutualFund have been purchased";
        }
    
        return "Failed to add Investment";
    }

    /**
     * Locates an existing investment and allows modifications if appropriate 
     * @param type
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     * @return 
     */
    public static int searchLists (int type, String symbol, String name, int quantity, double price) {
       
        
        for(Investment inv : investments) {

            if (((inv.getSymbol()).toLowerCase()).equals(symbol.toLowerCase())) {
                if (!(name.equals("") || name.toLowerCase().equals(inv.getName().toLowerCase()))) {
                        return INVALID_NAME;
                    }
                if (type == TYPE_STOCK && (inv instanceof Stock)) { //if a Stock and the symbol found is of type stock, proceed
                    Stock s = (Stock) inv;
                    /* new quantity and price in the stock */
                    s.setQuantity(s.getQuantity()+quantity);
                    s.setPrice(price);
                    s.setBookValue(s.getBookValue() + (quantity * s.getPrice()) + STOCK_COST);
                   return UPDATED;  
                }
                else if (type == TYPE_STOCK && (inv instanceof MutualFund)) {
                    /*Symbol already exists in the MutualFund investment*/
                    return MF_EXIST;
                }

                if (type == TYPE_MUTUALFUND && (inv instanceof MutualFund)) { //if a mutualFund and symbol found is a type of mutualfund, proceed
                    MutualFund m = (MutualFund) inv;
                    /* new quantity and price in the Mutual Fund */  
                    m.setQuantity(m.getQuantity() + quantity);
                    m.setPrice(price);
                    m.setBookValue(m.getBookValue() + (quantity * m.getPrice()));
                    return UPDATED;
                }
                else if (type == TYPE_MUTUALFUND && (inv instanceof Stock)) {
                    /*Symbol already exists in the Stock investment*/
                    return STOCK_EXIST;
                }
            }  

        }
            
        return NOT_FOUND;  
    }
    
    /**
     * Checks to see if a symbol actually exists in the correct investment requested before selling
     * @param symbol
     * @param quantity
     * @param price
     * @return  
     * @throws a3.Investment.EmptySymbol  
     * @throws a3.Investment.InvalidQuantity  
     * @throws a3.Investment.InvalidPrice  
     */
    public static String searchListSymbols(String symbol, int quantity, double price) throws Investment.EmptySymbol, Investment.InvalidQuantity, Investment.InvalidPrice {
        
        int i = 0;   
        
        for(Investment inv : investments) {
            if (((inv.getSymbol()).toLowerCase()).equals(symbol.toLowerCase())) {
                    return sell(inv, i, quantity, price);     
            }
           ++i; 
        }
  
        return "The Symbol " + symbol + " does not exist";
    }
    
    /**
     * Appropriately modifies the quantity and price after a sell
     * @param x
     * @param i - the index the object is at
     * @param quantity
     * @param price
     * @return 
     */
    public static String sell(Object x, int i, int quantity, double price) {
        
        int prevQuant;
     
        Investment symb = (Investment)x;            
        symb.setPrice(price);  
        if (quantity > symb.getQuantity()) {
            return "Quantity greater than available stock";
           
        }
        if (quantity == symb.getQuantity()) {
            String returnVal = "All of the " + symb.getSymbol() + " investment has been sold at $" + price +" per share.";
            investments.remove(i); 
            Portfolio.createNameMap();
            return returnVal;

        } else {
            prevQuant = symb.getQuantity();
            symb.setQuantity(symb.getQuantity() - quantity);
            symb.setBookValue(symb.getBookValue() * (symb.getQuantity()/(double)prevQuant));
            return quantity + " share(s) have been sold at $" + price;
        }
        
    }
    
    /**
     * Checks if user inputs a valid price
     * @param price
     * @return the price or -1 if invalid 
     */
    public static double checkPrice(String price) {
       
           try {
               return Double.parseDouble(price);
           }
           catch (NumberFormatException e) {
               return -1;
           }
             
    }
    
    /**
     * Checks the validity of a user entered quantity
     * @param quantity
     * @return valid quantity
     */
    public static int checkQuantity(String quantity) {
       
        
           try {
            return Integer.parseInt(quantity);

           }
           catch (NumberFormatException e) {
                return -1;
           }
           
    }
    
    /**
     * Allows the user to iterate through the investments
     * @param option
     * @return 
     */
    public static String[] fetchData(int option) {
        
        String[] data = {null,null,null};
        
        if (investments.isEmpty()) 
            return null;
        
        switch (option) {
            case NEXT:
                counter++;
                data[0] = investments.get(counter).getSymbol();
                data[1] = investments.get(counter).getName();
                break;
            case PREV:
                --counter;
                data[0] = investments.get(counter).getSymbol();
                data[1] = investments.get(counter).getName();
                break;
            default:
                data[0] = investments.get(counter).getSymbol();
                data[1] = investments.get(counter).getName();
                break;
        }
        
        if (counter + 1  == investments.size()) {
            data[2] = "END";
        } else if (counter == 0) {
            data[2] = "START";
        } else {
            data[2] = "MIDDLE";
        }
        return data;
    }
    
    /**
     * allows the user to modify the price in each investment
     * @param symbol
     * @param price
     * @return 
     * @throws a3.Investment.EmptySymbol 
     * @throws a3.Investment.EmptyName 
     * @throws a3.Investment.InvalidQuantity 
     * @throws a3.Investment.InvalidPrice 
     */
    public static String update(String symbol, double price) throws Investment.EmptySymbol, Investment.EmptyName, Investment.InvalidQuantity, Investment.InvalidPrice {
        
        Investment investment = new Investment(symbol, "check", 1, price, 1 * price + 1);
        int flag = 0;
        for(Investment inv : investments) {
           if (inv.getSymbol().toLowerCase().equals(symbol.toLowerCase())) {
               flag = 1;
               inv.setPrice(price);
               investment = inv;
               break;
           }
         }
        if (flag == 0)
            return "This investment no longer exists, it can't be updated";
        
        return "The Investment has been updated!\n\nUpdated Contents:\n" + investment.toString();
    }

    /**
     * generates the total gain of all investments and each individually
     * @return 
     */
    public static String[] getGain() {
        
        if (investments.isEmpty())
            return null;
        
        double gain = 0;
        double LOSS;
        String[] output = {"Total gain for each investment:\n","temp"};
        for(Investment inv : investments) {
            if (inv instanceof MutualFund) {
                LOSS = MF_COST;
            } else {
                LOSS = STOCK_COST;
            }             
            gain += (inv.getQuantity() * inv.getPrice() - LOSS) - inv.getBookValue();
            output[0] += inv.getSymbol() + ": " + String.format("%.2f", gain) + "\n";
            
        }
        output[1] = String.format("%.2f", gain);
        return output;
        
    }
    
    /**
     * Searches all investments according to the user input
     * Uses hashMap to locate the given name
     * @param name name as a string
     * @param symbol symbol as a string 
     * @param range range as a string
     * @param num1 range1
     * @param num2 range2 if exists
     * @return 
     */
    public static String search(String name, String symbol, String range, double num1, double num2) {
        
        
        symbol = symbol.toLowerCase();
        name = name.toLowerCase();
        createNameMap();
        boolean found = true;
        boolean x = false;
        int i = 0;
        String output = "Results:\n";
   
        for (Investment check : investments) {
            
            found = true;
            
            if (name.length() != 0) {
                if (!getIndex(name, i)) {
                    
                    found = false;
                } 
            }
            
            if (symbol.length() != 0) {
                if (!(check.getSymbol().toLowerCase().equals(symbol))) {
                    found = false;
                } 
            }
   
            if (range.length() != 0) {
                if (num1 != EMPTY && num2 == EMPTY) {
                    if(!(check.getPrice() <= num1) ) {
                        found = false;       
                    }
                }
                if (num1 == EMPTY && num2 != EMPTY) {
                    if(!(check.getPrice() >= num2) ) {
                        found = false;     
                    }
                }
                if (num1 != EMPTY && num2 != EMPTY && num2 != -2) {
                    if(!((check.getPrice()) >= num1 && (check.getPrice() <= num2))) {
                        found = false;                      
                    }
                }
                if (num1 != EMPTY && num2 == -2) {
                    if (!(num1 == check.getPrice())) {
                        found = false;
                    }
                }
            }
            if (found) {
                x = found;
                output += check.toString() + "\n";
            }
            ++i;
        }
        if (!x) {
            output = "No investments of this type";
        }
        return output;
    }
    
    /**
     * Reads from the file name provided and stores the investments into the arrayList
     * @param fileName 
     * @throws a3.Investment.EmptySymbol 
     * @throws a3.Investment.InvalidQuantity 
     * @throws a3.Investment.EmptyName 
     * @throws a3.Investment.InvalidPrice 
     */
    public static void readFile(String fileName) throws Investment.EmptySymbol, Investment.InvalidQuantity, Investment.EmptyName, Investment.InvalidPrice {
         
        String text;
        ArrayList<String> words = new ArrayList<String>();

        try {
            FileReader fReader = new FileReader(fileName);
            BufferedReader bReader = new BufferedReader(fReader);
            while ((text = bReader.readLine()) != null) {
                for(String kind: text.split(",")) {
                    words.add(kind);
                }
            
                if (words.get(0).equals("Stock")) {
                    Stock s = new Stock(words.get(1), words.get(2), Integer.parseInt(words.get(3)),Double.parseDouble(words.get(4)), Double.parseDouble(words.get(5)));
                   Investment inv = (Investment) s;
                   investments.add(inv);
                }
                else if (words.get(0).equals("MutualFund")) {
                   MutualFund m = new MutualFund(words.get(1), words.get(2), Integer.parseInt(words.get(3)),Double.parseDouble(words.get(4)), Double.parseDouble(words.get(5)));
                   Investment inv = (Investment) m;
                   investments.add(inv);
                }
                words.clear();
            }
        }
        catch (IOException e) {
            
        }
        
    }
    
    /**
     * Writes to the file specified, stores all previous (unless removed) and newly added investments
     * @param fName 
     */
    public static void writeFile (String fName) {
        
        BufferedWriter writer;
  
        try {
            writer = new BufferedWriter(new FileWriter(fName));
            for (Investment inv : investments) {
              if (inv instanceof Stock)
                writer.write("Stock" + "," + inv.getSymbol() + "," + inv.getName() + "," + inv.getQuantity() + "," + inv.getPrice() + "," + inv.getBookValue()+ "\n");
              else if (inv instanceof MutualFund)
                writer.write("MutualFund" + "," + inv.getSymbol() + "," + inv.getName() + "," + inv.getQuantity() + "," + inv.getPrice() + "," + inv.getBookValue()+ "\n");
            }   
            writer.close();
        }
        catch (IOException e){
            System.out.print("Error writing to file");
                   
        }
                    

    }
    
    /**
     * The creation of a hashMap
     * Assigns all of the corresponding indexes to a specific key
     */
    public static void createNameMap () {
        index.clear();
        int i = 0;
        String name;
        for (Investment inv : investments) {
            name = inv.getName();
            name = name.toLowerCase();
            ArrayList <String> splitName = new ArrayList <String>();
            for(String key : name.split(" ")) {
                splitName.add(key);
            }
            
            for (String check : splitName) {
                
                if (index.containsKey(check)) {
                    if (!(index.get(check).contains(i)))
                        index.get(check).add(i);
                }
                else {
                    ArrayList <Integer> a = new ArrayList <Integer>();
                    a.add(i);
                    index.put(check, a);
                }
            }
            ++i;
        }
    }
    
    /**
     * Checks for intersection
     * @param name
     * @param i
     * @return 
     */
    public static boolean getIndex(String name, int i) {
        
        boolean x;

        for(String check: name.split(" ")) {
            if (index.containsKey(check)) {
                x = index.get(check).contains(i);
                if (!x) 
                    return false;
            } else {
                return false;
            }
           
        }
        return true;    
    }
    
}

    