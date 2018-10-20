package a3;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Class containing the main function 
 * @author ryangundu
 */
public class A3 extends JFrame implements ActionListener {

    public static final String GREETING =   "<html>Welcome to Investment Portfolio<br><br>"
                                            +"Choose a command from the \"Commands\" menu to buy"
                                            +" or sell an investment, update prices for all investments,"
                                            +" get gain for the portfolio, search for relevant investments,"
                                            +" or quit the program.<html>";
    
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int LINES = 60;
    public static final int CHAR_PER_LINE = 120;
    public static String EXIT_MESSAGE = "Are you sure you want to exit the program?\nYour ivestments will be saved to ";
    
    /**
     * Constructor that takes file name and creates main frame
     * @param fName
     * @throws a3.Investment.EmptySymbol
     * @throws a3.Investment.InvalidQuantity
     * @throws a3.Investment.EmptyName
     * @throws a3.Investment.InvalidPrice 
     */
    public A3 (String fName) throws Investment.EmptySymbol, Investment.InvalidQuantity, Investment.EmptyName, Investment.InvalidPrice {
        
        super("Investment Portfolio");
        String fileName = fName;
        this.EXIT_MESSAGE = EXIT_MESSAGE + fileName;
        Portfolio.readFile(fileName);
        
        setSize(WIDTH, HEIGHT);
        
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            
            if (Portfolio.getDataSize() != 0) {
                int confirmed = JOptionPane.showConfirmDialog(null, EXIT_MESSAGE, "Exit Program and Save", JOptionPane.YES_NO_OPTION); 
                if (confirmed == JOptionPane.YES_OPTION) {
                    Portfolio.writeFile(fileName);
                    System.exit(0);
                } else if (confirmed == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
                }
            } else {
                 System.exit(0);
            }
         
          }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        /* Defne panels */
        JPanel home = new JPanel();
        BuyMenu buyMenu = new BuyMenu();
        SellMenu sellMenu = new SellMenu();
        UpdateMenu updateMenu = new UpdateMenu();
        GetGainMenu getGainMenu = new GetGainMenu();
        SearchMenu searchMenu = new SearchMenu();

        /*Create home panel*/
        home.setLayout(new BorderLayout());
        /***************/
        
        /*Add details to panels*/
        JLabel intro = new JLabel(GREETING);
        intro.setHorizontalAlignment(JLabel.CENTER);
        intro.setVerticalAlignment(JLabel.CENTER);
        home.add(intro,BorderLayout.CENTER);
        intro.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
        intro.setFont(new Font("Arial", Font.PLAIN, 20));
        
        this.add(buyMenu);
        this.add(sellMenu);
        this.add(updateMenu);
        this.add(getGainMenu);
        this.add(searchMenu);
        this.add(home);
        /***********************/
        
        
        /* Command Bar */
        JMenu commands = new JMenu("Commands");
        
        /* Menu Items */
        JMenuItem buy = new JMenuItem("Buy");
        commands.add(buy);
        buy.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
              searchMenu.setVisible(false);
              getGainMenu.setVisible(false);
              home.setVisible(false);
              sellMenu.setVisible(false);
              updateMenu.setVisible(false);
              buyMenu.setVisible(true);
                
            }
            
        });
        
        JMenuItem sell = new JMenuItem("Sell");
        commands.add(sell);
        sell.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              searchMenu.setVisible(false);
              getGainMenu.setVisible(false);
              home.setVisible(false);
              buyMenu.setVisible(false);
              updateMenu.setVisible(false);
              sellMenu.setVisible(true);
            }
            
        });
        
        JMenuItem update = new JMenuItem("Update");
        commands.add(update);
        update.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              searchMenu.setVisible(false);
              getGainMenu.setVisible(false);
              home.setVisible(false);
              buyMenu.setVisible(false);
              sellMenu.setVisible(false);
              updateMenu.setFields();
              updateMenu.setVisible(true);        
            }
        
        });
        
        JMenuItem getGain = new JMenuItem("Get Gain");
        commands.add(getGain);
        getGain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
              searchMenu.setVisible(false);
              home.setVisible(false);
              buyMenu.setVisible(false);
              sellMenu.setVisible(false);
              updateMenu.setVisible(false);
              getGainMenu.setGain();
              getGainMenu.setVisible(true);
              
            }
        
        });
        
        JMenuItem search = new JMenuItem("Search");
        commands.add(search);
        search.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              
              home.setVisible(false);
              buyMenu.setVisible(false);
              sellMenu.setVisible(false);
              updateMenu.setVisible(false);
              getGainMenu.setVisible(false);
              searchMenu.setVisible(true);
            }
  
        });
        
      
        JMenuItem quit = new JMenuItem("Quit");
        commands.add(quit);
        quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Portfolio.getDataSize() != 0) {
                    int confirmed = JOptionPane.showConfirmDialog(null, EXIT_MESSAGE, "Exit Program and Save", JOptionPane.YES_NO_OPTION); 
                    if (confirmed == JOptionPane.YES_OPTION) {
                        Portfolio.writeFile(fileName);
                        System.exit(0);
                    } else if (confirmed == JOptionPane.NO_OPTION) {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
                    }
                } else {
                     System.exit(0);
                }
            }
        
        });
        
        
        /*Add the command bar*/   
        JMenuBar bar = new JMenuBar();
        bar.add(commands);
        this.setJMenuBar(bar);
        /**********************/
        
    }

    /**
     * @param args the command line arguments
     * @throws a3.Investment.EmptySymbol
     * @throws a3.Investment.InvalidQuantity
     * @throws a3.Investment.EmptyName
     * @throws a3.Investment.InvalidPrice
     */
    public static void main(String[] args) throws Investment.EmptySymbol, Investment.InvalidQuantity, Investment.EmptyName, Investment.InvalidPrice {

        if(0 < args.length) {          
            A3 gui = new A3(args[0]);
            gui.setVisible(true);
        } else {
            System.out.println("File name must be provided");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
