package display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import logic.Date;
import logic.EventCollection;
import logic.FileReadWrite;
import logic.Month;

/**
 * The main frame which displays the calendar
 * Displays the calendar, and is used to open the "View events", and "Create events" frames
 * @author Kevin
 *
 */
public class MainFrame implements BaseFrame{
	private EventCollection events;
	private Date currDate; 
	private EventFrame eFrame;
	private DayFrame dayFrame;
	private JFrame frmMain;
	private Container pane;
	private JComboBox cmbYear, cmbMonth;
	private JPanel pnlCalendar;
	private JButton btnPrev, btnNext, btnCreateEvent, btnViewEvent, btnRefresh, btnSave;
	private JScrollPane stblCalendar;
	private JTable tblCalendar;
	private DefaultTableModel mtblCalendar;
	private String [] searchMonth = {" ", "JANUARAY", "FEBURARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };
	
	/**
	 * Constructor of Main Frame
	 * Initializes a "Create Event" and "View Event" frame that is currently hidden
	 */
	
	/**
	 * Empty constructor:
	 * 		- system date
	 * 		- empty EventCollection
	 */
	public MainFrame(){
		this(new Date(), new EventCollection());
	}
	
	/**
	 * Date constructor:
	 * 		- empty EventCollection
	 * @param currDate
	 */
	public MainFrame(Date currDate){		
		this(currDate, new EventCollection());
	}
	
	/**
	 * Makes the main frame according to the arguments taken
	 * @param currDate
	 * @param events
	 */
	public MainFrame(Date currDate, EventCollection events){
		this.currDate = currDate;
		this.events = events;
		eFrame = new EventFrame(events);
		makeFrame();
		setComboBox();
		setPanel();
		setLocation();
		setActionListeners();
		finishFrame();
	}
	
	/**
	 * Creates the objects required in the frame
	 */
	@Override
	public void makeFrame() {
		frmMain = new JFrame ("Calendar"); //Create frame
		frmMain.setSize(330, 400); //Set size to 330x400 pixels
		pane = frmMain.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
		
		cmbYear = new JComboBox();
		cmbMonth = new JComboBox();
		btnPrev = new JButton ("Previous");
		btnNext = new JButton ("Next");
		btnCreateEvent = new JButton("Create Event");
		btnViewEvent = new JButton("View Events");
		btnRefresh = new JButton("Refresh");
		btnSave = new JButton("Save");
		pnlCalendar = new JPanel(null);
		pnlCalendar.setBorder(BorderFactory.createTitledBorder(""));
		
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
	}

	/**
	 * Sets values in the combo boxes in the main frame
	 */
	public void setComboBox() {
		// Year
		for (int i = 2007; i <= 2027; i++){
			cmbYear.addItem(String.valueOf(i));
		}
		// Month
		for(int index = 1; index < searchMonth.length; index++){
			cmbMonth.addItem(searchMonth[index]);
		}
	}

	/**
	 * Assigns the action listeners to the objects
	 */
	@Override
	public void setActionListeners() {
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		btnCreateEvent.addActionListener(new btnCreateEvent_Action());
		btnRefresh.addActionListener(new btnRefresh_Action());
		btnViewEvent.addActionListener(new btnViewEvent_Action());
		btnSave.addActionListener(new btnSave_Action());
		cmbMonth.addActionListener(new cmbMonth_Action(cmbMonth));
		cmbYear.addActionListener(new cmbYear_Action(cmbYear));
		tblCalendar.addMouseListener(new tblCalendar_Action());
	}

	/**
	 * Sets all the objects into the panel
	 */
	@Override
	public void setPanel() {
		pane.add(pnlCalendar);
		pnlCalendar.add(btnPrev);
		pnlCalendar.add(btnNext);
		pnlCalendar.add(btnViewEvent);
		pnlCalendar.add(btnCreateEvent);
		pnlCalendar.add(btnRefresh);
		pnlCalendar.add(btnSave);
		pnlCalendar.add(cmbYear);
		pnlCalendar.add(cmbMonth);
		pnlCalendar.add(stblCalendar);
	}

	/**
	 * Sets the location of all objects
	 */
	@Override
	public void setLocation() {
		pnlCalendar.setBounds(0, 0, 323, 370);
		stblCalendar.setBounds(10, 50, 300, 250);
		cmbYear.setBounds(10, 0, 100, 20);
		cmbMonth.setBounds(210, 0, 100, 20);
		btnPrev.setBounds(10, 25, 100, 25);
		btnNext.setBounds(210, 25, 100, 25);
		btnViewEvent.setBounds(10, 300, 140,25);
		btnCreateEvent.setBounds(170, 300, 140,25);
		btnRefresh.setBounds(115, 25, 90, 25);
		btnSave.setBounds(10, 340, 100, 25);
		frmMain.setLocation(550, 200);
	}

	/**
	 * Creates the JTable, and makes the frame visible
	 */
	public void finishFrame(){
		// Creates the header of every day of the week in Calendar pane
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; 
		for (int i = 0; i < 7; i++){
			mtblCalendar.addColumn(headers[i]);
		}    
		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);
		
		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Set row/column count
		tblCalendar.setRowHeight(36);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
		
		//Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		refreshCal();	
	}
	
