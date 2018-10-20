package a3;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Class that contains/creates the buyMenu and its functionality
 * @author ryangundu
 */
public class BuyMenu extends JPanel implements ActionListener {
    
    public static final String MAIN_TEXT = "To purchase a new investment fill out all fields\n"
                         + "If the symbol exists it will process to buy more shares.\n\n"
                         + "To purchase additional shares of an existing Investment:\nMake sure the symbol and name match "
                         + "the exsisting investment.";
    /**
     * Constructor for BuyMenu
     */
    public BuyMenu () {
    
        super(new GridLayout(2,1));

        /* Create panel */
        JPanel top = new JPanel();//main top half
        top.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JPanel input = new JPanel();
        input.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JPanel buttons = new JPanel();
        JPanel messages = new JPanel();
        /****************/

        /* Define layout */
        top.setLayout(new GridLayout(1,2));

        input.setLayout(new GridLayout(6,2));
        buttons.setLayout(new GridLayout(4,3,20,20));
        messages.setLayout(new BorderLayout());
        /******************/

        /* Messages Title */
        JLabel message = new JLabel("Messages");
        message.setFont(new Font("Arial", Font.BOLD, 15));
        messages.add(message,BorderLayout.NORTH);
        messages.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right

        /* Add Text Area */
        JTextArea messageField = new JTextArea();
        messageField.setEditable(false);
        JScrollPane scrolledText = new JScrollPane(messageField);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messages.add(scrolledText,BorderLayout.CENTER);

        messageField.setText(MAIN_TEXT);


        /* Text and Label Fields */
        JLabel title = new JLabel("Buying an Investment");
        title.setFont(new Font("Arial", Font.BOLD, 15));
        input.add(title);
        input.add(new JLabel(""));

        String[] petStrings = { "Stock", "MutualFund"};
        JComboBox invType = new JComboBox(petStrings);
        invType.setSelectedIndex(0);
        input.add(new JLabel("Type"));
        input.add(invType);

        JTextField symbolText = new JTextField();
        input.add(new JLabel("Symbol"));
        input.add(symbolText);


        JTextField nameText = new JTextField();
        input.add(new JLabel("Name"));
        input.add(nameText);


        JTextField quantText = new JTextField();
        input.add(new JLabel("Quantity"));
        input.add(quantText);

        JTextField priceText = new JTextField();
        input.add(new JLabel("Price"));
        input.add(priceText);
        /***************/
        
        /* Buttons */
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        JButton reset = new JButton("Reset");
        buttons.add(reset);
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                invType.setSelectedIndex(0);
                symbolText.setText("");
                nameText.setText("");
                quantText.setText("");
                priceText.setText("");
                messageField.setText(MAIN_TEXT);

            }

        });
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        JButton buy = new JButton("Buy");
        buttons.add(buy);
        buy.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                messageField.setText("");
                int type = invType.getSelectedIndex(); 
                String symb = symbolText.getText();
                String name = nameText.getText();
                String quantity = quantText.getText();
                String price = priceText.getText();
                int quant = Portfolio.checkQuantity(quantity);
                double price1 = Portfolio.checkPrice(price);
                try {
                  
                    String prompt = Portfolio.check(type, Portfolio.BUY_COMMAND, symb, name, quant, price1);
                    messageField.setText(prompt);
                    if (prompt.equals("The Investment has been added!")) {
                        invType.setSelectedIndex(0);
                        symbolText.setText("");
                        nameText.setText("");
                        quantText.setText("");
                        priceText.setText("");
                    }
                    
                } catch (Investment.EmptySymbol | Investment.EmptyName | Investment.InvalidQuantity | Investment.InvalidPrice a) {
                    messageField.setText(a.getMessage());
                }
                
            }

        });
        buttons.add(new JLabel(""));
        /**********************************/



        /* Add to top panel*/
        top.add(input);
        top.add(buttons);  
        /*************************/

        /* Add the 2 components to the Buy Menu */
        this.add(top);
        this.add(messages);
        this.setVisible(false);
        /***************/   
    
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
