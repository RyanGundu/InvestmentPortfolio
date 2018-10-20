package a3;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Class that contains/creates the GetGainMenu and its functionality
 * @author ryangundu
 */
class GetGainMenu extends JPanel implements ActionListener {

    JTextField gain;
    JTextArea messageField;
    
    /**
     * Constructor for GetGainMenu
     */
    public GetGainMenu () {
        
        super(new GridLayout(2,1));
        
        /* Create Panels */
        JPanel top = new JPanel();
        JPanel title = new JPanel();
        JPanel field = new JPanel();
        
        /* SET LAYOUTS */
        title.setLayout(new BorderLayout());
        field.setLayout(new GridLayout(3,6));
        top.setLayout(new GridLayout(2,1));
        
        
        /* Add details to panels */

        JLabel heading = new JLabel("Getting total gain");
        heading.setFont(new Font("Arial", Font.BOLD, 15));
        heading.setHorizontalAlignment(JLabel.LEFT);
        title.add(heading,BorderLayout.NORTH);
        heading.setBorder(new EmptyBorder(35,25,0,0));//top,left,bottom,right
        
        gain = new JTextField();
        JLabel text = new JLabel("Total gain");
        gain.setEditable(false);
        text.setBorder(new EmptyBorder(25,35,25,0));//top,left,bottom,right
        field.add(text);
        field.add(gain);
        
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        
        JLabel messages = new JLabel("Messages");
        messages.setFont(new Font("Arial", Font.BOLD, 15));
        messages.setBorder(new EmptyBorder(25,15,0,0));//top,left,bottom,right
        field.add(messages);
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        field.add(new JLabel(""));
        
        /* Add Text Area */
        messageField = new JTextArea();
        messageField.setEditable(false);
        JScrollPane scrolledText = new JScrollPane(messageField);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        top.add(title);
        top.add(field);
        
        
        
        /****************/
        this.add(top);
        this.add(scrolledText,BorderLayout.SOUTH);
        this.setVisible(false);
        /********************/
        
    }
    
    /**
     * Function to place values in fields
     */
    public void setGain() {
        String[] gainTotal = Portfolio.getGain();
        if (gainTotal != null) {
            this.messageField.setText(gainTotal[0]);
            this.gain.setText(gainTotal[1]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
