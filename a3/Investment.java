package a3;

/**
 * Parent class to Stock and MutualFund
 * @author ryangundu
 */
public class Investment {
    
    
    private String symbol;
    private String name;
    private int quantity;
    private double price; 
    private double bookValue;
    
    
    Investment (String symbol, String name, int quantity, double price, double bookValue) throws EmptySymbol, EmptyName, InvalidQuantity, InvalidPrice {
        if (symbol.equals(""))
            throw new EmptySymbol();
        if (name.equals(""))
            throw new EmptyName();
        if (quantity <= 0)
            throw new InvalidQuantity();
        if (price <= 0)
            throw new InvalidPrice();
          
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;
          
    }
    
    /* Exceptions */
    
    /**
     * Class for EmptySymbol exception
     */
    public class EmptySymbol extends Exception {
        
        public EmptySymbol () {
            super("Symbol can't be empty");
        }
        
    }
    
    /**
     * Class for EmptyName exception
     */
    public class EmptyName extends Exception {
        
        public EmptyName () {
            super("Name can't be empty");
        }
        
    }
    
    /**
     * Class for InvalidQuantity exception
     */
    public class InvalidQuantity extends Exception {
        
        public InvalidQuantity () {
            super("Quantity must be greater than zero");
        }
        
    }
    
    /**
     * Class for invalidPrice exception 
     */
    public class InvalidPrice extends Exception {
        
        public InvalidPrice () {
            super("Price must be greater than zero");
        }
        
    }
    /**********************************************/

    
    /* Setters and getters*/
    
    /**
     * sets the Symbol
     * @param symbol 
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }  
    
    /**
     * gets the symbol
     * @return 
     */
    public String getSymbol() {
        
        return symbol;    
    }
    
    /**
     * sets the Name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * gets the name
     * @return 
     */
    public String getName() {
        
        return name;   
    }
    
    /**
     * sets the quantity
     * @param quantity 
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    } 
    
    /**
     * gets the quantity
     * @return 
     */
    public int getQuantity() {
        
        return quantity;  
    }
    
    /**
     * sets the price
     * @param price 
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * gets the price
     * @return 
     */
    public double getPrice() {
        
        return price; 
    }
    
    /**
     * sets the book value
     * @param bookValue 
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }
    
    /**
     * gets the book value
     * @return 
     */
    public double getBookValue() {
        
        return bookValue; 
    }
    /**
     * Equals method for investments
     * @param a
     * @return 
     */
    @Override
    public boolean equals (Object a) {
        
        if (this == a) return true;
        if (a == null) return false;
        if (!this.getClass().equals(a.getClass())) return false;
        
        Investment o = (Investment)a;
        if (!this.getSymbol().equals(o.getSymbol())) return false;
        if (!(this.getPrice()==o.getPrice())) return false;
        if (!(this.getQuantity()==o.getQuantity())) return false;
        if (!this.getName().equals(o.getName())) return false;
        if (!(this.getPrice()==o.getPrice())) return false;
        if (!(this.getBookValue()==o.getBookValue())) return false;
        return true;
        
    }
}
