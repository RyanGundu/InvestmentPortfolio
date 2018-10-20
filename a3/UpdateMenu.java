package a3;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Class that contains/creates the UpdateMenu and its functionality
 * @author ryangundu
 */
public class UpdateMenu extends JPanel implements ActionListener {
    
    public static final String MAIN_TEXT = "You may enter a new price for the displayed Investment\n"; 
                                           
    public static final int FIRST_INV = -1;
    
    JTextArea messageField;
    JTextField symbolText;
    JTextField nameText;
    
    JButton prev;
    JButton next;
    
    /**
     * Constructor for UpdateMenu
     */
    public UpdateMenu () {
        
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

        input.setLayout(new GridLayout(8,2));
        buttons.setLayout(new GridLayout(3,3,20,20));
        messages.setLayout(new BorderLayout());
        /******************/

        /* Messages Title */
        JLabel message = new JLabel("Messages");
        message.setFont(new Font("Arial", Font.BOLD, 15));
        messages.add(message,BorderLayout.NORTH);
        messages.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right

        /* Add Text Area */
        messageField = new JTextArea();
        messageField.setEditable(false);
        JScrollPane scrolledText = new JScrollPane(messageField);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messages.add(scrolledText,BorderLayout.CENTER);

        messageField.setText(MAIN_TEXT);

        /* Text and Label Fields */
        JLabel title = new JLabel("Updating Investments");
        title.setFont(new Font("Arial", Font.BOLD, 15));
        input.add(title);
        input.add(new JLabel(""));

        input.add(new JLabel(""));
        input.add(new JLabel(""));

        symbolText = new JTextField();
        input.add(new JLabel("Symbol"));
        symbolText.setEditable(false);
        input.add(symbolText);

        input.add(new JLabel(""));
        input.add(new JLabel(""));

        nameText = new JTextField();
        nameText.setEditable(false);
        input.add(new JLabel("Name"));
        input.add(nameText);

        input.add(new JLabel(""));
        input.add(new JLabel(""));

        JTextField priceText = new JTextField();
        input.add(new JLabel("Price"));
        input.add(priceText);

        input.add(new JLabel(""));
        input.add(new JLabel(""));
        /***********************************/
       
        /* Buttons */
        prev = new JButton("Prev");
        next = new JButton("Next");
        
        buttons.add(new JLabel(""));

        buttons.add(prev);
        prev.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                next.setEnabled(true);
                String[] data = Portfolio.fetchData(Portfolio.PREV);
                if (data[2].equals("START"))
                    prev.setEnabled(false);
                
                symbolText.setText(data[0]);
                nameText.setText(data[1]);    
            }

        });
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        
        
        buttons.add(next);
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                prev.setEnabled(true);
                String[] data = Portfolio.fetchData(Portfolio.NEXT);
                if (data[2].equals("END"))
                    next.setEnabled(false);
                
                symbolText.setText(data[0]);
                nameText.setText(data[1]);         
            }
                                  
            

        });
        
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
 
        
        JButton save = new JButton("Save");
        buttons.add(save);
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Portfolio.getDataSize() != 0) {
                    String price = priceText.getText();
                    double price1 = Portfolio.checkPrice(price);
                    try {
                        messageField.setText(Portfolio.update(symbolText.getText(),price1));
                        priceText.setText("");
                    } catch (Investment.EmptySymbol | Investment.EmptyName | Investment.InvalidQuantity | Investment.InvalidPrice a) {
                        messageField.setText(a.getMessage());
                        priceText.setText("");
                    }
                } else messageField.setText("There are currently no investments");

            }

        });
        buttons.add(new JLabel(""));
        /***********************************/
        
        
        /* Add to top panel*/
        top.add(input);
        top.add(buttons);  
        /*************************/

        /* Add the 2 components to the Buy Menu */
        this.add(top);
        this.add(messages);
        this.setVisible(false);
        /***********************/
    }    
    
    /**
     * Function sets the fields for the menu each time update is clicked
     */
    public void setFields() {
        Portfolio.resetCounter();
        String[] fields = Portfolio.fetchData(FIRST_INV);
        if (fields != null) {
            this.messageField.setText(MAIN_TEXT);
            this.symbolText.setText(fields[0]);
            this.nameText.setText(fields[1]);
        } else {
            this.symbolText.setText("");
            this.nameText.setText("");
            this.messageField.setText("There are currently no investments");
        }
        if (Portfolio.getDataSize() == 0 ||Portfolio.getDataSize() == 1 ) {
            next.setEnabled(false);
            prev.setEnabled(false);
        } else {
            next.setEnabled(true);
            prev.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