	/**
	 * Fills in the table with the correct dates of any month specified
	 */
	public void refreshCal(){
		Month monthTemp = new Month(currDate);
		events.equals(new EventCollection());
		String[][] monthGrid = monthTemp.getGrid();
		events = eFrame.getEvents();			
		monthTemp.labelCells(events);
		
		for(int row = 0; row < monthGrid[0].length - 1;row++){
			for(int col = 0; col < monthGrid.length; col++){
				mtblCalendar.setValueAt(monthGrid[row][col], row, col);
			}
		}
		
		cmbMonth.setSelectedIndex(currDate.getMonth() - 1);
		cmbYear.setSelectedIndex(currDate.getYear() - 2007);
		changeBgColour();
	}
	
	/**
	 * Changes the background colour depending on the month selected
	 */
	public void changeBgColour(){
		String month = "";
		for(int i = 0; i < searchMonth.length; i++){
			if(i == currDate.getMonth()){
				month = searchMonth[i];
			}
		}
		
		pnlCalendar.setBackground(MonthColour.valueOf(month).getColour());
	}
	/**
	 * Action for Previous button. 
	 * Changes the month to previous month, if current month is Jan, decreases the year by 1
	 */
	public class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			int testInt;
			testInt = currDate.getMonth();
			if( testInt !=1){
				testInt = testInt - 1;
			}else{
				currDate.setYear(currDate.getYear() - 1);
				testInt = 12;
			}
			currDate.setMonth(testInt);
			
			refreshCal();
		}
	} 
	/**
	 * Action for Next button.
	 * Changes the month to next month, if current month is Dec, increase the year by 1
	 */
	public class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			int testInt;            
			testInt = currDate.getMonth();
			if(testInt != 12)
				testInt = testInt + 1;
			else{
				currDate.setYear(currDate.getYear() + 1);
				testInt = 1;
			}
			currDate.setMonth(testInt);
			
			refreshCal();
		}
	}
	/**
	 * Action for "Create Event" button.
	 * opens the Event frame
	 */
	public class btnCreateEvent_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			eFrame.openFrame();
		}
	}    
	
	/**
	 * Action for "ViewEvent" button.
	 * opens the Event frame
	 */
	public class btnViewEvent_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			ViewFrame vFrame = new ViewFrame(eFrame.getEvents());
			vFrame.openFrame();
			
		}
	}
	
	/**
	 * Action for the "refresh" button
	 * Simply updates table
	 */
	public class btnRefresh_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			refreshCal();
		}
	}
	
	/**
	 * Action for the "save" button
	 * Saves all events to file
	 */
	public class btnSave_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			FileReadWrite temp = new FileReadWrite(events);
			temp.saveToFile("events");
			frmMain.dispatchEvent(new WindowEvent(frmMain, WindowEvent.WINDOW_CLOSING));
		}
	}
	/**
	 * Action for the "Month" Combo Box.
	 * Changes the current month to the month selected 
	 */
	public class cmbMonth_Action implements ActionListener{
		private JComboBox cmbTemp;
		public cmbMonth_Action(JComboBox cmbTemp){
			this.cmbTemp = cmbTemp;
		}
		public void actionPerformed (ActionEvent e){
			currDate.setMonth(cmbTemp.getSelectedIndex() + 1);
			refreshCal();
		}
	}    
	
	/**
	 * Action for the "Year" Combo Box.
	 * Changes the current year to the year selected
	 */
	public class cmbYear_Action implements ActionListener{
		private JComboBox cmbTemp;
		public cmbYear_Action(JComboBox cmbTemp){
			this.cmbTemp = cmbTemp;
		}
		public void actionPerformed (ActionEvent e){
			currDate.setYear(cmbTemp.getSelectedIndex() + 2007);
			refreshCal();
		}
	}
	
	 /**
     * clicking on a certain date will bring up the day view mode
     * and set the current date as the clicked day
     * if the user clicks on an empty cell then it does nothing
     */
    public class tblCalendar_Action implements MouseListener {
  
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable temp = (JTable)e.getSource();
            int row = temp.getSelectedRow();
            int column = temp.getSelectedColumn();
            Month monthTemp = new Month(currDate);
            String[][] monthGrid = monthTemp.getGrid();  
            currDate.getMonth();
            currDate.getYear();
            if(monthGrid[row][column] != ""){
                currDate.setDay(Integer.parseInt(monthGrid[row][column]));
                dayFrame = new DayFrame(currDate, events);
                dayFrame.openFrame();
            }      
        }
  
        @Override
        public void mouseEntered(MouseEvent e) {}
  
        @Override
        public void mouseExited(MouseEvent e) {}
  
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
  
    }
}
