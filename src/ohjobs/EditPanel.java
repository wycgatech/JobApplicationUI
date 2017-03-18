/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohjobs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Yichuan
 */
public class EditPanel extends JPanel{
    JTextField date = new JTextField(10);
    JTextField cName = new JTextField(10);
    JTextField jTitle = new JTextField(10);
    JTextField cLocation = new JTextField(10);
    JTextField link = new JTextField(10);
    String[] statusChoices = {"Accepted", "Interview", "Fail","Reviewing"};
    String status = "Accepted";
    
    public EditPanel(String[] s){
        setLayout(new GridLayout(0,1));
        add(new JLabel("Date submitted:"));
        date.setText(s[0]);
        date.setEditable(false);
        add(date);
        add(new JLabel("Company name:"));
        cName.setText(s[1]);
        add(cName);
        add(new JLabel("Job title:"));
        jTitle.setText(s[2]);
        add(jTitle);
        add(new JLabel("Company location:"));
        cLocation.setText(s[3]);
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
        link.setText(s[5]);
        add(link);
    }
}

