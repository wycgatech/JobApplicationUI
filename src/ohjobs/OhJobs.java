/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohjobs;
import javax.swing.JFrame;
/**
 *
 * @author Yichuan
 */
public class OhJobs {
    
    public static void main(String[] args) {
        /**
        * Create the GUI and show it.  For thread safety,
        * this method should be invoked from the
        * event-dispatching thread.
        */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // 5 steps to create a GUI
                //create the frame               
                GUI mainWindow = new GUI();
                //create and set up the menu bar and content pane.
                mainWindow.setJMenuBar(mainWindow.createMenuBar());
                //set close options
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //set the size of the frame. a easier way is to use frame.pack();
                mainWindow.setSize(1360,720);
                //Default maximize the window
                mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                //set the frame to be visible
                mainWindow.setVisible(true);
            }
        });
    }
}
