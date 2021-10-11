package display;
 
import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
 
import logic.Date;
import logic.CalendarEvents;
import logic.EventCollection;
/*
 * This is the dayview mode, it displays a table consisting of all times of the day(24 hours)
 * and can match up the time with an event if it is on that certain day(within the hour)
 * Changelog (April 11 at 1:00PM): Complete
 * @author Parker
 */
public class DayFrame implements BaseFrame{
    private Date date;
    private EventCollection events;
    private JFrame frmDay;
    private Container dayPane;
    private JPanel pnlDay;
    private JLabel currentDate;
    private JTable tblDay;
    private JButton btnCloseDay;
    private DefaultTableModel mtblDay;
    private JScrollPane stblDay;
     
    /**
     * Creates the day view mode frame which takes in a Date and EventCollection 
     * object as parameters
     * @param currdate
     * @param event
     */
    public DayFrame(Date currdate, EventCollection event){
        this.date = currdate;
        this.events = event;
        makeFrame();
        setPanel();
        setLocation();
        setActionListeners();
        finishFrame(date);
    }
     
    /**
     * Method to make frame visible
     */
    public void openFrame(){
        frmDay.setResizable(false);
        frmDay.setVisible(true);
    }
     
    /**
     * Creates objects required in the "Day view mode" frame
     * which includes a label that has the current date in which they clicked on
     * and a close button along with the JTable that provides the grid for the times
     */
    @Override
    public void makeFrame(){        
        //creates the frame
        frmDay = new JFrame ("Day View Mode"); 
        frmDay.setSize(500, 450);
        dayPane = frmDay.getContentPane(); 
        dayPane.setLayout(null); 
        frmDay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  
        //creates the date label and close button  
        currentDate = new JLabel("Date: " + date.getDateInString()); 
        btnCloseDay = new JButton("Close");
        pnlDay =  new JPanel(null);   
        pnlDay.setBorder(BorderFactory.createTitledBorder("Day View Mode"));
         
        //creates the JTable and JScrollPane
        mtblDay = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblDay = new JTable(mtblDay);
        stblDay = new JScrollPane(tblDay);
         
    }
     
    /**
     * Assigns the objects to the panel
     */
    @Override
    public void setPanel() {
        dayPane.add(pnlDay);
        pnlDay.add(currentDate);
        pnlDay.add(stblDay);
        pnlDay.add(btnCloseDay);
    }
    /**
     * Assigns the respective location for each object
     */
    public void setLocation(){
        currentDate.setBounds(200, 10, 200, 50); 
        pnlDay.setBounds(0, 0, 500, 450);
        stblDay.setBounds(10, 50, 475, 350);
        frmDay.setLocation(450, 300);
        btnCloseDay.setBounds(375, 20, 100, 25);        
    }
     
    /**
     * Creates the JTable, and makes the frame visible
     * table contains 1 column that displays all possible times of the day
     * and the other column provides event details if there is an event on that day at a specific time(within the hour)
     */
    public void finishFrame(Date currDate){
        //creates the constants and the columns
         String time = ":00"; 
         String date = currDate.getDateInString();
         mtblDay.addColumn("");     
         mtblDay.addColumn("");  
        // Creates the times for every hour of the day and stores it in the first column     
        for (int row = 0; row < 24; row++){
            mtblDay.addRow(new Object[]{row + time});
        }
         
        // gets the event details using the hashmap from the CalendarEvents and stores them to compare times
        // Checks to see what the event time is and properly aligns it with the hour displaying all event details    
        Set key = events.getAllDates();
        Iterator iter = key.iterator();
        while(iter.hasNext()){
            Date temp = (Date)iter.next();
            if(temp.getDateInString().equals(date)){
                ArrayList<CalendarEvents> currEvents = events.getEvents(temp);
                for(int index = 0; index < currEvents.size(); index ++){
                    CalendarEvents tempEvent = currEvents.get(index);
                    String eTime = tempEvent.getTime();
                    String eLoc = tempEvent.getLocation();
                    String eName = tempEvent.getName();
                    if (eTime != ""){
                        for(int row = 0; row < 24; row++){
                            if (eTime.substring(0,1).equals(Integer.toString(row))  || eTime.substring(0,2).equals(Integer.toString(row))){
                                String eventDetails = String.format("Event Name: %s \nEvent Time: %s \nEvent Location: %s\n\n", eName, eTime, eLoc);
                                tblDay.setValueAt(eventDetails, row, 1);        
                            }
                        }
                    }
                }
            }
        } 
        
        tblDay.getTableHeader().setReorderingAllowed(false);
          
        //Single cell selection
        tblDay.setColumnSelectionAllowed(true);
        tblDay.setRowSelectionAllowed(true);
        tblDay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          
        //Set row/column count and sets the width of each column
        tblDay.setRowHeight(30);
        mtblDay.setColumnCount(2); 
        mtblDay.setRowCount(24);
        TableColumnModel colMdl = tblDay.getColumnModel();
        colMdl.getColumn(0).setPreferredWidth(0);
        colMdl.getColumn(1).setPreferredWidth(300);
   
        //Make frame visible
        frmDay.setResizable(false);
        frmDay.setVisible(true);
    }  
     
    /**
     * Simply closes the day view mode window via the close button
     */
    public class btnCloseDay_Action implements ActionListener{
        public void actionPerformed(ActionEvent e){
            frmDay.setVisible(false);
        }
    }
 
    /**
     * Sets action listener to the close button
     */
    @Override
    public void setActionListeners() {
        btnCloseDay.addActionListener(new btnCloseDay_Action());        
    }
   
}