/*
 * GUI.java
 *
 * 01/10/2015
 * 
 * Yichuan Wang
 */

package ohjobs;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
/**
 * 
   This class implements all the GUIs of the program
 * @author Yichuan
 */
public class GUI extends JFrame {
    private final JLabel srh_title;
    private final JTextField cName,cDate,cTitle;
    private final JTextField[][] bottomDetail;
    private final JLabel[] totalStatus;
    private final JRadioButton apt,intw,fail,revi;
    private final ButtonGroup cStatus;
    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem NewItem,OpenItem,ExitItem;
    private JCheckBoxMenuItem cbMenuItem;
    private final JButton add_but,edt_but,delete_but,dup_but,add_imgbut,edt_imgbut,del_imgbut;
    private final ImageIcon add,add_hover,add_pre,edit,edit_hover,edit_pre,
                      delete,delete_hover,delete_pre;
    private final Container pane;
    private final JPanel topBar,leftBar,rightBar,bottomBar,centerBar,infoPanel,statusBar;
    private final JScrollPane tablePane;
    private final JTable table;
    private String search_cName,search_status,search_title,search_date;
    private final FileHandlers FHandler = new FileHandlers();
    private final SearchHandler SHandler = new SearchHandler();
    private final RadioButtonHandler RHandler = new RadioButtonHandler();
    private final AddButtonHandler AHandler = new AddButtonHandler();
    private final ExsitenceHandler EHandler = new ExsitenceHandler();
    private final UpdateButtonHandler UHandler = new UpdateButtonHandler();
    private final DeleteHandler DHandler = new DeleteHandler();
    private final ResetHandler ResetHandler = new ResetHandler();
    private ArrayList<String[]> queryResult;
    private String[] userInput,nothingInput,total,currentSelected;
    private final String[] columnNames = {"Date Submitted",
            "Company Name",
            "Job Title",
            "Company Location",
            "Status",
            "Link"
            };
    private final Border raisedbevel,loweredbevel,compound;
    private piechart c;
    private final DBConnect db;
    private AddPanel addPanel;
    private EditPanel editPanel;
    
