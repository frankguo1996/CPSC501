package display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Date;
import logic.FileReadWrite;

/**
 * Initial frame that prompts the user if they want to use the save file (events) of last instance 
 * of running the calendar application 
 *
 */

public class OpenFileFrame implements BaseFrame{
	private JFrame frmFile;
	private Container filePane;
	private JButton btnYes, btnNo;
	private JPanel pnlFile;
	private JLabel fileMessage;
	
	public OpenFileFrame(){
		makeFrame();
		setActionListeners();
		setPanel();
		setLocation();
		frmFile.setResizable(false);
		frmFile.setVisible(true);
	}
	
	
	/**
	 * Creates objects required in the "Create Events" frame
	 */
	@Override
	public void makeFrame() {
		frmFile = new JFrame (); //Create frame
		frmFile.setSize(300, 150);
		filePane = frmFile.getContentPane(); //Get content pane
		filePane.setLayout(null); //Apply null layout
		frmFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnYes = new JButton("Yes");
		btnNo = new JButton("No");
		fileMessage = new JLabel("Continue from last operation?");
		pnlFile =  new JPanel(null);
		pnlFile.setBorder(BorderFactory.createTitledBorder(""));		
	}

	/**
	 * sets action listeners to the buttons
	 */
	@Override
	public void setActionListeners() {
		btnYes.addActionListener(new btnYes_Action());
		btnNo.addActionListener(new btnNo_Action());
	}

	/**
	 * Assigns the objects the panel
	 */
	@Override
	public void setPanel() {
		filePane.add(pnlFile);
		pnlFile.add(fileMessage);
		pnlFile.add(btnYes);
		pnlFile.add(btnNo);
		
	}

	/**
	 * Sets the location of all objects of this frame
	 */
	@Override
	public void setLocation() {
		pnlFile.setBounds(0, 0, 290, 120);
		fileMessage.setBounds(50, 30, 1000, 30);
		btnYes.setBounds(30, 70, 100, 25);
		btnNo.setBounds(150, 70, 100, 25);
		
		frmFile.setLocation(575, 200);
	}
	
	/*
	 * Opens the events in "events.txt" if yes is clicked
	 */
	public class btnYes_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			FileReadWrite frw = new FileReadWrite();
			MainFrame m = new MainFrame(new Date(), frw.readFromFile("events"));
			frmFile.setVisible(false);
		}
	}
	
	/**
	 * Makes a new instance of the calendar when no is clicked
	 *
	 */
	public class btnNo_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			MainFrame m = new MainFrame();
			frmFile.setVisible(false);
		}
	}
}
