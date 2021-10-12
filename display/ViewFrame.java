package display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import logic.CalendarEvents;
import logic.Date;
import logic.EventCollection;
import logic.Time;
/**
 * Frame when the user clicks "View Events" button in the main frame
 * Displays all the events stored on a single date (all dates are displayed in the combo box) 
 * in the text area
 *
 */
public class ViewFrame implements BaseFrame{
	EventCollection events;
	private JFrame frmViewEvent;
	private Container viewEventPane;
	private JComboBox cmbViewEvent;
	private JPanel pnlViewEvent;
	private JTextArea taViewEvent;

	/**
	 * Creates the "View Events" frame
	 */
	public ViewFrame(EventCollection events){
		this.events = events;
		makeFrame();
		setComboBox();
		setActionListeners();
		setPanel();
		setLocation();
	}
	
	/**
	 * Method to make this frame visible
	 */
	public void openFrame(){
		frmViewEvent.setResizable(false);
		frmViewEvent.setVisible(true);
	}
	
	/**
	 * Creates all objects in this frame
	 */
	@Override
	public void makeFrame() {
		frmViewEvent = new JFrame("View Event");
		frmViewEvent.setSize(300, 350);
		frmViewEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		viewEventPane = frmViewEvent.getContentPane();
		viewEventPane.setLayout(null);
				
		cmbViewEvent = new JComboBox();
		pnlViewEvent = new JPanel(null);
		
		taViewEvent = new JTextArea();
		taViewEvent.setEditable(false);
		pnlViewEvent.setBorder(BorderFactory.createTitledBorder("View Event"));
	}

	/**
	 * Sets "Select Date" as the initial value of the combo box
	 */
	public void setComboBox() {
		cmbViewEvent.addItem("Select Date");		
		Set keys = events.getAllDates();
		Iterator iter = keys.iterator();
		while(iter.hasNext()){
			Date temp = (Date)iter.next();
			cmbViewEvent.addItem(temp.getDateInString());
		}
	}

	/**
	 * Sets the action listener to the combo box
	 */
	@Override
	public void setActionListeners() {
		cmbViewEvent.addActionListener(new cmbViewEvent_Action(cmbViewEvent));
	}

	/**
	 * Adds everything to the panel
	 */
	@Override
	public void setPanel() {
		viewEventPane.add(pnlViewEvent);
		pnlViewEvent.add(cmbViewEvent);
		pnlViewEvent.add(taViewEvent);
	}

	/**
	 * Sets the location of all the objects in this frame
	 */
	@Override
	public void setLocation() {
		pnlViewEvent.setBounds(0,0,280,400);		
		cmbViewEvent.setBounds(10, 25, 260, 25);
		taViewEvent.setBounds(50, 60, 195, 250);
		frmViewEvent.setLocation(575, 200);
		
	
	}
	
	/**
	 * Prints the event details when the user selects a date in the "View Event" combo box
	 * @param date in [YYYY/MM/DD] form
	 */
	public void updateViewEvent(String date){
		taViewEvent.setText("");
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
					
					String resultText = String.format("Event Name: %s \nEvent Time: %s \nEvent Location: %s\n\n", eName, eTime, eLoc);
					taViewEvent.setText(taViewEvent.getText() + resultText);
				}
			}		
		}	
	}
	
	/**
	 * Action for the "View Event" Combo Box. 
	 * 
	 */
	public class cmbViewEvent_Action implements ActionListener{
		private JComboBox cmbTemp;
		public cmbViewEvent_Action(JComboBox cmbTemp){
			this.cmbTemp = cmbTemp;
		}
		public void actionPerformed (ActionEvent e){
			String date = (String) cmbTemp.getSelectedItem();
			if(cmbTemp.getSelectedIndex()!= 0){
				updateViewEvent(date);				
			}
		}
	}
}