    public GUI(){
        //one of the constructor of JFrame which set the frame title
        super("Oh Jobs!");
        //create new database object
        db = new DBConnect();
        pane = getContentPane();
        setLayout(new BorderLayout());
        //Default setting for variables
        search_status = "";
        nothingInput = new String[4];
        for(int i = 0; i < 4; i++){
            nothingInput[i] = "";
        }
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(
                          raisedbevel, loweredbevel);
        total = new String[5];
        currentSelected = new String[6];
        for(int i = 0; i < 6; i++){
            currentSelected[i] = "";
        }
        /*
         * Top bar of the window, including 3 image buttons and one title 
         */
        topBar = new JPanel();
        topBar.setBorder(BorderFactory.createTitledBorder(""));
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        add = new ImageIcon("C:\\Users\\Yichuan\\Documents\\NetBeansProjects\\"
                + "OhJobs\\src\\ohjobs\\buttons\\new1.png");
        add_hover = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\new2.png");
        add_pre = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\new3.png");
        edit = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\edit1.png");
        edit_hover = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\edit2.png");
        edit_pre = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\edit3.png");
        delete = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\delet1.png");
        delete_hover = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\delet2.png");
        delete_pre = new ImageIcon("C:\\Users\\Yichuan\\Documents\\"
                + "NetBeansProjects\\OhJobs\\src\\ohjobs\\buttons\\delet3.png");
        //add toolbar buttons
        add_imgbut = new JButton(add);
        toolBarButtons(add_imgbut,add_hover,add_pre);
        edt_imgbut = new JButton(edit);
        toolBarButtons(edt_imgbut,edit_hover,edit_pre);
        del_imgbut = new JButton(delete);
        toolBarButtons(del_imgbut,delete_hover,delete_pre);
        
        add_imgbut.addActionListener(AHandler);
        //edt_imgbut.addActionListener(editHandler);
        del_imgbut.addActionListener(DHandler);
        
        topBar.add(add_imgbut);
        topBar.add(edt_imgbut);
        topBar.add(del_imgbut);
        
        /*
         * Left search part of the window
         */
        leftBar = new JPanel();
        leftBar.setLayout(new GridLayout(0,1));
        //Search Module
        JPanel Searching = new JPanel(new BorderLayout(2,2));
        srh_title = new JLabel("Search");
        srh_title.setFont(new Font("Arial", Font.BOLD, 15));
        Searching.add(srh_title,BorderLayout.NORTH);
        Searching.setBorder(new TitledBorder("Tool Bar"));
        JPanel labelsFields = new JPanel(new GridLayout(0,1,1,1));
        labelsFields.setBorder(new TitledBorder("status:"));
        JPanel inputFields = new JPanel(new GridLayout(0,1,1,1));
        inputFields.setBorder(new TitledBorder("Search by"));
        //Company infomation textfields
        inputFields.add(new JLabel("  company name: "));
        cName = new JTextField(12);
        inputFields.add(cName);
        inputFields.add(new JLabel("  Date submitted: "));
        cDate = new JTextField(12);
        inputFields.add(cDate);
        inputFields.add(new JLabel("  job title: "));
        cTitle = new JTextField(12);
        inputFields.add(cTitle);
        //Status radiobutton
        apt = new JRadioButton("Accepted",false);
        apt.setActionCommand("Accepted");
        apt.addActionListener(RHandler);
        intw = new JRadioButton("Interview",false);
        intw.setActionCommand("Interview");
        intw.addActionListener(RHandler);
        fail = new JRadioButton("Fail",false);
        fail.setActionCommand("Fail");
        fail.addActionListener(RHandler);
        revi = new JRadioButton("Reviewing",false);
        revi.setActionCommand("Reviewing");
        revi.addActionListener(RHandler);
        labelsFields.add(apt);
        labelsFields.add(intw);
        labelsFields.add(fail);
        labelsFields.add(revi);
        cStatus = new ButtonGroup();
        cStatus.add(apt);
        cStatus.add(intw);
        cStatus.add(fail);
        cStatus.add(revi);

        Searching.add(labelsFields,BorderLayout.EAST);
        Searching.add(inputFields,BorderLayout.CENTER);
        
        JButton search = new JButton("Search"); 
        search.addActionListener(SHandler);
        JButton reset = new JButton("Reset");
        reset.addActionListener(ResetHandler);
        
        JPanel but_group = new JPanel();
        but_group.setLayout(new GridLayout(0,1,1,1));
        but_group.add(search); 
        but_group.add(reset); 
        
        Searching.add(but_group,BorderLayout.SOUTH);
        /*
         * Editing panel, including adding, updating and deleting functionalities
         */
        JPanel Editing = new JPanel(new GridLayout(0,1,1,1));
        Editing.setBorder(new TitledBorder("Edit"));
        add_but = new JButton("Add new record");
        add_but.addActionListener(AHandler);
        edt_but = new JButton("Update record");
        edt_but.addActionListener(UHandler);
        delete_but = new JButton("Delete record");
        delete_but.addActionListener(DHandler);
        dup_but = new JButton("Check existence");
        dup_but.addActionListener(EHandler);
        Editing.add(add_but);
        Editing.add(edt_but);
        Editing.add(delete_but);
        Editing.add(dup_but);
        
        leftBar.add(Searching);
        leftBar.add(Editing); 
        
        /*
         * Right Status analysis part of the window
         */
        rightBar = new JPanel();
        rightBar.setLayout(new BorderLayout());
        //Call drawPieChart method to draw the pie chart based on the data
        drawPieChart();
        
        //Total status
        statusBar = new JPanel();
        statusBar.setLayout(new GridLayout(0,1,1,27));
        totalStatus = new JLabel[6];
        for(int i = 0; i < 6; i ++){
            totalStatus[i] = new JLabel();
        }
        totalStatus[0].setText("   Total: " + total[0]);
        totalStatus[1].setText("   Accepted: " + total[1]);
        totalStatus[2].setText("   Interview: " + total[2]);
        totalStatus[3].setText("   Fail: " + total[4]);
        totalStatus[4].setText("   Reviewing: " + total[3]);
        totalStatus[5].setText("   ");
        for(int i = 0; i < 6; i ++){
            statusBar.add(totalStatus[i]);
        }
        
        rightBar.add(c,BorderLayout.CENTER);
        rightBar.add(statusBar,BorderLayout.SOUTH);

        /*
         * Buttom detailed information of the record
         */
        bottomBar = new JPanel();
        bottomBar.setLayout(new FlowLayout());
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2,6));
        bottomDetail = new JTextField[2][6];
        //Initialize JTextField
        for(int i = 0; i < 2; i ++){
            for(int j = 0; j < 6; j ++){
                bottomDetail[i][j] = new JTextField(15);
                bottomDetail[i][j].setBorder(javax.swing.BorderFactory.createEmptyBorder());
                if(i < 1)
                    bottomDetail[i][j].setFont(new Font("Courier", Font.BOLD, 12));
                else
                    bottomDetail[i][j].setFont(new Font("Courier", Font.PLAIN, 14));
            }
        }
        bottomDetail[0][0].setText("Date");
        bottomDetail[0][1].setText("Company name");
        bottomDetail[0][2].setText("Job title");
        bottomDetail[0][3].setText("Company location");
        bottomDetail[0][4].setText("Status");
        bottomDetail[0][5].setText("Link");
        //add bottomDetails to infoPanel
        for(int i = 0; i < 2; i ++){
            for(int j = 0; j < 6; j ++){
                 infoPanel.add(bottomDetail[i][j]);
            }
        }
        bottomBar.add(infoPanel);
        
        /*
         * Center database module
         */
        centerBar = new JPanel();
        //Arraylist to store the result
        queryResult = new ArrayList<String[]>();
        db.returnData(nothingInput,queryResult);
        int resultNo = queryResult.size();
        String [][] data = new String[resultNo][6]; 
        //display the result on a table
        for(int i = 0; i < resultNo;i ++){
            for(int j = 0; j < 6; j ++){
                data[i][j] = queryResult.get(i)[j];
            }
        }
        //Create table
        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(720, 545));
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.removeColumn(table.getColumnModel().getColumn(5));
        tablePane = new JScrollPane(table);
        centerBar.setBorder(compound);
        centerBar.add(tablePane,BorderLayout.CENTER);
        
        pane.add(topBar,BorderLayout.PAGE_START);
        pane.add(leftBar,BorderLayout.LINE_START);
        pane.add(rightBar,BorderLayout.LINE_END);
        pane.add(bottomBar,BorderLayout.PAGE_END);
        pane.add(centerBar,BorderLayout.CENTER);
    }
    
    /*
     * Creat menu bar, including file,edit and search buttons
     */
    public JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        //Build the first menu
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);  //Interact with menu using keyboard keys
        menu.getAccessibleContext().setAccessibleDescription(
                "File Menu");  //Assistive Technologies
        menuBar.add(menu);
        //A group of JMenuItems
        //New Button
        NewItem = new JMenuItem("New");
        NewItem.setMnemonic(KeyEvent.VK_N);
        NewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        NewItem.getAccessibleContext().setAccessibleDescription(
                "Create a new file");
        NewItem.addActionListener(FHandler);
        //Open Button
        OpenItem = new JMenuItem("Open",KeyEvent.VK_O);
        OpenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        OpenItem.getAccessibleContext().setAccessibleDescription(
                "Open a file");
        OpenItem.addActionListener(FHandler);
        //Exit Button
        ExitItem = new JMenuItem("Exit");
        ExitItem.setMnemonic(KeyEvent.VK_E);
        ExitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        ExitItem.getAccessibleContext().setAccessibleDescription(
                "Exit the program");
        ExitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            } 
        });
        //Add buttons to the menu
        menu.add(NewItem);
        menu.add(OpenItem);
        menu.addSeparator();
        menu.add(ExitItem);
        //Build the second menu
        menu = new JMenu("Edit");
        menu.setMnemonic(KeyEvent.VK_E);  //Interact with menu using keyboard keys
        menu.getAccessibleContext().setAccessibleDescription(
                "Edit Menu");  //Assistive Technologies
        menuBar.add(menu);
        //Build the third menu
        menu = new JMenu("Search");
        menu.setMnemonic(KeyEvent.VK_S);  //Interact with menu using keyboard keys
        menu.getAccessibleContext().setAccessibleDescription(
                "Search Menu");  //Assistive Technologies
        menuBar.add(menu);
        
        return menuBar;
    }
    
    /*
     * Action listener of buttons at menu bar
     */
    private class FileHandlers implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == NewItem){
                String s = "new 你妹啊！";
                JOptionPane.showMessageDialog(null, s);
             }
            else if(e.getSource() == OpenItem){
                String s = "开你个头啊开！";
                JOptionPane.showMessageDialog(null, s);
            }
        }
    }
    
    /*
     * Action listener of searching
     */
    private class SearchHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            try{
                queryResult = new ArrayList<String[]>();
                //Get user input and store them into an array
                search_cName = cName.getText();
                search_title = cTitle.getText();
                search_date = cDate.getText();
                //Create String array to store the input
                userInput = new String[4];
                userInput[0] = search_cName;
                userInput[1] = search_date;
                userInput[2] = search_title;
                if(search_status == "")
                    userInput[3] = "";
                else
                    userInput[3] = search_status; //should retrieve data from radio button
                //Clear the queryResult arrayList to prepare for getting result
                queryResult.clear();
                //Pass this array of search keys as parameter of query function
                db.returnData(userInput,queryResult);
                //Print the results
                int resultNo = queryResult.size();
                int index = 0;
                while(index < resultNo){
                    for(int i = 0; i < 5; i ++){
                        System.out.println(queryResult.get(index)[i]);
                    }
                    index ++;
                }
                String [][] data = new String[resultNo][6]; 
                //Display the result on a table
                for(int i = 0; i < resultNo;i ++){
                    for(int j = 0; j < 6; j ++){
                        data[i][j] = queryResult.get(i)[j];
                    }
                }
                //Create table
                table.setModel(new DefaultTableModel(data, columnNames));
                table.removeColumn(table.getColumnModel().getColumn(5));
            }
            catch (NumberFormatException e){
                System.out.println("Caught NumberFormatException: please enter valid inputs.");
            }
        }
    }
    
    /*
     *  Action listener of Status Radio Button
     */
    private class RadioButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            search_status = event.getActionCommand();
        }
    }
    
    /*
     * Action listener of Reset Button
       Reset the search module as well as the query result
     */
    private class ResetHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            cStatus.clearSelection();
            cName.setText("");
            cDate.setText("");
            cTitle.setText("");
            refreshGUI();
        }
    }
    
    /*
     * Action listener of Add Button
       Add new record to the database
     */
    private class AddButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            addPanel = new AddPanel();
            String[] inputSet = new String[6];
            int result = JOptionPane.showConfirmDialog(null, addPanel, 
                "Apply another one?", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if(!addPanel.date.getText().toString().isEmpty()){
                    inputSet[0] = addPanel.date.getText();
                    inputSet[1] = addPanel.cName.getText();
                    inputSet[2] = addPanel.jTitle.getText();
                    inputSet[3] = addPanel.cLocation.getText();
                    inputSet[4] = addPanel.status;
                    inputSet[5] = addPanel.link.getText();
                    db.addData(inputSet);
                    refreshGUI();
                }
                else JOptionPane.showMessageDialog(null,"You must enter the date.",
                    "Invalid input",JOptionPane.WARNING_MESSAGE); 
            }
            else if(result == JOptionPane.CANCEL_OPTION){}
        }
    }
    /*
     * Action listener of Add Button
       Update the selected record
     */
    private class UpdateButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            if(currentSelected[0] != "" && (!currentSelected[0].isEmpty())){
                editPanel = new EditPanel(currentSelected);
                String[] inputSet = new String[6];
                int result = JOptionPane.showConfirmDialog(null, editPanel, 
                    "Update this record?", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if(!editPanel.date.getText().toString().isEmpty()){
                        inputSet[0] = editPanel.date.getText();
                        inputSet[1] = editPanel.cName.getText();
                        inputSet[2] = editPanel.jTitle.getText();
                        inputSet[3] = editPanel.cLocation.getText();
                        inputSet[4] = editPanel.status;
                        inputSet[5] = editPanel.link.getText();
                        db.edtData(inputSet);
                        refreshGUI();
                    }
                }
            }
            else JOptionPane.showMessageDialog(null,"You must select which record you want to update.",
                        "Non selection",JOptionPane.WARNING_MESSAGE); 
        }
    }
    
    /*
     * Action listener of delete Button
       Delete record from the database
     */
    private class DeleteHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            if(!bottomDetail[1][0].getText().isEmpty()){
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you really want to delete this record?",
                        "Delete?", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    int r = Integer.parseInt(bottomDetail[1][0].getText().toString());
                    db.delData(r);
                    refreshGUI();
                }
            }
        }
    }
    
    /*
     * Action listener of Checking existence 
       Check if there are same company being stored
     */
    private class ExsitenceHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            String checked_name = JOptionPane.showInputDialog
                            ("Please input the company name you want to check:");
            if ((checked_name != null) && (checked_name.length() > 0)) {
                queryResult = new ArrayList<String[]>();
                String checkName = checked_name;
                //clear the queryResult arrayList to prepare for getting result
                queryResult.clear();
                //pass this array of search keys as parameter of query function
                db.CheckExt(checkName,queryResult);
                if(queryResult.isEmpty()){
                    JOptionPane.showMessageDialog
                            (null,"This company does not exist in the database");
                }
                else
                    JOptionPane.showMessageDialog
                            (null,"This company has been stored in the database");
            }
        }
    }
    
    /*
     * Action Listener of row selection
       Get the user's selection of the table row
     */
    private class RowListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            int selected = table.getSelectedRow();
            if(selected >= 0){
                String tem;
                for(int i = 0; i < 6; i ++){
                    tem = (String)table.getModel().getValueAt(selected,i);
                    bottomDetail[1][i].setText(tem);
                    currentSelected[i] = tem;
                }
                //table.clearSelection();
                //refresh bottom bar
                bottomBar.removeAll();
                for(int i = 0; i < 2; i ++){
                    for(int j = 0; j < 6; j ++){
                        infoPanel.add(bottomDetail[i][j]);
                    }
                }
                bottomBar.add(infoPanel);
                bottomBar.revalidate();    //#1
            }
        }
    }
    
    //Image Button settings
    private void toolBarButtons(JButton b,ImageIcon i,ImageIcon p){
        b.setPreferredSize(new Dimension(40, 40));
        b.setBorderPainted(false); 
        b.setContentAreaFilled(false); 
        b.setFocusPainted(false); 
        b.setOpaque(false);
        b.setRolloverIcon(i);
        b.setPressedIcon(p);
    }
    
    /*
     * Draw Pie chart
     */
    private void drawPieChart(){
        int statusPercen[] = new int[4];
        queryResult = new ArrayList<String[]>();
        db.returnData(nothingInput,queryResult);
        int totalSize = queryResult.size();
        total[0] = Integer.toString(totalSize);
        userInput = new String[4];
        userInput[0] = "";
        userInput[1] = "";
        userInput[2] = "";
        userInput[3] = "Accepted";
        queryResult = new ArrayList<String[]>();
        db.returnData(userInput,queryResult);
//        statusPercen[0] = queryResult.size() * 100 / totalSize;
//        total[1] = Integer.toString(queryResult.size());
//        userInput[3] = "Interview";
//        queryResult = new ArrayList<String[]>();
//        db.returnData(userInput,queryResult);
//        statusPercen[1] = queryResult.size() * 100 / totalSize;
//        total[2] = Integer.toString(queryResult.size());
//        userInput[3] = "Reviewing";
//        queryResult = new ArrayList<String[]>();
//        db.returnData(userInput,queryResult);
//        statusPercen[2] = queryResult.size() * 100 / totalSize;
//        total[3] = Integer.toString(queryResult.size());
//        userInput[3] = "Fail";
//        queryResult = new ArrayList<String[]>();
//        db.returnData(userInput,queryResult);
//        statusPercen[3] = queryResult.size() * 100 / totalSize;
//        total[4] = Integer.toString(queryResult.size());
        c = new piechart("Status"," ",statusPercen);
        c.setPreferredSize(new Dimension(350,200));
    }
    
    /*
     * Refresh all the relavent components of the GUI regarding to the recent
       changes
     */
    private void refreshGUI(){
        //refresh the table
        queryResult = new ArrayList<String[]>();
        db.returnData(nothingInput,queryResult);
        int resultNo = queryResult.size();
        String [][] data = new String[resultNo][6]; 
        //display the result on a table
        for(int i = 0; i < resultNo;i ++){
            for(int j = 0; j < 6; j ++){
                    data[i][j] = queryResult.get(i)[j];
                }
            }
        table.setModel(new DefaultTableModel(data, columnNames));
        table.removeColumn(table.getColumnModel().getColumn(5));
        //refresh the pie chart
        rightBar.removeAll();
        drawPieChart();
        //refresh total
        for(int i = 0; i < 6; i ++){
            totalStatus[i] = new JLabel();
        }
        statusBar.removeAll();
        totalStatus[0].setText("   Total: " + total[0]);
        totalStatus[1].setText("   Accepted: " + total[1]);
        totalStatus[2].setText("   Interview: " + total[2]);
        totalStatus[3].setText("   Fail: " + total[4]);
        totalStatus[4].setText("   Reviewing: " + total[3]);
        totalStatus[5].setText("   ");
        for(int i = 0; i < 6; i ++){
            statusBar.add(totalStatus[i]);
        }
        statusBar.revalidate();
        rightBar.add(c,BorderLayout.CENTER);
        rightBar.add(statusBar,BorderLayout.SOUTH);
        rightBar.revalidate();    //#1
        
    }
}

//Add new field date
//update
//optimize the GUI
//DISPLAY DATA AT RIGHT