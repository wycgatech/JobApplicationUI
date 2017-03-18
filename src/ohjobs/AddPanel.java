/*
 * AddPanel.java 
 *
 *
 */

package ohjobs;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Yichuan
 */
public class AddPanel extends JPanel{
    JTextField date = new JTextField(20);
    JTextField cName = new JTextField(20);
    JTextField jTitle = new JTextField(20);
    JTextField cLocation = new JTextField(20);
    JTextField link = new JTextField(20);
    String[] statusChoices = {"Accepted", "Interview", "Fail","Reviewing"};
    String status = "Accepted";
    
    public AddPanel(){
        setLayout(new GridLayout(0,1));
        add(new JLabel("Date submitted:"));
        add(date);
        add(new JLabel("Company name:"));
        add(cName);
        add(new JLabel("Job title:"));
        add(jTitle);
        add(new JLabel("Company location:"));
        add(cLocation);
        JComboBox<String> statusMenu = new JComboBox<String>(statusChoices);
        statusMenu.setVisible(true);
        statusMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                JComboBox cb = (JComboBox)event.getSource();
                status = (String)cb.getSelectedItem();
            }
        });
        add(new JLabel("Status:"));
        add(statusMenu);
        add(new JLabel("Link:"));
        add(link);
    }
}
