package logic;
/**
 * Used to store and open events from a txt
 * TODO: modify the catch statements
 * TODO: BufferReader
 *  2017-04-03 11:24 PM
 *  Change log:
 *  	Changed Scanner to BufferedReader
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class FileReadWrite {
	private EventCollection eventColl;
	
	/**
	 * Constructors:
	 * Takes an EventCollection as an argument if class is intended to write to file
	 * Creates a new EventCollection as an argument if class is intended to read from a file
	 */
	public FileReadWrite(){
		this.eventColl = new EventCollection();
	}
	public FileReadWrite(EventCollection eventColl){
		this.eventColl = new EventCollection(eventColl);
	}
	
	/**
	 * Counts the number of events that occur in a single day.
	 * The events that occur during the same day are stored in the same line
	 * @param line 
	 * @return num of events
	 */
	public int countEvents(String line){
		int count = 0;
		int inx = line.indexOf("|");
		// find events
		while(inx < line.length() - 1){
			count++;
			int inx2 = line.indexOf("|", inx + 1);
			inx = inx2;
		}
		return count;
	}
	
	/**
	 * Puts all the information of the events into a single string ArrayList
	 * @return the string ArrayList containing all the events
	 */
	public ArrayList<String> packEvents(){
		ArrayList <String> toWrite = new ArrayList <String>();
		Set keys = eventColl.getAllDates();
		Iterator iter = keys.iterator();
		while(iter.hasNext()){
			Date currDate = (Date)iter.next();
			String tempStr = currDate.getDateInString() + "|";
			ArrayList<CalendarEvents> event = eventColl.getEvents(currDate);
			
			for(CalendarEvents temp: event){
				String name = temp.getName();
				String loc = temp.getLocation();
				String time = temp.getTime();
				tempStr = String.format("%s%s %s %s|", tempStr, name, loc, time);
			}
			toWrite.add(tempStr);
		}
		return toWrite;
	}
	
	/**
	 * Parses the ArrayList and puts the correct information in EventsCollection
	 * @param toParse
	 */
	public void parseEvents(ArrayList<String> toParse){
		for(String temp : toParse){
			// find date
			int inx = temp.indexOf("|");
			Date date = new Date(temp.substring(0, inx));
			int inxSpc = inx + 1;
			// find events
			for(int eventCount = 0; eventCount < countEvents(temp); eventCount++){
				String name = "";
				String loc = "";
				String time = "";
				
				for(int i = 0; i < 3; i++){
					int inxSpc2 = temp.indexOf(" ", inxSpc);
					if(i == 0)
						name = temp.substring(inxSpc, inxSpc2);
					else if(i == 1)
						loc = temp.substring(inxSpc, inxSpc2);
					else{
						inxSpc2 = temp.indexOf("|", inxSpc);
						time = temp.substring(inxSpc, inxSpc2);
					}	
					inxSpc = inxSpc2 + 1;
				}
				CalendarEvents event = new CalendarEvents(name);
				event.setLocation(loc);
				event.setTime(time);
				eventColl.setEvent(date, event);
			}
		}
	}
	
	/**
	 * Writes the string compiled in packEvents() to the filename specified in the argument
	 * @param fileName
	 */
	public void saveToFile(String fileName){
		FileWriter fw;
		try {
			fw = new FileWriter(new File(fileName + ".txt"));
			for(String temp: packEvents()){
				fw.write(temp.toString());
				fw.write(System.lineSeparator());
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads from specified fileName
	 * Stores each new line from the file to a new index of the ArrayList
	 * @param fileName
	 * @return toRead ArrayList
	 */
	public EventCollection readFromFile(String fileName){
		ArrayList <String> toRead = new ArrayList<String>();
		BufferedReader inputStream = null;
		try {
			String line;
			inputStream = new BufferedReader(new FileReader(fileName + ".txt"));
			while((line = inputStream.readLine()) != null){
				toRead.add(line);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		parseEvents(toRead);
		return eventColl;
	}
}
