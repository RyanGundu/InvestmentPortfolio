package a3;

/**
 * Sub class of Investment
 * @author ryangundu
 */
public class Stock extends Investment {
     
    
     /**
     * Constructor for MutuaFund
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     * @param bookValue 
     */
    Stock (String symbol, String name, int quantity, double price, double bookValue) throws EmptySymbol, InvalidQuantity, EmptyName, InvalidPrice {
        
        super(symbol, name, quantity, price, bookValue);
          
    }
    
    /**
     * equals function for stock
     * @param a
     * @return 
     */
    @Override
    public boolean equals (Object a) {
        if (this == a) return true;
        if (a == null) return false;
        if (!this.getClass().equals(a.getClass())) return false;    
        return (super.equals(a));
    }
    
    /**
     * toString method for stock
     * @return 
     */
    @Override
    public String toString() {
       
        return ("Stock: " + "Symbol: " + getSymbol() + " Name: " + getName() + " Quantity: " 
                + getQuantity() + " Price: " + String.format("%.2f", getPrice()) + " BookValue: " + String.format("%.2f", getBookValue()));
    }
}
