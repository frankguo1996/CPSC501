package display;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Date;
import logic.CalendarEvents;
import logic.EventCollection;
import logic.Month;

/**
 * The Frame when user clicks "Create Event" in the main frame
 * User inputs event name, date, time, location.
 * The day combo box changes number, depending on the month and year selected (valid dates only)
 *
 */
public class EventFrame implements BaseFrame{
	private EventCollection events;
	private ErrorFrame error = new ErrorFrame();
	private JFrame frmEvent;
	private Container eventPane;
	private JComboBox cmbEventYear, cmbEventMonth, cmbEventDay, cmbEventHour, cmbEventMin;
	private JButton btnEventConfirm, btnEventCancel;
	private JPanel pnlEvent;
	private JLabel eventTitle, eventDate, eventTime, eventLocation, eventColon;
	private JTextField tfInput, tfLocation;
	
	/**
	 * Creates the "Create Event" frame 
	 */
	public EventFrame(EventCollection events){
		this.events = events;
		makeFrame();
		setComboBox();
		setActionListeners();
		setPanel();
		setLocation();
	}
	
	/**
	 * Method to make frame visible
	 */
	public void openFrame(){
		resetDayBox();
		frmEvent.setResizable(false);
		frmEvent.setVisible(true);
	}
	/**
	 * Resets the day combo box to 0 entries
	 */
	public void resetDayBox(){
		List<String> ls = new ArrayList<String>(); 
		cmbEventDay.setModel(new DefaultComboBoxModel(ls.toArray()));
	}
	
	/**
	 * Creates objects required in the "Create Events" frame
	 */
	@Override
	public void makeFrame() {
		frmEvent = new JFrame ("Event"); //Create frame
		frmEvent.setSize(300, 350);
		eventPane = frmEvent.getContentPane(); //Get content pane
		eventPane.setLayout(null); //Apply null layout
		frmEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		cmbEventYear = new JComboBox();
		cmbEventMonth = new JComboBox();
		cmbEventDay = new JComboBox();
		cmbEventHour = new JComboBox();
		cmbEventMin = new JComboBox();
		
		btnEventConfirm = new JButton("Confirm");
		btnEventCancel = new JButton("Cancel");
		eventTitle = new JLabel("Event Title: ");
		eventDate = new JLabel("Date:");
		eventTime = new JLabel("Time:");
		eventLocation = new JLabel("Location:");
		eventColon = new JLabel(":");
		
		tfInput = new JTextField(null);
		tfLocation = new JTextField();
		pnlEvent =  new JPanel(null);
		pnlEvent.setBorder(BorderFactory.createTitledBorder("Add Event"));		
	}

	/**
	 * Assigns values to the combo boxes
	 */
	public void setComboBox() {
		//Event year
		for (int i = 2007; i <= 2027; i++){
			cmbEventYear.addItem(String.valueOf(i));
		}
		// Event month
		for (int j = 1; j <= 12; j++){
			if(j < 10){	
				cmbEventMonth.addItem("0" + j);
			}
			else{				
				cmbEventMonth.addItem(j);		
			}
		}
		// Event Time: Start
		for( int k = 0; k <= 24; k++){
			if(k < 10){
				cmbEventHour.addItem("0" + k);				
			}
			else{				
				cmbEventHour.addItem(k);
			}
		}       
		// Event Time: End
		for( int m = 0; m <= 59; m++){
			if(m < 10){
				cmbEventMin.addItem("0" + m);
			}
			else{				
				cmbEventMin.addItem(m);
			}
		}
	}

	/**
	 * sets action listeners to the buttons
	 */
	@Override
	public void setActionListeners() {
		btnEventCancel.addActionListener(new btnEventCancel_Action());
		btnEventConfirm.addActionListener(new btnEventConfirm_Action());
		cmbEventMonth.addActionListener(new FillCmbDate(cmbEventYear, cmbEventMonth));
		cmbEventYear.addActionListener(new FillCmbDate(cmbEventYear, cmbEventMonth));
	}

	/**
	 * Assigns the objects the panel
	 */
	@Override
	public void setPanel() {
		eventPane.add(pnlEvent);
		eventTitle.setBackground(Color.black);
		pnlEvent.add(eventTitle);
		pnlEvent.add(tfInput);
		pnlEvent.add(tfLocation);
		pnlEvent.add(eventDate);
		pnlEvent.add(eventTime);
		pnlEvent.add(eventLocation);
		pnlEvent.add(eventColon);
		pnlEvent.add(cmbEventYear);
		pnlEvent.add(cmbEventMonth);
		pnlEvent.add(cmbEventDay);
		pnlEvent.add(cmbEventHour);
		pnlEvent.add(cmbEventMin);
		pnlEvent.add(btnEventConfirm);
		pnlEvent.add(btnEventCancel);
		
	}

