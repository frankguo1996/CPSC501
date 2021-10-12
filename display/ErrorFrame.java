package display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Simple frame that prints an error message
 *
 */
public class ErrorFrame implements BaseFrame{
	private JFrame frmError;
	private Container errorPane;
	private JButton btnErrorOK;
	private JPanel pnlError;
	private JLabel errorMessage;
	
	public ErrorFrame(){
		makeFrame();
		setActionListeners();
		setPanel();
		setLocation();
	}
	
	/**
	 * Method to make frame visible
	 */
	public void openFrame(){
		frmError.setResizable(false);
		frmError.setVisible(true);
	}
	
	/**
	 * Creates objects required in the "Create Events" frame
	 */
	@Override
	public void makeFrame() {
		frmError = new JFrame (); //Create frame
		frmError.setSize(300, 150);
		errorPane = frmError.getContentPane(); //Get content pane
		errorPane.setLayout(null); //Apply null layout
		frmError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		btnErrorOK = new JButton("Okay");
		errorMessage = new JLabel("Cannot create event. Conflicts with a date/time.");
		pnlError =  new JPanel(null);
		pnlError.setBorder(BorderFactory.createTitledBorder("Error"));		
	}

	/**
	 * sets action listneres to the buttons
	 */
	@Override
	public void setActionListeners() {
		btnErrorOK.addActionListener(new btnErrorOK_Action());
	}

	/**
	 * Assigns the objects the panel
	 */
	@Override
	public void setPanel() {
		errorPane.add(pnlError);
		pnlError.add(errorMessage);
		pnlError.add(btnErrorOK);
		
	}

	/**
	 * Sets the location of all objects of this frame
	 */
	@Override
	public void setLocation() {
		pnlError.setBounds(0, 0, 290, 120);
		errorMessage.setBounds(10, 30, 1000, 10);
		btnErrorOK.setBounds(100, 70, 100, 25);
		
		frmError.setLocation(575, 200);
	}
	
	/**
	 * Action for btnErrorOk. 
	 * Simply closes the frame
	 */
	public class btnErrorOK_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			frmError.setVisible(false);
		}
	}
}