	/**
	 * Sets the location of all objects of this frame
	 */
	@Override
	public void setLocation() {
		pnlEvent.setBounds(0,0,280,320);
		eventTitle.setBounds(10, 25, 100, 25);
		tfInput.setBounds(80, 25, 195, 25);
		tfLocation.setBounds(80, 145, 195, 25);
		eventDate.setBounds(10, 60, 100, 25);
		eventTime.setBounds(10, 100, 100, 25);
		eventLocation.setBounds(10, 145, 100, 25);
		eventColon.setBounds(140, 100, 20, 40);
		btnEventConfirm.setBounds(10, 290, 100, 25);
		btnEventCancel.setBounds(170, 290, 100, 25);
		cmbEventYear.setBounds(80, 55, 80, 40);
		cmbEventMonth.setBounds(165,55, 50, 40);
		cmbEventDay.setBounds(225, 55, 50, 40); 
		cmbEventHour.setBounds(80, 100,50,40);
		cmbEventMin.setBounds(150, 100, 50,40);
		frmEvent.setLocation(575, 200);
	}

	/**
	 * Resets the text fields and combo boxes of Event frame
	 */
	public void resetEventFrame(){
		tfInput.setText("");
		tfLocation.setText("");
		cmbEventYear.setSelectedIndex(0);
		cmbEventMonth.setSelectedIndex(0);
		cmbEventHour.setSelectedIndex(0);
		cmbEventMin.setSelectedIndex(0);
	}
	
	/**
	 * Updates the Day ComboBox to the correct number of days
	 * @param num
	 * @param toBox
	 */
	public void setDayComboBox(int num, JComboBox toBox){
		for (int index = 1; index <= num; index++){
				toBox.addItem(index);				
		}
	}
	
	/**
	 * Extracts the info the user selected in the create events frame and stores it in the events HashMap
	 * If an event already exist in the same day, add the event to the ArrayList CalendarEvents,
	 * otherwise, create a new ArrayList and add the event to it.
	 * If an event already exists in the same day and time, doesn't store new event, and opens an error window
	 * @param key
	 * @param title
	 * @param time
	 * @param location
	 */
	public void storeEvent(Date key, String title, String time, String location){
		CalendarEvents temp = new CalendarEvents(title);
		temp.setTime(time);
		temp.setLocation(location);
		if(!events.checkEvent(key, temp)){
			events.setEvent(key,temp);			
		}
		else{
			error.openFrame();
		}
	}
	
	/**
	 * Getter for all events
	 * @return events
	 */
	public EventCollection getEvents(){
		return events;
	}
	/**
	 * Action for the "Cancel" button.
	 * Closes the Event frame and resets its values
	 */
	public class btnEventCancel_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			frmEvent.setVisible(false);
			resetEventFrame();
		}
	}
	
	/**
	 * Action for the "Confirm" button.
	 * Stores the event created in the HashMap and resets the values in the Event window
	 */
	public class btnEventConfirm_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if(cmbEventDay.getItemCount() > 0){
				
				String title = tfInput.getText();
				String location = tfLocation.getText();
				int hour = cmbEventHour.getSelectedIndex();			
				int min = cmbEventMin.getSelectedIndex();
				String time =  Integer.toString(hour) + ":" + Integer.toString(min);
				int year = cmbEventYear.getSelectedIndex() + 2007;
				int month = cmbEventMonth.getSelectedIndex() + 1;
				int day = cmbEventDay.getSelectedIndex() + 1;
				
				Date key= new Date(year,month,day);
				storeEvent(key, title, time, location);
				cmbEventDay.setSelectedIndex(0);
			}
			
			frmEvent.setVisible(false);
			resetEventFrame();
		}
	}
	/**
	 * Adds the days of the month when month dropbox is selected
	 *
	 */
	public class FillCmbDate implements ActionListener{
		private JComboBox cmbTempYear;
		private JComboBox cmbTempMonth;
		public FillCmbDate(JComboBox cmbTempYear, JComboBox cmbTempMonth){
			this.cmbTempYear = cmbTempYear;
			this.cmbTempMonth = cmbTempMonth;
		}	
		public void actionPerformed(ActionEvent e){
			String year = (String)cmbTempYear.getSelectedItem();
			int month = cmbTempMonth.getSelectedIndex() + 1;
			Date key= new Date(Integer.parseInt(year),month,1);
			Month temp = new Month(key);
			int numOfDays = temp.numOfDay();
			if(cmbTempYear.equals(cmbEventYear))
				setDayComboBox(numOfDays, cmbEventDay);				
			
		}
	}
}
